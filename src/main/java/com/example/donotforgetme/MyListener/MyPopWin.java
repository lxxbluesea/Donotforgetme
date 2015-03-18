package com.example.donotforgetme.MyListener;

import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.donotforgetme.R;

/**
 * Created by ZJGJK03 on 2015/2/25.
 */
public class MyPopWin {
    LayoutInflater layoutInflater;
    View view;
    int menuId;
MyPopWinListener popWinListener;
    int offsetX,offsetY;

    public void setPopWinListener(MyPopWinListener popWinListener) {
        this.popWinListener = popWinListener;
    }

    public MyPopWin(LayoutInflater layoutInflater, View view, int menuId) {
        this.layoutInflater = layoutInflater;
        this.view = view;
        this.menuId = menuId;
    }

    public MyPopWin(LayoutInflater layoutInflater, View view, int menuId, int offsetX, int offsetY) {
        this.layoutInflater = layoutInflater;
        this.view = view;
        this.menuId = menuId;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void showPopMenu() {
        View popwindow = layoutInflater.inflate(menuId, null);
        if(popWinListener!=null)
            popWinListener.Execute(popwindow);
        //btn_warn=(Button)popwindow.findViewById(R.id.selectormenu_btn_warn);
        //btn_warn=(Button)popwindow.findViewById(R.id.selectormenu_btn_note);
        //btn_warn.setOnClickListener(this);
        //btn_note.setOnClickListener(this);

        final PopupWindow popupWindow = new PopupWindow(popwindow, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view, offsetX, offsetY);
    }
}
