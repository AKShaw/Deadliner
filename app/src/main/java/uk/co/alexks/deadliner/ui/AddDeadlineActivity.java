package uk.co.alexks.deadliner.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Spinner;

import uk.co.alexks.deadliner.Database.DeadlineDB;
import uk.co.alexks.deadliner.Database.DeadlineDBHelper;
import uk.co.alexks.deadliner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormatSymbols;


public class AddDeadlineActivity extends AppCompatActivity implements OnItemSelectedListener{

    Calendar cal = Calendar.getInstance();
    TextView dateLabel;
    TextView timeLabel;

    EditText nameEdit;
    EditText typeEdit;
    EditText notesEdit;


    Spinner unitSpinner;
    Spinner timeSpinner;

    ArrayAdapter<CharSequence> unitAdapter;
    ArrayAdapter<CharSequence> timeAdapterHours;
    ArrayAdapter<CharSequence> timeAdapterDays;
    ArrayAdapter<CharSequence> timeAdapterWeeks;
    ArrayAdapter<CharSequence> timeAdapterMonths;

    DeadlineDBHelper DbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deadline);

        dateLabel = (TextView)findViewById(R.id.currentDateLabel);
        timeLabel = (TextView)findViewById(R.id.currentTimeLabel);

        nameEdit = (EditText)findViewById(R.id.nameEditText);
        typeEdit = (EditText)findViewById(R.id.typeEditText);
        notesEdit = (EditText)findViewById(R.id.notesEditText);

        unitAdapter = ArrayAdapter.createFromResource(this, R.array.time_units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner = (Spinner)findViewById(R.id.unitSpinner);
        unitSpinner.setAdapter(unitAdapter);
        unitSpinner.setOnItemSelectedListener(this);

        createTimeAdapters();
        timeSpinner = (Spinner)findViewById(R.id.numberOfUnitsSpinner);
        timeSpinner.setAdapter(timeAdapterHours);
        timeSpinner.setOnItemSelectedListener(this);

        DbHelper = new DeadlineDBHelper(getApplicationContext());
        db = DbHelper.getWritableDatabase();

        setDateLabel();
        setTimeLabel();
    }

    private void createTimeAdapters() {
        timeAdapterHours = ArrayAdapter.createFromResource(this, R.array.hour_items, android.R.layout.simple_spinner_item);
        timeAdapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeAdapterDays = ArrayAdapter.createFromResource(this, R.array.day_items, android.R.layout.simple_spinner_item);
        timeAdapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeAdapterWeeks = ArrayAdapter.createFromResource(this, R.array.week_items, android.R.layout.simple_spinner_item);
        timeAdapterWeeks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeAdapterMonths = ArrayAdapter.createFromResource(this, R.array.month_items, android.R.layout.simple_spinner_item);
        timeAdapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void setTimeLabel() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        String currentTime = sdfTime.format(cal.getTime());
        timeLabel.setText(currentTime);
    }

    private void setDateLabel() {
        SimpleDateFormat sdfDateYear =  new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfDateMonth = new SimpleDateFormat("MM");
        SimpleDateFormat sdfDateDay = new SimpleDateFormat("dd");
        String year = sdfDateYear.format(cal.getTime());
        String month = sdfDateMonth.format(cal.getTime());
        String day = sdfDateDay.format(cal.getTime());

        String prettyDay = day.replaceFirst("^0+(?!$)", "");

        String currentDate = prettyDay + " " + getMonth(month) + " " + year;

        dateLabel.setText(currentDate);
    }

    private String getMonth(String month){
        int m = Integer.parseInt(month);
        return new DateFormatSymbols().getMonths()[m-1];
    }

    public void selectDate(View view){
        DatePickerDialog datePicker  = new DatePickerDialog(this, selectDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePicker.show();
    }

    public void selectTime(View view){
        new TimePickerDialog(this, selectTimeListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener selectDateListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            setDateLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener selectTimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute){
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            setTimeLabel();
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        Log.d("Item selected", "true");
        switch(parent.getId()){
            case R.id.unitSpinner:
                Log.d("Unit Spinner Changed", "True");
                //hours, days, weeks, months
                switch(pos){
                    case 0:
                        //hours selected
                        timeSpinner.setAdapter(timeAdapterHours);
                        break;
                    case 1:
                        //days selected
                        timeSpinner.setAdapter(timeAdapterDays);
                        break;
                    case 2:
                        //Weeks selected
                        timeSpinner.setAdapter(timeAdapterWeeks);
                        break;
                    case 3:
                        //Months selected
                        timeSpinner.setAdapter(timeAdapterMonths);
                }
                break;
            case R.id.numberOfUnitsSpinner:
                //time spinner changed
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }


    public void saveDeadline(View v){
        //Get data and add it to the database
        ContentValues val = new ContentValues();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdft = new SimpleDateFormat("HH:mm");

        String deadlineName = nameEdit.getText().toString();
        String deadlineType = typeEdit.getText().toString();
        String deadlineDate = sdfd.format(cal.getTime());
        String deadlineTime = sdft.format(cal.getTime());
        int deadlineNotifyTime = Integer.parseInt(timeSpinner.getSelectedItem().toString());
        String deadlineNotifyUnit = unitSpinner.getSelectedItem().toString();
        String deadlineNotes = notesEdit.getText().toString();

        val.put(DeadlineDB.DBSchema.COL_NAME, deadlineName);
        val.put(DeadlineDB.DBSchema.COL_TYPE, deadlineType);
        val.put(DeadlineDB.DBSchema.COL_DATE, deadlineDate);
        val.put(DeadlineDB.DBSchema.COL_TIME, deadlineTime);
        val.put(DeadlineDB.DBSchema.COL_NOTIFY_LENGTH, deadlineNotifyTime);
        val.put(DeadlineDB.DBSchema.COL_NOTIFY_UNITS, deadlineNotifyUnit);
        val.put(DeadlineDB.DBSchema.COL_NOTES, deadlineNotes);

        long newRowId = db.insert(DeadlineDB.DBSchema.TABLE_NAME, null, val);

        db.close();

        Intent home = new Intent(this, MainActivity.class);
        home.setFlags(home.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(home);
    }
}
