package com.example.donotforgetme.Utils;

import android.content.DialogInterface;

import java.lang.reflect.Field;

/**
 * Created by ZJGJK03 on 2015/3/31.
 */
public class AlertDailogUtil {
    public static void SetDismiss(DialogInterface dialog,boolean flag)
    {
        try {
            Field field = dialog.getClass()
                    .getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            // 将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, flag);
            dialog.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
