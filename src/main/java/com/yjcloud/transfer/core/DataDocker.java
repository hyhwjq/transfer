package com.yjcloud.transfer.core;

import com.google.common.primitives.Ints;
import com.yjcloud.transfer.queue.ArrayQueue;
import com.yjcloud.transfer.queue.Queue;
import com.yjcloud.transfer.util.config.PropertyConfigurer;

import java.util.Collection;

/**
 * Created by hhc on 17/9/13.
 */
public class DataDocker<E> {
    private volatile Queue<E> queue;

    public DataDocker() {
        if (queue == null) {
            String size = PropertyConfigurer.getProperty("data.queue.size");
            int queueSize = Ints.tryParse(size);
            queue = new ArrayQueue<>(queueSize);
        }
    }

    public E pull() {
        return this.queue.pull();
    }

    public void pull(Collection<E> collections) {
        this.queue.pull(collections);
    }

    public void pushData(E e) {
        this.queue.push(e);
    }

    public void pushData(Collection<E> collections) {
        this.queue.push(collections);
    }
}
