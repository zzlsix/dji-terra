package pojo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import lombok.Data;
import request.DjiRequest;

@Data
public class Token {
    /**
     * AWS_S3、 ALI_OSS、 AZURE_BLOB 暂时只支持 AWS_S3 与 ALI_OSS
     */
    private String cloudName;

    private String accessKeyID;

    private String secretAccessKey;

    private String sessionToken;

    private String region;

    private String cloudBucketName;

    private String callbackParam;

    /**
     * "xxxxxxxxx/{fileName}"，将 "{fileName}" 替换为文件名
     */
    private String storePath;
    /**
     * 过期时间，秒单位时间戳，int64
     */
    private long expireTime;

    public void setStorePath(String storePath) {
        // 去掉 storePath 中的 {fileName}
        this.storePath = storePath.substring(0, storePath.length() - 10);
    }

    public static Token buildToken() {
        JSONObject data = (JSONObject) DjiRequest.access(DjiConfig.URI_TOKEN, "", HttpMethod.POST);
        return JSONUtil.toBean(data, Token.class);
    }
}