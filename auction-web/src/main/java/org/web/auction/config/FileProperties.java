package org.web.auction.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component  //显式加上，spring容器托管该实例
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String staticAccessPath;  //http映射路径
    private String uploadFileFolder;  // 文件存放的物理路径

    public String getStaticAccessPath() {
        return staticAccessPath;
    }

    public void setStaticAccessPath(String staticAccessPath) {
        this.staticAccessPath = staticAccessPath;
    }

    public String getUploadFileFolder() {
        return uploadFileFolder;
    }

    public void setUploadFileFolder(String uploadFileFolder) {
        this.uploadFileFolder = uploadFileFolder;
    }
}
