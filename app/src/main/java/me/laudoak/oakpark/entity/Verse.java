package me.laudoak.oakpark.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by LaudOak on 2015-9-30.
 */
public class Verse extends BmobObject {

    private Poet poet;
    private String title;
    private String author;
    private String verse;
    private String imageURL;
    private long pushTime;

}
