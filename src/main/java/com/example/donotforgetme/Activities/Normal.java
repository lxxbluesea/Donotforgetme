package com.example.donotforgetme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.Status;
import com.example.donotforgetme.MyListener.MyClickTouchListener;
import com.example.donotforgetme.MyListener.MyPageAdapterListener;
import com.example.donotforgetme.MyListener.MyPageChangeListener;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;
import com.example.donotforgetme.Utils.StatusUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Normal extends Fragment {

    ViewPager viewPager;
    List<View> views;
    List<String> titles;
    ImageView cursor;


    //Normal_Executing窗口的代码
    ListView listView;
    ItemUtil itemUtil;
    int type = 1;//分类，传入不同的值，得到不同的结果
    //=========结束==========


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.activity_normal, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //这里写一些代码加载的工作
        System.out.println("onActivityCreated");
        initViewPager();
    }

    void initViewPager() {
        viewPager = (ViewPager) getView().findViewById(R.id.normal_viewpager);
        cursor = (ImageView) getView().findViewById(R.id.normal_cursor);
        if (viewPager != null) {
            views = new ArrayList<View>();
            titles = new ArrayList<String>();

            LayoutInflater layoutInflater = LayoutInflater.from(this.getActivity());

            head = layoutInflater.inflate(R.layout.listview_head, null);

            views.add(layoutInflater.inflate(R.layout.activity_normal_executing, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delay, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_delete, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_finish, null));
            views.add(layoutInflater.inflate(R.layout.activity_normal_note, null));

            titles.add(getResources().getString(R.string.title_activity_executing));
            titles.add(getResources().getString(R.string.title_activity_delay));
            titles.add(getResources().getString(R.string.title_activity_delete));
            titles.add(getResources().getString(R.string.title_activity_finish));
            titles.add(getResources().getString(R.string.title_activity_note));

            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(views, titles);
            myPagerAdapter.setMyPageAdapterListener(new MyPageAdapterListener() {
                //其它这些代码写在这里不太合适
                //个人觉得可以写在ViewPager在页面切换时
                //viewPager.setOnPageChangeListener();写在这里可能会更合适一些
                //因为这里只使用一个数据容器来存放数据，所以每次切换都要重新加载
                //也可以使用多个容器，这样每个页面都有自己的容器，就不用每次切换时都加载一次数据
                //只有在数据变化时再更新，这样的话效率会更高一些，但相应的代码量会大一些，逻辑也会简单一些。
                @Override
                public void Execute(int index) {
                    //如果当前页面和正在加载的页面相同时，则执行加载数据
                    //因为ViewPager每次划动时，可能加载2-3个页面
                    if (viewPager.getCurrentItem() == index) {

                        switch (index) {
                            case 0:
                                listView = (ListView) getActivity().findViewById(R.id.normal_execute_listview);
                                type = StatusUtil.EXECUTE;
                                break;
                            case 1:
                                listView = (ListView) getActivity().findViewById(R.id.normal_delay_listview);
                                type = StatusUtil.RELAY;
                                break;
                            case 2:
                                listView = (ListView) getActivity().findViewById(R.id.normal_delete_listview);
                                type = StatusUtil.DELETE;
                                break;
                            case 3:
                                listView = (ListView) getActivity().findViewById(R.id.normal_finish_listview);
                                type = StatusUtil.FINISH;
                                break;
                            case 4:
                                listView = (ListView) getActivity().findViewById(R.id.normal_note_listview);
                                type = StatusUtil.NOTE;
                                break;
                        }
                        //获取数据
                        getDate(type);
                    }
                }
            });

            viewPager.setAdapter(myPagerAdapter);
            viewPager.setCurrentItem(0);
            viewPager.setOnPageChangeListener(new MyPageChangeListener(initTextView(), cursor, getDisplayMetrice(), 5));

            itemUtil = new ItemUtil();
        }
    }

    List<TextView> initTextView() {
        TextView execute, delay, delete, finish, note;
        execute = (TextView) getView().findViewById(R.id.normal_execute_tab_title);
        execute.setOnTouchListener(new MyClickTouchListener(0, viewPager));
        delay = (TextView) getView().findViewById(R.id.normal_delay_tab_title);
        delay.setOnTouchListener(new MyClickTouchListener(1, viewPager));
        delete = (TextView) getView().findViewById(R.id.normal_delete_tab_title);
        delete.setOnTouchListener(new MyClickTouchListener(2, viewPager));
        finish = (TextView) getView().findViewById(R.id.normal_finish_tab_title);
        finish.setOnTouchListener(new MyClickTouchListener(3, viewPager));
        note = (TextView) getView().findViewById(R.id.normal_note_tab_title);
        note.setOnTouchListener(new MyClickTouchListener(4, viewPager));

        List<TextView> textViews = new ArrayList<TextView>();
        textViews.add(execute);
        textViews.add(delay);
        textViews.add(delete);
        textViews.add(finish);
        textViews.add(note);

        return textViews;

    }

    /**
     * 获得屏幕的信息，其中包括有长和宽
     *
     * @return
     */
    DisplayMetrics getDisplayMetrice() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }


    //以下代码是处理ViewPager内部的Activity代码

    /**
     * 获得数据，
     *
     * @param type 指定获取类型
     */
    List<Item> itemList;
    View head;

    void getDate(int type) {
        itemList = itemUtil.getItems(type);
        List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
        if (!itemList.isEmpty()) {
            for (Item item : itemList) {
                HashMap<String, Object> d = new HashMap<String, Object>();
                d.put("content", item.getContent());
                d.put("begindatetime", DateUtil.getDateString(item.getBeginDateTime()));
                d.put("enddatetime", DateUtil.getDateString(item.getEndDateTime()));
                datas.add(d);
            }
        }


        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), datas, R.layout.listview_item, new String[]{"content", "begindatetime", "enddatetime"}, new int[]{R.id.listview_item_lv_title, R.id.listview_item_1, R.id.listview_item_2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(clickListener);
        listView.setOnItemLongClickListener(longClickListener);
        listView.setOnScrollListener(scrollListener);
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int itemId = itemList.get(position).getID();
            Intent intent = new Intent(getActivity(), Add.class);
            intent.putExtra("id", itemId);
            startActivity(intent);
        }
    };

    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return false;
        }
    };

    //是否下拉时更新数据，是否拉到最底时加载数据，是正更新完毕
    boolean scrollTop = false, scrollbottom = false, updatefinish = true;

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (scrollState == 0) {
                if (updatefinish) {//如果更新完毕后，才可以开始下一次的更新，为了防止连续多次更新
                    updatefinish = false;
                    if (scrollTop) {
                        listView.addHeaderView(head);
                        //getDate(type);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.obj = type;
                                handler.sendMessage(msg);
                            }
                        }).start();

                    } else if (scrollbottom) {

                    }
                }
            }

        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                int t = Integer.parseInt(msg.obj.toString());
                getDate(t);
                if (listView.getHeaderViewsCount() > 0)
                    listView.removeHeaderView(head);
                updatefinish = true;
                super.handleMessage(msg);

            }
        };


        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            Log.d("Normal", "firstVisibleItem is:" + firstVisibleItem + ",visibleItemCount is :" + visibleItemCount + ",totalItemCount is :" + totalItemCount);
            if (firstVisibleItem == 0) {
                scrollTop = true;
                scrollbottom = false;
            } else if (firstVisibleItem == (totalItemCount - visibleItemCount)) {
                scrollTop = false;
                scrollbottom = true;
            } else {
                scrollTop = false;
                scrollbottom = false;
            }

        }
    };

    //=========结束==========

}
