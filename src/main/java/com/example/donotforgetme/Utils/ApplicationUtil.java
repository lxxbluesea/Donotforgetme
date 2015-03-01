package com.example.donotforgetme.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by ZJGJK03 on 2015/2/23.
 */
public class ApplicationUtil extends Application {
    static Context context;//定义静态对象

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();//初始化对象
    }
    /**
     * 获取Context对象
     * @return
     */
    public static Context getContext()
    {
        return context;
    }
}
