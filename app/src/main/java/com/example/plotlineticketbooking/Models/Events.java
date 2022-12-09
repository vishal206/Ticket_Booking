package com.example.plotlineticketbooking.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable {
    String name,description,category,duration;
    Boolean isSelected;

    public Events(String name, String description, String category, String duration,Boolean isSelected) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.isSelected=isSelected;
    }

    protected Events(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        duration = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
    }

    public static final Creator<Events> CREATOR = new Creator<Events>() {
        @Override
        public Events createFromParcel(Parcel in) {
            return new Events(in);
        }

        @Override
        public Events[] newArray(int size) {
            return new Events[size];
        }
    };

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeString(duration);
        parcel.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
    }
}
