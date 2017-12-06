package ua.sng.kiwitest.presenters;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.sng.kiwitest.model.entities.album.PhotosResponseModel;
import ua.sng.kiwitest.model.network.ApiRequestService;
import ua.sng.kiwitest.model.network.NetworkUrls;
import ua.sng.kiwitest.utils.ConnectionDetector;
import ua.sng.kiwitest.view.fragments.views.PhotosView;

/**
 * Created by sanug on 05.12.2017.
 */

public class PhotosPresenter extends BasePresenter<PhotosView> {

    private ApiRequestService apiRequestService;
    private ConnectionDetector connectionDetector;
    private CompositeDisposable compositeDisposable;

    @Inject
    public PhotosPresenter(ApiRequestService apiRequestService, ConnectionDetector connectionDetector){
        this.apiRequestService = apiRequestService;
        this.connectionDetector = connectionDetector;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void getPhotosFromAlbum(String albumId){

        if(connectionDetector.isConnectionToInternet()){
            getView().showLoading();

            apiRequestService
                    .getPhotosFromAlbum(albumId, NetworkUrls.PHOTOS_FIELDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<PhotosResponseModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            if(compositeDisposable != null){
                                compositeDisposable.add(d);
                            }
                        }

                        @Override
                        public void onNext(Response<PhotosResponseModel> photosResponse) {
                            if(getView() != null
                                    && photosResponse.isSuccessful()){

                                if(photosResponse.body() != null){
                                    getView().onPhotosListLoaded(photosResponse.body().getPhotoModels());
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if(getView() != null){
                                getView().hideLoading();
                                getView().showErrorMessage(e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onComplete() {
                            if(getView() != null){
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
        if(compositeDisposable != null
                && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    @Override
    public void destroy() {
        apiRequestService = null;
        connectionDetector = null;
        compositeDisposable = null;
    }
}
