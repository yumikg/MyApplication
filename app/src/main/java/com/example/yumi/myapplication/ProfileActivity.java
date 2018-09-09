package com.example.yumi.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class ProfileActivity extends AppCompatActivity
        implements View.OnClickListener {


    private ImageButton closeButton;
    private ImageButton photoButton;

    //ストレージに保存された画像のURIを格納するフィールド
    private Uri resultUri;
    private static final int REQUEST_CHOOSER = 1000;
    private final static int REQUEST_PERMISSION = 1002;
    private String filePath;
    private Uri cameraUri;
    private File cameraFile;
    private Intent intentCamera;

    //タブ
    private ViewPager viewPager;
    private TabLayout tabLayout;

    // Fragmentでの画面を2つ用意。
    //private String[] pageFragments = {"Fragment: 0", "Fragment: 1"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ImageButton toHomeButton = (ImageButton) findViewById(R.id.toHomeButton);
        toHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ImageButton editButton = (ImageButton) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageButton toTabSampleButton = (ImageButton) findViewById(R.id.toTabSampleButton);
        toTabSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, TabSampleActivity.class);
                startActivity(intent);
            }
        });


        photoButton = (ImageButton)findViewById(R.id.photo_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showGallery(); //_showGalleryメソッドを起動
            }
        });

        //Tabの実装
        // Set a ViewPager.
        this.setViewPager();

        // Set a TabLayout.
        this.setTabLayout();

    }



    public void onClick(View view){
        //
    }

    //カメラ画像をタップした時の処理
    public void onCameraButtonClick(View view) {

        //ストレージ使用の処理
        //WRITE_EXTERNAL_STORAGEの許可がおりてないなら
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions,2000);
            return;
        }

//        Calendar cal = Calendar.getInstance();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String nowStr = sf.format(cal.getTime());
//        String fileName = "ProfileActivityPhoto_" + nowStr + "jpg";
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, fileName);
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        ContentResolver resolver = getContentResolver();
//        _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
//        startActivityForResult(intent, 200);

    }


    //Tabの実装
    private void setViewPager() {
        //ViewPagerの初期化
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(fragmentManager);
        viewPager.setAdapter(tabsPagerAdapter);
        //TabLayoutの初期化
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setTabLayout() {

        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        this.tabLayout.setupWithViewPager(this.viewPager);

    }

    //カメラ処理ここから
    private void _showGallery() {

        //カメラの起動Intentの用意
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        }
        else {
            intentCamera = cameraIntent(); //cameraIntentというIntentを返す
        }

        // ギャラリー用のIntent作成
        Intent intentGallery;
        if (Build.VERSION.SDK_INT < 19) {
            intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
            intentGallery.setType("image/*");
        } else {
            intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
            intentGallery.setType("image/jpeg");
        }

        //ChooserにGallaryのIntentとCameraのIntentを登録
        Intent intent = Intent.createChooser(intentGallery, "Select Image");
        if(intentCamera!=null){
            intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {intentCamera});
        }
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHOOSER) {

            if(resultCode != RESULT_OK) {
                // キャンセル時
                return ;
            }

            //dataがnullの場合はギャラリーではなくカメラからの取得と判定しカメラのUriを使う
            resultUri = (data != null ? data.getData() : cameraUri);

            if(resultUri == null) {
                // 取得失敗
                Toast.makeText(this, "Error.Try again.", Toast.LENGTH_LONG).show();
                return;
            }

            // ギャラリーへスキャンを促す
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{resultUri.getPath()},
                    new String[]{"image/jpeg"},
                    null
            );

            // 画像を設定
            ImageView imageView = (ImageView)findViewById(R.id.photo);
            imageView.setImageURI(resultUri);

        }
    }

    private Intent cameraIntent(){
        // 保存先のフォルダーを作成
        File cameraFolder = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IMG"
        );
        cameraFolder.mkdirs();

        // 保存ファイル名
        String fileName = new SimpleDateFormat("ddHHmmss").format(new Date());
        filePath = cameraFolder.getPath() +"/" + fileName + ".jpg";
        Log.d("debug","filePath:"+filePath);

        // capture画像のファイルパス
        cameraFile = new File(filePath);
        cameraUri = Uri.fromFile(cameraFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);

        return intent;
    }

    // Runtime Permission check
    private void checkPermission(){
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            cameraIntent();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(ProfileActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        } else {
            Toast toast = Toast.makeText(this, "Camera function is disabled", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions
                    (this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION);

        }
    }



}
