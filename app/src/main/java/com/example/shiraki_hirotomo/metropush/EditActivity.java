package com.example.shiraki_hirotomo.metropush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


public class EditActivity extends Activity implements OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //出発駅のedittext
        EditText editDeparture = (EditText) findViewById(R.id.editDeparture);
        // エディットテキストのテキストを設定します
        editDeparture.setText("出発駅");
        // エディットテキストのテキストを全選択します
        editDeparture.selectAll();
        // エディットテキストのテキストを取得します
        String textDeparture = editDeparture.getText().toString();
        Toast.makeText(this, textDeparture, Toast.LENGTH_LONG);
        //到着駅のedittext
        EditText editArrival = (EditText) findViewById(R.id.editArrival);
        // エディットテキストのテキストを設定します
        editArrival.setText("到着駅");
        // エディットテキストのテキストを全選択します
        editArrival.selectAll();
        // エディットテキストのテキストを取得します
        String textArrival = editArrival.getText().toString();
        Toast.makeText(this, textArrival, Toast.LENGTH_LONG);

        //各通知設定項目
        //togglebuttonインスタンスを取得
        ToggleButton tBtnDeparture = (ToggleButton) findViewById(R.id.togglebuttondeparture);
        ToggleButton tBtnArrival = (ToggleButton) findViewById(R.id.togglebuttonarrival);
        ToggleButton tBtnEveryStops = (ToggleButton) findViewById(R.id.togglebuttoneverystops);
        //リスナーを設定
        tBtnDeparture.setOnCheckedChangeListener(this);
        tBtnArrival.setOnCheckedChangeListener(this);
        tBtnEveryStops.setOnCheckedChangeListener(this);

        //完了ボタン
        //buttonインスタンスを取得
        Button button = (Button) findViewById(R.id.button);
        // ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ボタンがクリックされた時に呼び出されます
                Button button = (Button) v;
                //Toast.makeText(EditActivity.this, "onClick()",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);//v.getContextってどういう意味？
                startActivity(intent);
                //今いるアクティビティを終了
                finish();
            }
        });
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(isChecked){
            Toast.makeText(this, buttonView.getId()+":Checked!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "UnChecked!", Toast.LENGTH_SHORT).show();
        }
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
