package com.example.shiraki_hirotomo.metropush;

import android.content.Context;
import android.content.SharedPreferences;

import twitter4j.Twitter;

/**
 * Created by shiraki-hirotomo on 2014/09/20.
 */
public class SettingUtils {
    //キーたち
    private static final String PREF_NAME = "twitter_access_token";
    private static final String CONTACT_IS_ENABLE = "contact_is_enable";
    private static final String DEPARTURE = "departure";
    private static final String ARRIVAL = "arrival";
    private static final String EVERYSTOPS = "everystops";
    private static final String TWITTER_NAME = "twittername";
    private static final String TWITTER_SCREEN_NAME = "twitter_screen_name";
    private static final String TWITTER_TO_NAME = "twitter_to_name";
    private static final String TWITTER_MESSAGE = "twitter_message";
    private static final String EDIT_DEPARTURE = "edit_departure";
    private static final String EDIT_ARRIVAL = "edit_arrival";
    private static final String DEPARTURE_NAME = "departure_name";
    private static final String DEPARTURE_LONGITUDE = "departure_longitude";
    private static final String DEPARTURE_LATITUDE = "departure_latitude";
    private static final String ARRIVAL_NAME = "arrival_name";
    private static final String ARRIVAL_LONGITUDE = "arrival_longitude";
    private static final String ARRIVAL_LATITUDE = "arrival_latitude";


//    public static void storeContactIsEnable(Context context, boolean isChecked){
//        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
//        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
//                Context.MODE_PRIVATE);
//        // Editorのインスタンスを取得
//        SharedPreferences.Editor editor = preferences.edit();
//        //editorに、TOKENをキーにしてaccessTokenのtokenを追加している
//        editor.putBoolean(CONTACT_IS_ENABLE, isChecked);
//        //editorの変更をコミットする。
//        editor.commit();
//    }

    //togglebuttonをまとめて管理。
    //
    public static void storeToggleIsChecked(Context context, int id,boolean isChecked){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        if(id==R.id.togglebuttoncontactisenable) {
            editor.putBoolean(CONTACT_IS_ENABLE, isChecked);
        }else if(id==R.id.togglebuttondeparture){
            editor.putBoolean(DEPARTURE, isChecked);
        }else if(id==R.id.togglebuttonarrival){
            editor.putBoolean(ARRIVAL, isChecked);
        }else if(id==R.id.togglebuttoneverystops){
            editor.putBoolean(EVERYSTOPS, isChecked);
        }
        //editorの変更をコミットする。
        editor.commit();
    }

    public static boolean loadToggleIsChecked(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isCheck = true;

        if (id == R.id.togglebuttoncontactisenable) {
            isCheck = preferences.getBoolean(CONTACT_IS_ENABLE, false);
        } else if (id == R.id.togglebuttondeparture) {
            isCheck = preferences.getBoolean(DEPARTURE, true);
        } else if (id == R.id.togglebuttonarrival) {
            isCheck = preferences.getBoolean(ARRIVAL, true);
        } else if (id == R.id.togglebuttoneverystops) {
            isCheck = preferences.getBoolean(EVERYSTOPS, false);
        }
        return isCheck;
    }

    public static void storeTwitterUser(Context context, Twitter mTwitter){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(TWITTER_NAME, "みつを");//twitterのuserのnameをとりたい
        editor.putString(TWITTER_SCREEN_NAME, "mitsuo");//twitterのuserのscreennameをとりたい
        //editorの変更をコミットする。
        editor.commit();
    }

    public static String loadTwitterUser(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String twitterName = preferences.getString(TWITTER_NAME, null);
        String twitterScreenName = preferences.getString(TWITTER_SCREEN_NAME, null);
        if(twitterName != null && twitterScreenName != null){
            return twitterName + "(@" + twitterScreenName + ")";
        }else{
            return "未設定";
        }
    }
//    public static boolean hasTwitterUser(Context context){
//        return loadTwitterUser(context) != null;
//    }

    public static void storeTwitterToName(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TWITTER_TO_NAME, s);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadTwitterToName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String twitterToName = preferences.getString(TWITTER_TO_NAME, null);
        if(twitterToName != null){
            return twitterToName;
        }else{
            return "宛先未設定";
        }
    }

    public static void storeTwitterMessage(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(TWITTER_MESSAGE, s);//twitterのuserのscreennameをとりたい
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadTwitterMessage(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String twitterMessage = preferences.getString(TWITTER_MESSAGE, null);
        if(twitterMessage != null){
            return twitterMessage;
        }else{
            return "メッセージ未設定";
        }
    }



    public static void storeDeparture(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(EDIT_DEPARTURE, s);//twitterのuserのscreennameをとりたい
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadDeparture(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String departure = preferences.getString(EDIT_DEPARTURE, null);
        return departure;
    }

    public static boolean hasDeparture(Context context){
        return loadDeparture(context) != null;
    }

    public static void storeArrival(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EDIT_ARRIVAL, s);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadArrival(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String arrival = preferences.getString(EDIT_ARRIVAL, null);
        return arrival;
    }

    public static boolean hasArrival(Context context){
        return loadDeparture(context) != null;
    }


    //注意！storeDeparture・storeArrivalと違い、storeDepartureName・storeArrivalNameはEdit
    public static void storeDepartureName(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DEPARTURE_NAME, s);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadDepartureName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String departure_name = preferences.getString(DEPARTURE_NAME, null);
        return departure_name;
    }

    public static void storeDepartureLongitude(Context context, double d){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(DEPARTURE_LONGITUDE, (float)d);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    //一度floatにされたのがdoubleで返される。
    public static double loadDepartureLongitude(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        double departure_longitude = preferences.getFloat(DEPARTURE_LONGITUDE, 0);
        return departure_longitude;
    }
    public static void storeDepartureLatitude(Context context, double d){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(DEPARTURE_LATITUDE, (float)d);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static double loadDepartureLatitude(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        double departure_latitude = preferences.getFloat(DEPARTURE_LATITUDE, 0);
        return departure_latitude;
    }

    public static void storeArrivalName(Context context, String s){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ARRIVAL_NAME, s);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static String loadArrivalName(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String arrival_name = preferences.getString(ARRIVAL_NAME, null);
        return arrival_name;
    }

    public static void storeArrivalLongitude(Context context, double d){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(ARRIVAL_LONGITUDE, (float)d);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static double loadArrivalLongitude(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        double arrival_longitude = preferences.getFloat(ARRIVAL_LONGITUDE, 0);
        return arrival_longitude;
    }
    public static void storeArrivalLatitude(Context context, double d){
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(ARRIVAL_LATITUDE, (float)d);//edittextを入れる
        //editorの変更をコミットする。
        editor.commit();
    }
    public static double loadArrivalLatitude(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        double arrival_latitude = preferences.getFloat(ARRIVAL_LATITUDE, 0);
        return arrival_latitude;
    }
}
