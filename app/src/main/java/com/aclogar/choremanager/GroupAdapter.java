package com.aclogar.choremanager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aclogar.choremanager.objects.Group;

import java.util.ArrayList;

/**
 * Created by Carl on 4/17/2016.
 */
public class GroupAdapter extends BaseAdapter {
    ArrayList<Group> group;

    @Override
    public int getCount() {
        return group.size();
    }

    @Override
    public Object getItem(int position) {
        return group.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
