package com.yjcloud.transfer.core;

import com.yjcloud.transfer.application.Application;
import com.yjcloud.transfer.queue.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by hhc on 17/9/13.
 */
public abstract class AbstractDocker<E> {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private volatile Queue<E> queue;

    public AbstractDocker() {
        if (queue == null) {
            synchronized (AbstractDocker.class) {   // double check locking
                if (queue == null) {
                    queue = Application.getBean("dataQueue");
                }
            }
        }
    }

    protected E pull() {
        return this.queue.pull();
    }

    protected void pull(Collection<E> collections) {
        this.queue.pull(collections);
    }

    protected void pushData(E e) {
        this.queue.push(e);
    }

    protected void pushData(Collection<E> collections) {
        this.queue.push(collections);
    }
}
