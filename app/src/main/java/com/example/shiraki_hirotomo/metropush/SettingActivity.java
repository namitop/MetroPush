package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class SettingActivity extends Activity implements OnCheckedChangeListener, OnClickListener{
    //キー
    private static final String REQUEST_TOKEN = "request_token";
    //mCallbackURLを宣言
    private String mCallbackURL;
    //mTwitterを宣言
    private Twitter mTwitter;
    //RequestToken型変数mRequestTokenを宣言
    private RequestToken mRequestToken;
    //ログイン、ログアウトの表示を切り替えるためにグローバルにした。
    Button buttonTwitterLoginLogout;

    TextView textViewFrom;
    EditText editTextTo;
    EditText editTextMessage;

    ToggleButton tBtnContactIsEnable;
    ToggleButton tBtnDeparture;
    ToggleButton tBtnArrival;
    ToggleButton tBtnEveryStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //mCallbackURLにtwitter_callback_urlの文字列を代入
        mCallbackURL = Config.CALLBACK_URL;

        //連絡を有効無効にするトグル
        //togglebuttonインスタンスを取得
        tBtnContactIsEnable = (ToggleButton) findViewById(R.id.togglebuttoncontactisenable);
        //リスナーを設定
        tBtnContactIsEnable.setOnCheckedChangeListener(this);

        //ツイッターと連携
        buttonTwitterLoginLogout = (Button) findViewById(R.id.buttontwitterloginlogout);
//        if(TwitterUtils.hasAccessToken(this)) {
//            buttonTwitterLoginLogout.setText("ログアウト");
//        }else{
//            buttonTwitterLoginLogout.setText("ログイン");
//        }
        buttonTwitterLoginLogout.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               //OAuth認証（厳密には認可）を開始します。
               if (!TwitterUtils.hasAccessToken(v.getContext())) {
                   startAuthorize();
               }else{
                   logout();
               }
           }
        });

        textViewFrom = (TextView) findViewById(R.id.twitterfromaccount);
        editTextTo = (EditText) findViewById(R.id.twittertoaccount);
        editTextMessage = (EditText) findViewById(R.id.contactmessage);


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
        Button button = (Button) findViewById(R.id.buttonsetting);
        // ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        button.setOnClickListener(SettingActivity.this);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SettingUtils.storeTwitterToName(this, editTextTo.getText());
