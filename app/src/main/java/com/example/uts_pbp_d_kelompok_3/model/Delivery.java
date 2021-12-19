package com.example.uts_pbp_d_kelompok_3.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Delivery implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("resi")
    private String resi;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("origin")
    private String origin;

    @SerializedName("destination")
    private String destination;

    @SerializedName("weight")
    private int weight;

    @SerializedName("total")
    private int total;

    @SerializedName("date")
    private String date;

    @SerializedName("histories")
    private ArrayList<History> histories;

    public Delivery(int id, String resi, int userId, String origin, String destination, int weight, int total, String date, ArrayList<History> histories) {
        this.id = id;
        this.resi = resi;
        this.userId = userId;
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.total = total;
        this.date = date;
        this.histories = histories;
    }

    protected Delivery(Parcel in) {
        id = in.readInt();
        resi = in.readString();
        userId = in.readInt();
        origin = in.readString();
        destination = in.readString();
        weight = in.readInt();
        total = in.readInt();
        date = in.readString();
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel in) {
            return new Delivery(in);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResi() {
        return resi;
    }

    public void setResi(String resi) {
        this.resi = resi;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<History> getHistories() {
        return histories;
    }

    public void setHistories(ArrayList<History> histories) {
        this.histories = histories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(resi);
        dest.writeInt(userId);
        dest.writeString(origin);
        dest.writeString(destination);
        dest.writeInt(weight);
        dest.writeInt(total);
        dest.writeString(date);
    }
}
