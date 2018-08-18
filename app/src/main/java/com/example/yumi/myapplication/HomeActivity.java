package com.example.yumi.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Map;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {



    //イベントをホーム画面に一覧表示するために配列を用意
    ListView listView;
    private static final String[] events =
            {"A","B","C","D","E","F","G","H","I","J","K","L",
            "M","N","O","P","Q","R","S","T","U","V","W","X",
            "Y","Z","a","b","c","d","e","f","g","h","i","j"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listView = (ListView) findViewById(R.id.listView);

        //ArrayAdapterオブジェクトを生成
        //ArrayAdapter(Context context, int textViewResourceId, List< T > objects)

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,events);

        //Adapterの指定
        listView.setAdapter(arrayAdapter);

        //画面遷移
        findViewById(R.id.inputButton).setOnClickListener(this);
//        Button sendButton = findViewById(R.id.inputButton);
//        sendButton.setOnClickListener(new View.OnClickListener();


    }

    //ボタンが押された時の処理
    public void onClick(View view) {
        //インテントの作成
        Intent intent = new Intent(this, ProfileActivity.class);

        //遷移先に転送
        startActivity(intent);

    }



}
