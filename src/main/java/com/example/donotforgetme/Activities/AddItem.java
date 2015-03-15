package com.example.donotforgetme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.donotforgetme.R;

public class AddItem extends Fragment {


    Button btn_createNotice,btn_createNote;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_additem, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initControls();
    }

    void initControls()
    {
        btn_createNotice=(Button)getActivity().findViewById(R.id.additem_btn_addnotice);
        btn_createNote=(Button)getActivity().findViewById(R.id.additem_btn_addnote);

        btn_createNotice.setOnClickListener(createlistener);
        btn_createNote.setOnClickListener(createlistener);
    }

    View.OnClickListener createlistener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=null;
            switch (v.getId())
            {
                case R.id.additem_btn_addnotice:
                    intent=new Intent(getActivity(),Add.class);
                    break;
                case R.id.additem_btn_addnote:

                    break;
            }

            if(intent!=null)
            {
                startActivity(intent);
            }
        }
    };
}
