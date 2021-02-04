package com.moringa.workoutbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.models.Exercise;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewWorkoutAdapter extends RecyclerView.Adapter<NewWorkoutAdapter.ExerciseViewHolder>{
    private List<Exercise> mExercises = new ArrayList<Exercise>();
    private Context mContext;

    public NewWorkoutAdapter(Context mContext, List<Exercise> mExercises) {
        this.mContext = mContext;
        this.mExercises = mExercises;
    }

    @NonNull
    @Override
    public NewWorkoutAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_workout_list_item, parent, false);
        ExerciseViewHolder viewHolder = new ExerciseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewWorkoutAdapter.ExerciseViewHolder holder, int position) {
        holder.bindExercise(mExercises.get(position));
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.newExerciseNameView)
        TextView mNewExerciseNameView;
        private Context mContext;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindExercise(Exercise exercise){
            mNewExerciseNameView.setText(exercise.getName());
        }
    }
}
