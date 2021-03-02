package com.moringa.workoutbuddy.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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
    ImageButton mAddWorkoutButton;
    @BindView(R.id.noWorkoutsTextView)
    TextView mNoWorkoutsTextView;
    @BindView(R.id.workoutsRecyclerView) RecyclerView mWorkoutsRecyclerView;

    static List<Workout> workoutsList = new ArrayList<Workout>();
    private WorkoutListAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);
        ButterKnife.bind(this);

        createAdapter();
        checkList();

        mAddWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(WorkoutListActivity.this, NewWorkoutActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkList(){
        if (workoutsList.isEmpty()){
            mNoWorkoutsTextView.setVisibility(View.VISIBLE);
            mWorkoutsRecyclerView.setVisibility(View.GONE);
        }else{
            mNoWorkoutsTextView.setVisibility(View.GONE);
            mWorkoutsRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    public void createAdapter(){
        workoutAdapter = new WorkoutListAdapter(WorkoutListActivity.this, workoutsList);
        mWorkoutsRecyclerView.setAdapter(workoutAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(WorkoutListActivity.this);
        mWorkoutsRecyclerView.setLayoutManager(layoutManager);
        mWorkoutsRecyclerView.setHasFixedSize(true);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mWorkoutsRecyclerView);
        workoutAdapter.setOnItemClickListener(new WorkoutListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(WorkoutListActivity.this,ViewWorkoutActivity.class);
                intent.putExtra("workout", workoutsList.get(position));
                startActivity(intent);
            }
        });
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
        Intent intent = new Intent(WorkoutListActivity.this, LoginActivity.class);
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
            workoutsList.remove(viewHolder.getLayoutPosition());
            workoutAdapter.notifyDataSetChanged();
            checkList();
        }
    };
}