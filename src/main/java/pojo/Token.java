package pojo;

public static class Token {

        @JsonProperty("cloudName")
        private String cloudName;

        @JsonProperty("accessKeyID")
        private String accessKeyID;

        @JsonProperty("secretAccessKey")
        private String secretAccessKey;

        @JsonProperty("sessionToken")
        private String sessionToken;

        @JsonProperty("region")
        private String region;

        @JsonProperty("cloudBucketName")
        private String cloudBucketName;

        @JsonProperty("callbackParam")
        private String callbackParam;

        @JsonProperty("storePath")
        private String storePath;

        @JsonProperty("expireTime")
        private long expireTime;