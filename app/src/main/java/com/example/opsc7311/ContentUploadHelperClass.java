package com.example.opsc7311;

public class ContentUploadHelperClass {
    String contentID,name,description,imageUrl;
    Boolean checkedIn;

    public ContentUploadHelperClass() {
    }

    public ContentUploadHelperClass(String contentID, String name, String description, String imageUrl, Boolean checkedIn) {
        this.contentID = contentID;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.checkedIn = checkedIn;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
