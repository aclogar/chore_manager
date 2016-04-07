package com.aclogar.choremanager.objects;

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
}
