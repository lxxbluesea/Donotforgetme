    package com.example.donotforgetme.MyListener;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.support.v4.app.Fragment;
    import android.text.TextUtils;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.donotforgetme.Activities.Add;
    import com.example.donotforgetme.Entities.Item;
    import com.example.donotforgetme.Entities.ItemNotice;
    import com.example.donotforgetme.Entities.ItemStatus;
    import com.example.donotforgetme.R;
    import com.example.donotforgetme.Utils.AlertDailogUtil;
    import com.example.donotforgetme.Utils.DateUtil;
    import com.example.donotforgetme.Utils.ItemStatusUtil;
    import com.example.donotforgetme.Utils.ItemUtil;
    import com.example.donotforgetme.Utils.StatusUtil;

    import java.util.List;

    /**
     * Created by ZJGJK03 on 2015/4/3.
     */
    public class MyListViewAdapter extends BaseAdapter {

        MyListViewAdapterListener myListViewAdapterListener;
        List<Item> itemList;
        LayoutInflater layoutInflater;
        int index = 0;
        Item currentItem;
        Activity activity;
        ItemUtil itemUtil;
        ItemStatusUtil statusUtil;
        //用它来启动Acticity，如果使用Activity的话，在获取
        Fragment fragment;

        public static int AddNotice=1000,AddNote=2000;
        public static int AddNoticeResult=1001,AddNoteResult=2001;

        public MyListViewAdapter(Activity activity, List<Item> items, int index) {
            this.activity=activity;
            this.itemList = items;
            this.index = index;
            layoutInflater=activity.getLayoutInflater();
            //itemUtil=new ItemUtil();
        }

        public MyListViewAdapter(Activity activity, List<Item> items, int index,Fragment fragment,MyListViewAdapterListener myListViewAdapterListener) {
            this.activity=activity;
            this.itemList = items;
            this.index = index;
            layoutInflater=activity.getLayoutInflater();
            this.myListViewAdapterListener=myListViewAdapterListener;
            this.fragment=fragment;
            //itemUtil=new ItemUtil();
        }
        public void setMyListViewAdapterListener(MyListViewAdapterListener myListViewAdapterListener) {
            this.myListViewAdapterListener = myListViewAdapterListener;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
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
            currentItem = itemList.get(position);
            HoderView_execute hoderView_execute;
            HoderView_finish hoderView_finish;
            HoderView_nobegin hoderView_nobegin;
            HoderView_delete hoderView_delete;
            HoderView_note hoderView_note;
            switch (index) {
                case 0:
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item_execute, null);
                        hoderView_execute = new HoderView_execute();
                        hoderView_execute.tv_content = (TextView) convertView.findViewById(R.id.listview_item_execute_lv_title);
                        hoderView_execute.tv_begin = (TextView) convertView.findViewById(R.id.listview_item_execute_1);
                        hoderView_execute.tv_end = (TextView) convertView.findViewById(R.id.listview_item_execute_2);
                        hoderView_execute.btn_warntimes = (Button) convertView.findViewById(R.id.listview_item_execute_btn_warntimes);
                        hoderView_execute.btn_finish = (Button) convertView.findViewById(R.id.listview_item_execute_btn_finish);
                        hoderView_execute.btn_delay = (Button) convertView.findViewById(R.id.listview_item_execute_btn_delay);
                        hoderView_execute.btn_delete = (Button) convertView.findViewById(R.id.listview_item_execute_btn_delete);

                        convertView.setTag(hoderView_execute);
                    } else {
                        hoderView_execute = (HoderView_execute) convertView.getTag();
                    }

                    hoderView_execute.tv_content.setTag(currentItem.getID());
                    hoderView_execute.tv_content.setText(currentItem.getContent());
                    hoderView_execute.tv_content.setOnClickListener(myClick);
                    hoderView_execute.tv_begin.setText(DateUtil.getDateString(currentItem.getBeginDateTime()));
                    hoderView_execute.tv_end.setText(DateUtil.getDateString(currentItem.getEndDateTime()));

                    hoderView_execute.btn_warntimes.setTag(currentItem.getID());
                    hoderView_execute.btn_warntimes.setOnClickListener(myClick);
                    hoderView_execute.btn_finish.setTag(currentItem.getID());
                    hoderView_execute.btn_finish.setOnClickListener(myClick);
                    hoderView_execute.btn_delay.setTag(currentItem.getID());
                    hoderView_execute.btn_delay.setOnClickListener(myClick);
                    hoderView_execute.btn_delete.setTag(currentItem.getID());
                    hoderView_execute.btn_delete.setOnClickListener(myClick);
                    break;
                case 1:
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listivew_item_finish, null);
                        hoderView_finish = new HoderView_finish();
                        hoderView_finish.tv_content = (TextView) convertView.findViewById(R.id.listview_item_finish_lv_title);
                        hoderView_finish.tv_begin = (TextView) convertView.findViewById(R.id.listview_item_finish_1);
                        hoderView_finish.tv_end = (TextView) convertView.findViewById(R.id.listview_item_finish_2);
                        hoderView_finish.btn_delete = (Button) convertView.findViewById(R.id.listview_item_finish_btn_delete);

                        convertView.setTag(hoderView_finish);
                    } else {
                        hoderView_finish = (HoderView_finish) convertView.getTag();
                    }

                    hoderView_finish.tv_content.setTag(currentItem.getID());
                    hoderView_finish.tv_content.setText(currentItem.getContent());
                    hoderView_finish.tv_content.setOnClickListener(myClick);
                    hoderView_finish.tv_begin.setText(DateUtil.getDateString(currentItem.getBeginDateTime()));
                    hoderView_finish.tv_end.setText(DateUtil.getDateString(currentItem.getEndDateTime()));

                    hoderView_finish.btn_delete.setTag(currentItem.getID());
                    hoderView_finish.btn_delete.setOnClickListener(myClick);
                    break;
                case 2:
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item_nobegin, null);
                        hoderView_nobegin = new HoderView_nobegin();
                        hoderView_nobegin.tv_content = (TextView) convertView.findViewById(R.id.listview_item_nobegin_lv_title);
                        hoderView_nobegin.tv_begin = (TextView) convertView.findViewById(R.id.listview_item_nobegin_1);
                        hoderView_nobegin.tv_end = (TextView) convertView.findViewById(R.id.listview_item_nobegin_2);
                        hoderView_nobegin.btn_warntimes = (Button) convertView.findViewById(R.id.listview_item_nobegin_btn_warntimes);
                        hoderView_nobegin.btn_finish = (Button) convertView.findViewById(R.id.listview_item_nobegin_btn_finish);
                        hoderView_nobegin.btn_nowbegin = (Button) convertView.findViewById(R.id.listview_item_nobegin_btn_nowbegin);
                        hoderView_nobegin.btn_delete = (Button) convertView.findViewById(R.id.listview_item_nobegin_btn_delete);

                        convertView.setTag(hoderView_nobegin);
                    } else {
                        hoderView_nobegin = (HoderView_nobegin) convertView.getTag();
                    }

                    hoderView_nobegin.tv_content.setTag(currentItem.getID());
                    hoderView_nobegin.tv_content.setText(currentItem.getContent());
                    hoderView_nobegin.tv_content.setOnClickListener(myClick);
                    hoderView_nobegin.tv_begin.setText(DateUtil.getDateString(currentItem.getBeginDateTime()));
                    hoderView_nobegin.tv_end.setText(DateUtil.getDateString(currentItem.getEndDateTime()));

                    hoderView_nobegin.btn_warntimes.setTag(currentItem.getID());
                    hoderView_nobegin.btn_warntimes.setOnClickListener(myClick);
                    hoderView_nobegin.btn_finish.setTag(currentItem.getID());
                    hoderView_nobegin.btn_finish.setOnClickListener(myClick);
                    hoderView_nobegin.btn_nowbegin.setTag(currentItem.getID());
                    hoderView_nobegin.btn_nowbegin.setOnClickListener(myClick);
                    hoderView_nobegin.btn_delete.setTag(currentItem.getID());
                    hoderView_nobegin.btn_delete.setOnClickListener(myClick);
                    break;
                case 3:
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item_delete, null);
                        hoderView_delete = new HoderView_delete();
                        hoderView_delete.tv_content = (TextView) convertView.findViewById(R.id.listview_item_delete_lv_title);
                        hoderView_delete.tv_begin = (TextView) convertView.findViewById(R.id.listview_item_delete_1);
                        hoderView_delete.tv_end = (TextView) convertView.findViewById(R.id.listview_item_delete_2);
                        hoderView_delete.btn_back = (Button) convertView.findViewById(R.id.listview_item_delete_btn_back);

                        convertView.setTag(hoderView_delete);
                    } else {
                        hoderView_delete = (HoderView_delete) convertView.getTag();
                    }

                    hoderView_delete.tv_content.setTag(currentItem.getID());
                    hoderView_delete.tv_content.setText(currentItem.getContent());
                    hoderView_delete.tv_content.setOnClickListener(myClick);
                    hoderView_delete.tv_begin.setText(DateUtil.getDateString(currentItem.getBeginDateTime()));
                    hoderView_delete.tv_end.setText(DateUtil.getDateString(currentItem.getEndDateTime()));

                    hoderView_delete.btn_back.setTag(currentItem.getID());
                    hoderView_delete.btn_back.setOnClickListener(myClick);

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
                AlertDialog.Builder dialog = null;
                itemUtil=new ItemUtil(Integer.parseInt(v.getTag().toString()));
                currentItem=itemUtil.getItem();
                statusUtil= new ItemStatusUtil(currentItem);
                final View finishview = activity.getLayoutInflater().inflate(R.layout.listview_item_normal_dialog, null);
                String content="",warntimes="";


                switch (v.getId()) {
                    case R.id.listview_item_execute_lv_title:
                        intent = new Intent(activity, Add.class);
                        intent.putExtra("id", Integer.parseInt(v.getTag().toString()));
                        break;
                    case R.id.listview_item_nobegin_btn_finish:
                    case R.id.listview_item_execute_btn_finish:
                        dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_finish_title_text)
                                .setView(finishview)
                                .setPositiveButton(R.string.normal_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EditText et_content = (EditText) finishview.findViewById(R.id.listview_item_normal_et_content);
                                        String content = et_content.getText().toString().trim();
                                        if (!TextUtils.isEmpty(content)) {
                                            //这里是保存代码
                                            ItemStatus itemStatus = statusUtil.getItemStatus(StatusUtil.FINISH);
                                            itemStatus.setNote(content);
                                            boolean result = statusUtil.AddItemStatus(itemStatus);
                                            if (result)//保存成功
                                            {
                                                AlertDailogUtil.SetDismiss(dialog, true);
                                                if(myListViewAdapterListener!=null)
                                                    myListViewAdapterListener.Finished(index);
                                            } else {
                                                //这里保存错误
                                            }
                                        } else {
                                            et_content.setError(activity.getString(R.string.listview_item_finish_content_error));
                                            AlertDailogUtil.SetDismiss(dialog, false);
//
                                        }
                                    }
                                })
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AlertDailogUtil.SetDismiss(dialog, true);
                                    }
                                });
                        break;
                    case R.id.listview_item_execute_btn_delay:
                        dialog=new AlertDialog.Builder(activity)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_delay_title)
                                .setItems(activity.getResources().getStringArray(R.array.delaytimes),new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        long oldendtime,newendtime;
                                        oldendtime=currentItem.getEndDateTime();
                                        newendtime=currentItem.getEndDateTime();
                                        switch (which)
                                        {
                                            case 0:
                                            newendtime=oldendtime+DateUtil.getDateOffset("1小时");
                                                break;
                                            case 1:
                                                newendtime=oldendtime+DateUtil.getDateOffset("2小时");
                                                break;
                                            case 2:
                                                newendtime=oldendtime+DateUtil.getDateOffset("4小时");
                                                break;
                                            case 3:
                                                newendtime=oldendtime+DateUtil.getDateOffset("1天");
                                                break;
                                            case 4:
                                                newendtime=oldendtime+DateUtil.getDateOffset("2天");
                                                break;
                                            case 5:
                                                newendtime=oldendtime+DateUtil.getDateOffset("4天");
                                                break;
                                            case 6:
                                                newendtime=oldendtime+DateUtil.getDateOffset("1周");
                                                break;
                                        }
                                        currentItem.setEndDateTime(newendtime);
                                        itemUtil.setItem(currentItem);
                                        itemUtil.ModifyNoticeTimes(currentItem.getNoticeTime());
                                        boolean result=itemUtil.SaveItem(2);
                                        if(result) {//更新Item成功
                                            if(currentItem.getBeginDateTime()>DateUtil.getNow()) {//如果开始时间大于目前的时间，说明没有开始
                                                ItemStatus itemStatus = statusUtil.getItemStatus(StatusUtil.NOBEGIN);
                                                result = statusUtil.AddItemStatus(itemStatus);
                                                if (result)//保存新的状态成功
                                                {
                                                    if(myListViewAdapterListener!=null)
                                                        myListViewAdapterListener.Finished(index);
                                                }
                                                else
                                                {
                                                    Toast.makeText(activity,activity.getResources().getString(R.string.listview_item_status_save_error_message),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                        else
                                        {
                                            Toast.makeText(activity,activity.getResources().getString(R.string.listview_item_save_error_message),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;
                    case R.id.listview_item_nobegin_btn_delete:
                    case R.id.listview_item_finish_btn_delete:
                    case R.id.listview_item_execute_btn_delete:
                        dialog = new AlertDialog.Builder(activity)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_delete_title_text)
                                .setView(finishview)
                                .setPositiveButton(R.string.normal_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EditText et_content = (EditText) finishview.findViewById(R.id.listview_item_normal_et_content);
                                        String content = et_content.getText().toString().trim();
                                        if (!TextUtils.isEmpty(content)) {
                                            ItemStatus itemStatus = statusUtil.getItemStatus(StatusUtil.DELETE);
                                            itemStatus.setNote(content);
                                            boolean result = statusUtil.AddItemStatus(itemStatus);
                                            if (result)//保存成功
                                            {
                                                AlertDailogUtil.SetDismiss(dialog, true);
                                                if(myListViewAdapterListener!=null)
                                                    myListViewAdapterListener.Finished(index);

                                            } else {
                                                //这里保存错误
                                            }
                                        } else {
                                            et_content.setError(activity.getString(R.string.listview_item_finish_content_error));
                                            AlertDailogUtil.SetDismiss(dialog, false);
                                        }

                                    }
                                })
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;
                    case R.id.listview_item_nobegin_btn_warntimes:
                    case R.id.listview_item_execute_btn_warntimes:
                        for(ItemNotice notice:itemUtil.getNoticeList())
                        {
                            warntimes+=DateUtil.getDateString(notice.getNoticeTime())+"\n";
                        }
                        dialog = new AlertDialog.Builder(activity)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_item_warntimes)
                                .setMessage(warntimes)
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;
                    case R.id.listview_item_finish_lv_title:
                    case R.id.listview_item_delete_lv_title:
                        content+="内容：\n";
                        content+=currentItem.getContent();
                        content+="\n\n提醒时间：\n";
                        for(ItemNotice notice:itemUtil.getNoticeList())
                        {
                            content+=DateUtil.getDateString(notice.getNoticeTime())+"\n";
                        }
                        dialog = new AlertDialog.Builder(activity)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_finish_lv_title_text)
                                .setMessage(content)
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;
                    case R.id.listview_item_nobegin_btn_nowbegin:

                        break;
                    case R.id.listview_item_delete_btn_back:
                        dialog = new AlertDialog.Builder(activity)
                                .setIcon(R.drawable.ic_launcher)
                                .setTitle(R.string.listview_item_back_title)
                                .setPositiveButton(R.string.normal_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ItemStatusUtil statusUtil = new ItemStatusUtil(currentItem);
                                        boolean result = statusUtil.backCurrentStatus();
                                        if (result)//保存成功
                                        {
                                            AlertDailogUtil.SetDismiss(dialog, true);
                                            if(myListViewAdapterListener!=null)
                                                myListViewAdapterListener.Finished(index);

                                        } else {
                                            //这里保存错误
                                        }
                                    }

                                })
                                .setNegativeButton(R.string.normal_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        break;
                }
                if (dialog != null)
                    dialog.show();
                if (intent != null && fragment!=null)
                    fragment.startActivityForResult(intent, AddNotice);
            }
        };

        class HoderView_execute {
            TextView tv_content, tv_begin, tv_end;
            Button btn_warntimes, btn_finish, btn_delay,btn_delete;
        }

        class HoderView_nobegin {
            TextView tv_content, tv_begin, tv_end;
            Button btn_warntimes, btn_finish, btn_nowbegin,  btn_delete;
        }
        class HoderView_finish {
            TextView tv_content, tv_begin, tv_end;
            Button btn_delete;
        }
        class HoderView_delete {
            TextView tv_content, tv_begin, tv_end;
            Button btn_back;
        }
        class HoderView_note {
            TextView tv_content, tv_begin, tv_end;
            Button btn_warntimes, btn_finish, btn_delay,  btn_delete;
            ImageView iv_1, iv_2, iv_3;
        }
    }
