package com.xxxx.server.utils;

import com.roncoo.common.MyException;
import com.roncoo.fastdfs.ClientGlobal;
import com.roncoo.fastdfs.FileInfo;
import com.roncoo.fastdfs.StorageClient;
import com.roncoo.fastdfs.StorageServer;
import com.roncoo.fastdfs.TrackerClient;
import com.roncoo.fastdfs.TrackerServer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * FastDFS工具类
 *
 * @author lizongzai
 * @since 1.0.0
 */
public class FastDFSUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(FastDFSUtils.class);

  /**
   * 初始化客户端
   * ClientGlobal.init(filePath),读取配置文件,并初始化对应的属性
   */
  static {
    try {
      String filePath = new ClassPathResource("fdfs_client.conf").getFile().getAbsolutePath();
      ClientGlobal.init(filePath);
    } catch (Exception e) {
      LOGGER.error("初始化FastDFS失败!");
    }

  }

  /**
   * 生成TrackerServer服务器
   *
   * @return
   * @throws IOException
   */
  public static TrackerServer getTrackerServer() throws IOException {
    TrackerClient trackerClient = new TrackerClient();
    TrackerServer trackerServer = trackerClient.getTrackerServer();
    return trackerServer;
  }

  /**
   * 生成StorageClient客户端
   *
   * @return
   * @throws IOException
   */
  public static StorageClient getStorageClient() throws IOException {
    TrackerServer trackerServer = getTrackerServer();
    StorageClient storageClient = new StorageClient(trackerServer, null);
    return storageClient;
  }

  /**
   * 获取文件信息
   *
   * @param groupName
   * @param remoteFileName
   * @return
   */
  public static FileInfo getFileInfo(String groupName, String remoteFileName) {
    StorageClient storageClient = null;
    try {
      storageClient = getStorageClient();
      FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);
      return fileInfo;
    } catch (Exception e) {
      LOGGER.error("文件获取失败!");
    }
    return null;
  }

  /**
   * 文件删除
   *
   * @param groupName
   * @param remoteFileName
   */
  public void deleteFileInfo(String groupName, String remoteFileName) {
    StorageClient storageClient = null;
    try {
      storageClient = getStorageClient();
      storageClient.delete_file(groupName, remoteFileName);
    } catch (Exception e) {
      LOGGER.error("文件删除失败!");
    }
  }

  /**
   * 文件下载失败
   *
   * @param groupName
   * @param remoteFileName
   * @return
   */
  public static InputStream downFile(String groupName, String remoteFileName) {
    StorageClient storageClient = null;
    try {
      byte[] bytes = storageClient.download_file(groupName, remoteFileName);
      InputStream inputStream = new ByteArrayInputStream(bytes);
      return inputStream;
    } catch (Exception e) {
      LOGGER.error("文件下载失败", e.getMessage());
    }
    return null;
  }

  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  public static String[] uploadFile(MultipartFile file) {
    String filename = file.getOriginalFilename();
    System.out.println("文件名 = " + filename);

    String[] uploadResults = null;
    StorageClient storageClient = null;
    try {
      //上传文件: 第一个参数表示字节码,第二参数表示文件后缀名,第三个参数表示描述内容，但是这里为Null
      uploadResults = storageClient.upload_file(file.getBytes(),
          filename.substring(filename.lastIndexOf(".") + 1),
          null);
    } catch (Exception e) {
      LOGGER.error("上传文件失败", e.getMessage());
    }

    if (uploadResults == null && storageClient != null) {
      LOGGER.error("上传文件失败", storageClient.getErrorCode());
    }

    //上传成功返回groupName
    LOGGER.info(
        "upload file successfully!!!" + "group_name:" + uploadResults[0] + ", remoteFileName:" + " "
            + uploadResults[1]);
    return uploadResults;
  }

  /**
   * 获取文件路径
   *
   * @return
   */
  public static String getTrackerUrl() {
    TrackerClient trackerClient = new TrackerClient();
    TrackerServer trackerServer = null;
    StorageServer storageServer = null;
    try {
      trackerServer = trackerClient.getTrackerServer();
      storageServer = trackerClient.getStoreStorage(trackerServer);
    } catch (Exception e) {
      LOGGER.error("文件路径获取失败", e.getMessage());
    }
    return "http://" + storageServer.getInetSocketAddress().getHostString() + ":8888/";
  }

}
