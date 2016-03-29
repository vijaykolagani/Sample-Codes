package com.appzollo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appzollo.classes.EventDetail;
import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;

import java.util.Calendar;

/**
 * Created by saikrishna on 11/11/14.
 */
public class TripDetailsActivity extends BaseActivity implements View.OnClickListener,
        DatePickerDialogFragment.DatePickerDialogHandler, RadioGroup.OnCheckedChangeListener {
    private Button done, cancel, addDate;
    private EditText startDate, endDate, et_cost;
    Spinner et_days, et_nights;
    private RadioButton singleDate, multiDate, everyDay, selectedDay;
    private RadioGroup whenGroup, dateGroup;
    private LinearLayout singleDateLayout, multiDateLayout, selectedDaysLayout, datesLayout;
    TextView date;
    String date_type = "singledate";
    String multiple_dates = " ";
    static final int DATE_START_PICKER_ID = 1111, DATE_END_PICKER_ID = 2222,DATE_ID = 3333;
    private int year;
    private int month;
    private int day;
    int from_to = 0;
    private String[] Currency = {"INR", "Dollar", "Euro"};
    Spinner et_currency;
    private boolean edit;
    private EventDetail events;
    View view_date = null;
    String muldates = "",singledate="";
LinearLayout llDone,llCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.add_trip_details);

        edit = getIntent().getBooleanExtra("edit", false);
        if (edit) {
            events = getIntent().getParcelableExtra("event");

        }
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH); // Note: zero based!
        day = now.get(Calendar.DAY_OF_MONTH);

        llDone = (LinearLayout) findViewById(R.id.llDone);
        llCancel = (LinearLayout) findViewById(R.id.llCancel);
        addDate = (Button) findViewById(R.id.bt_add_date);

        startDate = (EditText) findViewById(R.id.et_start_date);
        endDate = (EditText) findViewById(R.id.et_end_date);
        date = (TextView) findViewById(R.id.et_add_date);
        et_days = (Spinner) findViewById(R.id.et_days);
        et_nights = (Spinner) findViewById(R.id.et_nights);
        et_cost = (EditText) findViewById(R.id.et_cost);
        et_currency = (Spinner) findViewById(R.id.et_currency);


        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Currency);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        et_currency.setAdapter(adapter_state);

        startDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    singledate = editable.toString();
            }
        });


        et_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                et_currency.setSelection(i);
                String selState = (String) et_currency.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        singleDate = (RadioButton) findViewById(R.id.rb_single_date);
        multiDate = (RadioButton) findViewById(R.id.rb_multi_date);
        everyDay = (RadioButton) findViewById(R.id.rb_everyday);
        selectedDay = (RadioButton) findViewById(R.id.rb_selected_days);
        whenGroup = (RadioGroup) findViewById(R.id.rg_when);
        dateGroup = (RadioGroup) findViewById(R.id.rg_date);

        singleDateLayout = (LinearLayout) findViewById(R.id.single_date_layout);
        multiDateLayout = (LinearLayout) findViewById(R.id.multi_date_layout);
        selectedDaysLayout = (LinearLayout) findViewById(R.id.selected_days_layout);
        datesLayout = (LinearLayout) findViewById(R.id.dates_layout);


        if(edit){
            if(events.getCost()!= null){
                et_cost.setText(events.getCost());
            }

           /* if(events.getDuration() != null){
                et_days.set
            }*/
        }

        startDate.setFocusable(false);
        endDate.setFocusable(false);
        date.setFocusable(false);

        llDone.setOnClickListener(this);
        llCancel.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        date.setOnClickListener(this);
        addDate.setOnClickListener(this);

        whenGroup.setOnCheckedChangeListener(this);
        dateGroup.setOnCheckedChangeListener(this);
        if(events!=null){
            if(events.getCustome_date() != null) {
                if (events.getCustome_date().toString().contains("Every")) {
                    multiDate.setChecked(true);
                    everyDay.setChecked(true);
                    Toast.makeText(getApplicationContext(), "every", Toast.LENGTH_SHORT).show();
                } else if (events.getCustome_date().toString().contains("Selected")) {
                    multiDate.setChecked(true);
                    selectedDay.setChecked(true);
                    if(events.getDeparture_date() != null) {
                        String departuredates[] = events.getDeparture_date().toString().split("#");
                        Log.d("datesss",events.getDeparture_date().toString()+"");
                        for (int i = 0; i < departuredates.length; i++) {
                            if (i == 0) {
                                date.setText(departuredates[i] + "");

                            } else {
                                View view1 = View.inflate(TripDetailsActivity.this, R.layout.add_date_layout, null);

                                final TextView etdate = (TextView) view1.findViewById(R.id.et_add_date);

                                datesLayout.addView(view1);
                                etdate.setText(departuredates[i] + "");
                                etdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        view_date = view;
                                        String splitdate[] = etdate.getText().toString().replace(" ","").split("/");
                                        if(splitdate.length>2){
                                            day = Integer.parseInt(splitdate[0]);
                                            month = Integer.parseInt(splitdate[1])-1;
                                            year = Integer.parseInt(splitdate[2]);

                                        }
                                        showDialog(DATE_ID);
                                    }
                                });
                            }
                        }
                    }
                } else {
                 //   Toast.makeText(getApplicationContext(), "elseee", Toast.LENGTH_SHORT).show();
                    singleDate.setChecked(true);
                    multiple_dates = "";
                    startDate.setText(events.getDate_from());
                    Log.d("dates single",events.getDate_from());
                }
            }
            else {
               // Toast.makeText(getApplicationContext(), "elseee", Toast.LENGTH_SHORT).show();
                singleDate.setChecked(true);
                multiple_dates = "";
                startDate.setText(events.getDate_from());
                Log.d("dates single",events.getDate_from());
            }

            if (events.getDuration() == null) {
                et_days.setSelection(0);
                et_nights.setSelection(0);
                et_cost.setText(events.getCost());
            } else {
                String[] duration = events.getDuration().split(" ");
                if (duration.length > 2) {
                    et_days.setSelection(getDayPos(duration[0]));
                    et_nights.setSelection(getNightPos(duration[2]));
                } else {
                    et_days.setSelection(getDayPos(duration[0]));
                    et_nights.setSelection(0);
                }
            }
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_date = view;
                String splitdate[] = date.getText().toString().replace(" ","").split("/");
                if(splitdate.length>2){
                    day = Integer.parseInt(splitdate[0]);
                    month = Integer.parseInt(splitdate[1]) -1;
                    year = Integer.parseInt(splitdate[2]);

                }

                showDialog(DATE_ID);
            }
        });

    }

    private int getDayPos(String day) {
        String days[] = getResources().getStringArray(R.array.days);
        int index = 0;
        for (int i = 0; i < days.length; i++) {
            if (days[i].equalsIgnoreCase(day)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getNightPos(String night) {
        String nights[] = getResources().getStringArray(R.array.nights);
        int index = 0;
        for (int i = 0; i < nights.length; i++) {
            if (nights[i].equalsIgnoreCase(night)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDone:
                if ( et_days.getSelectedItem().toString().length() > 0 && et_nights.getSelectedItem().toString().length() > 0
                        && et_cost.getText().toString().length() > 0) {
                 //   muldates = date.getText().toString()+",";
                    if(selectedDay.isChecked()){
                       // muldates = date.getText().toString()+",";
                        for (int i = 0; i < datesLayout.getChildCount(); i++) {
                            View child = datesLayout.getChildAt(i);
                            TextView childTextView = (TextView)(child.findViewById(R.id.et_add_date));
                            if(muldates.toString().length()>2)
                             muldates = muldates+","+childTextView.getText().toString();
                            else
                                muldates = childTextView.getText().toString();
                            Log.d("ANDROID DY NAMIC VIEWS:", "EdiText: " + muldates);

                        }
                    }
String dates="";
                    if(singleDate.isChecked()){
                        dates = singledate;
                    }else{
                        dates = muldates;
                    }

                    startActivity(new Intent(this, BroadcastActivity.class)
                            .putExtra("name", getIntent().getExtras().getString("name"))
                            .putExtra("where", getIntent().getExtras().getString("where"))
                            .putExtra("latitude", getIntent().getExtras().getString("latitude"))
                            .putExtra("longitude", getIntent().getExtras().getString("longitude"))
                            .putExtra("place1", getIntent().getExtras().getString("place1"))
                            .putExtra("place2", getIntent().getExtras().getString("place2"))
                            .putExtra("place3", getIntent().getExtras().getString("place3"))
                            .putExtra("placefull", getIntent().getExtras().getString("placefull"))
                            .putExtra("isfood", getIntent().getExtras().getString("isfood"))
                            .putExtra("istrans", getIntent().getExtras().getString("istrans"))
                            .putExtra("isaccom", getIntent().getExtras().getString("isaccom"))
                            .putExtra("detail_itinerary", getIntent().getExtras().getString("detail_itinerary"))
                            .putExtra("cost_plan", getIntent().getExtras().getString("cost_plan"))
                            .putExtra("cost_details",getIntent().getExtras().getString("cost_details"))
                            .putExtra("food", getIntent().getExtras().getString("food"))
                            .putExtra("trans", getIntent().getExtras().getString("trans"))
                            .putExtra("accom", getIntent().getExtras().getString("accom"))
                            .putExtra("others", getIntent().getExtras().getString("others"))
                            .putExtra("segment", getIntent().getExtras().getString("segment"))
                            .putExtra("img", getIntent().getExtras().getString("img"))
                            .putExtra("date_type", date_type)
                            .putExtra("start_date",dates.replace(" ",""))
                            .putExtra("multiple_date", multiple_dates)
                            .putExtra("end_date", endDate.getText().toString())
                            .putExtra("duration", et_days.getSelectedItem().toString())
                            .putExtra("duration1", et_nights.getSelectedItem().toString())
                            .putExtra("cost", et_cost.getText().toString())
                            .putExtra("currency", et_currency.getSelectedItem().toString())
                            .putExtra("which", 3)
                            .putExtra("edit", edit)
                            .putExtra("event", events));
                    Toast.makeText(getApplicationContext(), muldates+","+endDate.getText().toString(), Toast.LENGTH_LONG).show();
                    Log.d("dates",date_type+","+muldates);

                } else {
                    Toast.makeText(getApplicationContext(), "Text Fields should not be empty", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.llCancel:
                finish();
                break;
            case R.id.et_start_date:
               /* DatePickerBuilder sdt = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_start_date);
                sdt.show();*/
                if(edit) {
                    String dateTosplit[], dateFromsplit[];
                    dateTosplit = startDate.getText().toString().split("-");
                    if(dateTosplit.length > 2) {
                        year = Integer.parseInt(dateTosplit[0]);
                        month = Integer.parseInt(dateTosplit[1]) - 1;
                        day = Integer.parseInt(dateTosplit[2]);
                    }

                }
                showDialog(DATE_START_PICKER_ID);
                break;
            case R.id.et_end_date:
               /* DatePickerBuilder edt = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_end_date);
                edt.show();*/
                showDialog(DATE_END_PICKER_ID);
                break;
            case R.id.et_date:
                DatePickerBuilder dt = new DatePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light)
                        .setReference(R.id.et_date);
                dt.show();

                break;
            case R.id.bt_add_date:
                final TextView addDate = (TextView) View.inflate(this, R.layout.add_date_layout, null);
                addDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      // addDate.getId();
                       view_date = view;
                        showDialog(DATE_ID);
                    }
                });
                //addDate.setId(1);
                datesLayout.addView(addDate);
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_START_PICKER_ID:
                from_to = 0;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
            case DATE_END_PICKER_ID:
                from_to = 1;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day+1);
            case DATE_ID:
                from_to = 2;
                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener1, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            TextView tv = (TextView) view_date.findViewById(R.id.et_add_date);

                tv.setText(new StringBuilder().append(leftPad(day,2))
                        .append("/").append(leftPad(month+1,2)).append("/").append(leftPad(year,2))
                        .append(""));

            // Show selected date


        }
    };




    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if (from_to == 0) {
                startDate.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(""));
            } else if(from_to == 1){
                endDate.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(""));
            }else {
                date.setText(new StringBuilder().append(year)
                        .append("-").append(leftPad(month+1,2)).append("-").append(leftPad(day,2))
                        .append(""));
            }
            // Show selected date

        }
    };


    public static String leftPad(int n, int padding) {
        return String.format("%0" + padding + "d", n);
    }
    @Override
    public void onDialogDateSet(int i, int year, int month, int date) {
        switch (i) {
            case R.id.et_start_date:
                startDate.setText(year + "/" + month + "/" + date);
                break;
            case R.id.et_end_date:
                endDate.setText(date + "/" + month + "/" + year);
                break;
            case R.id.et_date:
                this.date.setText(date + "/" + month + "/" + year);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_single_date:
                if (singleDate.isChecked()) {
                    date_type = "singledate";
                    multiple_dates = "singledate";
                    singleDateLayout.setVisibility(View.VISIBLE);
                    multiDateLayout.setVisibility(View.GONE);
                    selectedDaysLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_multi_date:
                if (multiDate.isChecked()) {
                    multiple_dates = "every day";
                    date_type = "multipledates";
                    singleDateLayout.setVisibility(View.GONE);
                    multiDateLayout.setVisibility(View.VISIBLE);
                    if (selectedDay.isChecked()) {
                        selectedDaysLayout.setVisibility(View.VISIBLE);
                    } else {
                        selectedDaysLayout.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.rb_everyday:
                if (everyDay.isChecked()) {
                    multiple_dates = "every day";
                    singleDateLayout.setVisibility(View.GONE);
                    multiDateLayout.setVisibility(View.VISIBLE);
                    selectedDaysLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_selected_days:
                if (selectedDay.isChecked()) {
                    multiple_dates = "selected dates";
                    singleDateLayout.setVisibility(View.GONE);
                    multiDateLayout.setVisibility(View.VISIBLE);
                    selectedDaysLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
