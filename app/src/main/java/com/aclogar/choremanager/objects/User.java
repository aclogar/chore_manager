package com.aclogar.choremanager.objects;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Anthony on 4/7/2016.
 */
public class User implements Serializable{

    public static final String DEFAULT_USER = "NOT_LOGGED";
    private static final long serialVersionUID = 32L;
    private static final String CURRENT_USER = "CURRENT_USER_LOGGED_IN";
    private String user_id;
    private String user_name;
    private String user_email;
    private String password;
    private ArrayList<Group> groups = new ArrayList<>();

    public User(String user_id,  String password, String user_name, String user_email, ArrayList<Group> groups) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
        this.groups = groups;
    }

    public User(String user_id,String password,  String user_name, String user_email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password= password;
    }

    public static boolean setCurrentLoggedIn(Context context, String user) {
        SharedPreferences users = context.getSharedPreferences(CURRENT_USER, 0);
        SharedPreferences.Editor editor = users.edit();
        editor.putString("USER", user);
        editor.commit();
        return true;
    }

    public static String getCurrentUser(Context context) {
        SharedPreferences users = context.getSharedPreferences(CURRENT_USER, 0);
        return users.getString("USER", DEFAULT_USER);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public boolean addGroup(Group g){
        return groups.add(g);
    }

    public boolean removeGroup(Group g){
        return groups.remove(g);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
