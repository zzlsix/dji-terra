package param;

import lombok.Data;

import java.util.List;

@Data
public class AssociateFileParam {
    /**
     * 选择要关联到的资源 uuid
     */
    private String resourceUuid;
    /**
     * 回调参数
     */
    private String callbackParam;
    private List<SingleFile> files;
    public AssociateFileParam() {}
    public AssociateFileParam(String resourceUuid, String callbackParam, List<SingleFile> files) {
        this.resourceUuid = resourceUuid;
        this.callbackParam = callbackParam;
        this.files = files;
    }

    @Data
    public static class SingleFile {
        /**
         * 文件名，与上传时替换 {fileName} 的值一致
         */
        private String name;
        /**
         * 文件特征信息，用于校验本地文件是否已上传
         */
        private String checksum;
        /**
         * 存储平台返回的特征信息
         */
        private String etag;

        public SingleFile() {}
        public SingleFile(String name, String checksum, String etag) {
            this.name = name;
            this.checksum = checksum;
            this.etag = etag;
        }
    }
}
