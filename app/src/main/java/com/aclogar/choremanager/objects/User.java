package com.aclogar.choremanager.objects;

import java.util.ArrayList;

/**
 * Created by Anthony on 4/7/2016.
 */
public class User {
    private String user_id;
    private String user_name;
    private String user_email;
    private ArrayList<Group> groups = new ArrayList<Group>();

    public User(String user_id, String user_name, String user_email, ArrayList<Group> groups) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.groups = groups;
    }

    public User(String user_id, String user_name, String user_email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
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
}
