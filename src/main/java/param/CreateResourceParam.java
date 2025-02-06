package param;

import lombok.Data;

@Data
public class CreateResourceParam {
    private String name;
    /**
     * 只有{@code map}类型
     */
    private String type;
    public CreateResourceParam() {}
    public CreateResourceParam(String name) {
        this(name, "map");
    }
    public CreateResourceParam(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
