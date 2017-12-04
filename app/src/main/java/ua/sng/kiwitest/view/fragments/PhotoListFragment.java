package ua.sng.kiwitest.view.fragments;

import ua.sng.kiwitest.R;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.view.activities.BaseActivity;

/**
 * Created by sanug on 05.12.2017.
 */

@Layout(id = R.layout.fragment_profile)
public class PhotoListFragment extends BaseFragment {


    @Override
    public String getTitle() {
        return "album";
    }

    @Override
    protected void inject() {
        if(getActivity() != null){
            ((BaseActivity) getActivity())
                    .getApplicationComponent()
                    .inject(this);
        }
    }

    @Override
    protected void setupInOnCreateView() {

    }
}
