package ua.sng.kiwitest.view.fragments.views;

import java.util.ArrayList;

import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.view.BaseView;

/**
 * Created by sanug on 05.12.2017.
 */

public interface PhotosView extends BaseView {
    void onPhotosListLoaded(ArrayList<PhotoModel> photoModels);
}
