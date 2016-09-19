package app.com.example.victoriajuan.nphshomework;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import java.util.Calendar;


public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        CalendarView calendarView=(CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        int day = GlobalVariables.getDay();
        int month = GlobalVariables.getMonth();
        int year = GlobalVariables.getYear();
        calendar.clear();
        calendar.set(year,month,day);
        long milliseconds = calendar.getTimeInMillis();

        calendarView.setDate(milliseconds,true,true);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                GlobalVariables.setDate(dayOfMonth,month,year);
                finish();

            }
        });

    }
}
