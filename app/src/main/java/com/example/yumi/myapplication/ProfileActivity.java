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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.text.SimpleDateFormat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //ストレージに保存された画像のURIを格納するフィールド
//    private Uri _imageUri;
//    private static final int READ_REQUEST_CODE = 42;
    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setViews();

        //findViewById(R.id.toHomeButton).setOnClickListener(this);
        //findViewById(R.id.editButton).setOnClickListener(this);
        //findViewById(R.id.toTabSampleButton).setOnClickListener(this);

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
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageButton toTabSampleButton = (ImageButton) findViewById(R.id.toTabSampleButton);
        toTabSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,TabSampleActivity.class);
                startActivity(intent);
            }
        });

//
//        findViewById(R.id.camera_ImageView).setOnClickListener(new View.OnClickListener() {
//            //端末標準ギャラリーを起動し、画像のURIを取得
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//
//                startActivityForResult(intent, READ_REQUEST_CODE);
//            }
//        });

    }

    public void onClick(View view){

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

    private void setViews(){
        ImageView imageView = (ImageView)findViewById(R.id.camera_ImageView);
        imageView.setOnClickListener(camera_onClick);
    }
    private View.OnClickListener camera_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showGallery();
        }
    };

    private void showGallery() {

        //カメラの起動Intentの用意
        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        m_uri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri);

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
        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {intentGallery});
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int[] grantResults) {
        //WRITE_EXTERNAL_STORAGEに対する許可をダイアログで行ったら
        if(requestCode == 2000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
            onCameraImageClick(camera_ImageView);
        }
    }

    //カメラ画像への処理：撮影した後の処理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if(requestCode == 200 && resultCode == RESULT_OK ){
//            //ストレージ不使用の処理
////            Bitmap bitmap = data.getParcelableExtra("data");
////            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
////            camera_ImageView.setImageBitmap(bitmap);
//            //ストレージ使用の処理
//            ImageView camera_ImageView = findViewById(R.id.camera_ImageView);
//            camera_ImageView.setImageURI(_imageUri);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHOOSER) {

            if(resultCode != RESULT_OK) {
                // キャンセル時
                return ;
            }

            Uri resultUri = (data != null ? data.getData() : m_uri);

            if(resultUri == null) {
                // 取得失敗
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
            ImageView imageView = (ImageView)findViewById(R.id.camera_ImageView);
            imageView.setImageURI(resultUri);
        }
    }

}
