package calendar.example.com.calendar;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int COLUMNS = 7;
    private final int ROWS = 6;
    private final int TOTAL_DAYS_TO_DISPLAY = COLUMNS * ROWS;
    private List<Integer> days = new ArrayList<>();

    GridLayout calendarGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarGrid = (GridLayout) findViewById(R.id.grid);
        displayCalendar();
    }

    public void displayCalendar() {
        int count = 0;
        setPreviousMonthDays(count);
    }

    private void setPreviousMonthDays(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int date_today = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -date_today);
        int last_date_prev_month = calendar.get(Calendar.DAY_OF_MONTH);
        int last_day_prev_month = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = Calendar.SUNDAY; i <= last_day_prev_month; i++) {
            count = setGrid(last_date_prev_month - last_day_prev_month + i, count, R.color.grey_dark);
        }

        setCurrentMonthDays(count);

    }

    private int setGrid(int i, int count, int color) {
        TextView tv = (TextView) calendarGrid.getChildAt(count);
        tv.setText(i + "");
        tv.setTextColor(ContextCompat.getColor(this, color));
        tv.setOnClickListener(this);
        return count + 1;
    }

    private void setCurrentMonthDays(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int LAST_DAY = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int FIRST_DAY = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        for (int i = FIRST_DAY; i <= LAST_DAY; i++) {
            count = setGrid(i, count, R.color.colorAccent);
        }
        setNextMonthDays(count);
    }

    private void setNextMonthDays(int count) {
        for (int i = count, j = 1; i < TOTAL_DAYS_TO_DISPLAY; j++, i++) {
            count = setGrid(j, count, R.color.grey_dark);
        }
    }

    @Override
    public void onClick(View v) {
        int index = calendarGrid.indexOfChild(v);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int date_today = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, -date_today);
        int last_date_prev_month = calendar.get(Calendar.DAY_OF_MONTH);
        int last_day_prev_month = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, -(last_day_prev_month - 1) + index);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        Toast.makeText(this, dateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
    }
}
