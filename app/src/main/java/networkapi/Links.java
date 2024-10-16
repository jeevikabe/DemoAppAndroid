package networkapi;

import android.content.Context;

public class Links {
    public static String POI_IMAGE;
    private static String IP = "https://primaface.capulustech.com/app/";
    //public static String SHOW_IMAGE;
    public static String OTP_GET;
    public static String OTP_VERIFY;
    public static String GET_POI_LIST;
    public static String USER_PASS;
    public static  String UPDATE_NOTIFICATION_ID;
    public static String UPLOAD_IMAGE;
    public static String UPLOAD_VIDEO;

    public static String UPLOAD_AUDIO;
    public static String UPLOAD_PDF;
    public static String UPLOAD_ZIP;
    public static String LOAD;
    public static String GET_VEHICLE_LOCATION;
    private static String BASEURL;
    private static String GETOTP;
    private static String VERIFYOTP;
    private static String USERPASS_URL;
    private static String NOTIFICATION_MODULE_URL;

    private static String BASE_URL;

    private  Context context;
    public Links(Context context) {
        this.context = context;
    }

    public static void setLinks(Context context) {
        BASEURL = "http://172.16.17.143:9098/mediamodule/api/mobile/";
        NOTIFICATION_MODULE_URL = "http://172.16.17.143:9098/notificationmodules/";

        USERPASS_URL = "http://172.16.18.11:8080/biosync/api/v1/";

        GETOTP = "https://primaface.capulustech.com/app/";
        VERIFYOTP = "https://primaface.capulustech.com/app/";

        BASE_URL = IP + "/api";


        

//https://primaface.capulustech.com/app/getotp
//https://primaface.capulustech.com/app/verify
//http://172.16.17.143:9098/mediamodule/api/mobile/fileupload
// http://172.16.18.11:8080/biosync/api/v1/user/login

        //declare your apis here
        UPLOAD_IMAGE = BASEURL + "fileupload";
        UPLOAD_VIDEO = BASEURL + "fileupload";
        UPLOAD_AUDIO = BASEURL + "fileupload";
        UPLOAD_PDF = BASEURL + "fileupload";
        UPLOAD_ZIP = BASEURL + "fileupload";
        LOAD = BASEURL + "searchterm";
        UPDATE_NOTIFICATION_ID = NOTIFICATION_MODULE_URL+ "sendnotification";
        GET_VEHICLE_LOCATION = NOTIFICATION_MODULE_URL+ "randomlatLng";

        USER_PASS = USERPASS_URL + "user/login";

        OTP_GET = GETOTP + "api/getotp";
        OTP_VERIFY = VERIFYOTP + "api/verify";
        GET_POI_LIST = BASE_URL + "/poi/get";
        POI_IMAGE = BASE_URL + "/media";
    }
}
