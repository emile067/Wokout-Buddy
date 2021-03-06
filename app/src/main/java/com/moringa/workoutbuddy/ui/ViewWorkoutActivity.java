package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.adapters.NewWorkoutAdapter;
import com.moringa.workoutbuddy.models.Exercise;
import com.moringa.workoutbuddy.models.Workout;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewWorkoutActivity extends AppCompatActivity implements Serializable {
    @BindView(R.id.workoutNameTextView)
    TextView mWorkoutNameTextView;
    @BindView(R.id.workoutDescriptionTextView)
    TextView mWorkoutDescriptionTextView;
    @BindView(R.id.exercisesRecyclerView)
    RecyclerView mExercisesRecyclerView;
    @BindView(R.id.startWorkoutButton)
    Button mStartWorkoutButton;

    Workout workout;

    private NewWorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);
        ButterKnife.bind(this);

        workout= (Workout) getIntent().getSerializableExtra("workout");
        mWorkoutNameTextView.setText(workout.getName());
        mWorkoutDescriptionTextView.setText(workout.getDescription());
        createAdapter();

        mStartWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewWorkoutActivity.this, WorkoutActivity.class);
                intent.putExtra("workout",workout);
                startActivity(intent);
            }
        });
    }

    public void createAdapter(){
        workoutAdapter = new NewWorkoutAdapter(ViewWorkoutActivity.this, workout.getExerciseList());
        mExercisesRecyclerView.setAdapter(workoutAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(ViewWorkoutActivity.this);
        mExercisesRecyclerView.setLayoutManager(layoutManager);
        mExercisesRecyclerView.setHasFixedSize(true);
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
        Intent intent = new Intent(ViewWorkoutActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}