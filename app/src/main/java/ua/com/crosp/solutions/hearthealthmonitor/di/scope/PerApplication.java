package ua.com.crosp.solutions.hearthealthmonitor.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Oleksandr "CROSP" Molochko on 3/18/16 at 6:42 AM.
 * Project : KopilkaReferalApp
 * Package : com.crosp.solutions.discountgroup.di.annotation
 * Copyright (c) 2013-2016 Datascope Ltd.
 * All Rights Reserved.
 */
@Scope
@Retention(RUNTIME)
public @interface PerApplication {}
