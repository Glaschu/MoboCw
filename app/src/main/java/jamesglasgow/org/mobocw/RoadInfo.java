package jamesglasgow.org.mobocw;

import android.util.Log;

import java.util.Date;

/**
 * Created by jamesglasgow on 22/02/16.
 */
public class RoadInfo {
    private String Title;
    private String Discription;
    private String GPS;
    private Date Startdate;
    private Date Enddate;
    private String PubDate;
    private boolean visable;

    public RoadInfo()
    {
        //super();
        Title="";
        Discription="";
         GPS="";
        PubDate="";
        Startdate=null;
        Enddate=null;
        visable=true;
    }

    public RoadInfo(String aTitle, String aDiscription,String aGPS, String aPubDate)
    {
        //super();
        Title = aTitle;
        Discription = aDiscription;
        GPS = aGPS;
        PubDate=aPubDate;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String aTitle)
    {
        Title = aTitle;
    }
    public String getDiscription()
    {
        return Discription;
    }

    public void setDiscription(String aDiscription)
    {
        Discription = aDiscription;
    }
    public String getGPS()
    {
        return GPS;
    }

    public void setGPS(String aGPS)
    {
        GPS = aGPS;
    }
    public String getPubDate()
    {
        return PubDate;
    }

    public void setPubDate(String aPubDate)
    {
        PubDate = aPubDate;
    }
    public String toString()
    {
        String temp;

        temp = Title + " ";

        return temp;
    }
    public void setStartDate(Date date)
    {
        Startdate = date;
        Log.e("DateFind Save ", "" + Startdate);
    }
    public void setEndDate(Date date)
    {
        Enddate = date;
    }
    public Date getStartDate()
    {
        return Startdate;
    }
    public Date getEndDate()
    {
        return Enddate;
    }

    public void setVisable(boolean vis)
    {
        visable= vis;

    }
    public Boolean getVisable()
    {
        return visable;

    }


}
