package me.laudoak.oakpark.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by LaudOak on 2015-9-30.
 */
public class XVerse extends BmobObject implements Serializable{

    private String whisper;
    private int DateCode;

    private Poet bulbul;
    private String title;
    private String author;
    private String verse;
    private String imageName;
    private String imageURL;


    public Poet getPoet() {
        return bulbul;
    }

    public void setPoet(Poet poet) {
        this.bulbul = poet;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public String getWhisper() {
        return whisper;
    }

    public void setWhisper(String whisper) {
        this.whisper = whisper;
    }

    public int getDateCode() {
        return DateCode;
    }

    public void setDateCode(int dateCode) {
        DateCode = dateCode;
    }
}
