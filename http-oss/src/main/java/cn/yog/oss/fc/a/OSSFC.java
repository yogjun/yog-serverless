package cn.yog.oss.fc.a;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.Credentials;
import com.aliyun.fc.runtime.PojoRequestHandler;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class OSSFC implements PojoRequestHandler<MultipartFile, String> {

  private OSSClient oss;
  private UploadConfig uploadConfig = new UploadConfig();
  /**
   * @param file 文件
   * @param context 阿里云上下文
   * @return
   */
  @Override
  public String handleRequest(MultipartFile file, Context context) {
    Credentials creds = context.getExecutionCredentials();
    oss =
        new OSSClient(
            uploadConfig.getEndpoint(),
            creds.getAccessKeyId(),
            creds.getAccessKeySecret(),
            creds.getSecurityToken());
    //    client.putObject(bucketName, "my-object", new ByteArrayInputStream(new
    // String("hello").getBytes()));
    //    outputStream.write(new String("done").getBytes());
    //    String message = "Hello, " + request.getFirstName() + " " + request.getLastName();
    return upload(file);
  }

  /**
   * 上传文件---去除URL中的？后的时间戳
   *
   * @param file 文件
   * @return 文件的访问地址
   */
  public String upload(MultipartFile file) {
    createBucket(uploadConfig.getBucketName());
    String fileName = uploadFile(file, uploadConfig.getBucketName(), uploadConfig.getDir());
    String fileOssUrl = getImgUrl(fileName, uploadConfig.getBucketName(), uploadConfig.getDir());
    int firstChar = fileOssUrl.indexOf("?");
    if (firstChar > 0) {
      fileOssUrl = fileOssUrl.substring(0, firstChar);
    }
    return fileOssUrl;
  }

  /** 当Bucket不存在时创建Bucket */
  private void createBucket(String bucketName) {
    try {
      if (!oss.doesBucketExist(bucketName)) {
        oss.createBucket(bucketName);
      }
    } catch (Exception e) {
      //      log.error("{}",
      // "创建Bucket失败,请核对Bucket名称(规则：只能包含小写字母、数字和短横线，必须以小写字母和数字开头和结尾，长度在3-63之间)");
    }
  }

  /**
   * 上传到OSS服务器 如果同名文件会覆盖服务器上的
   *
   * @param file 文件
   * @param bucketName 桶名
   * @param dir 文件夹
   * @return 文件的访问地址
   */
  private String uploadFile(MultipartFile file, String bucketName, String dir) {
    String fileName =
        String.format(
            "%s.%s",
            UUID.randomUUID().toString(), FilenameUtils.getExtension(file.getOriginalFilename()));
    try (InputStream inputStream = file.getInputStream()) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(inputStream.available());
      objectMetadata.setCacheControl("no-cache");
      objectMetadata.setHeader("Pragma", "no-cache");
      objectMetadata.setContentType(
          getContentType(FilenameUtils.getExtension("." + file.getOriginalFilename())));
      objectMetadata.setContentDisposition("inline;filename=" + fileName);
      PutObjectResult putResult =
          oss.putObject(bucketName, dir + fileName, inputStream, objectMetadata);
    } catch (Exception e) {
      //      log.error("{}", "上传文件失败");
      //      throw new GlobalException(ResultCode.ERROR.getCode(),e.getMessage());
    }
    return fileName;
  }

  /**
   * 获得文件路径
   *
   * @param fileUrl 文件的URL
   * @param bucketName 桶名
   * @param dir 文件夹
   * @return 文件的路径
   */
  private String getImgUrl(String fileUrl, String bucketName, String dir) {
    if (StringUtils.isEmpty(fileUrl)) {
      //      log.error("{}", "文件地址为空");
      throw new RuntimeException("文件地址为空");
    }
    String[] split = fileUrl.split("/");

    URL url =
        oss.generatePresignedUrl(
            bucketName, dir + split[split.length - 1], DateUtils.addDays(new Date(), 365 * 10));
    if (url == null) {
      //      log.error("{}", "获取oss文件URL失败");
    }
    return url.toString();
  }

  /**
   * 判断OSS服务文件上传时文件的contentType
   *
   * @param filenameExtension 文件后缀
   * @return 后缀
   */
  private String getContentType(String filenameExtension) {
    if (filenameExtension.equalsIgnoreCase("bmp")) {
      return "image/bmp";
    }
    if (filenameExtension.equalsIgnoreCase("gif")) {
      return "image/gif";
    }
    if (filenameExtension.equalsIgnoreCase("jpeg")
        || filenameExtension.equalsIgnoreCase("jpg")
        || filenameExtension.equalsIgnoreCase("png")) {
      return "image/jpg";
    }
    if (filenameExtension.equalsIgnoreCase("html")) {
      return "text/html";
    }
    if (filenameExtension.equalsIgnoreCase("txt")) {
      return "text/plain";
    }
    if (filenameExtension.equalsIgnoreCase("vsd")) {
      return "application/vnd.visio";
    }
    if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
      return "application/vnd.ms-powerpoint";
    }
    if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
      return "application/msword";
    }
    if (filenameExtension.equalsIgnoreCase("xml")) {
      return "text/xml";
    }
    return "image/jpeg";
  }
}
