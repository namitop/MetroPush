package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements LocationListener, LoaderCallbacks<String> {
    //private PushAdapter mAdapter;
    private Push mPush;
    ArrayList<Push> push_list;
    LocationManager mLocationManager;    // ロケーションマネージャ
    SimpleAdapter sa;
    double nowLatitude=35.11111;//仮に。
    double nowLongitude=135.11111;//仮に。
    Bundle bundle;
    TextView test_notificationtext;
    ArrayList<Station> station_list;
    String test_depaturestationname="金沢";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //親クラスをonCreateしている
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //位置情報
        // LocationManagerを取得
        mLocationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();

        // Accuracyを指定(低精度)
        //criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        // PowerRequirementを指定(低消費電力)
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);

        // ロケーションプロバイダの取得
        String provider = mLocationManager.getBestProvider(criteria, true);
        //String provider = mLocationManager.getGps;

        // 取得したロケーションプロバイダを表示
        TextView tv_provider = (TextView) findViewById(R.id.provider);
        tv_provider.setText("Provider: "+provider);

        // LocationListenerを登録
        mLocationManager.requestLocationUpdates(provider, 0, 0, this);

        push_list = new ArrayList<Push>();
        //仮にpush_listの中身を入れておく。どこかで生成する必要があると思われる。（TrainScheduleFactory的な感じで,かつ、SharedPreferencesを使って。）
        push_list.add(new Push("departure", SettingUtils.loadToggleIsChecked(this, R.id.togglebuttondeparture)));
        push_list.add(new Push("arrival", SettingUtils.loadToggleIsChecked(this, R.id.togglebuttonarrival)));
        push_list.add(new Push("everystops", SettingUtils.loadToggleIsChecked(this, R.id.togglebuttoneverystops)));
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for (Push push : push_list) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type", push.getType());
            map.put("isEnable", String.valueOf(push.getIsEnable()));
            data.add(map);
        }
        Log.d("dataの中(onCreate)", data.toString());
        String[] from = {"type", "isEnable"};
        int[] to = {R.id.type, R.id.isEnable};
        SimpleAdapter sa = new SimpleAdapter(this, data, R.layout.row, from, to);

        ListView lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(sa);

        test_notificationtext = (TextView) findViewById(R.id.test_notificationtext);

        TextView test_departure = (TextView) findViewById(R.id.test_departure);
        test_departure.setText("departure: "+SettingUtils.loadDepartureName(this)+SettingUtils.loadDepartureLatitude(this)+SettingUtils.loadDepartureLongitude(this));

        TextView test_arrival = (TextView) findViewById(R.id.test_arrival);
        test_arrival.setText("arrival: "+SettingUtils.loadArrivalName(this)+SettingUtils.loadArrivalLatitude(this)+SettingUtils.loadArrivalLongitude(this));

        //proximityalertのためのradius,expiration
        float radius=500F;//m
        long expiration = -1L;
        PendingIntent pendingIntent = createPendingIntent();

        //設定した出発駅の位置情報。
        double departure_latitude = SettingUtils.loadDepartureLatitude(this);
        double departure_longitude = SettingUtils.loadDepartureLongitude(this);

        //設定した到着駅の位置情報。
        double arrival_latitude = SettingUtils.loadArrivalLatitude(this);
        double arrival_longitude = SettingUtils.loadArrivalLongitude(this);

        Log.d(SettingUtils.loadDepartureName(this) + "(アラームセット)", departure_latitude+"/"+departure_longitude);
        mLocationManager.addProximityAlert(departure_latitude, departure_longitude, radius, expiration, pendingIntent);

        // Bundleにはパラメータを保存する（1）
        bundle = new Bundle();

        // 駅のを返すAPIのURLを格納する（2）
        Log.d("now緯度経度の中身(onCreate)",nowLongitude+":"+nowLatitude);
        Log.d("リクエスト内容(onCreate)","http://express.heartrails.com/api/json?method=getStations&x="+nowLongitude+"&y="+nowLatitude);
        bundle.putString("url", "http://express.heartrails.com/api/json?method=getStations&x="+nowLongitude+"&y="+nowLatitude);
        // LoaderManagerの初期化（3）
        //getLoaderManager().initLoader(0, bundle, this);//このinitloader必要ないかもしれない

        //Button test_button_tweet = new Button();
    }

    protected void onResume(){
        super.onResume();

        // ロケーションマネージャのインスタンスを取得する
        mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 位置情報の更新を受け取るように設定
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // プロバイダ
                0, // 通知のための最小時間間隔
                0, // 通知のための最小距離間隔
                this); // 位置情報リスナー



        push_list = new ArrayList<Push>();
        //仮にpush_listの中身を入れておく。どこかで生成する必要があると思われる。（TrainScheduleFactory的な感じで,かつ、SharedPreferencesを使って。）
        push_list.add(new Push("departure", SettingUtils.loadToggleIsChecked(this, R.id.togglebuttondeparture)));
        push_list.add(new Push("arrival", SettingUtils.loadToggleIsChecked(this, R.id.togglebuttonarrival)));
        push_list.add(new Push("everystops",  SettingUtils.loadToggleIsChecked(this, R.id.togglebuttoneverystops)));
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for(Push push : push_list){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("type", push.getType());
            map.put("isEnable", String.valueOf(push.getIsEnable()));
            data.add(map);
        }
        Log.d("dataの中(onResume)",data.toString());
        String[] from = {"type", "isEnable"};
        int[] to = {R.id.type, R.id.isEnable};
        sa = new SimpleAdapter(this, data, R.layout.row, from, to);

        ListView lv = (ListView)findViewById(R.id.listview);
        lv.setAdapter(sa);

        //通知を設定する。

        //mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //mLocationManager.addProximityAlert (latitude, longitude, radius, expiration, pendingIntent);

    }

    protected void onPause(){
        super.onPause();

        // 位置情報の更新を止める
        mLocationManager.removeUpdates(this);
    }


    //LoaderManger指定されたIDに紐付くLoaderインスタンスの生成要求です。ここで、データのロード準備ができたLoaderインスタンスを返却します。
    @Override
    public Loader<String> onCreateLoader(int id, Bundle bundle) {

        // HttpAsyncLoaderの生成（4）
        HttpAsyncLoader loader = new HttpAsyncLoader(this, bundle.getString("url"));

        // Web APIの呼び出し（5）
        loader.forceLoad();
        return loader;
    }




    //Loaderのデータロード完了通知です。
    @Override
    public void onLoadFinished(Loader<String> loader, String body) {
        Log.d("loaderの中身(onLoadFinished):",loader.toString());
        // 実行結果の書き出し（6）
        if ( loader.getId() == 0 ) {
            if (body != null) {
                Log.d("ボディ(onLoadFinished)", body);
            }
        }
        //TextView textView = (TextView) findViewById(R.id.train_schedule_all);
        // テキストビューのテキストを設定します
        //textView.setText(body);

        StationFactory.refresh();
        station_list =  StationFactory.create(body);
        if(station_list==null) Log.d("null(onLoadFinished)","nullだよ");
        Log.d("緯度", String.valueOf(station_list.get(0).getX()));
        Log.d("経度", String.valueOf(station_list.get(0).getY()));
        Log.d("駅名", station_list.get(0).getName());
        Log.d("何線", station_list.get(0).getLine());
        Log.d("ここからの距離", station_list.get(0).getDistance());
        Log.d("何県？", station_list.get(0).getPrefecture());

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for(Station station : station_list){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("x",String.valueOf(station.getX()));
            map.put("y",String.valueOf(station.getY()));
            map.put("name",station.getName());
            map.put("line",station.getLine());
            map.put("distance",station.getDistance());
            map.put("prefecture",station.getPrefecture());
            data.add(map);
        }


        Log.d("dataの中(onLoadFinished)",data.toString());

        String[] from = {"x", "y", "name", "line", "distance", "prefecture"};
        int[] to = {R.id.x, R.id.y, R.id.name, R.id.line, R.id.distance, R.id.prefecture};
        sa = new SimpleAdapter(this, data, R.layout.test_trainschedulerow, from, to);

        ListView lv = (ListView)findViewById(R.id.test_trainschedulelistview);
        lv.setAdapter(sa);
    }

    //指定されたIDに紐付くLoaderインスタンスの生成要求です。ここで、データのロード準備ができたLoaderインスタンスを返却します。
    @Override
    public void onLoaderReset(Loader<String> loader) {
        // 今回は何も処理しない
    }

    //onLocationChangedは一番最初も読んでいるのかどうか分からない。
    @Override
    public void onLocationChanged(Location location) {

        Log.d("lati","Latitude:"+location.getLatitude());
        Log.d("longi","Longitude:"+location.getLongitude());

        // 緯度の表示
        nowLatitude = location.getLatitude();
        TextView tv_lat = (TextView) findViewById(R.id.latitude);
        tv_lat.setText("Latitude:"+location.getLatitude());

        // 経度の表示
        nowLongitude = location.getLongitude();
        TextView tv_lng = (TextView) findViewById(R.id.longitude);
        tv_lng.setText("Longitude:"+location.getLongitude());


        // 駅のを返すAPIのURLを格納する（2）
        Log.d("now緯度経度の中身(onLocationChanged)",nowLongitude+":"+nowLatitude);
        Log.d("リクエスト内容(onLocationChanged)","http://express.heartrails.com/api/json?method=getStations&x="+nowLongitude+"&y="+nowLatitude);
        bundle.clear();
        bundle.putString("url", "http://express.heartrails.com/api/json?method=getStations&x="+nowLongitude+"&y="+nowLatitude);
        // LoaderManagerの初期化（3）
        getLoaderManager().destroyLoader(0);
        getLoaderManager().initLoader(0, bundle, this);

        if(station_list != null) {
            for (Station station : station_list) {
                Log.d("loc駅/set駅", station.getName() + "/" + test_depaturestationname);
                if (station.getName().equals(test_depaturestationname)) {
                    //Toast.makeText(this, test_depaturestationname + "にきた", Toast.LENGTH_SHORT).show();
                    Log.d("station_listのループ", test_depaturestationname+"にきた。");
                    test_notificationtext.setText(station.getName());
                }
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;        //MenuInflaterを宣言
        MenuInflater inflater = getMenuInflater();
        //MenuInflaterからXMLの取得
        inflater.inflate(R.menu.main, menu);
        //オーバーライドしたものをsuperで呼び出し実行する。
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
        //メニューの中のどのボタンを押したかidで判断しswitch
        switch (item.getItemId()) {
            //メニューの設定が選択された場合
            case R.id.menu_setting:
                //intent先にTweetActivityを設定する。
                Intent intentSetting = new Intent(this, SettingActivity.class);
                //設定したActivityにintentを送る。
                startActivity(intentSetting);
                return true;
            //メニューの今どこ？が選択された場合
            case R.id.menu_now:
                return true;
            //メニューの編集
            case R.id.menu_edit:
                //intent先にTweetActivityを設定する。
                Intent intentEdit = new Intent(this, EditActivity.class);
                //設定したActivityにintentを送る。
                startActivity(intentEdit);
                return true;
        }
        //オーバーライドしたものをsuperで呼び出し実行する。
        return super.onOptionsItemSelected(item);
    }

    private PendingIntent createPendingIntent(){
        Intent intent = new Intent(this, ProximityAlertActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
