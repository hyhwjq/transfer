package com.yjcloud.transfer.util;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hhc on 17/9/1.
 */
public class MemUsage {

    private static Logger LOG = LoggerFactory.getLogger(MemUsage.class);
    private static MemUsage INSTANCE = new MemUsage();

    private MemUsage() {

    }

    public static MemUsage getInstance() {
        return INSTANCE;
    }

    /**
     * Purpose:采集内存使用率
     *
     * @return float, 内存使用率, 小于1
     */
    public float get() {
        float memUsage = 0.0f;
        Process pro = null;
        BufferedReader in;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/meminfo";
            pro = r.exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            int count = 0;
            long totalMem = 0, freeMem = 0;
            try {
                while ((line = in.readLine()) != null) {
                    String[] memInfo = line.split("\\s+");
                    if (memInfo[0].startsWith("MemTotal")) {
                        totalMem = Long.parseLong(memInfo[1]);
                    }
                    if (memInfo[0].startsWith("MemFree")) {
                        freeMem = Long.parseLong(memInfo[1]);
                    }
                    memUsage = 1 - (float) freeMem / (float) totalMem;
                    if (++count == 2) {
                        break;
                    }
                }
            } finally {
                IOUtils.closeQuietly(in);
            }
        } catch (IOException e) {
            LOG.error("capture memUsage error: {}", e.getMessage(), e);
        } finally {
            if (pro != null) {
                pro.destroy();
            }
        }
        return memUsage;
    }

}
