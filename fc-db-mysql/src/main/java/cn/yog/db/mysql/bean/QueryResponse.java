package cn.yog.db.mysql.bean;

import java.util.List;

/**
 * {@link QueryResponse}
 *
 * @author <a href="mailto:matthew.miao@yunlsp.com">Matthew.miao</a>
 * @version ${project.version} - 2021/1/25
 */
public class QueryResponse {

  private String isBase64Encoded;
  private Integer statusCode = 200;
  private List<String> headers;
  private String body;

  public String getIsBase64Encoded() {
    return isBase64Encoded;
  }

  public void setIsBase64Encoded(String isBase64Encoded) {
    this.isBase64Encoded = isBase64Encoded;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public List<String> getHeaders() {
    return headers;
  }

  public void setHeaders(List<String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
