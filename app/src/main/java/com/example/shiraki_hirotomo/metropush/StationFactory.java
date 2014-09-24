package com.example.shiraki_hirotomo.metropush;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shiraki-hirotomo on 2014/09/23.
 */
public class StationFactory {

    public static ArrayList<Station> list;

    /*
     * テキストデータの解析
     */
    public static ArrayList<Station> create(String response) {

        Log.d("createの引数のresponse(StationFactory)", response);
        if(list == null){
            list = new ArrayList<Station>();
        }

        try {
            //Log.d("前JSONObjectに入れたresponse", response);
            JSONObject rootObject = new JSONObject(response);
            //JSONArray rootArray = new JSONArray(response);
            //Log.d("後JSONObjectに入れたresponse", response);
            // dataオブジェクトの取得
            JSONObject firstObject = rootObject.getJSONObject("response");//二つある要素のうち一つ
            //JSONObject firstObject = new JSONObject(response);//二つある要素のうち一つ
            Log.d("1", "あ");
            JSONArray dataArray = firstObject.getJSONArray("station");
            Log.d("2", "あ");

            int tmpCount=0;
            for(int i = 0; i < dataArray.length(); i++) {
                if(++tmpCount==dataArray.length()) Log.d(tmpCount+"回", "あ");
                Station station = new Station();
                JSONObject data = dataArray.getJSONObject(i);

                station.setX(0);
                if(data.has("x")) {
                    station.setX(data.getDouble("x"));
                }
                station.setY(0);
                if(data.has("y")) {
                    station.setY(data.getDouble("y"));
                }

                station.setName(null);
                if(data.has("name")) {
                    station.setName(data.getString("name"));
                }

                station.setLine(null);
                if(data.has("line")) {
                    station.setLine(data.getString("line"));
                }

                station.setDistance(null);
                if(data.has("distance")) {
                    station.setDistance(data.getString("distance"));
                }

                station.setPrefecture(null);
                if(data.has("prefecture")) {
                    station.setPrefecture(data.getString("prefecture"));
                }

                list.add(station);
            }
        }
        catch(JSONException je) {
            list = null;
            //je.printStackTrace();
            Log.d("例外json", "ここか！");
        }
        return list;
    }

    public static void refresh(){
        list=null;
    }
}
