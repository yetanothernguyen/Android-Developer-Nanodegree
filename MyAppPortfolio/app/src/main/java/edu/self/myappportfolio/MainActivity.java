package edu.self.myappportfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn;
        btn = (Button) findViewById(R.id.btn_spotify);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_scores);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_library);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_buildit);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_xyz);
        btn.setOnClickListener(this);

        btn = (Button) findViewById(R.id.btn_capstone);
        btn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int messageStrId = 0;
        switch (v.getId()) {
            case R.id.btn_spotify:
                messageStrId = R.string.msg_app_spotify;
                break;
            case R.id.btn_scores:
                messageStrId = R.string.msg_app_scores;
                break;
            case R.id.btn_library:
                messageStrId = R.string.msg_app_library;
                break;
            case R.id.btn_buildit:
                messageStrId = R.string.msg_app_buildit;
                break;
            case R.id.btn_xyz:
                messageStrId = R.string.msg_app_xyz;
                break;
            case R.id.btn_capstone:
                messageStrId = R.string.msg_app_capstone;
                break;
        }

        if (messageStrId > 0) {
            Toast toast = Toast.makeText(MainActivity.this, messageStrId, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
