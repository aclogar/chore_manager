package com.aclogar.choremanager.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Anthony on 4/6/2016.
 */
public class Chore {


    public static final String TITLE = "TITLE_ASCENDING";
    public static final String TITLE_DESCENDING = "TITLE_DESCENDING";
    public static final String ASSIGNEE = "ASSIGNEE_ASCENDING";
    public static final String ASSIGNEE_DESCENDING = "ASSIGNEE_DESCENDING";
    public static final String DUE_DATE = "DUE_DATE";
    public static final String PRIORITY = "PRIORITY";
    private static final String API_URL = "http://www.aclogar.com:5000/chores/api/v1.0/";
    private String title;
    private String description;
    private String owner_id;
    private String assigne_id;
    private String group_id;
    private Date assgined_date;
    private Date due_date;
    private int priority;
    private ArrayList<String> categories = new ArrayList<>();
    private boolean completed;


    public Chore( String title, String description, String owner_id, String assigne_id
            , String group_id, Date assgined_date, Date due_date,int priority, ArrayList<String> categories) {
        this.due_date = due_date;
        this.title = title;
        this.description = description;
        this.owner_id = owner_id;
        this.assigne_id = assigne_id;
        this.group_id = group_id;
        this.assgined_date = assgined_date;
        this.priority = priority;
        this.categories = categories;
    }

    public Chore( String title, String description, String owner_id) {
        this(title, description, owner_id, owner_id, null, null, null, 3, new ArrayList<String>());
    }

    public Chore( String title, String description, String owner_id, ArrayList<String> categories) {
        this(title, description, owner_id, owner_id, null, null, null, 3, categories);
    }


    public Chore(String title, String description, String owner_id, String assignee_id, int priority) {
        this(title, description, owner_id, assignee_id, null, null, null, priority, null);
    }

    public Chore( String title, String description, String owner_id, int priority, ArrayList<String> categories) {
        this(title, description, owner_id, owner_id, null, null, null, priority, categories);
    }

    public Chore( String title, String description, String owner_id, String assigne_id
            , String group_id, Date assgined_date,Date due_date, ArrayList<String> categories) {

        this(title, description, owner_id, assigne_id, group_id, assgined_date, due_date, 3, categories);
    }

    public Chore( String title, String description, String owner_id, String assigne_id
            , String group_id ,Date due_date, ArrayList<String> categories) {

        this(title, description, owner_id, assigne_id, group_id, new Date(), due_date, 3, categories);
    }

    public Chore( String title, String description, String owner_id, String assigne_id
            , String group_id ,Date due_date,int priority, ArrayList<String> categories) {

        this(title, description, owner_id, assigne_id, group_id, new Date(), due_date, priority,categories);
    }

