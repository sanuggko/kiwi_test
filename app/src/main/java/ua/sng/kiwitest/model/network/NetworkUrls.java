package ua.sng.kiwitest.model.network;

public class NetworkUrls {

    public static final String MAIN_URL = "https://graph.facebook.com/";

    private static final String API_URL = "api/";
    private static final String VERSION = "v2.11/";

    public static final String USER_PROFILE_FIELDS = "id,name,email,birthday,gender";
    public static final String ALBUMS_FIELDS = "albums{description,name,count,edit_link,cover_photo.fields(source)}";

    public static final String USER_PROFILE_URL = VERSION + "me";

}
