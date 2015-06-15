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
import android.widget.NumberPicker;

public class TimeSettingDialog extends DialogFragment implements View.OnClickListener {

    private Button btnCancel, btnOk;
    private NumberPicker pickerHour, pickerMin, pickerSec;


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

        pickerHour = (NumberPicker) d.findViewById(R.id.number_hour);
        pickerMin = (NumberPicker) d.findViewById(R.id.number_minute);
        pickerSec = (NumberPicker) d.findViewById(R.id.number_second);

        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(23);
        pickerMin.setMinValue(0);
        pickerMin.setMaxValue(59);
        pickerSec.setMinValue(0);
        pickerSec.setMaxValue(59);

        pickerHour.setValue(Integer.parseInt(getArguments().getString(MainActivity.HOUR)));
        pickerMin.setValue(Integer.parseInt(getArguments().getString(MainActivity.MINUTE)));
        pickerSec.setValue(Integer.parseInt(getArguments().getString(MainActivity.SECOND)));

        btnCancel = (Button) d.findViewById(R.id.button_cancel_setting);
        btnOk = (Button) d.findViewById(R.id.button_ok_setting);

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
        if (id == R.id.button_cancel_setting) {
            dismiss();

        } else if (id == R.id.button_ok_setting) {
            int h = pickerHour.getValue();
            int m = pickerMin.getValue();
            int s = pickerSec.getValue();

            ((MainActivity)getActivity()).setTime(h, m, s);
            dismiss();
        }
    }

}
