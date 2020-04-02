package com.baru.shawnmendes.lirikmp3;

public class Story {
    private String title;
    private int numOfPagess;
    private int cover;
    private String content;
    private String ytUrl;

    public Story(){
    }

    public Story(String title, int numOfPagess, int cover, String content, String ytUrl){
        this.title = title;
        this.numOfPagess = numOfPagess;
        this.cover = cover;
        this.content = content;
        this.ytUrl = ytUrl;
    }

    public String getTitle() {return title;}
    public void setTitle() {this.title=title;}

    public int getnumOfPagess() {return numOfPagess;}
    public void setnumOfPagess() {this.numOfPagess=numOfPagess;}

    public int getCover() {return cover;}
    public void setCover() {this.cover=cover;}

    public String getContent() {return content;}
    public void setContent() {this.content=content;}

    public String getYtUrl() {return ytUrl;}
    public void setYtUrl() {this.ytUrl=ytUrl;}

}
