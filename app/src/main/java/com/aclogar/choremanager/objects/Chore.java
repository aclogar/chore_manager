package com.aclogar.choremanager.objects;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Anthony on 4/6/2016.
 */
public class Chore {


    private String title;
    private String description;

    public Chore(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Chore(String title){
        this(title, "");
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    private static final String API_URL = "http://www.aclogar.com:5000/chores/api/v1.0/";
    public static Chore retriveChore(String task_id) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("Fetching from API");
        String title, description;
        AsyncTask<String, Void, String> task = new getJSON().execute("chores/" + task_id);
        String data;
        try {
            URL url = new URL(API_URL + "chores/" + task_id);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                data = stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

        try {
            JSONObject json = new JSONObject(data);
            return new Chore(json.getString("title"), json.getString("description"));
        }catch (Exception e){
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }



    private static class getJSON extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... endpoint) {
            if (endpoint.length != 1){
                this.cancel(true);
            }

            try {
                URL url = new URL(API_URL + endpoint[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }
}
