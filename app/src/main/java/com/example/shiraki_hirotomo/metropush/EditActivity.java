package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class EditActivity extends Activity implements OnCheckedChangeListener, OnClickListener, LoaderManager.LoaderCallbacks<String> {

    EditText editDeparture;
    EditText editArrival;
    ToggleButton tBtnDeparture;
    ToggleButton tBtnArrival;
    ToggleButton tBtnEveryStops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        //出発駅のedittext
        editDeparture = (EditText) findViewById(R.id.editDeparture);
        // エディットテキストのテキストを取得します
        String textDeparture = editDeparture.getText().toString();
        Toast.makeText(this, textDeparture, Toast.LENGTH_LONG);
        //到着駅のedittext
        editArrival = (EditText) findViewById(R.id.editArrival);
        // エディットテキストのテキストを取得します
        String textArrival = editArrival.getText().toString();
        Toast.makeText(this, textArrival, Toast.LENGTH_LONG);

        //各通知設定項目
        //togglebuttonインスタンスを取得
        tBtnDeparture = (ToggleButton) findViewById(R.id.togglebuttondeparture);
        tBtnArrival = (ToggleButton) findViewById(R.id.togglebuttonarrival);
        tBtnEveryStops = (ToggleButton) findViewById(R.id.togglebuttoneverystops);
        //リスナーを設定
        tBtnDeparture.setOnCheckedChangeListener(this);
        tBtnArrival.setOnCheckedChangeListener(this);
        tBtnEveryStops.setOnCheckedChangeListener(this);

        //完了ボタン
        //buttonインスタンスを取得
        Button button = (Button) findViewById(R.id.buttonedit);

        button.setOnClickListener(EditActivity.this);
    }

    public void onClick(View v) {

        SettingUtils.storeDeparture(this, editDeparture.getText().toString());
        SettingUtils.storeArrival(this, editArrival.getText().toString());



        // Bundleにはパラメータを保存する（1）
        Bundle bundle_departure = new Bundle();
        Bundle bundle_arrival = new Bundle();

        // APIのURLを格納する（2）
        try {
            bundle_departure.putString("url_departure", "http://express.heartrails.com/api/json?method=getStations&name=" + URLEncoder.encode(editDeparture.getText().toString(), "utf-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }

        try {
            bundle_arrival.putString("url_arrival", "http://express.heartrails.com/api/json?method=getStations&name=" + URLEncoder.encode(editArrival.getText().toString(), "utf-8"));
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }



        // LoaderManagerの初期化（3）
        getLoaderManager().initLoader(0, bundle_departure, this);
        getLoaderManager().initLoader(1, bundle_arrival, this);



        Intent intent = new Intent(v.getContext(), MainActivity.class);//v.getContextってどういう意味？
        startActivity(intent);
        //今いるアクティビティを終了
        finish();
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(isChecked){
            Toast.makeText(this, buttonView.getId()+":Checked!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "UnChecked!", Toast.LENGTH_SHORT).show();
        }
        SettingUtils.storeToggleIsChecked(this, buttonView.getId(), isChecked);
    }

    protected void onResume(){
        super.onResume();

        tBtnDeparture.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttondeparture));
        tBtnArrival.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttonarrival));
        tBtnEveryStops.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttoneverystops));

        if(SettingUtils.hasDeparture(this)){
            editDeparture.setText(SettingUtils.loadDeparture(this));
        }else{
            editDeparture.setHint("出発駅");
        }

        if(SettingUtils.hasArrival(this)){
            editArrival.setText(SettingUtils.loadArrival(this));
        }else{
            editArrival.setHint("到着駅");
        }
    }


    //LoaderManger指定されたIDに紐付くLoaderインスタンスの生成要求です。ここで、データのロード準備ができたLoaderインスタンスを返却します。
    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {
        if(id==0) {
            // HttpAsyncLoaderの生成（4）
            HttpAsyncLoader loader = new HttpAsyncLoader(this, bundle.getString("url_departure"));
            // Web APIの呼び出し（5）
            loader.forceLoad();
            return loader;
        }else{ //else if(id==1) だった。このメソッドから絶対実行されるreturnがなくなるからただのelseにしたけど、もしかしたらエラーで通るとき-1とかになってたりしたら、elseにするのはだめかも。
            // HttpAsyncLoaderの生成（4）
            HttpAsyncLoader loader = new HttpAsyncLoader(this, bundle.getString("url_arrival"));
            // Web APIの呼び出し（5）
            loader.forceLoad();
            return loader;
        }
    }




    //Loaderのデータロード完了通知です。
    @Override
    public void onLoadFinished(Loader<String> loader, String body) {
        Log.d("loaderの中身:", loader.toString());
        // 実行結果の書き出し（6）
        if ( loader.getId() == 0 ) {
            if (body != null) {
                Log.d("ボディ", body);
                StationSpotFactory.refresh();
                ArrayList<StationSpot> departure_station_spot_list =  StationSpotFactory.create(body);
                if(departure_station_spot_list==null) Log.d("null","nullだよ");

                Log.d("(出)駅名", departure_station_spot_list.get(0).getName());
                Log.d("(出)longitude", String.valueOf(departure_station_spot_list.get(0).getX()));
                Log.d("(出)latitude", String.valueOf(departure_station_spot_list.get(0).getY()));
                SettingUtils.storeDepartureName(this, departure_station_spot_list.get(0).getName());
                SettingUtils.storeDepartureLongitude(this, departure_station_spot_list.get(0).getX());
                SettingUtils.storeDepartureLatitude(this, departure_station_spot_list.get(0).getY());
            }
        }else{
            if (body != null) {
                Log.d("ボディ", body);
                StationSpotFactory.refresh();
                ArrayList<StationSpot> arrival_station_spot_list =  StationSpotFactory.create(body);
                if(arrival_station_spot_list==null) Log.d("null","nullだよ");
                Log.d("(着)駅名", arrival_station_spot_list.get(0).getName());
                Log.d("(着)longitude", String.valueOf(arrival_station_spot_list.get(0).getX()));
                Log.d("(着)latitude", String.valueOf(arrival_station_spot_list.get(0).getY()));
                SettingUtils.storeArrivalName(this, arrival_station_spot_list.get(0).getName());
                SettingUtils.storeArrivalLongitude(this, arrival_station_spot_list.get(0).getX());
                SettingUtils.storeArrivalLatitude(this, arrival_station_spot_list.get(0).getY());
            }
        }
    }
    //指定されたIDに紐付くLoaderインスタンスの生成要求です。ここで、データのロード準備ができたLoaderインスタンスを返却します。
    @Override
    public void onLoaderReset(Loader<String> loader) {
        // 今回は何も処理しない
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
