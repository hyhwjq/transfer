package com.yjcloud.transfer.core.adapter;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.yjcloud.transfer.core.Lifecycle;
import com.yjcloud.transfer.util.config.ConfigureEnum;
import com.yjcloud.transfer.util.config.PropertyConfigurer;

/**
 * Created by hhc on 17/9/13.
 */
public abstract class OSSAdapter extends AbstractAdapter implements Lifecycle, Runnable {
    private Thread worker;
    private volatile boolean running = true;

    protected OSSClient ossClient = null;
    protected String bucketName;
    protected String appendSuffix = null;

    protected abstract void runTask();

    @Override
    public void run() {
        this.runTask();
    }

    @Override
    public void start() {
        if (worker == null) {
            this.init();
            worker = new Thread(this);
        }
        worker.start();
        running = true;
    }

    @Override
    public void init() {
        String endpoint = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_ENDPOINT.getName());
        String accessKeyId = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_ACCESS_KEY_ID.getName());
        String accessKeySecret = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_ACCESS_KEY_SECRET.getName());
        this.appendSuffix = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_FILE_SUFFIX.getName(), null);
        this.bucketName = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_BUCKET_NAME.getName());

        if (PropertyConfigurer.containsKey(ConfigureEnum.DESTINATION_OSS_SUPPORT_CNAME.getName())) {
            String supportCname = PropertyConfigurer.getProperty(ConfigureEnum.DESTINATION_OSS_SUPPORT_CNAME.getName());
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret, new ClientConfiguration().setSupportCname(!"false".equals(supportCname)));
        } else {
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void destroy() {
        running = false;
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
