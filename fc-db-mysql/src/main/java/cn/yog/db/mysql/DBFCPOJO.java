package cn.yog.db.mysql;

import cn.yog.db.mysql.bean.QueryRequest;
import cn.yog.db.mysql.bean.QueryResponse;
import cn.yog.db.mysql.config.FcConfig;
import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionComputeLogger;
import com.aliyun.fc.runtime.PojoRequestHandler;

import java.sql.*;

public class DBFCPOJO implements PojoRequestHandler<QueryRequest, QueryResponse> {

  private FcConfig config;

  public DBFCPOJO() {
    config = new FcConfig();
  }

  @Override
  public QueryResponse handleRequest(QueryRequest input, Context context) {
    return this.queryUsers(input, context);
  }

  private QueryResponse queryUsers(QueryRequest input, Context context) {
    FunctionComputeLogger logger = context.getLogger();
    QueryResponse response = new QueryResponse();

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
      ps.setString(1, input.getBody());
      ps.setString(2, input.getBody());

      ps.execute();

      resultSet = stmt.executeQuery("SELECT * FROM users");

      while (resultSet.next()) {
        logger.info("user: " + resultSet.getString(2));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    response.setBody(currentTime);
    return response;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPasswd());
  }
}
