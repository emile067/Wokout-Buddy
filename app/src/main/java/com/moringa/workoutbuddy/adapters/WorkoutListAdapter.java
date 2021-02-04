package com.moringa.workoutbuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringa.workoutbuddy.R;
import com.moringa.workoutbuddy.models.Exercise;
import com.moringa.workoutbuddy.models.Workout;
import com.moringa.workoutbuddy.ui.ViewWorkoutActivity;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.WorkoutViewHolder> {
    private List<Workout> mWorkoutList = new ArrayList<Workout>();
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public WorkoutListAdapter(Context mContext, List<Workout> mWorkoutList) {
        this.mContext = mContext;
        this.mWorkoutList = mWorkoutList;
    }

    @NonNull
    @Override
    public WorkoutListAdapter.WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_workouts_list_item, parent, false);
        WorkoutListAdapter.WorkoutViewHolder viewHolder = new WorkoutListAdapter.WorkoutViewHolder(view,mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutListAdapter.WorkoutViewHolder holder, int position) {
        holder.bindWorkout(mWorkoutList.get(position));
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.workoutNameTextView)
        TextView mWorkoutNameTextView;
        private Context mContext;

        public WorkoutViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bindWorkout(Workout workout){
            mWorkoutNameTextView.setText(workout.getName());
        }
    }
}
