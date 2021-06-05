package com.example.opsc7311;

import android.media.Image;

public class Content {
    public Image image;
    public String name;
    public String description;
    public boolean checekedIn;

    Category _category;
    Goal _goal;

    Content(Image Image, String Name, String Description){
        image = Image;
        name= Name;
        description = Description;
        checekedIn = false;

        _category = null;
        _goal = null;
    }

    public void CheckIn(){
        checekedIn = true;

        if(_category !=null){
            _category.IncreaseCompleted();
        }
        if(_goal !=null){
            _goal.IncreaseCompleted();
        }
    }
    public void AddToCatgeory(Category category){
        category.AddContent(this);
    }
    public void AddToGoal(Goal goal){
        goal.AddContent(this);
    }
}
