package com.example.donotforgetme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.donotforgetme.MyListener.MyFragmentTabAdapter;
import com.example.donotforgetme.MyListener.MyPopWin;
import com.example.donotforgetme.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity{

    RadioGroup radioGroup;
    List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initRadioGroup();

    }


    /**
     * 初始化Fragment，把三个Fragment都加入到集合中
     */
    void initFragment()
    {
        fragmentList=new ArrayList<Fragment>();

        fragmentList.add(new Normal());
        fragmentList.add(new Search());
        fragmentList.add(new AddItem());
        fragmentList.add(new Setting());
        fragmentList.add(new Help());
    }

    void initRadioGroup()
    {
        radioGroup=(RadioGroup)this.findViewById(R.id.tabs_item);
        if(radioGroup!=null)
        {
            MyFragmentTabAdapter tabAdapter=new MyFragmentTabAdapter(this,fragmentList,R.id.tabs_content,radioGroup);
            tabAdapter.setOnRgsExtraCheckedChangedListener(new MyFragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
                @Override
                public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                    //System.out.println("Extra---- " + index + " checked!!! ");

                }
            });

            //init_AddBtn();
        }
    }

    void init_AddBtn()
    {
        RadioButton radioButton = (RadioButton) MainActivity.this.findViewById(R.id.tab_item_add);
        final MyPopWin popWin = new MyPopWin(LayoutInflater.from(MainActivity.this), radioButton, R.layout.selectormenu,0,-330);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.showPopMenu();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
