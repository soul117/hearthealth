package ua.com.crosp.solutions.hearthealthmonitor.base.presentation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Created by Alexander Molochko on 12/18/16.
 * Project HeartHealthMonitor
 * Copyright (C) 2016 CROSP Solutions
 */

public abstract class BaseMapper<I, O> implements Function<I, O> {
    public abstract O transform(I inputItem);

    public List<O> transform(List<I> inputCollection) {
        List<O> outputCollection = new ArrayList<>();
        for (I inputItem : inputCollection) {
            outputCollection.add(transform(inputItem));
        }
        return outputCollection;
    }

    @Override
    public O apply(@NonNull I i) throws Exception {
        return transform(i);
    }
}