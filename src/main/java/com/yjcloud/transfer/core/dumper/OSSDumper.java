package com.yjcloud.transfer.core.dumper;

import com.yjcloud.transfer.core.DataDocker;
import com.yjcloud.transfer.core.Dumper;
import com.yjcloud.transfer.core.adapter.OSSAdapter;
import com.yjcloud.transfer.entity.MessageDTO;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;

/**
 * Created by hhc on 17/9/13.
 */
public class OSSDumper extends OSSAdapter implements Dumper {

    private DataDocker<MessageDTO> docker;

    public OSSDumper(DataDocker<MessageDTO> docker) {
        this.docker = docker;
    }

    @Override
    protected void runTask() {
        LOG.info("OSS dumper started.");

        while (isRunning()) {
            MessageDTO message = docker.pull();
            byte[] bytes = message.getData();
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            try {
                String name = message.getName();
                if (super.appendSuffix != null){
                    name += super.appendSuffix;
                }
                LOG.debug("upload file {} to oss", name);
                super.ossClient.putObject(super.bucketName, name, input);
            } finally {
                IOUtils.closeQuietly(input);
            }
        }

        LOG.info("OSS dumper completed.");
//        ossClient.shutdown();
    }

    @Override
    public void dump() {
        super.start();
    }

}
