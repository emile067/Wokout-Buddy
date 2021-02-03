package com.moringa.workoutbuddy.models;

public class Exercise {
    private int reps;
    private String name;
    private int time;

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Exercise(int reps, String name, int time) {
        this.reps = reps;
        this.name = name;
        this.time = time;
    }
}
