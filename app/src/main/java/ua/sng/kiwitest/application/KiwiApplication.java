package ua.sng.kiwitest.application;

import android.app.Application;

import ua.sng.kiwitest.di.components.ApplicationComponent;
import ua.sng.kiwitest.di.components.DaggerApplicationComponent;
import ua.sng.kiwitest.di.modules.ApplicationModule;

public class KiwiApplication extends Application {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent(){
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

}
