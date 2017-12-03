package ua.sng.kiwitest.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.sng.kiwitest.model.network.ApiRequestService;
import ua.sng.kiwitest.model.network.AuthenticationInterceptor;
import ua.sng.kiwitest.model.network.NetworkUrls;

@Module
public class NetworkModule {

    private static final int GENERAL_TIMEOUT = 60;  // seconds for timeout
    private static final int CONNECT_TIMEOUT = GENERAL_TIMEOUT;
    private static final int WRITE_TIMEOUT = GENERAL_TIMEOUT;
    private static final int READ_TIMEOUT = GENERAL_TIMEOUT;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;  // 10 mb for cache

    @Provides
    @Singleton
    Cache provideCache(Context context){
        File file = new File(context.getCacheDir(), "cacheResponses");
        return new Cache(file, CACHE_SIZE);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context, AuthenticationInterceptor authenticationInterceptor){
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .addInterceptor(authenticationInterceptor)
                .addInterceptor(new ChuckInterceptor(context))
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(NetworkUrls.MAIN_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiRequestService provideApiRequestService(Retrofit retrofit) {
        return retrofit.create(ApiRequestService.class);
    }

}
