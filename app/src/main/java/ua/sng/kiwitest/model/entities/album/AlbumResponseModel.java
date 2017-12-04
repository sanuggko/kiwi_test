package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sanug on 04.12.2017.
 */

public class AlbumResponseModel implements Serializable{

    @SerializedName("albums")
    private AlbumDataModel albumDataModel;

    public AlbumDataModel getAlbumDataModel() {
        return albumDataModel;
    }
}
