package ua.sng.kiwitest.view.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.lang.annotation.Annotation;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.sng.kiwitest.application.KiwiApplication;
import ua.sng.kiwitest.di.components.ApplicationComponent;
import ua.sng.kiwitest.utils.Layout;
import ua.sng.kiwitest.view.fragments.BaseFragment;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder uninder;

    protected BaseFragment currentFragment;
    protected BaseFragment previousFragment;
    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Class cls = getClass();

        if (!cls.isAnnotationPresent(Layout.class)) {
            return; // Layout annotation is required
        }

        Annotation annotation = cls.getAnnotation(Layout.class);
        setContentView(((Layout) annotation).id());
        uninder = ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        currentFragment = getDefaultFragment();

        inject();  // inject dependencies
    }

    public ApplicationComponent getApplicationComponent() {
        return ((KiwiApplication) getApplication()).getApplicationComponent();
    }

    public void showFragment(int containerViewId, BaseFragment fragment, boolean needToRefreshBackStack) {
        if (needToRefreshBackStack && fragmentManager.getBackStackEntryCount() > 0) {
            int indexToDelete = (fragment.getClass().getSimpleName()
                    .equals(fragmentManager.getBackStackEntryAt(0).getName())) ? 0 : 1;
            int backStackFragmentsCount = fragmentManager.getBackStackEntryCount();
            for (int i = backStackFragmentsCount - 1; i >= indexToDelete; i--) {
                int backStackId = fragmentManager.getBackStackEntryAt(i).getId();
                fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }

        previousFragment = currentFragment;
        currentFragment = fragment;

        String fragmentTag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (previousFragment != null) {
            fragmentTransaction.hide(previousFragment);
        }

        fragmentTransaction.add(containerViewId, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }

    public void showFragmentWithDelay(int containerId,
                                      BaseFragment baseFragment,
                                      boolean needToRefreshBackStack,
                                      long delay) {

        new Handler().postDelayed(() ->
                showFragment(containerId, baseFragment, needToRefreshBackStack), delay);
    }

    protected void showFragmentWithoutBackStack(int containerViewId, BaseFragment fragment) {
        previousFragment = currentFragment;
        currentFragment = fragment;

        String fragmentTag = fragment.getClass().getSimpleName();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        if (previousFragment != null) {
            fragmentTransaction.hide(previousFragment);
        }

        fragmentTransaction
                .add(containerViewId, fragment, fragmentTag)
                .commit();
    }


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected abstract void inject();

    protected abstract BaseFragment getDefaultFragment();

    @Override
    protected void onDestroy() {
        uninder.unbind();
        uninder = null;
        super.onDestroy();
    }
}