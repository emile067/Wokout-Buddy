package com.moringa.workoutbuddy.models;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.moringa.workoutbuddy.R;

public class ExerciseDialog extends AppCompatDialogFragment {
    private EditText repsEdit;
    private EditText timeEdit;
    private EditText exerciseNameEdit;
    private ExerciseDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Add Exercise")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String exerciseName = exerciseNameEdit.getText().toString();
                        String time = timeEdit.getText().toString();
                        String reps = repsEdit.getText().toString();
                        listener.applyTexts(exerciseName, time, reps);
                    }
                });
        repsEdit = view.findViewById(R.id.repsEditText);
        timeEdit = view.findViewById(R.id.timeEditText);
        exerciseNameEdit = view.findViewById(R.id.exerciseNameEditText);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExerciseDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExerciseDialogListener");
        }
    }
    public interface ExerciseDialogListener {
        void applyTexts(String exerciseName, String time, String reps);
    }
}
