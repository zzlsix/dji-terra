package param;

import lombok.Data;
import lombok.Getter;

@Data
public class StartJobParam {
    private String parameters;
    private String resourceUuid;
    private Integer type;
    public StartJobParam() {}
    public StartJobParam(String parameters, String resourceUuid, Integer type) {
        this.parameters = parameters;
        this.resourceUuid = resourceUuid;
        this.type = type;
    }

    @Getter
    public enum JobType {

       _2D_REBUILD(14),
       _3D_REBUILD(15),
       LIDAR_REBUILD(13);

       private final int value;

       JobType(int value) {
           this.value = value;
       }
    }
}
