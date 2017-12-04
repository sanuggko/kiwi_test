package ua.sng.kiwitest.view.fragments.views;

import java.util.ArrayList;

import ua.sng.kiwitest.model.entities.album.AlbumModel;
import ua.sng.kiwitest.model.entities.profile.ProfileModel;
import ua.sng.kiwitest.view.BaseView;

/**
 * Created by sanug on 03.12.2017.
 */

public interface ProfileView extends BaseView {
    void onProfileLoaded(ProfileModel profileModel);
    void onAlbumsLoaded(ArrayList<AlbumModel> albumModels);
}
