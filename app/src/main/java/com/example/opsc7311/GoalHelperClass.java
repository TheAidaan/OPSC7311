package com.example.opsc7311;

public class GoalHelperClass {

    String goalID, name;
    float percentageCompleted;

    public GoalHelperClass() { }

    public GoalHelperClass(String goalID, String name, String description) {
        this.goalID = goalID;
        this.name = name;
    }

    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    public String getCategoryID() {
        return goalID;
    }

    public void setCategoryID(String categoryID) {
        this.goalID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
