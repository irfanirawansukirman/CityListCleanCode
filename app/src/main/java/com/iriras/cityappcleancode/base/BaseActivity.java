package com.iriras.cityappcleancode.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iriras.cityappcleancode.R;
import com.iriras.cityappcleancode.dagger.DaggerInjector;
import com.iriras.cityappcleancode.dagger.Injector;
import com.iriras.cityappcleancode.dagger.NetworkModule;

import java.io.File;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by irfan on 6/17/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Injector mInjector;
    private File mCacheFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCacheFile = new File(getCacheDir(), "responses");
        mInjector = DaggerInjector.builder()
                .networkModule(new NetworkModule(mCacheFile))
                .build();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Poppins-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public Injector getInjector() {
        return mInjector;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
