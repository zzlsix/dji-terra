package param;

import lombok.Data;

@Data
public class CreateJobParam {
    private String name;
    public CreateJobParam() {
    }
    public CreateJobParam(String name) {
        this.name = name;
    }
}