    public Chore(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Chore(String title) {
        this(title, "");
    }

    // Not being used ATM
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
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

        try {
            JSONObject json = new JSONObject(data);
            return new Chore(json.getString("title"), json.getString("description"));
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    public static void saveChores(Context context, ArrayList<Chore> chores) {
        //context.getSharedPreferences();

        //turns into json
        Gson gson = new Gson();
        String json = gson.toJson(chores);

        SharedPreferences tasks = context.getSharedPreferences("TASKS", 0);
        SharedPreferences.Editor editor = tasks.edit();
        editor.putString("TASKS", json);
        editor.commit();
    }

    public static ArrayList<Chore> getAllChores(Context context) {
        //turns into json
        Gson gson = new Gson();
        SharedPreferences tasks = context.getSharedPreferences("TASKS", 0);
        String json = tasks.getString("TASKS", null);

        return gson.fromJson(json, new TypeToken<ArrayList<Chore>>() {
        }.getType());

    }

    public static void addChore(Context context, Chore chore) {
        ArrayList<Chore> chores = getAllChores(context);
        chores.add(chore);
        saveChores(context, chores);
    }

    public static void replaceChore(Context context, Chore oldChore, Chore newChore) {
        ArrayList<Chore> chores = getAllChores(context);
        int index = chores.indexOf((Chore) oldChore);
        chores.remove(oldChore);
        chores.add(index, newChore);
        Chore.saveChores(context, chores);
    }

    public static void deleteChore(Context context, Chore chore) {
        ArrayList<Chore> chores = getAllChores(context);
        chores.remove(chore);
        Chore.saveChores(context, chores);
    }

    private static int compareTitleAsc(Chore chore1, Chore chore2) {
        return chore1.getTitle().compareToIgnoreCase(chore2.getTitle());
    }

    private static int compareTitleDesc(Chore chore1, Chore chore2) {
        return chore2.getTitle().compareToIgnoreCase(chore1.getTitle());
    }

    private static int compareAssigneeAsc(Chore chore1, Chore chore2) {
        return chore1.getAssigne_id().compareToIgnoreCase(chore2.getAssigne_id());
    }

    private static int compareAssigneeDesc(Chore chore1, Chore chore2) {
        return chore2.getAssigne_id().compareToIgnoreCase(chore1.getAssigne_id());
    }

    private static int comparePriorityAsc(Chore chore1, Chore chore2) {
        return chore1.getPriority() - chore2.getPriority();
    }

    private static int compareDueDate(Chore chore1, Chore chore2) {
        if (chore1.due_date == null && chore2.due_date == null)
            return 0;
        else if (chore1.due_date == null)
            return 1;
        else if (chore2.due_date == null)
            return -1;
        else return chore1.getDue_date().compareTo(chore2.getDue_date());
    }

    public static ArrayList<Chore> sortBy(Context context, String sortBy) {
        ArrayList<Chore> chores = getAllChores(context);

        Chore tmp;
        int i;
        boolean unsorted = true;

        while (unsorted) {
            unsorted = false;
            for (i = 0; i < chores.size() - 1; i++) {
                if (sortBy.equals(TITLE)) {
                    if (Chore.compareTitleAsc(chores.get(i), chores.get(i + 1)) > 0)   // change to > for ascending sort
                    {
                        tmp = chores.get(i);                //swap elements
                        chores.remove(tmp);
                        chores.add(i + 1, tmp);
                        unsorted = true;              //shows a swap occurred
                    }
                } else if (sortBy.equals(ASSIGNEE)) {
                    if (Chore.compareAssigneeAsc(chores.get(i), chores.get(i + 1)) > 0)   // change to > for ascending sort
                    {
                        tmp = chores.get(i);                //swap elements
                        chores.remove(tmp);
                        chores.add(i + 1, tmp);
                        unsorted = true;              //shows a swap occurred
                    }
                } else if (sortBy.equals(DUE_DATE)) {
                    if (Chore.compareDueDate(chores.get(i), chores.get(i + 1)) > 0)   // change to > for ascending sort
                    {
                        tmp = chores.get(i);                //swap elements
                        chores.remove(tmp);
                        chores.add(i + 1, tmp);
                        unsorted = true;              //shows a swap occurred
                    }
                } else if (sortBy.equals(PRIORITY)) {
                    if (Chore.comparePriorityAsc(chores.get(i), chores.get(i + 1)) > 0)   // change to > for ascending sort
                    {
                        tmp = chores.get(i);                //swap elements
                        chores.remove(tmp);
                        chores.add(i + 1, tmp);
                        unsorted = true;              //shows a swap occurred
                    }
                }
            }
        }


        return chores;
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

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public boolean addCategory(String category) {
        //if category must not be blank or already exist
        return !(category == null || category.isEmpty() || categories.contains(category)) && categories.add(category);
    }

    public boolean removeCategory(String category) {
        return categories.remove(category);
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getAssigne_id() {
        return assigne_id;
    }

    public void setAssigne_id(String assigne_id) {
        this.assigne_id = assigne_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public Date getAssgined_date() {
        return assgined_date;
    }

    public void setAssgined_date(Date assgined_date) {
        this.assgined_date = assgined_date;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chore chore = (Chore) o;

        if (getPriority() != chore.getPriority()) return false;
        if (!getTitle().equals(chore.getTitle())) return false;
        if (getDescription() != null ? !getDescription().equals(chore.getDescription()) : chore.getDescription() != null)
            return false;
        if (getOwner_id() != null ? !getOwner_id().equals(chore.getOwner_id()) : chore.getOwner_id() != null)
            return false;
        if (getAssigne_id() != null ? !getAssigne_id().equals(chore.getAssigne_id()) : chore.getAssigne_id() != null)
            return false;
        if (getGroup_id() != null ? !getGroup_id().equals(chore.getGroup_id()) : chore.getGroup_id() != null)
            return false;
        if (getAssgined_date() != null ? !getAssgined_date().equals(chore.getAssgined_date()) : chore.getAssgined_date() != null)
            return false;
        if (getDue_date() != null ? !getDue_date().equals(chore.getDue_date()) : chore.getDue_date() != null)
            return false;
        return !(getCategories() != null ? !getCategories().equals(chore.getCategories()) : chore.getCategories() != null);

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getOwner_id() != null ? getOwner_id().hashCode() : 0);
        result = 31 * result + (getAssigne_id() != null ? getAssigne_id().hashCode() : 0);
        result = 31 * result + (getGroup_id() != null ? getGroup_id().hashCode() : 0);
        result = 31 * result + (getAssgined_date() != null ? getAssgined_date().hashCode() : 0);
        result = 31 * result + (getDue_date() != null ? getDue_date().hashCode() : 0);
        result = 31 * result + getPriority();
        result = 31 * result + (getCategories() != null ? getCategories().hashCode() : 0);
        return result;
    }

    private static class getJSON extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... endpoint) {
            if (endpoint.length != 1) {
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
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }
    }
}
