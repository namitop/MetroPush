package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity{
    //private PushAdapter mAdapter;
    private Push mPush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //親クラスをonCreateしている
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(PushUtils.hasPush(this)){
            ArrayList<Push> push_list = new ArrayList<Push>();
            //仮にpush_listの中身を入れておく。どこかで生成する必要があると思われる。（TrainScheduleFactory的な感じで,かつ、SharedPreferencesを使って。）
            push_list.add(new Push("departure",true));
            push_list.add(new Push("arrival",true));
            push_list.add(new Push("everystops",false));
            ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
            for(Push push : push_list){
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("type", push.getType());
                map.put("isEnable", String.valueOf(push.getIsEnable()));
                data.add(map);
            }
            Log.d("dataの中",data.toString());
            String[] from = {"type", "isEnable"};
            int[] to = {R.id.type, R.id.isEnable};
            SimpleAdapter sa = new SimpleAdapter(this, data, R.layout.row, from, to);

            ListView lv = (ListView)findViewById(R.id.listview);
            lv.setAdapter(sa);
        //}


    }

//    private class PushAdapter extends ArrayAdapter<Status>{
//        //LayoutInflaterは、ほかのxmlリソースのViewを取り扱える仕掛けです。
//        private LayoutInflater mInflater;
//        //TwitterAdapterのコンストラクタ。初期化をしている。
//        public TweetAdapter(Context context) {
//            super(context, android.R.layout.simple_list_item_1);
//            //layoutinflaterはgetSystemServiceメソッドから取得する。getSystemServiceメソッドを使えば、wifiやusbなどハードウェア関連のサービスも使えるようになる。
//            mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                //動的にViewオブジェクトを得ている。layoutのlist_item_tweetを使っている。
//                convertView = mInflater.inflate(R.layout.list_item_tweet, null);
//            }
//            //リストのpositionにあるもののstatusを取り出す
//            Status item = getItem(position);
//            //convertViewにあるidがnameのviewを取り出し、textview型変数nameに代入(ツイートしたユーザの名前)
//            TextView name = (TextView) convertView.findViewById(R.id.name);
//            //textview型変数nameにitemのuserのnameをセットする
//            name.setText(item.getUser().getName());
//            //convertViewにあるidがscreen_nameのviewを取り出し、textview型変数screennameに代入(ツイートしたユーザのID)
//            TextView screenName = (TextView) convertView.findViewById(R.id.screen_name);
//            //textview型変数screennameにitemのuserのscreennameをセットする
//            screenName.setText("@" + item.getUser().getScreenName());
//            //convertViewにあるidがtextのviewを取り出し、textview型変数textに代入(ツイートの投稿内容)
//            TextView text = (TextView) convertView.findViewById(R.id.text);
//            //textview型変数textにitemのtextをセットする
//            text.setText(item.getText());
//            //convertViewのidがiconのものをSmartImageView型変数iconに代入する
//            SmartImageView icon = (SmartImageView) convertView.findViewById(R.id.icon);
//            //SmartImageView型変数iconにitemのuserのprofileimageURLをセットする
//            icon.setImageUrl(item.getUser().getProfileImageURL());
//            //いろんなものをセットしたconvertviewを戻り値として返す。
//            return convertView;
//        }
//    }

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
}
