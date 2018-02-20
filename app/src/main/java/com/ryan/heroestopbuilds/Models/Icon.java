package com.ryan.heroestopbuilds.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Icon implements Serializable {

    public Icon(String portrait) {
        this.portrait = portrait;
    }

    @SerializedName("92x93")
    @Expose
    private String portrait;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
