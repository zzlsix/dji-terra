package pojo;

import lombok.Data;

/**
 * 产出文件封装
 */
@Data
public class File {
    /**
     * 文件 uuid
     */
    private String uuid;
    /**
     * 下载链接
     */
    private String url;
    /**
     * 文件名
     */
    private String name;
    public File() {}
    public File(String uuid, String url, String name) {
        this.uuid = uuid;
        this.url = url;
        this.name = name;
    }
}
