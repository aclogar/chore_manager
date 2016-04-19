package com.aclogar.choremanager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aclogar.choremanager.objects.Chore;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Anthony on 4/12/2016.
 */
public class ChoreAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<Chore> chores;
    Context context;

    public ChoreAdapter(MainActivity mainActivity, ArrayList<Chore> chores){
        this.chores = chores;
        context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return chores.size();
    }

    @Override
    public Object getItem(int position) {
        return chores.get(position);
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
        holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
        holder.checkBox.setChecked(chores.get(position).isCompleted());
        holder.title.setText(chores.get(position).getTitle());
        holder.desc.setText(chores.get(position).getDescription());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chores.get(position).setCompleted(isChecked);
                Chore.saveChores(context, chores);
            }
        });
        if(chores.get(position).isCompleted()) {
            // TODO Auto-generated method stub
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.desc.setPaintFlags(holder.desc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        CheckBox checkBox;
    }
}
