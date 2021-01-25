package cn.yog.db.mysql.bean;

import java.util.List;
import java.util.Map;

/**
 * {@link QueryRequest}
 *
 * @author <a href="mailto:matthew.miao@yunlsp.com">Matthew.miao</a>
 * @version ${project.version} - 2021/1/25
 */
public class QueryRequest {

  private String path;
  private String httpMethod;
  private Map<String,String> headers;
  private Map<String,String> queryParameters;
  private Map<String,String> pathParameters;
  private String body;
  private String isBase64Encoded = "false";

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public Map<String, String> getQueryParameters() {
    return queryParameters;
  }

  public void setQueryParameters(Map<String, String> queryParameters) {
    this.queryParameters = queryParameters;
  }

  public Map<String, String> getPathParameters() {
    return pathParameters;
  }

  public void setPathParameters(Map<String, String> pathParameters) {
    this.pathParameters = pathParameters;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getIsBase64Encoded() {
    return isBase64Encoded;
  }

  public void setIsBase64Encoded(String isBase64Encoded) {
    this.isBase64Encoded = isBase64Encoded;
  }
}
