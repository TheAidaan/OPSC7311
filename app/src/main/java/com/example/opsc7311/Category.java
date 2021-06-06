package com.example.opsc7311;


import android.graphics.Color;
import android.media.Image;

import java.util.List;

public class Category extends Group{
    Image icon;
    int iconId;
    String description;

    void SetIcon(Image Icon){
        icon = Icon;
    }
    Category(String Name/*, Image Icon*/, String Description, Color Color, int IconID){
        SetName(Name);
        SetColor(Color);
        //icon = Icon;
        description = Description;
        iconId = IconID;

    }
}
