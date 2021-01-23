package cn.yog.oss.fc.bean;

import org.springframework.web.multipart.MultipartFile;

/**
 * {@link LoadIn}
 *
 * @author <a href="mailto:matthew.miao@yunlsp.com">Matthew.miao</a>
 * @version ${project.version} - 2021/1/22
 */
public class LoadIn {
  private MultipartFile file;
  private String name;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
