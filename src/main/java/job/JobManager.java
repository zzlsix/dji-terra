package job;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import param.CreateJobParam;
import param.StartJobParam;
import pojo.Job;
import pojo.SimpleJob;
import request.DjiRequest;

public class JobBuilder {

    public static SimpleJob creatJob(String jobName) {
        CreateJobParam createJobParam = new CreateJobParam(jobName);
        return JSONUtil.toBean((JSONObject) DjiRequest.access(DjiConfig.URI_CREAT_JOB, JSONUtil.toJsonStr(createJobParam), HttpMethod.POST), SimpleJob.class);
    }

    public static void start3dJob(String resourceUuid, String jobUuid) {
        String url = DjiConfig.URI_START_JOB.replace("{jobUuid}", jobUuid);
        // 参数后续再封装
        String param = "{\"parameter\":{\"output_mesh\": true,\"generate_obj\": true,\"generate_b3dm\": true,\"generate_osgb\": true}}";
        StartJobParam startJobParam = new StartJobParam(param, resourceUuid, StartJobParam.JobType._3D_REBUILD.getValue());
        DjiRequest.access(url, JSONUtil.toJsonStr(startJobParam), HttpMethod.POST);
    }

    public static Job getJobStatus(String jobUuid) {
        String url = DjiConfig.URI_GET_JOB.replace("{jobUuid}", jobUuid);
        return JSONUtil.toBean((JSONObject) DjiRequest.access(url, "", HttpMethod.GET), Job.class);
    }

    public static void main(String[] args) {
        // job uuid 052efcc7-1f4f-4410-9055-6aee2caae1d6
        // creatJob("zzl20250206");
        // start3dJob("1860ae24-b076-4340-86cb-f29d2d6bb4a2", "052efcc7-1f4f-4410-9055-6aee2caae1d6");
        System.out.println(getJobStatus("052efcc7-1f4f-4410-9055-6aee2caae1d6"));
        // out put resource 709b1390-69ea-43fb-93de-005aa34f7066
    }
}
