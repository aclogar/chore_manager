package com.aclogar.choremanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aclogar.choremanager.objects.Chore;
import com.aclogar.choremanager.objects.Group;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Carl on 4/17/2016.
 */
public class GroupAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<Group> group;
    Context context;


    public GroupAdapter(GroupActivity mainActivity, ArrayList<Group> group){
        this.group = group;
        context = mainActivity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.chore_item, null);
        holder.title = (TextView) rowView.findViewById(R.id.chore_title);
        holder.desc = (TextView) rowView.findViewById(R.id.chore_desc);
        holder.title.setText(((Group)group.get(position)).getGroup_name());
        holder.desc.setText(((Group)group.get(position)).getGroup_description());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Gson gson = new Gson();
                String choreJson = gson.toJson(getItem(position));
                Intent i = new Intent(context, EditChoreActivity.class);
                i.putExtra("CHORE", choreJson);
                context.startActivity(i);

                //Toast.makeText(context, "You Clicked " + chores.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }
    public class Holder
    {
        TextView title;
        TextView desc;
    }
}
