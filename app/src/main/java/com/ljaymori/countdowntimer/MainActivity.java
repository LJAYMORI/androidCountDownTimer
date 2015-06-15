package com.ljaymori.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String DIALOG_TIME_SETTING = "time_setting";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String SECOND = "second";

    private TextView tvTime;
    private Button btnSetting, btnStop, btnStart;

    private CountDownTimer timer;
    private long millisec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView) findViewById(R.id.text_time);

        btnSetting = (Button) findViewById(R.id.button_time_setting);
        btnStop = (Button) findViewById(R.id.button_stop_time);
        btnStart = (Button) findViewById(R.id.button_start_time);

        btnSetting.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);

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

    private boolean isStarted = false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_time_setting) {

            StringTokenizer tokenizer = new StringTokenizer(tvTime.getText().toString(), ":");
            String hour = tokenizer.nextToken();
            String minute = tokenizer.nextToken();
            String second = tokenizer.nextToken();

            Bundle args = new Bundle();
            args.putString(HOUR, hour);
            args.putString(MINUTE, minute);
            args.putString(SECOND, second);

            TimeSettingDialog dialog = new TimeSettingDialog();
            dialog.setArguments(args);
            dialog.setCancelable(false);
            dialog.show(getFragmentManager(), DIALOG_TIME_SETTING);

        } else if (id == R.id.button_stop_time) {
            if(timer != null) {
                timer.onFinish();
                timer.cancel();
                millisec = 0;

                btnStart.setText(getResources().getString(R.string.start));
            }

        } else if (id == R.id.button_start_time) {
            if(isStarted) {
                btnStart.setText(getResources().getString(R.string.start));
                isStarted = false;
                timer.cancel();

            } else {
                btnStart.setText(getResources().getString(R.string.pause));
                isStarted = true;
                timer = new CountDownTimer(millisec, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        millisec = millisUntilFinished;
                        long hour = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                        long minute = (millisUntilFinished / (1000 * 60)) % 60;
                        long second = (millisUntilFinished / 1000) % 60;

                        String time = String.format("%02d", hour) + ":"
                                + String.format("%02d", minute) + ":"
                                + String.format("%02d", second);

                        tvTime.setText(time);
                    }

                    @Override
                    public void onFinish() {
                        //TODO notification
                        tvTime.setText(getResources().getString(R.string.default_time));
                    }
                }.start();
            }
        }
    }

    public void setTime(int h, int m, int s) {
        millisec = ((h * 60 * 60) + (m * 60) + s) * 1000;

        String time = String.format("%02d", h) + ":"
                + String.format("%02d", m) + ":"
                + String.format("%02d", s);

        tvTime.setText(time);
    }
}
