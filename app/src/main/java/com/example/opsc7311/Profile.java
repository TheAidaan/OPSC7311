package com.example.opsc7311;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;

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
    String name,email,password,username;
    DatabaseReference reference;

    List<CategoryHelperClass> categories = new ArrayList<CategoryHelperClass>();
    public List<Goal> goals = new ArrayList<Goal>();

    public static void setInstance(Profile instance) {
        Profile.instance = instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public void setCategories(List<CategoryHelperClass> category) {
        if (!this.categories.contains(categories))
            this.categories = category;
    }

    public List<CategoryHelperClass> getCategories() {
        return categories;
    }

    public void addCategory(CategoryHelperClass category) {
        this.categories.add(category);
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}
