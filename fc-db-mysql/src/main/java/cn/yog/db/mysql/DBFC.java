package cn.yog.db.mysql;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.FunctionInitializer;
import com.aliyun.fc.runtime.StreamRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class DBFC implements StreamRequestHandler, FunctionInitializer {

  private String host = System.getenv("MYSQL_HOST");
  private String port = System.getenv("MYSQL_PORT");
  private String dbName = System.getenv("MYSQL_DBNAME");
  private String user = System.getenv("MYSQL_USER");
  private String passwd = System.getenv("MYSQL_PASSWORD");

  private String url;

  {
    url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, dbName);
  }

  @Override
  public void initialize(Context context) {
    context.getLogger().info(context.getRequestId());
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
      throws IOException {

    FunctionComputeLogger logger = context.getLogger();

    String currentTime = "unavailable";

    try (Connection conn = getConnection()) {

      Statement stmt = conn.createStatement();
      ResultSet resultSet = stmt.executeQuery("SELECT NOW()");

      if (resultSet.next()) {
        currentTime = resultSet.getObject(1).toString();
      }

      logger.info("Successfully executed query.  Current: " + currentTime);

      String sql = "INSERT INTO user (id, name) VALUES(?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, "3");
      ps.setString(2, "du");

      ps.execute();

      resultSet = stmt.executeQuery("SELECT * FROM user");

      while (resultSet.next()) {
        logger.info("user: " + resultSet.getString(2));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    outputStream.write(currentTime.getBytes());
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, passwd);
  }
}
