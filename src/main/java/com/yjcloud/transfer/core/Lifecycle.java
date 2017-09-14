package com.yjcloud.transfer.core;

/**
 * Created by hhc on 17/9/13.
 */
public interface Lifecycle {

    void init();

    void start();

    void destroy();

    boolean isRunning();

}
