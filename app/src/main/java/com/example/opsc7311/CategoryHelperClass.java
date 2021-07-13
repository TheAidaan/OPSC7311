package com.example.opsc7311;

import java.util.ArrayList;
import java.util.List;

public class CategoryHelperClass {

    String categoryID, name, description;

    public CategoryHelperClass() { }

    public CategoryHelperClass(String categoryID, String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

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
}
