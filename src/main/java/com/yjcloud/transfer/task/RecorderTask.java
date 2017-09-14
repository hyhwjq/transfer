package com.yjcloud.transfer.task;

import com.yjcloud.transfer.util.CpuUsage;
import com.yjcloud.transfer.util.MemUsage;
import com.yjcloud.transfer.util.config.PropertyConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.DecimalFormat;

/**
 * Created by hhc on 17/8/31.
 */
@Component
public class RecorderTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(RecorderTask.class);
    private static final DecimalFormat fm = new DecimalFormat("##0.00");

    private int recordInterval = 10 * 1000;
    private boolean running = true;

    @PostConstruct
    public void init() {
        if (!PropertyConfigurer.containsKey("record.interval")){
            return;
        }
        recordInterval = Integer.valueOf(PropertyConfigurer.getProperty("record.interval", "10000"));
        LOG.info("record interval: {}", recordInterval);
        Thread worker = new Thread(this);
        worker.start();
        running = true;
    }

    @PreDestroy
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        LOG.info("RecorderTask started.");
        while (running) {
            LOG.info("memory usage: {} %", fm.format(MemUsage.getInstance().get() * 100));
            LOG.info("cpu usage: {} %", fm.format(CpuUsage.getInstance().get() * 100));

            try {
                Thread.sleep(recordInterval);
            } catch (InterruptedException e) {
            }
        }
        LOG.info("RecorderTask exited loop!");

    }

}
