package com.example.vuduc.libs;

import android.net.Uri;


/**
 * Created by Brucelee Thanh on 07/10/2016.
 */

public class ApiConstants {

    private static Uri.Builder builder;

    // Request code & response code
    public static final int RESPONSE_CODE_SUCCESS = 200;

    // API Urls
    private static final String API_ROOT = "155.94.144.150:1123/api";
    private static final String API_SCHEME = "http";
    public static final String API_SIGN_UP = "user/sign_up";
    public static final String API_LOGIN = "user/user_login";
    public static final String API_LOCATION_BROWSE = "location/browse";
    public static final String API_UPLOAD_IMAGE = "file/upload_image";
    public static final String API_STATUS_BROWSE = "status/browse";
    public static final String API_CATEGORY_BROWSE = "category/browse";
    public static final String API_VOLUNTARY_SPOT_CREATE = "voluntary_spot/create";
    public static final String API_VOLUNTARY_SPOT_BROWSE = "voluntary_spot/browse";


    // Default params
    public static final String DEF_CODE = "code";
    public static final String DEF_MSG = "message";
    public static final String DEF_DATA = "data";

    // Key params
    public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE_NUMBER_EMAIL = "phone_number_email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_ROLE = "role";
    public static final String KEY_ID = "_id";
    public static final String KEY_PARENT = "parent";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LINK = "link";
    public static final String KEY_FILE = "file";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_LABEL = "label";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_STATUS = "status";
    public static final String KEY_HAPPENED_AT = "happened_at";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_VIDEOS = "videos";


    public ApiConstants() {
    }

    private static void setBaseUrl() {
        builder = new Uri.Builder();
        builder.scheme(API_SCHEME).encodedAuthority(API_ROOT);
    }

    public Uri.Builder getBaseApi() {
        setBaseUrl();
        return builder;
    }

    public static Uri.Builder getApi(String path) {
        setBaseUrl();
        if (path.contains("/")) {
            builder.appendEncodedPath(path);
        } else {
            builder.appendPath(path);
        }
        return builder;
    }

    public static String getUrl(String path){
        return getApi(path).build().toString();
    }

}
