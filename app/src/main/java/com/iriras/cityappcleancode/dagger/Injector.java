package com.iriras.cityappcleancode.dagger;

import com.iriras.cityappcleancode.mvp.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by irfan on 6/18/17.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface Injector {
    void inject(MainActivity mainActivity);
}
