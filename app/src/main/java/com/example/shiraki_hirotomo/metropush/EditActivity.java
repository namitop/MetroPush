package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //出発駅のedittext
        EditText editDeparture = (EditText)findViewById(R.id.editDeparture);
        // エディットテキストのテキストを設定します
        editDeparture.setText("出発駅");
        // エディットテキストのテキストを全選択します
        editDeparture.selectAll();
        // エディットテキストのテキストを取得します
        String textDeparture = editDeparture.getText().toString();
        Toast.makeText(this, textDeparture, Toast.LENGTH_LONG);

        //到着駅のedittext
        EditText editArrival = (EditText)findViewById(R.id.editArrival);
        // エディットテキストのテキストを設定します
        editArrival.setText("到着駅");
        // エディットテキストのテキストを全選択します
        editArrival.selectAll();
        // エディットテキストのテキストを取得します
        String textArrival = editArrival.getText().toString();
        Toast.makeText(this, textArrival, Toast.LENGTH_LONG);
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
