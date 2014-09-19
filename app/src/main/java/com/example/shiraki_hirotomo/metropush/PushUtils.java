package com.example.shiraki_hirotomo.metropush;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shiraki-hirotomo on 2014/09/18.
 */
public class PushUtils {
    //キー
    private static final String TYPE = "type";
    //キー
    private static final String IS_ENABLE = "is_enable";
    //キー
    private static final String PREF_NAME = "twitter_access_token";


    public static void storePush(Context context, Push push) {
        // コンテキストからPREF_NAMEをキーにSharedPreferenceのインスタンスを取得している
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        // Editorのインスタンスを取得
        SharedPreferences.Editor editor = preferences.edit();
        //editorに、TOKENをキーにしてaccessTokenのtokenを追加している
        editor.putString(TYPE, push.getType());
        //editorに、TOKENをキーにしてaccessTokenのtokensecretを追加している
        editor.putBoolean(IS_ENABLE, push.getIsEnable());
        //editorの変更をコミットする。
        editor.commit();
    }

    public static Push loadPush(Context context){
        //contextからgetSharedPreferenceする。第一引数はこのSharedPreferenceの名前となる（キーのようなもの）。
        //第二引数はSharedPreferenceの共有モードを指定できる。MODE_PRIVATEにすると、そのアプリケーションだけで使用可能なデータとなる。
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        //TOKENをキーにしてデータをpreferencesから取り出す
        String type = preferences.getString(TYPE, null);
        //TOKENを_SECRETキーにしてデータをpreferencesから取り出す
        boolean isEnable = preferences.getBoolean(IS_ENABLE, true);//このtrueは初期値として設定されるもの。つまり「nullだから〜」みたいな判定がisEnableにおいてはできない。
        //tokenとtokenSecretの両方がnullじゃない場合、一つ目の処理が行われる。
        if (type != null) {// 「&& isEnable != null 」がif文に入ってたけど、判定の基準にならないだろうから外した
            //AccessTokenを作成して返り値にしている
            return new Push(type, isEnable);
        } else {
            //nullを返す。
            return null;
        }
    }

    public static boolean hasPush(Context context) {
        return loadPush(context) != null;
    }
}
