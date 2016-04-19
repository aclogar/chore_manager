package com.aclogar.choremanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aclogar.choremanager.objects.Chore;
import com.aclogar.choremanager.objects.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ChoreAdapter adapter;
    MainActivity main = this;


    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main=this;
        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"None", "Assignee", "Title", "Date"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Chore> chores;
                if(position == 2){
                    chores = Chore.sortBy(view.getContext(), Chore.TITLE);
                }
                else if(position ==1){
                    chores = Chore.sortBy(view.getContext(), Chore.ASSIGNEE);
                }
                else if(position == 3){
                    chores = Chore.sortBy(view.getContext(), Chore.DUE_DATE);
                }
                else {
                    chores = Chore.getAllChores(view.getContext());
                }

                ListView lv = (ListView) findViewById(R.id.listView);
                if (lv != null) {
                    lv.setAdapter(new ChoreAdapter(main, chores));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ListView lv = (ListView) findViewById(R.id.listView);
                if (lv != null) {
                    lv.setAdapter(new ChoreAdapter(main, Chore.getAllChores(parent.getContext())));
                }
            }
        });
        dropdown.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        loadTasks();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }


        //TODO still need to fix this nonsense
        String enteredEmail =
                User.getCurrentUser(getBaseContext());
        //data.getStringExtra("email");
        TextView tv = (TextView) findViewById(R.id.user_email);
        if (tv != null) {
            if (!enteredEmail.equals(User.DEFAULT_USER)) {
                tv.setText(enteredEmail);
            } else {
                tv.setText("Need to Sign In");
            }
        }
    }

    private void loadTasks() {
        //creates
        ArrayList<Chore> chores = Chore.getAllChores(this);


        if (chores == null || chores.isEmpty()) {

            chores = new ArrayList<>();
            // title, descritpon, email
            chores.add(new Chore("Do something", "having a dance party", "coledude919@gmail.com"));
            chores.add(new Chore("Clean HHD", "Clean old stuff on HD and defrag drive", "coledude919@gmail.com"));
            chores.add(new Chore("Watch TV", "Keep up with the season of Archer", "coledude919@gmail.com"));
            chores.add(new Chore("HW", "Do all assigned HW and projects", "coledude919@gmail.com"));
            chores.add(new Chore("Fun", "Play disc golf", "coledude919@gmail.com"));
            chores.add(new Chore("Games", "play league of legends and Pokemon", "batmanisinthecave@gmail.com"));
            chores.add(new Chore("Workout", "go to the gym", "batmanisinthecave@gmail.com"));
            chores.add(new Chore("Stop crime", "I having to stop the joker from destroying the city", "batmanisinthecave@gmail.com"));
            chores.add(new Chore("Build new toys", "Make new batarangs", "batmanisinthecave@gmail.com"));
            chores.add(new Chore("Taring", "punch a boxing bag in total darkness", "batmanisinthecave@gmail.com"));
            chores.add(new Chore("Things", "Take over the world", "google@gmail.com"));
            chores.add(new Chore("Updates", "Remove google+ from histroy", "google@gmail.com"));
            chores.add(new Chore("Expand", "Buy anything worth less than $1,000,000 including small countries", "google@gmail.com"));
            chores.add(new Chore("Fiber", "Have the whole worlds have 1Gb fiber", "google@gmail.com"));
            chores.add(new Chore("Water", "Have waves", "waterOcean@gmail.com"));
            chores.add(new Chore("Life", "Be a nice place for things to live", "waterOcean@gmail.com"));
            chores.add(new Chore("Enjoyment", "Watch sharks fuck shit up", "waterOcean@gmail.com"));


            //turns into json
            Gson gson = new Gson();
            String json = gson.toJson(chores);

            SharedPreferences tasks = getSharedPreferences("TASKS", 0);
            SharedPreferences.Editor editor = tasks.edit();
            editor.putString("TASKS", json);
            editor.commit();

            chores = Chore.getAllChores(getBaseContext());
        }

        ListView lv = (ListView) findViewById(R.id.listView);
        if (lv != null) {
            adapter = new ChoreAdapter(this, chores);

            lv.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);

            startActivityForResult(settings, 1);
            return true;
        }*/
        if (id == R.id.action_refresh){
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT);
            ListView lv = (ListView) findViewById(R.id.listView);
            if (lv != null) {
                adapter = new ChoreAdapter(this, Chore.getAllChores(this));

                lv.setAdapter(adapter);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    This gets us the email that was verified in the login page
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadTasks();
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String enteredEmail =
                        User.getCurrentUser(getBaseContext());
                //data.getStringExtra("email");
                TextView tv = (TextView)findViewById(R.id.user_email);
                if (tv != null && !enteredEmail.equals(User.DEFAULT_USER)) {
                    tv.setText(enteredEmail);
                }

            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.login) {

            /*
            This will find the class LoginActivity from com.aclogar.choremanager and can pass info
            by using something like login.putExtra("VARIABLE NAME", "VALUE")
             */
            Intent login = new Intent(this, LoginActivity.class);

            startActivityForResult(login, 1);

        } else if (id == R.id.list_chores) {

        } else if (id == R.id.nav_add_chore) {

            Intent addChore = new Intent(this, CreateChoreActivity.class);

            startActivityForResult(addChore, 1);
        } else if (id == R.id.group_page) {
            Intent grouppage = new Intent(this, GroupActivity.class);

            startActivityForResult(grouppage, 1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

}
