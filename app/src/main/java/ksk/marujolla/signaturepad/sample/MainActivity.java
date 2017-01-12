package ksk.marujolla.signaturepad.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import ksk.marujolla.signaturepad.SignaturePad;

public class MainActivity extends AppCompatActivity {
    SignaturePad mSignaturePad;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)findViewById(R.id.imageView);
        mSignaturePad = (SignaturePad)findViewById(R.id.signatureView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap b = mSignaturePad.getSignature();
                    mImageView.setImageBitmap(b);
                    mSignaturePad.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
