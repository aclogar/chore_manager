package com.aclogar.choremanager.objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Anthony on 4/7/2016.
 */
public class Group {
    private String group_id;
    private String group_name;
    private String group_description;

    public Group(String group_id, String group_name, String group_description) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_description = group_description;
    }

    public Group(String group_id, String group_name) {
        this.group_id = group_id;
        this.group_name = group_name;
        group_description = "";
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public static void saveGroups(Context context, ArrayList<Group> groups) {
        //context.getSharedPreferences();

        //turns into json
        Gson gson = new Gson();
        String json = gson.toJson(groups);

        SharedPreferences tasks = context.getSharedPreferences("GROUP", 0);
        SharedPreferences.Editor editor = tasks.edit();
        editor.putString("GROUP", json);
        editor.commit();
    }

    public static ArrayList<Group> getAllGroups(Context context) {
        //turns into json
        Gson gson = new Gson();
        SharedPreferences tasks = context.getSharedPreferences("GROUP", 0);
        String json = tasks.getString("GROUP", null);

        return gson.fromJson(json, new TypeToken<ArrayList<Group>>() {
        }.getType());
    }

    public static void addGroup(Context context, Group group) {
        ArrayList<Group> chores = getAllGroups(context);
        chores.add(group);
        saveGroups(context, chores);
    }

    public static void replaceGroup(Context context, Group oldGroup, Group newGroup){
        ArrayList<Group> groups = getAllGroups(context);
        int index = groups.indexOf((Group) oldGroup);
        groups.remove(oldGroup);
        groups.add(index, newGroup);
        Group.saveGroups(context, groups);
    }


    public static void resetData(Context context){
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group("Family", "Family", "My family"));
        groups.add(new Group("Work", "Work", "Idiots at work"));
        saveGroups(context, groups);
    }
}
