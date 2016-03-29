package com.appzollo.classes;

/**
 * Created by vijay on 31-Oct-14.
 */
import android.nfc.Tag;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    public long ID;
    public String title;
    public String author;
    public String url;
    @SerializedName("date")
    public Date date;
    public String body;

    public List<Tag> tags;

    public Post() {

    }
}