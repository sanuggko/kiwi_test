package ua.sng.kiwitest.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import ua.sng.kiwitest.R;
import ua.sng.kiwitest.model.entities.album.PhotoModel;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.view.activities.BaseActivity;
import ua.sng.kiwitest.view.adapters.PhotosPagerAdapter;

/**
 * Created by sanug on 06.12.2017.
 */

@Layout(id = R.layout.fragment_detail_pager)
public class PhotoDetailPagerFragment extends BaseFragment {

    public static final String PHOTOS_LIST_KEY = "photos_list_key";
    public static final String CURRENT_POSITION_KEY = "current_position";

    public static PhotoDetailPagerFragment newInstance(int position, ArrayList<PhotoModel> photoModels){
        PhotoDetailPagerFragment photoDetailPagerFragment = new PhotoDetailPagerFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(CURRENT_POSITION_KEY, position);
        arguments.putString(PHOTOS_LIST_KEY, new Gson().toJson(photoModels));

        photoDetailPagerFragment.setArguments(arguments);

        return photoDetailPagerFragment;
    }

    @BindView(R.id.detail_view_pager)
    ViewPager photoViewPager;

    private ArrayList<PhotoModel> photoModels;
    private int currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            currentPosition = getArguments().getInt(CURRENT_POSITION_KEY);

            String photosListJson = getArguments().getString(PHOTOS_LIST_KEY);

            if(photosListJson != null && !photosListJson.isEmpty()){
                Type listType = new TypeToken<ArrayList<PhotoModel>>(){}.getType();
                photoModels = new Gson().fromJson(photosListJson, listType);
            }

            if(photoModels == null){
                photoModels = new ArrayList<>();
            }
        }
    }

    @Override
    protected void setupInOnCreateView() {
        setupPhotosViewPager();
    }

    private void setupPhotosViewPager(){
        PhotosPagerAdapter photosPagerAdapter = new PhotosPagerAdapter(getChildFragmentManager(), getActivity(), photoModels);
        photoViewPager.setAdapter(photosPagerAdapter);

        if(currentPosition < photoModels.size()){
            photoViewPager.setCurrentItem(currentPosition);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.photos_title);
    }

    @Override
    protected void inject() {
        if(getActivity() != null){
            ((BaseActivity) getActivity()).getApplicationComponent().inject(this);
        }
    }
}
