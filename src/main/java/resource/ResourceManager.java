package resource;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import lombok.extern.slf4j.Slf4j;
import param.AssociateFileParam;
import param.CreateResourceParam;
import pojo.SimpleResource;
import request.DjiRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理
 */
@Slf4j
public class ResourceManager {
    /**
     * 创建资源
     */
    public static SimpleResource createResource(String resourceName) {
        CreateResourceParam param = new CreateResourceParam(resourceName);
        String paramStr = JSONUtil.toJsonStr(param);
        SimpleResource simpleResource = JSONUtil.toBean((JSONObject) DjiRequest.access(DjiConfig.URI_RESOURCE, paramStr, HttpMethod.POST), SimpleResource.class);
        log.info("创建resource:{}", simpleResource.getUuid());
        return simpleResource;
    }

    /**
     * 给指定的资源关联文件
     */
    public static void associateFile(String resourceUuid, String callbackParam) throws IOException {
        log.info("关联文件，resourceUuid:{},callbackParam:{}", resourceUuid, callbackParam);
        byte[] fileBytes = Files.readAllBytes(Paths.get("uploaded_files.json"));
        String filesJson = new String(fileBytes, StandardCharsets.UTF_8);
        List<AssociateFileParam.SingleFile> fileList = JSONUtil.toList(filesJson, AssociateFileParam.SingleFile.class);
        JSONArray filesArray = JSONUtil.parseArray(filesJson);

        // 批量处理的大小
        int batchSize = 50;
        List<AssociateFileParam.SingleFile> payloadList = new ArrayList<>();
        int totalFiles = filesArray.size();

        // 遍历所有文件，分批次处理
        for (int i = 0; i < totalFiles; i++) {
            // 添加当前文件到列表
            payloadList.add(fileList.get(i));

            // 达到批次大小时发送请求
            if (payloadList.size() >= batchSize || i == totalFiles - 1) {
                // 构建请求体
                AssociateFileParam associateFileParam = new AssociateFileParam(resourceUuid, callbackParam, payloadList);
                String payload = JSONUtil.toJsonStr(associateFileParam);

                // 发送请求
                DjiRequest.access(DjiConfig.URI_ASSOCIATE_FILES, payload, HttpMethod.POST);

                // 清空 payloadList 准备下一个批次
                payloadList.clear();
            }
        }
    }

    public static void main(String[] args) {
        // 1860ae24-b076-4340-86cb-f29d2d6bb4a2
        // callback ek1zZ3NpZTg5ZXRDVjA3WjBnYm1pMDlIRVI0S0JVSzMzMXplanNLU2hqVEdlSW5TeTNTM0w4ZzROV1FuMVk3b28xUUV2Ty9OcmJkTkN4Q0JMWnM5ZjJXZjVnVTZQN0xPenpFWHJmaWk5T243T3A0WUhMZmpsUXVmcXlta0JsVm82cTlXWDkvVWpIdmNkZkhwQ0VTa2krN2ttcThiWGJPL0NLUklsVVJXblM4MkdLOGRhRU1MZVBYazhuMGtEMVZM
        // createResource();
        try {
            associateFile("1860ae24-b076-4340-86cb-f29d2d6bb4a2", "ek1zZ3NpZTg5ZXRDVjA3WjBnYm1pMDlIRVI0S0JVSzMzMXplanNLU2hqVEdlSW5TeTNTM0w4ZzROV1FuMVk3b28xUUV2Ty9OcmJkTkN4Q0JMWnM5ZjJXZjVnVTZQN0xPenpFWHJmaWk5T243T3A0WUhMZmpsUXVmcXlta0JsVm82cTlXWDkvVWpIdmNkZkhwQ0VTa2krN2ttcThiWGJPL0NLUklsVVJXblM4MkdLOGRhRU1MZVBYazhuMGtEMVZM");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
