package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.models.Exercise;
import com.moringa.workoutbuddy.models.Workout;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutActivity extends AppCompatActivity {
    @BindView(R.id.OngoingWorkoutNameTextView) TextView mOngoingWorkoutNameTextView;
    @BindView(R.id.nextExerciseTextView) TextView mNextExerciseTextView;
    @BindView(R.id.timerTextView) TextView mTimerTextView;
    @BindView(R.id.repsTextView) TextView mRepsTextView;
    @BindView(R.id.onGoingExercise) TextView mOnGoingExercise;
    @BindView(R.id.pauseImageButton) ImageButton mPauseImageButton;
    @BindView(R.id.playImageButton) ImageButton mPlayImageButton;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;

    Workout workout;
    List<Exercise> exercises;
    int lastItemIndex;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        ButterKnife.bind(this);

        workout= (Workout) getIntent().getSerializableExtra("workout");
        exercises = workout.getExerciseList();
        lastItemIndex = exercises.size()-1;
        mOngoingWorkoutNameTextView.setText(workout.getName());

        startExercise();

        mPauseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
        mPlayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        updateCountDownText();
    }

    public void startExercise(){
//                int nextExerciseIndex = exercises.indexOf(exercise) + 1;
        mOnGoingExercise.setText(exercises.get(i).getName());
        mRepsTextView.setText(String.valueOf(exercises.get(i).getReps()));
        if (i+1 < exercises.size()) {
            mNextExerciseTextView.setText(exercises.get(i+1).getName());
        } else {
            mNextExerciseTextView.setText("Done");
        }
        long millis = exercises.get(i).getTime() * 1000;
        setTime(millis);
        startTimer();
    }

    public void nextExercise(){
        i++;
        if(i < exercises.size()){
            startExercise();
        }else{
            Intent intent = new Intent(WorkoutActivity.this,WorkoutListActivity.class);
            startActivity(intent);
        }
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                nextExercise();
            }
        }.start();
        mTimerRunning = true;
        mPauseImageButton.setVisibility(View.VISIBLE);
        mPlayImageButton.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mPauseImageButton.setVisibility(View.INVISIBLE);
        mPlayImageButton.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTimerTextView.setText(timeLeftFormatted);
    }
}