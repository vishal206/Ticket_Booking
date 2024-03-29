package com.example.plotlineticketbooking.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Events implements Parcelable {
    String name, description, category, duration, showDate, firebaseDocName,timestamp,bookedDocName;
    Boolean isSelected;
    ArrayList<String> bookedSeats, selectedSeats;
    String showPic;


    public Events(String name, String description, String category, String duration, Boolean isSelected, ArrayList<String> bookedSeats, ArrayList<String> selectedSeats, String showDate, String firebaseDocName,
                  String bookedDocName,String showPic) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.duration = duration;
        this.isSelected = isSelected;
        this.bookedSeats = bookedSeats;
        this.selectedSeats = selectedSeats;
        this.showDate = showDate;
        this.firebaseDocName = firebaseDocName;
        this.bookedDocName=bookedDocName;
        this.showPic=showPic;
    }

    protected Events(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        duration = in.readString();
        showDate = in.readString();
        firebaseDocName = in.readString();
        timestamp = in.readString();
        bookedDocName = in.readString();
        byte tmpIsSelected = in.readByte();
        isSelected = tmpIsSelected == 0 ? null : tmpIsSelected == 1;
        bookedSeats = in.createStringArrayList();
        selectedSeats = in.createStringArrayList();
        showPic = in.readString();
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

    public String getShowPic() {
        return showPic;
    }

    public void setShowPic(String showPic) {
        this.showPic = showPic;
    }

    public String getFirebaseDocName() {
        return firebaseDocName;
    }

    public void setFirebaseDocName(String firebaseDocName) {
        this.firebaseDocName = firebaseDocName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBookedDocName() {
        return bookedDocName;
    }

    public void setBookedDocName(String bookedDocName) {
        this.bookedDocName = bookedDocName;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public ArrayList<String> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(ArrayList<String> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public ArrayList<String> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(ArrayList<String> selectedSeats) {

        this.selectedSeats = selectedSeats;
    }

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
        parcel.writeString(showDate);
        parcel.writeString(firebaseDocName);
        parcel.writeString(timestamp);
        parcel.writeString(bookedDocName);
        parcel.writeByte((byte) (isSelected == null ? 0 : isSelected ? 1 : 2));
        parcel.writeStringList(bookedSeats);
        parcel.writeStringList(selectedSeats);
        parcel.writeString(showPic);
    }
}
