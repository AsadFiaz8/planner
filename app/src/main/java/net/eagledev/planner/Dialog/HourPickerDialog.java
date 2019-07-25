package net.eagledev.planner.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import net.eagledev.planner.Formatter;
import net.eagledev.planner.R;

import java.util.Calendar;

public class HourPickerDialog {

    Context context;
    Dialog dialog;
    NumberPicker hourPicker;
    NumberPicker minutePicker;

    Formatter f = new Formatter();
    HourPickerDialogListener listener;
    int hour = 0;
    int minute = 0;
    int requestCode;


    public HourPickerDialog(Context context){
        this.context = context;
        try {
            listener = (HourPickerDialogListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString() + "must implements HourPickerDialogListener");
        }

    }
    public HourPickerDialog(Context context, int hour, int minute){
        this.context = context;
        this.hour = hour;
        this.minute = minute;
        try {
            listener = (HourPickerDialogListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString() + "must implements HourPickerDialogListener");
        }

    }

    public void ShowDialog(final int requestCode){
        this.requestCode = requestCode;
        dialog = new Dialog(context);
        dialog.setTitle("Hour Picker");
        dialog.setContentView(R.layout.dialog_hour_picker);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        hourPicker = dialog.findViewById(R.id.dialog_picker_hour);
        minutePicker = dialog.findViewById(R.id.dialog_picker_minute);

        String[] displayHours = new String[24];
        String[] displayMinutes = new String[60];
        for (int h = 0; h < displayHours.length; h++) {
            displayHours[h] = f.z(h);
        }
        for (int i = 0; i < displayMinutes.length; i++) {
            displayMinutes[i] = f.z(i);
        }


        hourPicker.setDisplayedValues(displayHours);
        minutePicker.setDisplayedValues(displayMinutes);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        hourPicker.setValue(hour);
        minutePicker.setValue(minute);
        Button button = dialog.findViewById(R.id.dialog_picker_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getHourPickerDialogTime(requestCode, hourPicker.getValue(), minutePicker.getValue());
                dialog.dismiss();
            }
        });



    }




    public interface HourPickerDialogListener{
        void getHourPickerDialogTime(int requestCode, int hour, int minute);
    }

}
