package oss;

import cn.hutool.json.JSONUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import config.DjiConfig;
import lombok.extern.slf4j.Slf4j;
import pojo.Token;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UploadFile {

    public static void upload(Token token) {
        doUploadFile(token.getAccessKeyID(), token.getSecretAccessKey(), token.getCloudBucketName(), token.getStorePath(), token.getSessionToken());
    }

    private static boolean doUploadFile(String accessKeyId, String secretAccessKey, String bucketName, String storePath, String token) {
        boolean success = false;
        OSS ossClient = new OSSClientBuilder().build(DjiConfig.ENDPOINT, accessKeyId, secretAccessKey, token);
        List<Map<String, String>> uploadedFiles = new ArrayList<>();

        File localFolder = new File(DjiConfig.FILE_PATH);
        try {
            Files.walk(localFolder.toPath())
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String relativePath = localFolder.toURI().relativize(path.toUri()).getPath();
                        String ossFilePath = storePath + relativePath.replace("\\", "/");
                        try {
                            PutObjectResult putResult = ossClient.putObject(bucketName, ossFilePath, path.toFile());
                            String etag = putResult.getETag();
                            System.out.println("Uploaded: " + path + " -> " + ossFilePath + ", etag: " + etag);

                            Map<String, String> fileInfo = new HashMap<>();
                            fileInfo.put("name", relativePath.replace("\\", "/"));
                            fileInfo.put("etag", etag);
                            fileInfo.put("checksum", etag);
                            uploadedFiles.add(fileInfo);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        if (!uploadedFiles.isEmpty()) {
            String json = JSONUtil.toJsonStr(uploadedFiles);
            // 将JSON字符串写入文件
            try {
                Files.write(Paths.get("uploaded_files.json"), json.getBytes());
                log.info("文件上传成功！");
                success = true;
            } catch (Exception e) {
                log.error("文件上传失败", e);
            }
        }
        ossClient.shutdown();
        return success;
    }
}