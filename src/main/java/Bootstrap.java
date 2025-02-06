import file.FileManager;
import job.JobManager;
import oss.Uploader;
import pojo.*;
import resource.ResourceManager;

import java.io.IOException;

/**
 * 快速启动
 */
public class Bootstrap {

    public static void main(String[] args) {
        // 获取token
        Token token = Token.buildToken();
        // 准备素材
        Uploader.upload(token);
        // 创建resource
        SimpleResource simpleResource = ResourceManager.createResource("<替换成resource name>");
        try {
            // 关联文件
            ResourceManager.associateFile(simpleResource.getUuid(), token.getCallbackParam());
            // 创建job
            SimpleJob simpleJob = JobManager.creatJob("<替换成job name>");
            // 启动job
            JobManager.start3dJob(simpleResource.getUuid(), simpleJob.getUuid());
            // 注意任务启动后立刻查询无法获取结果，先简单等待一分钟，有必要后续再优化
            Thread.sleep(60 * 1000);
            // 查询job状态
            Job job = JobManager.getJobStatus(simpleResource.getUuid());
            // 下载计算结果
            OutputResource outputResource = FileManager.getOutputResource(job.getOutputResourceUuid());
            FileManager.downloadFiles(outputResource);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
