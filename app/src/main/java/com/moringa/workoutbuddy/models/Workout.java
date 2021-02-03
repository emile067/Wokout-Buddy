package com.moringa.workoutbuddy.models;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    private String name;
    private String description;
    private List<Exercise> exerciseList = new ArrayList<Exercise>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public Workout(String name, String description, List<Exercise> exerciseList) {
        this.name = name;
        this.description = description;
        this.exerciseList = exerciseList;
    }
}
