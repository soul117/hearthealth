package ua.com.crosp.solutions.hearthealthmonitor.game.infrastructure.input.handler;

import java.util.Collection;

/**
 * Created by Alexander Molochko on 10/20/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */

public interface BoundedCollection<E> extends Collection<E> {

    /**
     * Returns true if this collection is full and no new elements can be added.
     *
     * @return <code>true</code> if the collection is full
     */
    boolean isFull();

    /**
     * Gets the maximum size of the collection (the bound).
     *
     * @return the maximum number of elements the collection can hold
     */
    int maxSize();

}