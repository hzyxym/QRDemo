package com.phone580.qrdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class QRcodeActivity extends AppCompatActivity {
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView iv = (ImageView) findViewById(R.id.iv);
        final EditText et = (EditText) findViewById(R.id.et);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    bitmap = ZXingUtil.createQRCodeBitmap(QRcodeActivity.this, str);
                    iv.setImageBitmap(bitmap);
                }
            }
        });
    }


}
