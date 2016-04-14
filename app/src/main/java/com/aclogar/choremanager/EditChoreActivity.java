package com.aclogar.choremanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EditChoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chore);
        Intent intent = getIntent();
        TextView tv1 = (TextView) findViewById(R.id.textView2);
        TextView tv2 = (TextView) findViewById(R.id.textView3);
        //Gson gson = new Gson();
        //Chore c = gson.fromJson(intent.getStringExtra("CHORE"), Chore.class);
        //tv1.setText(c.getTitle());
        //tv2.setText(c.getDescription());
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
