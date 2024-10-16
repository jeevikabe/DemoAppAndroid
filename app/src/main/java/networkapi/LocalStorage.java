package networkapi;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @author NK
 */
public class LocalStorage
{
    /**
     * Parameter Names for Local Storage When you add new parameter, Don't
     * forget to initialize in initSettings() method
     */

    /*---- User ----*/
    public static final String USER = "user";


    /*---- App Settings ----*/
    public static final String FIRST_USE = "firstUse";
    public static final String ACCESS = "access";
    public static final String LOCATION = "location";
    public static final String SELECTED_DISTRICT = "selectedDistrict";
    public static final String TTS = "tts";
    public static final String IMEI = "imei";
    public static final String API_KEY = "apikey";
    public static final String GCM_REG_ID = "gcm_reg_id";
    public static final String DATA_INITIALIZED = "data_initialized";
    public static final String SOUND_ALERTS = "sound_alerts";
    public static final String SPEECH = "speech";
    public static final String IS_SHOWN_EXCEEDED_CSM_OF_DAY = "is_shown_exceeded_csm_of_day";
    public static final String TO_DAY_DATE = "to_day_date";

    public static final String ONGOING_BEATS = "ongoing_beats";

    public static final String APP_LINK = "appLink";

    /*Counts*/
    public static final String CSM_COUNT = "csm_count";
    public static final String CSM_PENDING_COUNT = "csm_pending_count";
    public static final String ACTS_COUNT = "acts_count";
    public static final String SECTIONS_COUNT = "sections_count";
    public static final String POI_COUNT = "poi_count";
    public static final String PS_COUNT = "ps_count";
    public static final String ASSIGNED_BEAT_COUNT = "assigned_beat_count";
    public static final String PENDING_BEAT_COUNT = "pending_beat_count";

    public static final String DEVELOPER_EMAIL = "utilizesoftwares@gmail.com";
    public static final String NOTIFICATIONS_ENABLED = "notifications_enabled";
    public static final String LOCAL_PASSWORD = "localpassword";
    public static final String MAP_VIEW_TYPE = "MAP_VIEW_TYPE";
    public static final String MAP_STYLE_TYPE = "MAP_STYLE_TYPE";
    public static final String MAP_RADIUS_RANGE = "radiusRange";
    public static final String SHOW_KML_LAYER = "showKmlLayer";
    public static final String AUTH = "auth";
    public static final String SHOWING_CARDLIST_ON_MAP = "showing_cardlist_on_map";
    public static final String APP_AUTH_PASSWORD = "app_Auth_password";
    public static final String APP_AUTH_USER_NAME = "app_Auth_user";
    public static final String CSM_CARD_ITEM_POSITION = "csm_card_item_position";
    public static final String SELECTED_YEAR = "selected_year";
    public static final String USER_TOKEN = "user_token";

    Context mContext;
    SharedPreferences sharedPreferences;
    private final String MyPREFERENCES = "csmPref";

    /**
     * @param context
     */
    public LocalStorage(final Context context)
    {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initSettings();
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    /**
     * @param param
     * @param value
     */
    public void put(final String param, final String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(param, value);
        editor.commit();
    }


    /**
     * @param param
     * @return
     */
    public String get(final String param)
    {
        String value = null;
        if (sharedPreferences.contains(param))
        {
            value = sharedPreferences.getString(param, "");
        }
        return value;
    }

    /**
     *
     */
    public void printAllValuesToLog()
    {
        Map<String, ?> keys = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : keys.entrySet())
        {
            //Log.d("NK", "Key:" + entry.getKey() + " Value: " + entry.getValue().toString());
        }

    }


    private void initSettings()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!sharedPreferences.contains(FIRST_USE))
        {
            editor.putString(FIRST_USE, "true");
            editor.commit();
        }

        if (!sharedPreferences.contains(LOCATION))
        {
            editor.putString(LOCATION, "true");
            editor.commit();
        }

        if (!sharedPreferences.contains(API_KEY))
        {
            editor.putString(API_KEY, "NA");
            editor.commit();
        }
        if (!sharedPreferences.contains(TTS))
        {
            editor.putString(TTS, "true");
            editor.commit();
        }
        if (!sharedPreferences.contains(SELECTED_YEAR))
        {
            editor.putString(SELECTED_YEAR, "null");
            editor.commit();
        }
        if (!sharedPreferences.contains(DATA_INITIALIZED))
        {
            editor.putString(DATA_INITIALIZED, "false");
            editor.commit();
        }

        if (!sharedPreferences.contains(APP_LINK)) {
            editor.putString(APP_LINK, null);
            editor.commit();
        }

        if (!sharedPreferences.contains(IS_SHOWN_EXCEEDED_CSM_OF_DAY))
        {
            editor.putString(IS_SHOWN_EXCEEDED_CSM_OF_DAY, "false");
            editor.commit();
        }
