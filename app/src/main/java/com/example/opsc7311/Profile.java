package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Profile {

    private static Profile instance = null;

    private Profile(){};

    public static Profile getInstance(){
        if (instance == null) {
            instance = new Profile();
        }
        return instance;
    }
    public String name;
    public String email;
    public String password;
    public String username;

    public List<Category> categories = new ArrayList<Category>();
    public List<Goal> goals = new ArrayList<Goal>();


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
