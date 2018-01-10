package cn.ttsx.face.view.timepicker;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.ttsx.face.utils.ToastUtils;


/**
 * Created by XL on 2017/3/27.
 */

public class TimePickerUtils {
    public static void showDayAndTimePacker(FragmentManager fragmentManager, SublimePickerFragment.Callback callback){
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getDayAndTimeOptions();

        if (!optionsPair.first) { // If options are not valid
            ToastUtils.showShortToast("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }

    public static void showDayAndTimePacker(FragmentManager fragmentManager, final TextView textView){
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
                Date d = new Date(selectedDate.getFirstDate().getTimeInMillis());
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                textView.setText(sf.format(d));
            }
        });

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getDayAndTimeOptions();

        if (!optionsPair.first) { // If options are not valid
            ToastUtils.showShortToast("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }

    public static void showDayPacker(FragmentManager fragmentManager, SublimePickerFragment.Callback callback){
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getDayOptions();

        if (!optionsPair.first) { // If options are not valid
            ToastUtils.showShortToast("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }


    public static void showDayPacker(FragmentManager fragmentManager, SublimePickerFragment.Callback callback, int year, int month, int day){
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(callback);

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getDayOptions(year,month,day);

        if (!optionsPair.first) { // If options are not valid
            ToastUtils.showShortToast("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }

    public static void showDayPacker(FragmentManager fragmentManager, final TextView textView){
        SublimePickerFragment pickerFrag = new SublimePickerFragment();
        pickerFrag.setCallback(new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
                Date d = new Date(selectedDate.getFirstDate().getTimeInMillis());
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                textView.setText(sf.format(d));
            }
        });

        // Options
        Pair<Boolean, SublimeOptions> optionsPair = getDayOptions();

        if (!optionsPair.first) { // If options are not valid
            ToastUtils.showShortToast("No pickers activated");
            return;
        }

        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);

        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(fragmentManager, "SUBLIME_PICKER");
    }


    private static Pair<Boolean, SublimeOptions> getDayAndTimeOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;

//        if (cbDatePicker.isChecked()) {
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
//        }

//        if (cbTimePicker.isChecked()) {
        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
//        }

//        if (cbRecurrencePicker.isChecked()) {
//            displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;
//        }

//        if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
//        } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
//        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
//        }

        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(false);

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    private static Pair<Boolean, SublimeOptions> getDayOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;
//        if (cbDatePicker.isChecked()) {
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
//        }

//        if (cbTimePicker.isChecked()) {
//        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
//        }

//        if (cbRecurrencePicker.isChecked()) {
//            displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;
//        }

//        if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
//        } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
//        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
//        }

        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(false);

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }

    private static Pair<Boolean, SublimeOptions> getDayOptions(int year, int month, int day) {
        SublimeOptions options = new SublimeOptions();
        options.setDateParams(year,month,day);
        int displayOptions = 0;
//        if (cbDatePicker.isChecked()) {
        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
//        }

//        if (cbTimePicker.isChecked()) {
//        displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
//        }

//        if (cbRecurrencePicker.isChecked()) {
//            displayOptions |= SublimeOptions.ACTIVATE_RECURRENCE_PICKER;
//        }

//        if (rbDatePicker.getVisibility() == View.VISIBLE && rbDatePicker.isChecked()) {
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
//        } else if (rbTimePicker.getVisibility() == View.VISIBLE && rbTimePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
//        } else if (rbRecurrencePicker.getVisibility() == View.VISIBLE && rbRecurrencePicker.isChecked()) {
//            options.setPickerToShow(SublimeOptions.Picker.REPEAT_OPTION_PICKER);
//        }

        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(false);

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }
}
