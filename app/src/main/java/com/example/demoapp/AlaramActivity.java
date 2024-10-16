package com.example.demoapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlaramActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alaram);

        // Initialize AlarmManager and Calendar
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        calendar = Calendar.getInstance();

        Button setAlarmButton = findViewById(R.id.set_alarm_button);
        setAlarmButton.setText(getString(R.string.set_alarm));

        // Set click listener for setting the alarm
        setAlarmButton.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    // Set the selected date in the calendar
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Show the time picker dialog after the date is selected
                    showTimePickerDialog();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // Create a TimePickerDialog with 12-hour format (AM/PM)
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    // Set the selected time in the calendar
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Set the alarm with the selected date and time
                    setAlarm(calendar.getTimeInMillis());
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false // This ensures 12-hour format with AM/PM
        );
        timePickerDialog.show();
    }

    private void setAlarm(long triggerAtMillis) {
        try {
            // Prepare an Intent to trigger AlarmReceiver
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            // Set the alarm to trigger at the selected date and time
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

            // Notify the user that the alarm has been set
            Toast.makeText(this, "Alarm set for the selected date and time!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to set alarm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
