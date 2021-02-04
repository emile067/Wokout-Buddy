package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.adapters.NewWorkoutAdapter;
import com.moringa.workoutbuddy.adapters.WorkoutListAdapter;
import com.moringa.workoutbuddy.models.Exercise;
import com.moringa.workoutbuddy.models.Workout;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutListActivity extends AppCompatActivity implements Serializable {
    @BindView(R.id.addWorkoutButton)
    ImageButton mAddExerciseButton;
    @BindView(R.id.workoutsRecyclerView) RecyclerView mWorkoutsRecyclerView;

    static List<Workout> workoutsList = new ArrayList<Workout>();
    private WorkoutListAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        ButterKnife.bind(this);

        createAdapter();

        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WorkoutListActivity.this, NewWorkoutActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createAdapter(){
        workoutAdapter = new WorkoutListAdapter(WorkoutListActivity.this, workoutsList);
        mWorkoutsRecyclerView.setAdapter(workoutAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(WorkoutListActivity.this);
        mWorkoutsRecyclerView.setLayoutManager(layoutManager);
        mWorkoutsRecyclerView.setHasFixedSize(true);
        workoutAdapter.setOnItemClickListener(new WorkoutListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(WorkoutListActivity.this,ViewWorkoutActivity.class);
                intent.putExtra("workout", workoutsList.get(position));
                startActivity(intent);
            }
        });
    }
}