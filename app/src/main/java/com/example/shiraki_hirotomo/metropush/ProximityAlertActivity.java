package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by shiraki-hirotomo on 2014/09/24.
 */
public class ProximityAlertActivity extends Activity {
    private String tag = this.getClass().getSimpleName();
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);
        Log.d(tag, "onCreate starts.");

        tv = (TextView)findViewById(R.id.tv1);

        showStatus();//                                                  ①
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(tag,"onDestroy starts.");
        showStatus();//                                                  ①
    }

    private void showStatus(){//                                         ②
        Intent intent = getIntent();
        boolean flag = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,false);
        tv.setText(Boolean.toString(flag));
    }
}
