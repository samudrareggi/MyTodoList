package com.reggisam.mtl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView simpleImageView=findViewById(R.id.imageView);
        simpleImageView.setImageResource(R.drawable.credit);
    }

    public void rotate(){
        ImageView imageView;
        Bitmap bitmap;
        Matrix matrix;

        imageView = findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.credit);
        matrix = new Matrix();
        matrix.postRotate(45);
        Bitmap bitmapRotate = Bitmap.createBitmap(bitmap,0,0,
                bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        imageView.setImageBitmap(bitmapRotate);
    }
    public void tekan(View view){
        rotate();
    }
    public void brightnes(int offset){
        ImageView imageView;
        Bitmap bitmap;
        Matrix matrix;

        imageView = findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.credit);

        int lbr = bitmap.getWidth();
        int tg = bitmap.getHeight();
        int[] ttk = new int[lbr*tg];
        bitmap.getPixels(ttk,0,lbr,0,0,lbr,tg);
        int indek=0;
        for (int y=0 ; y<tg ; y++){
            for (int x=0 ; x<lbr ; x++){
                int r = (ttk[indek]>>16) & 0xff;
                int g = (ttk[indek]>>8) & 0xff;
                int b = ttk[indek] & 0xff;
                r = Math.max(0,Math.min(255, r+ offset));
                g = Math.max(0,Math.min(255, g+ offset));
                b = Math.max(0,Math.min(255, b+ offset));
                ttk[indek++]=0xff000000|(r<<16)|(g<<8)|b;
            }
        }
        bitmap = Bitmap.createBitmap(lbr,tg,Bitmap.Config.RGB_565);
        bitmap.setPixels(ttk,0,lbr,0,0,lbr,tg);
        imageView.setImageBitmap(bitmap);
    }
    public void tekan2(View view){
        brightnes(50);
    }
    public void grey(){
        ImageView imageView;
        Bitmap bitmap;
        Matrix matrix;

        imageView = findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.credit);

        int lbr = bitmap.getWidth();
        int tg = bitmap.getHeight();
        int[] ttk = new int[lbr*tg];
        bitmap.getPixels(ttk,0,lbr,0,0,lbr,tg);
        int indek=0;
        for (int y=0 ; y<tg ; y++){
            for (int x=0 ; x<lbr ; x++){
                int r0 = (ttk[indek]>>16) & 0xff;
                int g0 = (ttk[indek]>>8) & 0xff;
                int b0 = ttk[indek] & 0xff;
                int r = Math.round(r0+g0+b0/3);
                int g = Math.round(r0+g0+b0/3);
                int b = Math.round(r0+g0+b0/3);

                ttk[indek++]=0xff000000|(r<<16)|(g<<8)|b;
            }
        }
        bitmap = Bitmap.createBitmap(lbr,tg,Bitmap.Config.RGB_565);
        bitmap.setPixels(ttk,0,lbr,0,0,lbr,tg);
        imageView.setImageBitmap(bitmap);
    }
    public void tekan3(View view){
        grey();
    }
}
