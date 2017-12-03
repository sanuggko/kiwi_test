package ua.sng.kiwitest.view.fragments.views;

import ua.sng.kiwitest.model.entities.ProfileModel;
import ua.sng.kiwitest.view.BaseView;

/**
 * Created by sanug on 03.12.2017.
 */

public interface ProfileView extends BaseView {
    void onProfileLoaded(ProfileModel profileModel);
}
