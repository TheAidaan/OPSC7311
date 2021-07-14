package com.example.opsc7311;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GoalStackAdapter extends ArrayAdapter {
List<Goal> _goals;
Context _context;
int _goalStackLayout;
    public GoalStackAdapter(List<Goal> goals, int layout, Context context){
        super(context,layout,goals);
        _goals =goals;
        _goalStackLayout = layout;
        _context = context;
    }

    @Override
    public int getCount() {
        return _goals.size();
    }

    @Override
    public Object getItem(int position) {
        return _goals.get(position);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(_goalStackLayout,parent,false);
        }
        Goal goal = _goals.get(position);

        TextView txtTitle = convertView.findViewById(R.id.txtTitle_goal_stack);
        TextView txtPercentageCompletion = convertView.findViewById(R.id.txtPercentageCompleted_goal_stack);

        txtTitle.setText(goal.name);

        //float percentage_float = goal.numberCheckedOfItems/goal.numberOfItems;
        //int percentage_int = Math.round(percentage_float);
        //String percentage_string = Integer.toString(percentage_int);
        //txtPercentageCompletion.setText(percentage_string);

        return convertView;
    }
}
