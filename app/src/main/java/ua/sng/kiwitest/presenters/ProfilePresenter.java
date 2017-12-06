package ua.sng.kiwitest.presenters;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.sng.kiwitest.model.entities.album.AlbumDataModel;
import ua.sng.kiwitest.model.entities.profile.ProfileModel;
import ua.sng.kiwitest.model.network.ApiRequestService;
import ua.sng.kiwitest.model.network.NetworkUrls;
import ua.sng.kiwitest.utils.ConnectionDetector;
import ua.sng.kiwitest.view.fragments.views.ProfileView;

/**
 * Created by sanug on 03.12.2017.
 */

public class ProfilePresenter extends BasePresenter<ProfileView> {

    private ApiRequestService apiRequestService;
    private ConnectionDetector connectionDetector;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ProfilePresenter(ApiRequestService apiRequestService, ConnectionDetector connectionDetector) {
        this.apiRequestService = apiRequestService;
        this.connectionDetector = connectionDetector;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void loadUserInfo() {

        if (connectionDetector.isConnectionToInternet()) {
            getView().showLoading();

            apiRequestService
                    .getUserProfile(NetworkUrls.USER_PROFILE_FIELDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<ProfileModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if (compositeDisposable != null) {
                                compositeDisposable.add(d);
                            }
                        }

                        @Override
                        public void onNext(Response<ProfileModel> profileModelResponse) {
                            if (getView() != null) {
                                if (profileModelResponse.isSuccessful()) {
                                    getView().onProfileLoaded(profileModelResponse.body());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().showErrorMessage(e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (getView() != null) {
                                getView().hideLoading();
                            }
                        }
                    });
        } else {
            getView().showNoConnectionMessage();
        }
    }

    public void getUserAlbumList() {
        if (connectionDetector.isConnectionToInternet()) {
            getView().showLoading();

            apiRequestService
                    .getUserAlbumsList(NetworkUrls.ALBUMS_FIELDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<AlbumDataModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if (compositeDisposable != null) {
                                compositeDisposable.add(d);
                            }
                        }

                        @Override
                        public void onNext(Response<AlbumDataModel> profileModelResponse) {
                            if (getView() != null) {
                                if (profileModelResponse.isSuccessful()) {
                                    AlbumDataModel albumDataModel = profileModelResponse.body();

                                    if (albumDataModel != null) {
                                        getView().onAlbumsLoaded(albumDataModel.getAlbumModels());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (getView() != null) {
                                getView().hideLoading();
                                getView().showErrorMessage(e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (getView() != null) {
                                getView().hideLoading();
                            }
                        }
                    });
        } else {
            getView().showNoConnectionMessage();
        }
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null
                && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void destroy() {
        compositeDisposable = null;
        apiRequestService = null;
    }
}
