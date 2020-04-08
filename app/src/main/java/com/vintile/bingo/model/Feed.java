package com.vintile.bingo.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sharath on 2020/04/05
 **/
@Entity(tableName = "table_box")
public class Feed {

    @PrimaryKey
    @NonNull
    @SerializedName("box_id")
    @Expose
    private int boxID;


    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("checked")
    @Expose
    private boolean checked;

    public Feed(){}

    public Feed(int boxID, int number, boolean checked) {
        this.boxID = boxID;
        this.number = number;
        this.checked = checked;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
