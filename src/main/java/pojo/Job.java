package pojo;

import lombok.Data;

@Data
public class Job {
    /**
     * 时间格式为：RFC3339，示例：2006-01-02T15:04:05Z07:00，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String completedAt;
    /**
     * 时间格式为：RFC3339，示例：2006-01-02T15:04:05Z07:00
     */
    private String createdAt;
    /**
     * 状态消息，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String message;
    /**
     * 用户扩展信息
     */
    private String meta;
    /**
     * 作业名
     */
    private String name;
    /**
     * 原始资源 uuid，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String originResourceUuid;
    /**
     * 重建结果保存所在资源 uuid，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String outputResourceUuid;
    /**
     * 该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String parameters;
    /**
     * 进度，0-1，float64。该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private int percentage;
    /**
     * 总像素，float64。该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private int pixels;
    /**
     * 剩余时间，秒单位，int，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private int remainSeconds;
    /**
     * 时间格式为：RFC3339，示例：2006-01-02T15:04:05Z07:00，该字段不是必返回字段，譬如作业未启动，则此字段不返回
     */
    private String startedAt;
    /**
     * 作业状态，0：待开始，1：等待中，2：准备中，3：执行中，4：处理结果中，5：已停止，6：执行完成，7：执行失败，uint
     */
    private int status;
    /**
     * 作业类型，14 - 2D 重建，15 - 3D 重建，13 - LiDAR 重建
     */
    private int type;
    /**
     * 时间格式为：RFC3339，示例：2006-01-02T15:04:05Z07:00
     */
    private String updatedAt;
    /**
     * 作业 uuid
     */
    private String uuid;
}
