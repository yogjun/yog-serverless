package cn.yog.db.mysql.config;

/**
 * {@link FcConfig}
 *
 * @author <a href="mailto:matthew.miao@yunlsp.com">Matthew.miao</a>
 * @version ${project.version} - 2021/1/25
 */
public class FcConfig {
  private String host = System.getenv("MYSQL_HOST");
  private String port = System.getenv("MYSQL_PORT");
  private String dbName = System.getenv("MYSQL_DBNAME");
  private String user = System.getenv("MYSQL_USER");
  private String passwd = System.getenv("MYSQL_PASSWORD");
  private String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, dbName);

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getDbName() {
    return dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
