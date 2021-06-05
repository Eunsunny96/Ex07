package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class ShapeActivity extends AppCompatActivity {
    int x=50,y=50;
    int width,height;
    int moveX=5, moveY=10;
    int red,green,blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }
    //View를 상속한 Custom View
    class MyView extends View implements Runnable{
        public MyView(Context context) {
            super(context);
            //백그라운드 스레드를 만들고 시작
            Thread th=new Thread(this);
            th.start(); // run()
        }

        //사이즈가 변경될 때
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //가로,세로 사이즈 계산
            width=getWidth();
            height=getHeight();
        }

        @Override
        public void run() {
            while(true){
                //좌우 벽처리
                if(x>(width-20)||x<20){
                    moveX=-moveX;
                    setColor();
                }
                //상하 벽처리
                if(y>(height-20)||y<20){
                    moveY=-moveY;
                    setColor();
                }
                //좌표 변경
                x+=moveX;
                y+=moveY;
                //잠시멈춤
                try{
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
                // 화면 갱신 요청 postInvalidate() => invalidate() => onDraw()
                postInvalidate();
            }
        }
        void setColor(){
            Random rand=new Random();
            red=rand.nextInt(256); //0~255
            green=rand.nextInt(256);
            blue=rand.nextInt(256);
        }
        //화면을 그리는 코드
        @Override
        protected void onDraw(Canvas canvas) {
            //배경 색상 설정
            canvas.drawColor(Color.LTGRAY);
            Paint p=new Paint();
            //페인트 색상
            p.setColor(Color.argb(255,red,green,blue));
            //원 그리기
            canvas.drawCircle(x,y,20,p);
        }
    }
}













