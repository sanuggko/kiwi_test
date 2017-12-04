package ua.sng.kiwitest.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.sng.kiwitest.application.KiwiApplication;
import ua.sng.kiwitest.utils.ConnectionDetector;

/**
 * Created by Oleksandr on 03.10.2017.
 */

@Module
public class ApplicationModule {

    private KiwiApplication context;

    public ApplicationModule(KiwiApplication context){
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return context;
    }

    @Provides
    @Singleton
    ConnectionDetector provideConnectionDetector(Context context){
        return new ConnectionDetector(context);
    }
}
