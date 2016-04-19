package com.aclogar.choremanager;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aclogar.choremanager.objects.Chore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateChoreActivity extends AppCompatActivity {

    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2 + 1, arg3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chore);
        setupActionBar();

        dateView = (TextView) findViewById(R.id.inputDueDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        Spinner dropdownPriority = (Spinner)findViewById(R.id.spinnerChorePriority);
        String[] itemsPriority = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsPriority);
        dropdownPriority.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.chore_add_button);
        if (add != null) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO add actual button action
                    TextView choreNameTV = (TextView) findViewById(R.id.inputChoreName);
                    TextView choreAssigneeTV = (TextView) findViewById(R.id.inputChoreAssignee);
                    TextView choreDescriptionTV = (TextView) findViewById(R.id.inputChoreDescription);
                    TextView dateTV = (TextView) findViewById(R.id.inputDueDate);
                    Spinner spinnerChorePriority = (Spinner) findViewById(R.id.spinnerChorePriority);


                    Chore c = new Chore(choreNameTV.getText().toString(), choreDescriptionTV.getText().toString(), "me",
                            choreAssigneeTV.getText().toString(), Integer.parseInt(spinnerChorePriority.getSelectedItem().toString()));
                    try {
                        c.setDue_date(formatter.parse(dateTV.getText().toString()));
                        Chore.addChore(v.getContext(), c);
                        Toast.makeText(v.getContext(), "Added Chore", Toast.LENGTH_LONG).show();
                        finish();
                    } catch (Exception e) {
                        dateTV.setError("Invalid Date");
                    }

                }
            });
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
