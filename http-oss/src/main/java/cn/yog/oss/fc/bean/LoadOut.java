package cn.yog.oss.fc.bean;

/**
 * {@link LoadOut}
 *
 * @author <a href="mailto:matthew.miao@yunlsp.com">Matthew.miao</a>
 * @version ${project.version} - 2021/1/22
 */
public class LoadOut {

  private String fileName;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public LoadOut(String fileName) {
    this.fileName = fileName;
  }
}
