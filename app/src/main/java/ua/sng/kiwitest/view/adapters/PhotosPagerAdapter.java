package ua.sng.kiwitest.view.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.view.fragments.PhotoDetailFragment;

public class PhotosPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ArrayList<PhotoModel> photoModels;

    public PhotosPagerAdapter(FragmentManager fm, Context context, ArrayList<PhotoModel> photoModels) {
        super(fm);
        this.context = context;
        this.photoModels = photoModels;
    }

    @Override
    public PhotoDetailFragment getItem(int position) {
        return PhotoDetailFragment.newInstance(photoModels.get(position));
    }

    @Override
    public int getCount() {
        return photoModels.size();
    }
}