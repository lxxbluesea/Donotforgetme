package com.example.donotforgetme.Activities;

import android.app.ProgressDialog;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.Entities.Status;
import com.example.donotforgetme.MyListener.MyClickTouchListener;
import com.example.donotforgetme.MyListener.MyPageAdapterListener;
import com.example.donotforgetme.MyListener.MyPageChangeListener;
import com.example.donotforgetme.MyListener.MyPageChangerUpdateDataListener;
import com.example.donotforgetme.MyListener.MyPagerAdapter;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;
import com.example.donotforgetme.Utils.SleepUtil;
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
    ListView listView_execute, listview_delay, listview_delete, listview_note, listView_finish;
    ItemUtil itemUtil;
    List<Item> itemList_execute, itemList_delete, itemList_delay, itemList_note, itemList_finish;
    View head;
    int Index = 0;//当前ViewPager值
    int type = 1;//分类，传入不同的值，得到不同的结果
    ProgressDialog progressDialog;

    boolean first=true;//这个变更的作是仅作为第一次加载数据的标志，执行一次行，它的值将变成flase，目前只能用这个办法来解决问题，以后有好办法时再修改

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
                @Override
                public void Execute(int index) {
                    if(first) {
                        first=false;
                        getData();
                    }
                }
            });
            viewPager.setAdapter(myPagerAdapter);
            viewPager.setOnPageChangeListener(new MyPageChangeListener(initTextView(), cursor, getDisplayMetrice(), 5, new MyPageChangerUpdateDataListener() {
                @Override
                public void execute(int index) {
                    progressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));
                    Index = index;
                    switch (index) {
                        case 0:
                            if (listView_execute == null) {
                                listView_execute = (ListView) getActivity().findViewById(R.id.normal_execute_listview);
                                type = StatusUtil.EXECUTE;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData(type, itemList_execute);
                                    }
                                }).start();

                            } else
                                progressDialog.dismiss();
                            break;
                        case 1:
                            if (listview_delay == null) {
                                listview_delay = (ListView) getActivity().findViewById(R.id.normal_delay_listview);
                                type = StatusUtil.RELAY;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData(type, itemList_delay);
                                    }
                                }).start();
                            } else
                                progressDialog.dismiss();
                            break;
                        case 2:
                            if (listview_delete == null) {
                                listview_delete = (ListView) getActivity().findViewById(R.id.normal_delete_listview);
                                type = StatusUtil.DELETE;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData(type, itemList_delete);
                                    }
                                }).start();

                            } else
                                progressDialog.dismiss();
                            break;
                        case 3:
                            if (listView_finish == null) {
                                listView_finish = (ListView) getActivity().findViewById(R.id.normal_finish_listview);
                                type = StatusUtil.FINISH;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData(type, itemList_finish);
                                    }
                                }).start();
                            } else
                                progressDialog.dismiss();
                            break;
                        case 4:
                            if (listview_note == null) {
                                listview_note = (ListView) getActivity().findViewById(R.id.normal_note_listview);
                                type = StatusUtil.NOTE;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData(type, itemList_note);
                                    }
                                }).start();
                            } else
                                progressDialog.dismiss();
                            break;
                    }
                }
            }));
            viewPager.setCurrentItem(0);
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

    void getData()
    {
        Index=0;
        progressDialog = ProgressDialog.show(getActivity(), getResources().getString(R.string.import_normal_warn), getResources().getString(R.string.import_normal_warntext));
        listView_execute = (ListView) getActivity().findViewById(R.id.normal_execute_listview);
        type = StatusUtil.EXECUTE;
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData(type, itemList_execute);
            }
        }).start();
    }

    /**
     * 获得数据，
     *
     * @param type 指定获取类型
     */
    void getData(int type, List<Item> items) {
        items = itemUtil.getItems(type);
        Message msg = new Message();
        msg.obj = items;
        msg.arg1 = type;
        getDateHandle.sendMessage(msg);
    }

    Handler getDateHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<Item> items = (List<Item>) msg.obj;
            int index = msg.arg1;
            switch (index) {
                case StatusUtil.EXECUTE:
                    listView_execute.setAdapter(new MyAdpater(items, getActivity().getLayoutInflater()));
                    listView_execute.setOnItemClickListener(clickListener);
                    //listView_execute.setOnScrollListener(scrollListener);
                    break;
                case StatusUtil.RELAY:
                    listview_delay.setAdapter(new MyAdpater(items, getActivity().getLayoutInflater()));
                    listview_delay.setOnItemClickListener(clickListener);
                    //listview_delay.setOnScrollListener(scrollListener);
                    break;
                case StatusUtil.DELETE:
                    listview_delete.setAdapter(new MyAdpater(items, getActivity().getLayoutInflater()));
                    listview_delete.setOnItemClickListener(clickListener);
                    //listview_delete.setOnScrollListener(scrollListener);
                    break;
                case StatusUtil.FINISH:
                    listView_finish.setAdapter(new MyAdpater(items, getActivity().getLayoutInflater()));
                    listView_finish.setOnItemClickListener(clickListener);
                    //listView_finish.setOnScrollListener(scrollListener);
                    break;
                case StatusUtil.NOTE:
                    listview_note.setAdapter(new MyAdpater(items, getActivity().getLayoutInflater()));
                    listview_note.setOnItemClickListener(clickListener);
                    //listview_note.setOnScrollListener(scrollListener);
                    break;

            }
            if (progressDialog != null)
                progressDialog.dismiss();
            super.handleMessage(msg);
        }
    };

    class MyAdpater extends BaseAdapter {
        List<Item> itemList;
        LayoutInflater layoutInflater;
        int index = 0;

        MyAdpater(List<Item> items, LayoutInflater inflater) {
            this.itemList = items;
            layoutInflater = inflater;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return itemList.get(position).getID();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.index = position;
            Item i = itemList.get(position);
            HoderView hoderView;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listview_item, null);
                hoderView = new HoderView();
                //hoderView.tv_id=(TextView)convertView.findViewById(R.id.listview_item_id);
                hoderView.tv_content = (TextView) convertView.findViewById(R.id.listview_item_lv_title);
                hoderView.tv_content.setClickable(true);
                hoderView.tv_content.setFocusable(true);

                hoderView.tv_begin = (TextView) convertView.findViewById(R.id.listview_item_1);
                hoderView.tv_end = (TextView) convertView.findViewById(R.id.listview_item_2);
                hoderView.btn_detail = (Button) convertView.findViewById(R.id.listview_item_btn_detail);
                hoderView.btn_back = (Button) convertView.findViewById(R.id.listview_item_btn_back);
                hoderView.btn_delay = (Button) convertView.findViewById(R.id.listview_item_btn_delay);
                hoderView.btn_delete = (Button) convertView.findViewById(R.id.listview_item_btn_delete);
                hoderView.btn_finish = (Button) convertView.findViewById(R.id.listview_item_btn_finish);
                hoderView.iv_1=(ImageView)convertView.findViewById(R.id.listview_item_iv_1);
                hoderView.iv_2=(ImageView)convertView.findViewById(R.id.listview_item_iv_2);
                hoderView.iv_3=(ImageView)convertView.findViewById(R.id.listview_item_iv_3);
                hoderView.iv_4=(ImageView)convertView.findViewById(R.id.listview_item_iv_4);

                convertView.setTag(hoderView);
            } else {
                hoderView = (HoderView) convertView.getTag();
            }

            //hoderView.tv_id.setText(i.getID());
            hoderView.tv_content.setText(i.getContent());
            hoderView.tv_begin.setText(DateUtil.getDateString(i.getBeginDateTime()));
            hoderView.tv_end.setText(DateUtil.getDateString(i.getEndDateTime()));

            hoderView.tv_content.setOnClickListener(myClick);
            hoderView.tv_content.setTag(i.getID());
            hoderView.btn_back.setOnClickListener(myClick);
            hoderView.btn_back.setTag(i.getID());
            hoderView.btn_delay.setOnClickListener(myClick);
            hoderView.btn_delay.setTag(i.getID());
            hoderView.btn_finish.setOnClickListener(myClick);
            hoderView.btn_finish.setTag(i.getID());
            hoderView.btn_delete.setOnClickListener(myClick);
            hoderView.btn_delete.setTag(i.getID());
            hoderView.btn_detail.setOnClickListener(myClick);
            hoderView.btn_detail.setTag(i.getID());
            switch (Index) {
                case 0:
                    hoderView.btn_back.setVisibility(View.GONE);
                    hoderView.btn_delay.setVisibility(View.VISIBLE);
                    hoderView.btn_finish.setVisibility(View.VISIBLE);
                    hoderView.btn_delete.setVisibility(View.VISIBLE);
                    hoderView.btn_detail.setVisibility(View.VISIBLE);

                    hoderView.iv_1.setVisibility(View.VISIBLE);
                    hoderView.iv_2.setVisibility(View.VISIBLE);
                    hoderView.iv_3.setVisibility(View.VISIBLE);
                    hoderView.iv_4.setVisibility(View.GONE);
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:
                    break;
            }

            return convertView;
        }

        View.OnClickListener myClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                switch (v.getId()) {
                    case R.id.listview_item_btn_back:

                        break;
                    case R.id.listview_item_btn_delay:

                        break;
                    case R.id.listview_item_btn_delete:

                        break;
                    case R.id.listview_item_lv_title:
                    case R.id.listview_item_btn_detail:
                        intent = new Intent(getActivity(), Add.class);
                        intent.putExtra("id", Integer.parseInt(v.getTag().toString()));
                        break;
                    case R.id.listview_item_btn_finish:

                        break;
                }
                if (intent != null)
                    startActivity(intent);
            }
        };

        class HoderView {
            TextView tv_id,tv_content, tv_begin, tv_end;
            Button btn_detail, btn_finish, btn_delay, btn_back, btn_delete;
            ImageView iv_1,iv_2,iv_3,iv_4;
        }
    }

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int itemId ;
            Intent intent = null;
            switch (Index) {
                case 0:
                    itemId=itemList_execute.get(position).getID();
                    intent = new Intent(getActivity(), Add.class);
                    intent.putExtra("id", itemId);
                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
            }

            if (intent != null)
                startActivity(intent);
        }
    };


    //是否下拉时更新数据，是否拉到最底时加载数据，是正更新完毕
    boolean scrollTop = false, scrollbottom = false, updatefinish = true;

    AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

//            if (scrollState == 0) {
//                if (updatefinish) {//如果更新完毕后，才可以开始下一次的更新，为了防止连续多次更新
//                    updatefinish = false;
//                    if (scrollTop) {
//                        listView.addHeaderView(head);
//                        //getDate(type);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                SleepUtil.Sleep();
//                                Message msg = new Message();
//                                msg.obj = type;
//                                handler.sendMessage(msg);
//                            }
//                        }).start();
//
//                    } else if (scrollbottom) {
//
//                    }
//                }
//            }

        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

//                int t = Integer.parseInt(msg.obj.toString());
//                getDate(t);
//                if (listView.getHeaderViewsCount() > 0)
//                    listView.removeHeaderView(head);
//                updatefinish = true;
//                super.handleMessage(msg);

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
