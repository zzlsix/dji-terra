package file;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import lombok.extern.slf4j.Slf4j;
import pojo.File;
import pojo.OutputResource;
import request.DjiRequest;

import java.util.List;

/**
 * 管理job的结果文件
 */
@Slf4j
public class FileManager {
    /**
     * 获取作业产出资源信息
     */
    public static OutputResource getOutputResource(String outputResourceUuid) {
        log.info("获取产出，outputResourceUuid:{}", outputResourceUuid);
        String url = DjiConfig.URI_OUTPUT_RESOURCE.replace("{outputResourceUuid}", outputResourceUuid);
        return JSONUtil.toBean((JSONObject) DjiRequest.access(url, "", HttpMethod.GET), OutputResource.class);
    }

    /**
     * 下载文件
     */
    public static void downloadFiles(OutputResource outputResource) {
        log.info("下载文件，outputResource:{}", outputResource);
        List<String> fileUuids = outputResource.getFileUuids();
        for (String fileUuid : fileUuids) {
            String url = DjiConfig.URI_GET_FILE.replace("{fileUuid}", fileUuid);
            File file = JSONUtil.toBean((JSONObject) DjiRequest.access(url, "", HttpMethod.GET), File.class);
            String storePath = DjiConfig.RESULT_STORE_PATH + file.getName() ;
            HttpUtil.downloadFile(file.getUrl(), storePath);
        }
    }

    public static void main(String[] args) {
        OutputResource outputResource = getOutputResource("709b1390-69ea-43fb-93de-005aa34f7066");
        downloadFiles(outputResource);
    }
}
