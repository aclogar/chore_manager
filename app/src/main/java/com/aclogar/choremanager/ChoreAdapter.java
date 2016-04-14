package com.aclogar.choremanager;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aclogar.choremanager.objects.Chore;

import java.util.ArrayList;

/**
 * Created by Anthony on 4/12/2016.
 */
public class ChoreAdapter extends BaseAdapter {
    ArrayList<Chore> chores;
    Context context;
    private static LayoutInflater inflater=null;

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

    public class Holder
    {
        TextView title;
        TextView desc;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.chore_item, null);
        holder.title=(TextView) rowView.findViewById(R.id.chore_title);
        holder.desc=(TextView) rowView.findViewById(R.id.chore_desc);
        holder.title.setText(chores.get(position).getTitle());
        holder.desc.setText(chores.get(position).getDescription());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+chores.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        return rowView;
    }
}