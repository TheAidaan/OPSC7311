package com.example.opsc7311;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public String name;
    public int numberOfItems;
    public int numberCheckedOfItems;

    public List<Content> contents = new ArrayList<Content>();

    public Color color;

    public float PercentageCompleted(){
        float percentage = numberOfItems/numberCheckedOfItems;
        return percentage;
    }

    public void SetColor(Color Color){
        color=Color;
    }

    public void SetName(String Name){
        name=Name;
    }

    public void IncreaseCompleted(){
        numberOfItems++;
    }

    public void AddContent(Content content){
        contents.add(content);
    }

    public void DeleteContent(Content content){
        contents.remove(content);
    }
}
