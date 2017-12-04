package ua.sng.kiwitest.di.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import ua.sng.kiwitest.di.modules.ApplicationModule;
import ua.sng.kiwitest.di.modules.NetworkModule;
import ua.sng.kiwitest.model.network.ApiRequestService;
import ua.sng.kiwitest.utils.ConnectionDetector;
import ua.sng.kiwitest.utils.SharedPreferencesManager;
import ua.sng.kiwitest.view.activities.AuthorizationActivity;
import ua.sng.kiwitest.view.activities.MainActivity;
import ua.sng.kiwitest.view.fragments.PhotoListFragment;
import ua.sng.kiwitest.view.fragments.ProfileFragment;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(AuthorizationActivity authorizationActivity);
    void inject(ProfileFragment profileFragment);
    void inject(PhotoListFragment photoListFragment);

    Context context();
    SharedPreferencesManager sharedPreferencesManager();
    ApiRequestService apiRequestService();
    ConnectionDetector connectionDetector();
}
