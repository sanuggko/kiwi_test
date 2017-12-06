package ua.sng.kiwitest.model.entities.album;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sanug on 05.12.2017.
 */

public class PhotosResponseModel implements Serializable {

    @SerializedName("data")
    private ArrayList<PhotoModel> photoModels;

    public ArrayList<PhotoModel> getPhotoModels() {
        return photoModels;
    }
}
