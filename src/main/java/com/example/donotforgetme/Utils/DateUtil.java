package com.example.donotforgetme.Utils;

import com.example.donotforgetme.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZJGJK03 on 2015/3/14.
 */
public class DateUtil {
    public static String getDateString(long date)
    {
        Date date1=new Date(date);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(ApplicationUtil.getContext().getResources().getString(R.string.datetimeformat1));
        return simpleDateFormat.format(date1);
    }
    public static long getDateLong(String date)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(ApplicationUtil.getContext().getResources().getString(R.string.datetimeformat1));

        Date date1=null;
        try
        {
            date1= simpleDateFormat.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date1.getTime();
    }
    public static long getNow()
    {
        Date date=new Date();
        return date.getTime();
    }
    public static long getDateOffset(String offset)
    {
        long longtimes;
        if(offset.equals("1小时"))
        {
            longtimes=Onehour;
        }else if(offset.equals("2小时"))
        {
            longtimes=Onehour*2;
        }
        else if(offset.equals("4小时"))
        {
            longtimes=Onehour*4;
        }else if(offset.equals("1天"))
        {
            longtimes=Onehour*24;
        }
        else if(offset.equals("2天"))
        {
            longtimes=Onehour*24*2;
        }
        else if(offset.equals("4天"))
        {
            longtimes=Onehour*24*4;
        }
        else if(offset.equals("1周"))
        {
            longtimes=Onehour*24*7;
        }
        else
            longtimes=Onehour;

        return longtimes;

    }
    public static long Onehour=60*60*1000;
}
