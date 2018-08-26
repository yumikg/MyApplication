package com.example.yumi.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
//import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String[] nameArray = {
            "ウォルター",
            "ジェシー",
            "スカイラー",
            "ガス",
            "マイク",
            "ナチョ",
            "ヘクター",
            "ジミー",
            "バッジャー",
            "ホリー",
            "ハンク",
            "マリー",
            "リディア",
            "トッド",
            "ジェーン",
            "グレッチェン",
            "ヒューエル",
            "ゲイル",
            "エミリオ",
            "ウェンディ",
            "テッド"
    };

    private static final String [] birthdayArray = {
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",
            "2012/04/14",
            "1876/05/23",

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //画面遷移
        findViewById(R.id.inputButton).setOnClickListener(this);

        // ListViewのインスタンスを生成
        ListView listView = findViewById(R.id.homeListView);

        // BaseAdapter を継承したadapterのインスタンスを生成
        // レイアウトファイル list_items.xml を
        // activity_main.xml に inflate するためにadapterに引数として渡す
        BaseAdapter adapter = new HomeListAdapter(this.getApplicationContext(),
                R.layout.row_home_list, nameArray, birthdayArray );

        // ListViewにadapterをセット
        listView.setAdapter(adapter);

    }

    //ボタンが押された時の処理
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.inputButton:

            //インテントの作成
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            //遷移先に転送
            startActivity(intent);


        }

    }

//    //リストビューがタップされた時の処理
//    private class ListItemClickListener implements AdapterView.OnItemClickListener {
//        @Override
//        //OnItemClickListener　インターフェすに定義されているonItemClick()メソッドを実装する
//        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//
//
//            String item = (String ) parent.getItemAtPosition(position);
//            //タップした時にトーストを表示
//            String show = item + "さんのページへ移動";
//            Toast.makeText(HomeActivity.this,show,Toast.LENGTH_LONG).show();
//
//        }
//    }


}
