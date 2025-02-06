package pojo;

import lombok.Data;

@Data
public class SimpleResource {
    private String uuid;
    public SimpleResource() {}
    public SimpleResource(String uuid) {
        this.uuid = uuid;
    }
}
