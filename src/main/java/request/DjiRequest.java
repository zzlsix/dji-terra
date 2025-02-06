package request;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

import static java.security.MessageDigest.getInstance;

/**
 * 专门用于发送请求
 */
@Slf4j
public class DjiRequest {
    // 主域名，仅作简化拼接
    public static final String HOST = "https://openapi-cn.dji.com";

    /**
     * 发送请求
     * @param URI uri
     * @param payload 请求负载，如 post 请求体
     * @param httpMethod 请求类型{@link HttpMethod}
     * @return 请求成功仅返回 data 部分
     */
    public static Object access(String URI, String payload, HttpMethod httpMethod) {
        try {
            String url = HOST + URI;
            String date = getFormattedDate();
            String digest = getDigest(payload);
            String requestSignature = calculateSignature(date, httpMethod.toString().toLowerCase(), URI, digest);
            HttpRequest request = null;
            // delete和post请求还未测试 根据需求进行添加
            switch (httpMethod) {
                case GET:
                    request = HttpRequest.get(url);
                    break;
                case POST:
                    request = HttpRequest.post(url);
                    break;
            }
            request.header("Date", date)
                    .header("Digest", "SHA-256=" + digest)
                    .header("Authorization", "hmac username=\"" + DjiConfig.DJI_APP_KEY + "\", algorithm=\"hmac-sha256\", headers=\"date @request-target digest\", signature=\"" + requestSignature + "\"")
                    .header("Content-Type", "application/json;charset=UTF-8");
            if (!payload.isEmpty()) {
                request.body(payload);
            }
            try (HttpResponse response = request.execute()) {
                if (response.isOk()) {
                    log.info(response.body());
                    return JSONUtil.parseObj(response.body()).get("data");
                } else {
                    log.info(response.toString());
                    return null;
                }
            }
        } catch (Exception e) {
            log.error("请求失败", e);
            return null;
        }
    }

    private static String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                .withZone(ZoneId.of("GMT"));
        return formatter.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }

    private static String getDigest(String data) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(getInstance("SHA-256").digest(data.getBytes(StandardCharsets.UTF_8)));
    }

    private static String calculateSignature(String date, String method, String uri, String digest) throws NoSuchAlgorithmException, InvalidKeyException {
        String content = "date: " + date + "\n@request-target: " + method + " " + uri + "\ndigest: SHA-256=" + digest;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(new SecretKeySpec(DjiConfig.DJI_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return Base64.getEncoder().encodeToString(hmacSha256.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }
}