package com.example.donotforgetme.Utils;

/**
 * Created by ZJGJK03 on 2015/3/26.
 */
public class SleepUtil {
    /**
     * 睡眠一段时间，默认睡眠300毫秒
     */
    public static void Sleep()
    {
        try
        {
            Thread.sleep(300);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 睡眠一段时间
     * @param relay 设置睡眠时间
     */
    public static void Sleep(int relay)
    {
        try
        {
            Thread.sleep(relay);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
