package com.moringa.workoutbuddy.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.moringa.workoutbuddy.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutActivity extends AppCompatActivity {
    @BindView(R.id.elapsedTimeTextView) TextView mElapsedTimeTextView;
    @BindView(R.id.timeLeftTextView) TextView mTimeLeftTextView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        ButterKnife.bind(this);

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
//                mButtonStartPause.setText("Start");
//                mButtonStartPause.setVisibility(View.INVISIBLE);
//                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mPauseImageButton.setVisibility(View.VISIBLE);
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