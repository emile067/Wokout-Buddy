package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.adapters.NewWorkoutAdapter;
import com.moringa.workoutbuddy.models.Exercise;
import com.moringa.workoutbuddy.models.ExerciseDialog;
import com.moringa.workoutbuddy.models.Workout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewWorkoutActivity extends AppCompatActivity implements ExerciseDialog.ExerciseDialogListener{
    @BindView(R.id.addExerciseButton)
    ImageButton mAddExerciseButton;
    @BindView(R.id.createWorkoutButton)
    Button mCreateWorkoutButton;
    @BindView(R.id.workoutNameEditText)
    EditText mWorkoutNameEditText;
    @BindView(R.id.workoutDescriptionEditText)
    EditText mWorkoutDescriptionEditText;
    @BindView(R.id.exercisesRecyclerView) RecyclerView mExercisesRecyclerView;

    List<Exercise> exerciseList = new ArrayList<Exercise>();
    private NewWorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        ButterKnife.bind(this);
        exerciseList = new ArrayList<Exercise>();
        createAdapter();

        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        mCreateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String workoutName = mWorkoutNameEditText.getText().toString();
                String workoutDescription = mWorkoutDescriptionEditText.getText().toString();
                Workout newWorkout = new Workout(workoutName,workoutDescription,exerciseList);
                WorkoutListActivity.workoutsList.add(newWorkout);
                Intent intent = new Intent(NewWorkoutActivity.this,WorkoutListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openDialog() {
        ExerciseDialog exerciseDialog = new ExerciseDialog();
        exerciseDialog.show(getSupportFragmentManager(), "exercise dialog");
    }

    @Override
    public void applyTexts(String exerciseName, String time, String reps) {
        Exercise newExercise = new Exercise(Integer.parseInt(reps),exerciseName,Integer.parseInt(reps));
        exerciseList.add(exerciseList.size(),newExercise);
        workoutAdapter.notifyItemChanged(exerciseList.size());
    }

    public void createAdapter(){
        workoutAdapter = new NewWorkoutAdapter(NewWorkoutActivity.this, exerciseList);
        mExercisesRecyclerView.setAdapter(workoutAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(NewWorkoutActivity.this);
        mExercisesRecyclerView.setLayoutManager(layoutManager);
        mExercisesRecyclerView.setHasFixedSize(true);
    }

    public void removeExercise(int position){
        exerciseList.remove(position);
        workoutAdapter.notifyItemChanged(position);
    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}