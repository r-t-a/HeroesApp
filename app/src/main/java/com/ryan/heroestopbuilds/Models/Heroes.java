package com.ryan.heroestopbuilds.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Heroes implements Serializable {

    public Heroes(String name, String url) {
        this.name = name;
        this.icon_url = new Icon(url);
    }

    @SerializedName("name")
    private String name;
    @SerializedName("short_name")
    private String short_name;
    @SerializedName("attribute_id")
    private String attribute_id;
    @SerializedName("translations")
    private String[] translations;
    @SerializedName("role")
    private String role;
    @SerializedName("type")
    private String type;
    @SerializedName("icon_url")
    private Icon icon_url;

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String[] getTranslations() {
        return translations;
    }

    public void setTranslations(String[] translations) {
        this.translations = translations;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon_url;
    }

    public void setIcon(Icon icon_url) {
        this.icon_url = icon_url;
    }
}
