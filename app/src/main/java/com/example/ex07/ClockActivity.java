package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {
    boolean isRun;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun=false;
        t=null;
        Log.i("test","onPause");
    }

    class MyView extends View implements Runnable{
        int cx,cy,cw;
        int watchW, watchH;
        Bitmap clock;
        Bitmap pins[]=new Bitmap[3];
        int hour,min,sec;
        float rHour, rMin, rSec;
        int width, height;

        public MyView(Context context) {
            super(context);
            DisplayMetrics disp=getApplicationContext().getResources()
                    .getDisplayMetrics();
            width=disp.widthPixels;
            height=disp.heightPixels;
            cx=width/2;
            cy=(height-100)/2;
            Resources res=context.getResources();
            clock= BitmapFactory.decodeResource(res, R.drawable.dial);
            pins[0]=BitmapFactory.decodeResource(res, R.drawable.pin_1);
            pins[1]=BitmapFactory.decodeResource(res, R.drawable.pin_2);
            pins[2]=BitmapFactory.decodeResource(res, R.drawable.pin_3);
            cw=clock.getWidth()/2;
            watchW=pins[0].getWidth()/2;
            watchH=pins[0].getHeight()-10;
            Thread th=new Thread(this);
            isRun=true;
            th.start();
        }
        void calcTime(){
            Calendar calendar=Calendar.getInstance();
            hour=calendar.get(Calendar.HOUR);
            min=calendar.get(Calendar.MINUTE);
            sec=calendar.get(Calendar.SECOND);
            rSec=sec*6;
            rMin=min*6;
            rHour=hour*30+rMin/12;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            calcTime();
            canvas.drawColor(Color.WHITE);
            canvas.scale(0.8f, 1, cx, cy);
            canvas.rotate(0,cx,cy);
            canvas.drawBitmap(clock, cx-cw, cy-cw, null);
            canvas.rotate(rHour, cx, cy);
            canvas.drawBitmap(pins[2], cx-watchW, cy-watchH, null);
            canvas.rotate(rMin-rHour, cx, cy);
            canvas.drawBitmap(pins[1], cx-watchW, cy-watchH, null);
            canvas.rotate(rSec-rMin, cx, cy);
            canvas.drawBitmap(pins[0], cx-watchW, cy-watchH, null);
            canvas.rotate(-rSec, cx, cy);
            Paint paint=new Paint();
            paint.setColor(Color.BLUE);
            paint.setColor(Color.BLUE);
            paint.setTextSize(100);
            canvas.drawText(String.format("%02d:%02d:%02d", hour, min, sec),
                    cx-100, cy+cw+150,paint);
        }

        @Override
        public void run() {
            //while(true){
            while(isRun){
                Log.i("test","running...");
                postInvalidate();
                try {
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}














