package com.moringa.workoutbuddy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
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
                if(exerciseList.size() == 0){
                    emptyExercisesDialog();
                }else {
                    String workoutName = mWorkoutNameEditText.getText().toString();
                    String workoutDescription = mWorkoutDescriptionEditText.getText().toString();
                    Workout newWorkout = new Workout(workoutName, workoutDescription, exerciseList);
                    WorkoutListActivity.workoutsList.add(newWorkout);
                    Intent intent = new Intent(NewWorkoutActivity.this, WorkoutListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void emptyExercisesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The workout won't be created if empty. Are you sure you want to exit?")
        .setCancelable(false)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(NewWorkoutActivity.this, WorkoutListActivity.class);
                startActivity(intent);
            }
        })
        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
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
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mExercisesRecyclerView);
    }

    public void removeExercise(int position){
        exerciseList.remove(position);
        workoutAdapter.notifyItemChanged(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NewWorkoutActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            removeExercise(viewHolder.getLayoutPosition());
        }
    };

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}