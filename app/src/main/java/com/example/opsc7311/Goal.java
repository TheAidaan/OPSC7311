package com.example.opsc7311;

import android.graphics.Color;
import android.media.Image;

import java.util.List;

public class Goal extends Group{

    public Boolean completed;

    void CheckCompleted(){
        if (PercentageCompleted == 1)
        {
            completed = true;
        }
    }

    Goal(String Name, Color Color){
        SetName(Name);
        SetColor(Color);
        completed = false;

    }
}
