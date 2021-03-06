package cn.yog.db.mysql;

import cn.yog.db.mysql.config.FcConfig;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.StreamRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;

public class DBFC implements StreamRequestHandler {

  private FcConfig config;

  public DBFC() {
    config = new FcConfig();
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
      throws IOException {
    context.getLogger().info("start log in it");
    this.queryUsers(inputStream, outputStream, context);
  }

  private void queryUsers(InputStream inputStream, OutputStream outputStream, Context context)
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

      String sql = "REPLACE INTO users (id, name) VALUES(?, ?)";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, "3");
      ps.setString(2, "du");

      ps.execute();

      resultSet = stmt.executeQuery("SELECT * FROM users");

      while (resultSet.next()) {
        logger.info("user: " + resultSet.getString(2));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    outputStream.write(currentTime.getBytes());
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPasswd());
  }
}
