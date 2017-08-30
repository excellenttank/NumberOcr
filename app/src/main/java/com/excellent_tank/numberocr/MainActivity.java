package com.excellent_tank.numberocr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.ralphpina.permissionsmanager.PermissionsManager;
import net.ralphpina.permissionsmanager.PermissionsResult;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button get_ocr_img;
    private ImageView ocr_img;
    private TextView ocr_text;
    OCR ocr;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        if (!PermissionsManager.get().isStorageGranted()) {
            System.out.println("isStorageGranted");
            PermissionsManager.get().requestStoragePermission().subscribe(new Action1<PermissionsResult>() {
                @Override
                public void call(PermissionsResult permissionsResult) {
                    if (permissionsResult.isGranted()) {
                        System.out.println("permissionsResult.isGranted()");
                        ocr = new OCR();
                    }
                    if (permissionsResult.hasAskedForPermissions()) {

                    }
                }
            });
        } else {
            System.out.println("StorageGranted");
            ocr = new OCR();
        }

    }

    private void bindViews() {
        get_ocr_img = (Button) findViewById(R.id.get_ocr_img);
        get_ocr_img.setOnClickListener(this);
        ocr_img = (ImageView) findViewById(R.id.ocr_img);
        ocr_text = (TextView) findViewById(R.id.ocr_text);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.get_ocr_img:
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Bundle bundle = data.getExtras();
                Bitmap cropBitmap = bundle.getParcelable("cropPic");
                doOCR(cropBitmap);
            }
        }
    }

    private void doOCR(final Bitmap bitmap) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "Processing", "Doing OCR...", true);
        }

        mProgressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                final String result = ocr.getOCRResult(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ocr_text.setText(result);
                        ocr_img.setImageBitmap(bitmap);
                        if (mProgressDialog != null) {
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                    }
                });

            }

            ;
        }).start();
    }

}
