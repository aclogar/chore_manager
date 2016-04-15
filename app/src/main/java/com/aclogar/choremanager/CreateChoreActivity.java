package com.aclogar.choremanager;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aclogar.choremanager.objects.Chore;

public class CreateChoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chore);
        setupActionBar();

        Spinner dropdownPriority = (Spinner)findViewById(R.id.spinnerChorePriority);
        String[] itemsPriority = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsPriority);
        dropdownPriority.setAdapter(adapter);

        Spinner dropdownCategory = (Spinner)findViewById(R.id.spinnerChoreCategory);
        String[] itemsCategory = new String[]{"None"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsCategory);
        dropdownCategory.setAdapter(adapter);

        Button add = (Button) findViewById(R.id.chore_add_button);
        if (add != null) {
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO add actual button action
                    TextView choreNameTV = (TextView) findViewById(R.id.inputChoreName);
                    TextView choreAssigneeTV = (TextView) findViewById(R.id.inputChoreAssignee);
                    TextView choreDescriptionTV = (TextView) findViewById(R.id.inputChoreDescription);


                    Chore c = new Chore(choreNameTV.getText().toString(), choreDescriptionTV.getText().toString(), "me", choreAssigneeTV.getText().toString());
                    Chore.addChore(v.getContext(), c);
                    Toast.makeText(v.getContext(), "Added Chore", Toast.LENGTH_LONG).show();
                    finish();
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


}
