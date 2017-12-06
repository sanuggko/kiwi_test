package ua.sng.kiwitest.model.network;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.sng.kiwitest.model.entities.album.AlbumDataModel;
import ua.sng.kiwitest.model.entities.album.PhotosResponseModel;
import ua.sng.kiwitest.model.entities.profile.ProfileModel;

public interface ApiRequestService {

    @GET(NetworkUrls.USER_PROFILE_URL)
    Observable<Response<ProfileModel>> getUserProfile(@Query("fields") String fields);

//    @GET(NetworkUrls.ALBUMS_LIST)
//    Observable<Response<AlbumResponseModel>> getUserAlbumsList(@Path("user_id") String userId,
//                                                               @Query("fields") String fields);

    @GET(NetworkUrls.ALBUMS_LIST)
    Observable<Response<AlbumDataModel>> getUserAlbumsList(@Query("fields") String fields);


    @GET(NetworkUrls.USER_PHOTOS_URL)
    Observable<Response<PhotosResponseModel>> getPhotosFromAlbum(@Path("album_id") String albumId,
                                                                 @Query("fields") String fields);
}
