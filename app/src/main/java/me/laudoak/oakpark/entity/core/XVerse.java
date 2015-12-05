package me.laudoak.oakpark.entity.core;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by LaudOak on 2015-9-30.
 */
public class XVerse extends BmobObject implements Serializable{

    private String whisper;
    private int dateCode;

    private Poet bulbul;
    private String title;
    private String author;
    private String verse;
    private String imageName;
    private String imageURL;

    private boolean pass;


    public String getWhisper() {
        return whisper;
    }

    public void setWhisper(String whisper) {
        this.whisper = whisper;
    }

    public int getDateCode() {
        return dateCode;
    }

    public void setDateCode(int dateCode) {
        this.dateCode = dateCode;
    }

    public Poet getBulbul() {
        return bulbul;
    }

    public void setBulbul(Poet bulbul) {
        this.bulbul = bulbul;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }
}
