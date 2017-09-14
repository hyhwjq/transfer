package com.yjcloud.transfer.queue;

import java.util.Collection;

/**
 * Created by hhc on 17/9/13.
 */
public interface Queue<E> {

    public int size();

    public boolean isEmpty();

    public String info();

    public void close();

    public void push(E e);

    public void push(Collection<E> es);

    public E pull();

    public void pull(Collection<E> e);
}
