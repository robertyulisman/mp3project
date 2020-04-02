package com.baru.shawnmendes.lirikmp3;

public class Audio {
    private int id;
    private String name, speaker, length, url;

    public Audio() {
    }

    public Audio(int id, String name, String speaker, String length, String url) {
        this.id = id;
        this.name = name;
        this.speaker = speaker;
        this.length = length;
        this.url = url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpeaker() {
        return speaker;
    }
    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
