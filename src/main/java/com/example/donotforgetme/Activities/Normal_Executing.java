package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.donotforgetme.Entities.Item;
import com.example.donotforgetme.R;
import com.example.donotforgetme.Utils.DateUtil;
import com.example.donotforgetme.Utils.ItemUtil;
import com.example.donotforgetme.Utils.StatusUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Normal_Executing extends Activity {

    ListView listView;
    ItemUtil itemUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_executing);
        //initControls();
    }

    void initControls(){
        listView=(ListView)this.findViewById(R.id.normal_execute_listview);
        itemUtil=new ItemUtil();
        getExecuteDate();
    }

    void getExecuteDate()
    {

        List<Item> itemList=itemUtil.getItems(StatusUtil.EXECUTE);
        List<HashMap<String,Object>> datas=new ArrayList<HashMap<String, Object>>();
        if(!itemList.isEmpty())
        {
            for(Item item:itemList)
            {
                HashMap<String,Object> d=new HashMap<String, Object>();
                d.put("content",item.getContent());
                d.put("begindatetime", DateUtil.getDateString(item.getBeginDateTime()));
                d.put("enddatetime",DateUtil.getDateString(item.getEndDateTime()));
                datas.add(d);
            }
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,datas,R.layout.listview_item,new String[]{"content","begindatetime","enddatetime"},new int[]{R.id.listview_item_lv_title,R.id.listview_item_1,R.id.listview_item_2});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(clickListener);
        listView.setOnItemLongClickListener(longClickListener);
    }

    AdapterView.OnItemClickListener clickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    AdapterView.OnItemLongClickListener longClickListener=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return false;
        }
    };

}
