package com.example.opsc7311;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GoalStackAdapter extends ArrayAdapter {
List<GoalHelperClass> _goals;
Context _context;
int _goalStackLayout;
    public GoalStackAdapter(List<GoalHelperClass> goals, int layout, Context context){
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
        GoalHelperClass goal = _goals.get(position);

        TextView txtTitle = convertView.findViewById(R.id.txtTitle_goal_stack);
        TextView txtFootNote = convertView.findViewById(R.id.txtFootNote_goal_stack);
        TextView txtPercentageCompletion = convertView.findViewById(R.id.txtPercentageCompleted_goal_stack);
        ProgressBar pgbGoalProgress = convertView.findViewById(R.id.pgbGoalProgress_goal_stack);



        Profile.getInstance().getReference().child("goals").child(goal.goalID).child("contents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalContents = 0;
                ArrayList<Boolean> checkedInContents = new ArrayList<Boolean>();
                for (DataSnapshot postSnapshot : snapshot.getChildren())
                {
                    String contentID = postSnapshot.getValue(String.class);

                    FirebaseDatabase.getInstance().getReference("uploads").child(contentID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot contentSnapshot) {
                            ContentUploadHelperClass content = contentSnapshot.getValue(ContentUploadHelperClass.class);
                            if(content.checkedIn)
                               checkedInContents.add(content.checkedIn);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                int totalCheckedInContents = checkedInContents.size();
                    totalContents++;

                Toast.makeText(_context, String.valueOf(totalCheckedInContents), Toast.LENGTH_SHORT).show();
                int percentageCompleted = Math.round((totalCheckedInContents/totalContents)*100);

                txtPercentageCompletion.setText(String.valueOf(percentageCompleted)+"%");
                int remainingContentsToBeCheckedIn = totalContents-totalCheckedInContents;
                if (remainingContentsToBeCheckedIn == 1)
                    txtFootNote.setText(String.valueOf(remainingContentsToBeCheckedIn) + " more place to go!");
                    else
                    txtFootNote.setText(String.valueOf(remainingContentsToBeCheckedIn) + " places to go!");

                    pgbGoalProgress.setProgress(percentageCompleted);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txtTitle.setText(goal.name);

        return convertView;
    }
}
