package com.example.yumi.myapplication;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;

public class TabSampleActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_sample);

//        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("タブ1"));
//        tabLayout.addTab(tabLayout.newTab().setText("タブ2"));

    }

    @Override
    public void onClick(View view) {

    }
}
