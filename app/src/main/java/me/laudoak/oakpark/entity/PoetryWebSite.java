package me.laudoak.oakpark.entity;

/**
 * Created by LaudOak on 2015-12-5 at 13:08.
 */
public class PoetryWebSite
{
    private String name;
    private String url;

    public PoetryWebSite(String url, String name)
    {
        this.name = name;
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
