package com.example.weather;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.weather.adapter.note.NoteAdapter;


/**
 * Created by 滕磊 on 2017/7/26.
 */

public class NoteEditActivity extends Activity {
    private ImageButton cancel;
    private ImageButton ok;
    private ImageButton get_start_date;
    private ImageButton get_start_time;
    private ImageButton get_end_date;
    private ImageButton get_end_time;
    private Time time;
    private int year, month, day, hour, minute;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private String Title,Start_Date,Start_Time,End_Date,End_Time,Local,Annotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_plan);

        time = new Time();
        time.setToNow();
        year = time.year;
        month = time.month;
        day = time.monthDay;
        hour = time.hour;
        minute = time.minute;

        get_start_date = findViewById(R.id.get_start_date);
        get_start_time = findViewById(R.id.get_start_time);
        get_end_date = findViewById(R.id.get_end_date);
        get_end_time = findViewById(R.id.get_end_time);

        get_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePicker();
            }
        });
        get_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartTimePicker();
            }
        });
        get_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDatePicker();
            }
        });
        get_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showEndTimePicker();
            }
        });


        cancel = (ImageButton) findViewById(R.id.cancel);
        ok = (ImageButton) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title = findViewById(R.id.start_title);
                TextView local = findViewById(R.id.start_local);
                TextView annotation = findViewById(R.id.annotation);
                Title = title.getText().toString();
                Local = local.getText().toString();
                Annotation = annotation.getText().toString();
                if(Title.isEmpty()){
                    Title = "（无标题）";
                }
                if(Local.isEmpty()){
                    Local = "null";
                }
                if(Annotation.isEmpty()){
                    Annotation = "null";
                }
                Intent intent = new Intent();
                intent.putExtra("test", "ok");
                intent.putExtra("title", Title);
                intent.putExtra("start_date", Start_Date);
                intent.putExtra("start_time", Start_Time);
                intent.putExtra("end_date", End_Date);
                intent.putExtra("end_time", End_Time);
                intent.putExtra("local", Local);
                intent.putExtra("annotation", Annotation);

                setResult(0, intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("test", "cancel");
                setResult(1, intent);
                finish();
            }
        });


    }

    public String getTitle(String text) {
        if (text.length() < 30 && text.length() > 0) {
            return text;
        } else if (text.length() < 0) {
            return "empty";
        } else {
            String res = "";
            for (int i = 0; i < 30; i++) {
                res += text.charAt(i);
            }
            return res;
        }
    }

    public String getContent(String content) {
        if (content.equals("")) {
            return "empty";
        } else {
            return content;
        }
    }

    public void showStartDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NoteEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
                TextView start_date = findViewById(R.id.start_date);
                start_date.setText(Year + "/" + monthOfYear + "/" + dayOfMonth);
                Start_Date = Year + "/" + monthOfYear + "/" + dayOfMonth;
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    public void showEndDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(NoteEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
                TextView end_date = findViewById(R.id.end_date);
                end_date.setText(Year + "/" + monthOfYear + "/" + dayOfMonth);
                End_Date = Year + "/" + monthOfYear + "/" + dayOfMonth;
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    public void showStartTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int Hour, int Minute) {
                TextView start_time = findViewById(R.id.start_time);
                start_time.setText(Hour + ":" + Minute);
                Start_Time = Hour + ":" + Minute;
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    public void showEndTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int Hour, int Minute) {
                TextView end_time = findViewById(R.id.end_time);
                end_time.setText(Hour + ":" + Minute);
                End_Time = Hour + ":" + Minute;
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

}
