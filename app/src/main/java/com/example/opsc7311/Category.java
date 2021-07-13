package com.example.opsc7311;


import android.graphics.Color;
import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Category extends Group{
    Image icon;
    int iconId;
    String description,categoryID;

    List<ContentUploadHelperClass> contents = new ArrayList<ContentUploadHelperClass>();

    public Category(Image icon, int iconId, String description, String categoryID) {
        this.icon = icon;
        this.iconId = iconId;
        this.description = description;
        this.categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public  List<ContentUploadHelperClass> getContents() {
        return contents;
    }

    public void setContents( List<ContentUploadHelperClass> contents) {
        this.contents = contents;
    }
}