//     if (!sharedPreferences.contains(TO_DAY_DATE))
//     {
//        editor.putString(TO_DAY_DATE, new LocalDateUtils(mContext).getDateOfTheDay());
//        editor.commit();
//     }
        if (!sharedPreferences.contains(GCM_REG_ID))
        {
            editor.putString(GCM_REG_ID, "");
            editor.commit();
        }
        if (!sharedPreferences.contains(SHOWING_CARDLIST_ON_MAP))
        {
            editor.putString(SHOWING_CARDLIST_ON_MAP, "false");
            editor.commit();
        }
        if (!sharedPreferences.contains(CSM_CARD_ITEM_POSITION))
        {
            editor.putString(CSM_CARD_ITEM_POSITION, "0");
            editor.commit();
        }
        if (!sharedPreferences.contains(SELECTED_DISTRICT))
        {
            editor.putString(SELECTED_DISTRICT, "null");
            editor.commit();
        }

        if (!sharedPreferences.contains(ACCESS))
        {
            editor.putString(ACCESS, "false");
            editor.commit();
        }

        if (!sharedPreferences.contains(USER))
        {
            editor.putString(USER, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(LOCAL_PASSWORD))
        {
            editor.putString(LOCAL_PASSWORD, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(SOUND_ALERTS))
        {
            editor.putString(SOUND_ALERTS, "true");
            editor.commit();
        }
        if (!sharedPreferences.contains(SPEECH))
        {
            editor.putString(SPEECH, "true");
            editor.commit();
        }
        if (!sharedPreferences.contains(ONGOING_BEATS))
        {
            editor.putString(ONGOING_BEATS, null);
            editor.commit();
        }

        if (!sharedPreferences.contains(CSM_COUNT))
        {
            editor.putString(CSM_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(CSM_PENDING_COUNT))
        {
            editor.putString(CSM_PENDING_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(ACTS_COUNT))
        {
            editor.putString(ACTS_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(SECTIONS_COUNT))
        {
            editor.putString(SECTIONS_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(POI_COUNT))
        {
            editor.putString(POI_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(PS_COUNT))
        {
            editor.putString(PS_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(ASSIGNED_BEAT_COUNT))
        {
            editor.putString(ASSIGNED_BEAT_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(PENDING_BEAT_COUNT))
        {
            editor.putString(PENDING_BEAT_COUNT, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(NOTIFICATIONS_ENABLED))
        {
            editor.putString(NOTIFICATIONS_ENABLED, "true");
            editor.commit();
        }
        if (!sharedPreferences.contains(MAP_STYLE_TYPE))
        {
            editor.putString(MAP_STYLE_TYPE, null);
            editor.commit();
        }

        if (!sharedPreferences.contains(MAP_VIEW_TYPE))
        {
            editor.putString(MAP_VIEW_TYPE, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(MAP_RADIUS_RANGE))
        {
            editor.putString(MAP_RADIUS_RANGE, "0.0");
            editor.commit();
        }
        if (!sharedPreferences.contains(SHOW_KML_LAYER))
        {
            editor.putString(SHOW_KML_LAYER, "false");
            editor.commit();
        }
        if (!sharedPreferences.contains(AUTH))
        {
            editor.putString(AUTH, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(APP_AUTH_PASSWORD))
        {
            editor.putString(APP_AUTH_PASSWORD, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(APP_AUTH_USER_NAME))
        {
            editor.putString(APP_AUTH_USER_NAME, null);
            editor.commit();
        }
        if (!sharedPreferences.contains(USER_TOKEN)) {
            editor.putString(USER_TOKEN, null);
            editor.commit();
        }
    }

}