package com.yjcloud.transfer.core.dumper;

import com.yjcloud.transfer.core.Dumper;
import com.yjcloud.transfer.core.adapter.OSSAdapter;
import com.yjcloud.transfer.entity.MessageDTO;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;

/**
 * Created by hhc on 17/9/13.
 */
public class OSSDumper extends OSSAdapter implements Dumper {

    @Override
    protected void runTask() {
        LOG.info("OSS dumper started.");

        while (isRunning()) {
            MessageDTO message = super.pull();
            byte[] bytes = message.getData();
            ByteArrayInputStream input = new ByteArrayInputStream(bytes);
            try {
                LOG.debug("upload file {} to oss", message.getName());
                super.ossClient.putObject(super.bucketName, message.getName(), input);
            } finally {
                IOUtils.closeQuietly(input);
            }
        }

        LOG.info("OSS dumper started.");
//        ossClient.shutdown();
    }

    @Override
    public void dump() {
        super.start();
    }

}
