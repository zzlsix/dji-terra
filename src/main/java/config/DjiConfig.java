package config;

/**
 * 大疆智图api固定配置，根据需要更改
 */
public class DjiConfig {
    /************************************* 需要更改的配置 *************************************/
    // 大疆智图API的APP Key，在控制台获取
    // https://developer.dji.com/cn/terra-api-console/detail/
    public static final String DJI_APP_KEY = "WrNk_fQmf4pMdNFakdAHk";
    // 大疆智图API的Secret Key，在控制台获取
    public static final String DJI_SECRET_KEY = "8VjhyIoiu6TLIFEr087LOqBodFzzvfWM";
    // 待上传的图片地址，自行下载
    // https://terra-1-g.djicdn.com/71a7d383e71a4fb8887a310eb746b47f/terra-cloud-api/v1.0/Three-Dimension-3D.zip
    public static final String FILE_PATH = "C:\\Users\\78765\\Desktop\\三维-3D\\3cm-三维影像-3D-70pic";
    // 结果保存地址，本地地址
    public static final String RESULT_STORE_PATH = "D:\\Idea Projects\\uav\\result\\";

    /************************************* 下面的配置不需要更改 *************************************/
    // 获取Token
    public static final String URI_TOKEN = "/terra-rescon-be/v2/store/obtain_token";
    // 创建resource
    public static final String URI_RESOURCE = "/terra-rescon-be/v2/resources";
    // 关联文件
    public static final String URI_ASSOCIATE_FILES = "/terra-rescon-be/v2/store/upload_callback";
    // 创建job
    public static final String URI_CREAT_JOB = "/terra-rescon-be/v2/jobs";
    // 启动job
    public static final String URI_START_JOB = "/terra-rescon-be/v2/jobs/{jobUuid}/start";
    // 查询job状态
    public static final String URI_GET_JOB = "/terra-rescon-be/v2/jobs/{jobUuid}";
    // 获取output resource
    public static final String URI_OUTPUT_RESOURCE = "/terra-rescon-be/v2/resources/{outputResourceUuid}";
    // 获取单个file信息
    public static final String URI_GET_FILE = "/terra-rescon-be/v2/files/{fileUuid}";


    // 阿里云oss端点
    public static final String ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";

}
