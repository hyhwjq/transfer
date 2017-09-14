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
public class CpuUsage {

    private static Logger LOG = LoggerFactory.getLogger(CpuUsage.class);
    private static CpuUsage INSTANCE = new CpuUsage();

    private CpuUsage() {

    }

    public static CpuUsage getInstance() {
        return INSTANCE;
    }

    /**
     * Purpose:采集CPU使用率
     *
     * @return float, CPU使用率, 小于1
     */
    public float get() {
        float cpuUsage = 0;
        Process pro1 = null, pro2 = null;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/stat";
            //第一次采集CPU时间
            pro1 = r.exec(command);
            String line = null;
            long idleCpuTime1 = 0, totalCpuTime1 = 0;   //分别为系统启动后空闲的CPU时间和总的CPU时间
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("cpu")) {
                        line = line.trim();
                        String[] temp = line.split("\\s+");
                        idleCpuTime1 = Long.parseLong(temp[4]);
                        for (String s : temp) {
                            if (!s.equals("cpu")) {
                                totalCpuTime1 += Long.parseLong(s);
                            }
                        }
                        break;
                    }
                }
            } finally {
                IOUtils.closeQuietly(bufferedReader);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            //第二次采集CPU时间
            pro2 = r.exec(command);
            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            long idleCpuTime2 = 0, totalCpuTime2 = 0;   //分别为系统启动后空闲的CPU时间和总的CPU时间
            try {
                while ((line = bufferedReader1.readLine()) != null) {
                    if (line.startsWith("cpu")) {
                        line = line.trim();
                        String[] temp = line.split("\\s+");
                        idleCpuTime2 = Long.parseLong(temp[4]);
                        for (String s : temp) {
                            if (!s.equals("cpu")) {
                                totalCpuTime2 += Long.parseLong(s);
                            }
                        }
                        break;
                    }
                }
            } finally {
                IOUtils.closeQuietly(bufferedReader1);
            }
            if (idleCpuTime1 != 0 && totalCpuTime1 != 0 && idleCpuTime2 != 0 && totalCpuTime2 != 0) {
                cpuUsage = 1 - (float) (idleCpuTime2 - idleCpuTime1) / (float) (totalCpuTime2 - totalCpuTime1);
            }

        } catch (IOException e) {
            LOG.error("capture cpuUsage err", e);
        } finally {
            if (pro1 != null) {
                pro1.destroy();
            }
            if (pro2 != null) {
                pro2.destroy();
            }
        }
        return cpuUsage;
    }

}
