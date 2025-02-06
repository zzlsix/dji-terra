package pojo;

import lombok.Data;

@Data
public class SimpleJob {
    private String uuid;
    public SimpleJob() {}
    public SimpleJob(String uuid) {
        this.uuid = uuid;
    }
}
