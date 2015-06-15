package com.ljaymori.countdowntimer;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TimeSettingDialog extends DialogFragment implements View.OnClickListener {

    private Button btnUpHour, btnUpMin, btnUpSec, btnDownHour, btnDownMin, btnDownSec;
    private Button btnCancel, btnOk;
    private EditText etHour, etMin, etSec;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());

        // Hide title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Full Screen
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,	WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        // Transparent background
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_setting_time);

        init(dialog);

        return dialog;
    }

    private void init(Dialog d) {
        btnUpHour = (Button) d.findViewById(R.id.button_hour_up_setting);
        btnUpMin = (Button) d.findViewById(R.id.button_minute_up_setting);
        btnUpSec = (Button) d.findViewById(R.id.button_sec_up_setting);
        btnDownHour = (Button) d.findViewById(R.id.button_hour_down_setting);
        btnDownMin = (Button) d.findViewById(R.id.button_minute_down_setting);
        btnDownSec = (Button) d.findViewById(R.id.button_sec_down_setting);

        etHour = (EditText) d.findViewById(R.id.edit_hour_setting);
        etHour.setText(getArguments().getString(MainActivity.HOUR));
        etMin = (EditText) d.findViewById(R.id.edit_minute_setting);
        etMin.setText(getArguments().getString(MainActivity.MINUTE));
        etSec = (EditText) d.findViewById(R.id.edit_sec_setting);
        etSec.setText(getArguments().getString(MainActivity.SECOND));

        btnCancel = (Button) d.findViewById(R.id.button_cancel_setting);
        btnOk = (Button) d.findViewById(R.id.button_ok_setting);

        btnUpHour.setOnClickListener(this);
        btnUpMin.setOnClickListener(this);
        btnUpSec.setOnClickListener(this);
        btnDownHour.setOnClickListener(this);
        btnDownMin.setOnClickListener(this);
        btnDownSec.setOnClickListener(this);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.button_hour_up_setting) {
            upHour(etHour.getText().toString());

        } else if (id == R.id.button_hour_down_setting) {
            downHour(etHour.getText().toString());

        } else if (id == R.id.button_minute_up_setting) {
            upMinute(etMin.getText().toString());

        } else if (id == R.id.button_minute_down_setting) {
            downMinute(etMin.getText().toString());

        } else if (id == R.id.button_sec_up_setting) {
            upSecond(etSec.getText().toString());

        } else if (id == R.id.button_sec_down_setting) {
            downSecond(etSec.getText().toString());

        } else if (id == R.id.button_cancel_setting) {
            dismiss();

        } else if (id == R.id.button_ok_setting) {
            int h = Integer.parseInt(etHour.getText().toString());
            int m = Integer.parseInt(etMin.getText().toString());
            int s = Integer.parseInt(etSec.getText().toString());

            ((MainActivity)getActivity()).setTime(h, m, s);
            dismiss();
        }
    }

    private void upHour(String hour) {
        try {
            int h = Integer.parseInt(hour);
            etHour.setText(String.valueOf(++h % 24));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Hour is not number. - " + hour, Toast.LENGTH_SHORT).show();
        }
    }

    private void downHour(String hour) {
        try {
            int h = Integer.parseInt(hour);
            if(h == 0) {
                h = 23;
            } else {
                h = --h % 24;
            }
            etHour.setText(String.valueOf(h));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "param is not number. - " + hour, Toast.LENGTH_SHORT).show();
        }
    }

    private void upMinute(String min) {
        try {
            int m = Integer.parseInt(min);
            etMin.setText(String.valueOf(++m % 60));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "param is not number. - " + min, Toast.LENGTH_SHORT).show();
        }
    }

    private void downMinute(String min) {
        try {
            int m = Integer.parseInt(min);
            if(m == 0) {
                m = 59;
            } else {
                m = --m % 60;
            }
            etMin.setText(String.valueOf(m));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "param is not number. - " + min, Toast.LENGTH_SHORT).show();
        }
    }

    private void upSecond(String sec) {
        try {
            int s = Integer.parseInt(sec);
            etSec.setText(String.valueOf(++s % 60));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "param is not number. - " + sec, Toast.LENGTH_SHORT).show();
        }
    }

    private void downSecond(String sec) {
        try {
            int s = Integer.parseInt(sec);
            if(s == 0) {
                s = 59;
            } else {
                s = --s % 60;
            }
            etSec.setText(String.valueOf(s));

        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "param is not number. - " + sec, Toast.LENGTH_SHORT).show();
        }
    }
}
