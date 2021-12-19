package com.example.uts_pbp_d_kelompok_3.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class History implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("delivery_id")
    private String deliveryId;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    public History(String id, String deliveryId, String date, String status) {
        this.id = id;
        this.deliveryId = deliveryId;
        this.date = date;
        this.status = status;
    }

    protected History(Parcel in) {
        id = in.readString();
        deliveryId = in.readString();
        date = in.readString();
        status = in.readString();
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(deliveryId);
        dest.writeString(date);
        dest.writeString(status);
    }
}
