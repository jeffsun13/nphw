package app.com.example.victoriajuan.nphshomework;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        int day = SaveSharedPreference.getDay(CalendarActivity.this);
        int month = SaveSharedPreference.getMonth(CalendarActivity.this);
        int year = SaveSharedPreference.getYear(CalendarActivity.this);
        calendar.clear();
        calendar.set(year,month,day);
        long milliseconds = calendar.getTimeInMillis();

        calendarView.setDate(milliseconds,true,true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                SaveSharedPreference.setDate(CalendarActivity.this,dayOfMonth,month,year);
                finish();

            }
        });

    }
}
