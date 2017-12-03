package ua.sng.kiwitest.view;

/**
 * Created by AlexLampa on 29.09.2017.
 */

public interface BaseView {
    void showLoading();
    void hideLoading();
    void showErrorMessage(String error);
    void showNoConnectionMessage();

}
