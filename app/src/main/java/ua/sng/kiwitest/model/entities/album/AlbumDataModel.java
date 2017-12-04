package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by sanug on 04.12.2017.
 */

public class AlbumDataModel {

    @SerializedName("data")
    private ArrayList<AlbumModel> albumModels;

    public ArrayList<AlbumModel> getAlbumModels() {
        return albumModels;
    }
}
