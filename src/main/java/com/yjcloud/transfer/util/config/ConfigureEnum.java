package com.yjcloud.transfer.util.config;

/**
 * Created by hhc on 17/9/13.
 */
public enum ConfigureEnum {
    SOURCE_MONGO_URL("source.mongo.url", "源mongo的url"),
    SOURCE_MONGO_USERNAME("source.mongo.username", "源mongo的用户名"),
    SOURCE_MONGO_PASSWORD("source.mongo.password", "源mongo的密码"),
    SOURCE_MONGO_DB("source.mongo.db", "源mongo的db"),
    SOURCE_MONGO_COLLECTIONS("source.mongo.collections", "源mongo的collections"),

    SOURCE_MONGO_GRID_FS_COLLECTIONS("source.mongo.grid.fs.collections", "源mongo gridFS的collections"),

    DESTINATION_MONGO_URL("dest.mongo.url", "目的mongo的url"),
    DESTINATION_MONGO_USERNAME("dest.mongo.username", "目的mongo的用户名"),
    DESTINATION_MONGO_PASSWORD("dest.mongo.password", "目的mongo的密码"),
    DESTINATION_MONGO_DB("dest.mongo.db", "目的mongo的db"),

    DESTINATION_OSS_ENDPOINT("dest.oss.endpoint", "目的oss的endpoint"),
    DESTINATION_OSS_ACCESS_KEY_ID("dest.oss.accessKeyId", "目的oss的accessKeyId"),
    DESTINATION_OSS_ACCESS_KEY_SECRET("dest.oss.accessKeySecret", "目的oss的accessKeySecret"),
    DESTINATION_OSS_BUCKET_NAME("dest.oss.bucket.name", "目的oss的bucketName"),
    DESTINATION_OSS_SUPPORT_CNAME("dest.oss.support.cname", "目的oss是否支持cname"),
    DESTINATION_OSS_FILE_SUFFIX("dest.oss.file.suffix", "追加的文件后缀"),

    RUN_MODE("run.mode", "运行模式"),
    RUN_MODE_MONGO_TO_MONGO("0", "mongo2mongo运行模式"),
    RUN_MODE_MONGO_GRID_FS_TO_OSS("1", "mongoGridFS2oss运行模式");

    ConfigureEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
