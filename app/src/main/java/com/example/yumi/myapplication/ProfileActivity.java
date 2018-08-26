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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.text.SimpleDateFormat;

import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //ストレージに保存された画像のURIを格納するフィールド
    private Uri _imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.editButton).setOnClickListener(this);
        findViewById(R.id.toTabSampleButton).setOnClickListener(this);

    }


    //カメラ画像への処理：撮影した後の処理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 200 && resultCode == RESULT_OK ){
            //ストレージ不使用の処理
//            Bitmap bitmap = data.getParcelableExtra("data");
//            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
//            camera_ImageView.setImageBitmap(bitmap);
            //ストレージ使用の処理
            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
            camera_ImageView.setImageURI(_imageUri);

        }
    }

    public void onClick(View view) {

        int id = view.getId();

        switch(id) {

            case R.id.toHomeButton:
                finish();
                break;

            case R.id.editButton:
                Intent editintent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(editintent);
                break;

            case R.id.toTabSampleButton:
                Intent intent = new Intent(ProfileActivity.this, TabSampleActivity.class);
                startActivity(intent);
                break;

        }

    }

    //カメラ画像をタップした時の処理
    public void onCameraImageClick(View view) {

        //ストレージ使用しない処理ここだけ
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, 200);

        //ストレージ使用の処理
        //WRITE_EXTERNAL_STORAGEの許可がおりてないなら
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions,2000);
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowStr = sf.format(cal.getTime());
        String fileName = "ProfileActivityPhoto_" + nowStr + "jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        ContentResolver resolver = getContentResolver();
        _imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
        startActivityForResult(intent, 200);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults) {
        //WRITE_EXTERNAL_STORAGEに対する許可をダイアログで行ったら
        if(requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
            onCameraImageClick(camera_ImageView);
        }
    }



}
