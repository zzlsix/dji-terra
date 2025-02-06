package job;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.HttpMethod;
import config.DjiConfig;
import lombok.extern.slf4j.Slf4j;
import param.CreateJobParam;
import param.StartJobParam;
import pojo.Job;
import pojo.SimpleJob;
import request.DjiRequest;

/**
 * 管理job
 */
@Slf4j
public class JobManager {

    /**
     * 创建 job
     */
    public static SimpleJob creatJob(String jobName) {
        CreateJobParam createJobParam = new CreateJobParam(jobName);
        SimpleJob simpleJob = JSONUtil.toBean((JSONObject) DjiRequest.access(DjiConfig.URI_CREAT_JOB, JSONUtil.toJsonStr(createJobParam), HttpMethod.POST), SimpleJob.class);
        log.info("创建作业，jobUuid:{}", simpleJob.getUuid());
        return simpleJob;
    }

    /**
     * 启动 3d 的 job，其他形式的job目前没有了解，有需要再扩展
     */
    public static void start3dJob(String resourceUuid, String jobUuid) {
        log.info("启动作业，resourceUuid:{},jobUuid:{}", resourceUuid, jobUuid);
        String url = DjiConfig.URI_START_JOB.replace("{jobUuid}", jobUuid);
        // 参数后续再封装
        String param = "{\"parameter\":{\"output_mesh\": true,\"generate_obj\": true,\"generate_b3dm\": true,\"generate_osgb\": true}}";
        StartJobParam startJobParam = new StartJobParam(param, resourceUuid, StartJobParam.JobType._3D_REBUILD.getValue());
        DjiRequest.access(url, JSONUtil.toJsonStr(startJobParam), HttpMethod.POST);
    }

    /**
     * 获取job信息
     */
    public static Job getJobStatus(String jobUuid) {
        log.info("查询作业详情，jobUuid:{}", jobUuid);
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
