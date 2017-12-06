package ua.sng.kiwitest.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.utils.viewutils.KiwiViewUtils;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private MaterialDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Class cls = getClass();

        if (!cls.isAnnotationPresent(Layout.class)) {
            return null; // Layout annotation is required
        }

        Annotation annotation = cls.getAnnotation(Layout.class);
        View view = inflater.inflate(((Layout) annotation).id(), null);
        unbinder = ButterKnife.bind(this, view);

        inject();
        setupInOnCreateView();

        return view;
    }

    public abstract String getTitle();

    protected abstract void inject();

    protected abstract void setupInOnCreateView();

    public MaterialDialog getProgressDialog() {
        if(progressDialog == null){
            progressDialog = KiwiViewUtils.generateProgressDialog(getActivity());
        }

        return progressDialog;
    }

    public String getFragmentName(){
        return BaseFragment.class.getSimpleName();
    }

    public void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        unbinder = null;
        super.onDestroyView();
    }
}