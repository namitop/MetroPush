package com.example.shiraki_hirotomo.metropush;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shiraki-hirotomo on 2014/09/23.
 */
public class StationSpotFactory {
    public static ArrayList<StationSpot> list;

    /*
     * テキストデータの解析
     */
    public static ArrayList<StationSpot> create(String response) {

        Log.d("createの引数のresponse(StationFactory)", response);
        if(list == null){
            list = new ArrayList<StationSpot>();
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
                StationSpot stationSpot = new StationSpot();
                JSONObject data = dataArray.getJSONObject(i);

                stationSpot.setX(0);
                if(data.has("x")) {
                    stationSpot.setX(data.getDouble("x"));
                }
                stationSpot.setY(0);
                if(data.has("y")) {
                    stationSpot.setY(data.getDouble("y"));
                }

                stationSpot.setName(null);
                if(data.has("name")) {
                    stationSpot.setName(data.getString("name"));
                }

                stationSpot.setLine(null);
                if(data.has("line")) {
                    stationSpot.setLine(data.getString("line"));
                }

                stationSpot.setPrefecture(null);
                if(data.has("prefecture")) {
                    stationSpot.setPrefecture(data.getString("prefecture"));
                }

                list.add(stationSpot);
            }
        }
        catch(JSONException je) {
            list = null;
            //je.printStackTrace();
            Log.d("例外json", "ここか！無い駅を指定したときもなるよ。四ツ谷じゃなくて四谷とかも。");
        }
        return list;
    }

    public static void refresh(){
        list=null;
    }
}
