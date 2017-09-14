package com.yjcloud.transfer.queue;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hhc on 17/9/13.
 */
public class ArrayQueue<E> implements Queue<E> {
    private static final Logger LOG = LoggerFactory.getLogger(ArrayQueue.class);

    private int maxElements;

    private ArrayBlockingQueue<E> items = null;

    private ReentrantLock lock;

    private Condition notInsufficient, notEmpty;

    private volatile boolean loadClose;

    public ArrayQueue(int maxElements) {
        Preconditions.checkArgument(maxElements > 0, "maxElements can't less 0");
        this.maxElements = maxElements;
        this.items = new ArrayBlockingQueue<>(maxElements);
        lock = new ReentrantLock();
        notInsufficient = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void clear() {
        this.items.clear();
    }

    public int size() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public String info() {
        return null;
    }

    public void close() {
        try {
            this.items.put(null);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        this.loadClose = true;
    }

    public void push(E e) {
        try {
            this.items.put(e);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void push(Collection<E> es) {
        try {
            lock.lockInterruptibly();

            while (es.size() > this.items.remainingCapacity()) {
                notInsufficient.await(200L, TimeUnit.MILLISECONDS);
            }
            this.items.addAll(es);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public E pull() {
        try {
            return this.items.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    public void pull(Collection<E> e) {
        try {
            lock.lockInterruptibly();
            e.clear();
            while (this.items.drainTo(e, maxElements) <= 0) {
                if (!loadClose) {
                    notEmpty.await(200L, TimeUnit.MILLISECONDS);
                } else {
                    break;
                }
            }
            notInsufficient.signalAll();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }
}
