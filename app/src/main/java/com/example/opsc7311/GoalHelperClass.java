package com.example.opsc7311;

public class GoalHelperClass {

    String goalID, name;
    float percentageCompleted;

    public GoalHelperClass() { }

    public GoalHelperClass(String goalID, String name,float percentageCompleted) {
        this.goalID = goalID;
        this.name = name;
        this.percentageCompleted = percentageCompleted;
    }

    public float getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(float percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
