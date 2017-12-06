package ua.sng.kiwitest.model.network;

public class NetworkUrls {

    public static final String MAIN_URL = "https://graph.facebook.com/";

    private static final String VERSION = "v2.11/";

    public static final String USER_PROFILE_FIELDS = "id,name,email,birthday,gender";
    public static final String ALBUMS_FIELDS = "description,name,count,edit_link,cover_photo.fields(source)";
    public static final String PHOTOS_FIELDS = "source,name,place,created_time,picture,likes.limit(0).summary(true),comments.limit(0).summary(true)";

    public static final String USER_PROFILE_URL = VERSION + "me";
   // public static final String ALBUMS_LIST = VERSION + "{user_id}/albums";
    public static final String ALBUMS_LIST = VERSION + "me/albums";
    public static final String USER_PHOTOS_URL = VERSION + "{album_id}/photos";

}
