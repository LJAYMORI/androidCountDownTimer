package com.ljaymori.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String DIALOG_TIME_SETTING = "time_setting";

    private TextView tvTime;
    private Button btnSetting, btnStop, btnStart;

    private CountDownTimer timer;
    private long millisec = 0;

    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_time_setting) {
            showTimerDialog();

        } else if (id == R.id.button_stop_time) {
            stopTimer();

        } else if (id == R.id.button_start_time) {
            if(isStarted) {
                pauseTimer();

            } else {
                startTimer();

            }
        }
    }

    /**
     * 앱 시작시 초기화 메소드입니다.
     */
    private void init() {
        tvTime = (TextView) findViewById(R.id.text_time);

        btnSetting = (Button) findViewById(R.id.button_time_setting);
        btnStop = (Button) findViewById(R.id.button_stop_time);
        btnStart = (Button) findViewById(R.id.button_start_time);

        btnSetting.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    /**
     * 타이머 다이얼로그를 띄웁니다.
     */
    private void showTimerDialog() {
        if(isStarted) {
            pauseTimer();
        }

        StringTokenizer tokenizer = new StringTokenizer(tvTime.getText().toString(), ":");
        String hour = tokenizer.nextToken();
        String minute = tokenizer.nextToken();
        String second = tokenizer.nextToken();

        Bundle args = new Bundle();
        args.putString(TimeSettingDialog.HOUR, hour);
        args.putString(TimeSettingDialog.MINUTE, minute);
        args.putString(TimeSettingDialog.SECOND, second);

        TimeSettingDialog dialog = new TimeSettingDialog();
        dialog.setArguments(args);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), DIALOG_TIME_SETTING);
    }

    /**
     * 타이머를 종료합니다.
     */
    private void stopTimer() {
        if(timer != null) {
            timer.onFinish();
            timer.cancel();
            millisec = 0;

            btnStart.setText(getResources().getString(R.string.start));
        }
    }

    /**
     * 타이머를 중지합니다.
     */
    private void pauseTimer() {
        btnStart.setText(getResources().getString(R.string.start));
        isStarted = false;
        timer.cancel();
    }

    /**
     * 타이머를 시작합니다.
     */
    private void startTimer() {
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
                Toast.makeText(MainActivity.this, "onFinish", Toast.LENGTH_SHORT).show();
                tvTime.setText(getResources().getString(R.string.default_time));
            }

        }.start();
    }

    /**
     * 값 들을 타이머에 셋팅합니다.
     */
    public void setTime(int hours, int minutes, int seconds) {
        millisec = ((hours * 60 * 60) + (minutes * 60) + seconds) * 1000;

        String time = String.format("%02d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        tvTime.setText(time);
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

}
