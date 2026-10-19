package com.example.swipeapp;

public class VideoItem {
    
    public String videoUrl, title, description, id;

    /**
     * 
     * contructor
     * 
     * @param url the video url
     * @param title the title of the video
     * @param desc the description of the video
     * @param id the video id
     */
    public VideoItem(String url, String title, String desc, String id) {
        this.videoUrl = url;
        this.title = title;
        this.description = desc;
        this.id = id;
    }
}
