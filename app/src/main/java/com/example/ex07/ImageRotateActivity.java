package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

public class ImageRotateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //중심 좌표
            int centerX=getWidth()/2;
            int centerY=getHeight()/2;
            int w=0;
            int h=0;
            int DIRECTION=2;
            Bitmap[] rose=new Bitmap[4];
            Resources res=getResources();
            // 이미지를 비트맵으로 불러옴
            rose[0]= BitmapFactory.decodeResource(res, R.drawable.rose_1);
            rose[1]= BitmapFactory.decodeResource(res, R.drawable.rose_2);
            rose[2]= BitmapFactory.decodeResource(res, R.drawable.rose_3);
            rose[3]= BitmapFactory.decodeResource(res, R.drawable.rose_4);
            Paint paint=new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawColor(Color.WHITE);
            switch (DIRECTION){
                case 1:
                    w=rose[0].getWidth()/2;
                    h=rose[0].getHeight();
                    break;
                case 2:
                    w=0;
                    h=rose[1].getHeight();
                    break;
                case 3:
                    w=rose[2].getWidth()/2;
                    h=0;
                    break;
                case 4:
                    w=rose[3].getWidth();
                    h=rose[3].getHeight()/2;
                    break;
            }
            //캔버스에 비트맵 이미지 출력
            canvas.drawBitmap(rose[DIRECTION-1], 10, 10, null);
            canvas.drawCircle(w+10, h+10, 10, paint);
            for(int i=1; i<=18; i++){
                //20도 회전 
                canvas.rotate(20, centerX, centerY);
                //이미지 출력
                canvas.drawBitmap(rose[DIRECTION-1],centerX-w,
                        centerY-h,null);
            }
        }
    }
}