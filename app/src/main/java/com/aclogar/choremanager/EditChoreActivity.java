package com.aclogar.choremanager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aclogar.choremanager.objects.Chore;
import com.google.gson.Gson;

public class EditChoreActivity extends AppCompatActivity {

    EditText inputTitle;
    EditText inputDesc;
    EditText inputAssignee;
    Spinner prioritySpinner;
    Button saveBtn;
    Chore oldChore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);
        Intent intent = getIntent();
        Spinner dropdownPriority = (Spinner)findViewById(R.id.spinnerChorePriority);
        String[] itemsPriority = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsPriority);
        dropdownPriority.setAdapter(adapter);


        Spinner dropdownCategory = (Spinner)findViewById(R.id.spinnerChoreCategory);
        String[] itemsCategory = new String[]{"None"};
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsCategory);
        dropdownCategory.setAdapter(adapter);

        inputTitle = (EditText)findViewById(R.id.inputChoreName);
        inputDesc  = (EditText)findViewById(R.id.inputChoreDescription);
        inputAssignee = (EditText)findViewById(R.id.inputChoreAssignee);
        prioritySpinner = (Spinner) findViewById(R.id.spinnerChorePriority);
        saveBtn = (Button) findViewById(R.id.chore_save_button);

        Gson gson = new Gson();
        oldChore = gson.fromJson(intent.getStringExtra("CHORE"), Chore.class);

        inputTitle.setText(oldChore.getTitle());
        inputDesc.setText(oldChore.getDescription());
        inputAssignee.setText(oldChore.getAssigne_id());
        prioritySpinner.setSelection(oldChore.getPriority() - 1);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chore newChore = new Chore(inputTitle.getText().toString(), inputDesc.getText().toString(), "me",
                        inputAssignee.getText().toString(), Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
                Chore.replaceChore(v.getContext(), oldChore, newChore);
                finish();
            }
        });

        setupActionBar();
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
