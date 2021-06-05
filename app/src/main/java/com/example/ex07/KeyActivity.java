package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class KeyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view=new MyView(this);
        view.setFocusable(true); //포커스를 받을 수 있도록 설정
        setContentView(view);
    }
    class MyView extends View {
        float x=50, y=50;
        int width, height;
        public MyView(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            width=getWidth();
            height=getHeight();
        }
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.LTGRAY);
            Paint paint=new Paint();
            paint.setColor(Color.YELLOW);
            canvas.drawCircle(x,y,20,paint);
        }
        //화면 터치 이벤트
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x=event.getX(); // 터치한 x 좌표
            y=event.getY(); // 터치한 y 좌표
            postInvalidate(); // 그래픽 갱신 요청
            return super.onTouchEvent(event);
        }
        //키 이벤트 처리 함수
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            Log.i("test","키코드:"+keyCode);
            switch (keyCode){ //키코드에 따라 처리
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    x-=5;
                    x=Math.max(20,x); //큰값
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x+=5;
                    x=Math.min(width-20,x); //작은값
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    y-=5;
                    y=Math.max(20,y);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    y+=5;
                    y=Math.min(height-20,y);
                    break;
            }
            postInvalidate();
            return super.onKeyDown(keyCode, event);
        }


    }
}