package com.iriras.cityappcleancode.dagger;

import com.iriras.cityappcleancode.BuildConfig;
import com.iriras.cityappcleancode.api.network.NetworkFetchData;
import com.iriras.cityappcleancode.api.service.ApiService;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by irfan on 6/18/17.
 */

@Module
public class NetworkModule {
    private File mCacheFile;
    private long CACHE_SIZE = 10 * 1024 * 1024;

    public NetworkModule(File mCacheFile) {
        this.mCacheFile = mCacheFile;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        Cache mCache = null;
        try {
            mCache = new Cache(mCacheFile, CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request mOriginRequest = chain.request();

                        //custom request
                        Request mRequest = mOriginRequest.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .build();

                        Response mResponse = chain.proceed(mRequest);
                        mResponse.cacheResponse();
                        return mResponse;
                    }
                })
                .cache(mCache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(mHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkFetchData provideNetworkFetchData(ApiService apiService){
        return new NetworkFetchData(apiService);
    }
}