//                SettingUtils.storeTwitterMessage(this, editTextMessage.getText());
//                // ボタンがクリックされた時に呼び出されます
//                //Button button = (Button) v;
//                //Toast.makeText(EditActivity.this, "onClick()",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), MainActivity.class);//v.getContextってどういう意味？
//                startActivity(intent);
//                //今いるアクティビティを終了
//                finish();
//            }
//        });
    }

    protected void onResume(){
        super.onResume();
        if(TwitterUtils.hasAccessToken(this)) {
            buttonTwitterLoginLogout.setText("ログアウト");
        }else{
            buttonTwitterLoginLogout.setText("ログイン");
        }

        tBtnContactIsEnable.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttoncontactisenable));
        tBtnDeparture.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttondeparture));
        tBtnArrival.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttonarrival));
        tBtnEveryStops.setChecked(SettingUtils.loadToggleIsChecked(this, R.id.togglebuttoneverystops));

        textViewFrom.setText(SettingUtils.loadTwitterUser(this));



        if(SettingUtils.hasDeparture(this)){
            editTextTo.setText(SettingUtils.loadTwitterToName(this));
        }else{
            editTextTo.setHint("宛先未設定(twitterの@からはじまるユーザーIDを記入してください)");
        }

        if(SettingUtils.hasDeparture(this)){
            editTextMessage.setText(SettingUtils.loadTwitterMessage(this));
        }else{
            editTextMessage.setHint("メッセージを書いてください。メッセージの文末に(@〜〜駅)とつけて、送られます。");
        }
    }


    //トグルによる変更
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(isChecked){
            Toast.makeText(this, buttonView.getId() + ":Checked!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "UnChecked!", Toast.LENGTH_SHORT).show();
        }
        SettingUtils.storeToggleIsChecked(this, buttonView.getId(), isChecked);
//SettingUtilsのほうでまとめた
//        if(buttonView.getId()==R.id.togglebuttoncontactisenable){
//            if(isChecked){
//                SettingUtils.storeContactIsEnable(this, isChecked);
//            }else{
//
//            }
//        }
    }

    public void onClick(View v) {

        SettingUtils.storeTwitterToName(this, editTextTo.getText().toString());
        SettingUtils.storeTwitterMessage(this, editTextMessage.getText().toString());
        // ボタンがクリックされた時に呼び出されます
        //Button button = (Button) v;
        //Toast.makeText(EditActivity.this, "onClick()",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(v.getContext(), MainActivity.class);//v.getContextってどういう意味？
        startActivity(intent);
        //今いるアクティビティを終了
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting, menu);
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




    //ここから下はほとんどTwitterOAuthActivityを参考
    //アクティビティの中断などにより呼び出される。
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //bundleにリクエストトークンを保存している。（キーはREQUEST_TOKEN）（リクエストトークンはserializableされている。）
        outState.putSerializable(REQUEST_TOKEN, mRequestToken);
    }

    //中断されたアクティビティが再開されるとき。
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //キーをREQUEST_TOKENでデータを呼び出し、代入する。
        mRequestToken = (RequestToken) savedInstanceState.getSerializable(REQUEST_TOKEN);
    }

    /**
     * OAuth認証（厳密には認可）を開始します。
     *
     * @param //listener
     */
    private void startAuthorize() {
        //AsyncTaskがた変数taskを宣言。
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            //doInBackGroundにより、メインスレッドとは別のスレッドで実行している。
            @Override
            protected String doInBackground(Void... params) {
                try {
                    //mTwitterのRequestTokenを取得し、代入。（RequestToken型）
                    // TwitterUtilsのgetTwitterInstanceによってコンシューマ・キーやコンシューマ・シークレット、アクセストークンがセットされたTwitter型のインスタンスが代入されている。
                    mTwitter = TwitterUtils.getTwitterInstance(SettingActivity.this);
                    mRequestToken = mTwitter.getOAuthRequestToken(mCallbackURL);
                    //認証のためにブラウザを開くが、そのブラウザのURLを返り値にしている
                    return mRequestToken.getAuthorizationURL();
                } catch (TwitterException e) {
                    //スタックトレースを出力
                    e.printStackTrace();
                }
                //返り値をnullにする
                return null;
            }
            //doInBackgroundメソッドの戻り値をこのメソッドの引数として受け取っている。メインスレッドで実行される。
            @Override
            protected void onPostExecute(String url) {
                if (url != null) {
                    //Intentによってブラウザに送る。(String action, Uri uri)になっている。uriのほうにアクセスする
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    // 失敗。。。
                }
            }
        };
        //設定したAsyncTask型変数taskを呼び出す。
        task.execute();
    }


    //onNewIntentにブラウザからIntentが送られてくる。
    @Override
    public void onNewIntent(Intent intent) {
        //intentがnullの場合、intentのgetDataがnullの場合、intentのgetDataのstringの先頭がmCallbackURLと同じじゃない場合は、
        //（mCallbackURLの先頭と同じじゃないとだめなのは、ブラウザからアプリに戻るときに、スキームの後ろにクエリがついているから。）
        if (intent == null
                || intent.getData() == null
                || !intent.getData().toString().startsWith(mCallbackURL)) {
            //返り値なしで終了
            return;
        }
        //"oauth_varifier"をキーにString型変数verifierに値を代入する。verifierは認証関係のやつ
        //このverifierのparams[0]にaccesstokenが入っている。
        String verifier = intent.getData().getQueryParameter("oauth_verifier");
        Log.d("verifierの中身", verifier);

        //AsyncTask<String, Void, AccessToken>
        AsyncTask<String, Void, AccessToken> task = new AsyncTask<String, Void, AccessToken>() {
            @Override
            protected AccessToken doInBackground(String... params) {
                try {
                    //mTwitterのOAuthAccessToken返り値にする。（キーは、mRequestTokenとverifierのparams[0]）
                    return mTwitter.getOAuthAccessToken(mRequestToken, params[0]);
                } catch (TwitterException e) {
                    //スタックとレースを出力
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(AccessToken accessToken) {
                //accesstokenがあれば成功
                if (accessToken != null) {
                    // 認証成功！
                    //トースト表示
                    showToast("認証成功！");
                    //successouathメソッドをaccesstokenを引数に実行
                    successOAuth(accessToken);
                } else {
                    // 認証失敗。。。
                    showToast("認証失敗。。。");
                }
            }
        };
        //verifierを引数にtaskを呼び出す。verifierの値は、doinbackgroundに渡される。
        task.execute(verifier);
    }

    //successOAuth
    private void successOAuth(AccessToken accessToken) {
        TwitterUtils.storeAccessToken(this, accessToken);
        //mTwitter.getId();
        SettingUtils.storeTwitterUser(this, mTwitter);

        mTwitter = null;
        buttonTwitterLoginLogout.setText("ログアウト");
        //intent先をMainActivity
        //Intent intent = new Intent(this, MainActivity.class);
        //設定されたintentを実行
        //startActivity(intent);
        //今いるアクティビティを終了
        //finish();
    }

    private void logout(){
        //preferencesに保存しているaccesstokenのtoken・tokensecretを消す。
        TwitterUtils.removeAccessToken(this);
        //accesstokenをなくす。（ログアウトの手順）
        //mTwitter.setOAuthAccessToken(null);
        buttonTwitterLoginLogout.setText("ログイン");
        //stackoverflowに載ってたけど、docにないし使えん。
        //mTwitter.shutdown();

    }
    //トーストを長めに表示
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
