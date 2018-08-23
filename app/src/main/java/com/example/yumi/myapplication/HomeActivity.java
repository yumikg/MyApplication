package com.example.yumi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ListView homeListView = findViewById(R.id.homeListView);

        List<Map<String,String>> homeList = new ArrayList<>();
        Map<String,String> homemap = new HashMap<>();
        homemap.put("name", "あああああ" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);
        homemap = new HashMap<>();
        homemap.put("name", "いいいいい" );
        homemap.put("birthday","2018/3/13");
        homeList.add(homemap);

        String[] from = {"name","birthday"};
        int[] to = {android.R.id.text1,android.R.id.text2};
        SimpleAdapter homeAdapter = new SimpleAdapter(HomeActivity.this,homeList,
            android.R.layout.simple_list_item_2, from, to);
        homeListView.setAdapter(homeAdapter);



        //画面遷移
        findViewById(R.id.inputButton).setOnClickListener(this);

    }

    //ボタンが押された時の処理
    public void onClick(View view) {
        //インテントの作成
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);

        //遷移先に転送
        startActivity(intent);

    }





}
