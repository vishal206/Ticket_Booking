package com.example.plotlineticketbooking.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Seats implements Parcelable {
    String name;
    Boolean selected,booked;

    public Seats(String name, Boolean selected, Boolean booked) {
        this.name = name;
        this.selected = selected;
        this.booked = booked;
    }

    protected Seats(Parcel in) {
        name = in.readString();
        byte tmpSelected = in.readByte();
        selected = tmpSelected == 0 ? null : tmpSelected == 1;
        byte tmpBooked = in.readByte();
        booked = tmpBooked == 0 ? null : tmpBooked == 1;
    }

    public static final Creator<Seats> CREATOR = new Creator<Seats>() {
        @Override
        public Seats createFromParcel(Parcel in) {
            return new Seats(in);
        }

        @Override
        public Seats[] newArray(int size) {
            return new Seats[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (selected == null ? 0 : selected ? 1 : 2));
        parcel.writeByte((byte) (booked == null ? 0 : booked ? 1 : 2));
    }
}
