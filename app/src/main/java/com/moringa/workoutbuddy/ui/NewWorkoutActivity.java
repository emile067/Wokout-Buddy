package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.models.ExerciseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewWorkoutActivity extends AppCompatActivity implements ExerciseDialog.ExerciseDialogListener {
    @BindView(R.id.addExerciseButton)
    ImageButton mAddExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);
        ButterKnife.bind(this);

        mAddExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        ExerciseDialog exerciseDialog = new ExerciseDialog();
        exerciseDialog.show(getSupportFragmentManager(), "exercise dialog");
    }

    @Override
    public void applyTexts(String exerciseName, String time, String reps) {

    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
}