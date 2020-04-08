package com.vintile.bingo.di.main;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Sharath on 2020/02/15
 **/
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MainScope {
}
