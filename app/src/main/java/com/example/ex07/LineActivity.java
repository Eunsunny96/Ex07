package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity {
    List<Point> points=new ArrayList<>(); //좌표 리스트
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
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.LTGRAY);
            Paint paint=new Paint();
            paint.setColor(Color.RED); //페인트 색상 설정
            //좌표 리스트 반복 처리
            for(int i=0; i<points.size(); i++){
                Point now=points.get(i); //i번째 좌표
                if(now.isDraw){ //그리는 중이면
                    Point before=points.get(i-1); //직전 좌표
                    Log.i("test","좌표:"+before);
                    //그리기
                    canvas.drawLine(before.x, before.y, now.x, now.y, paint);
                }
            }
        }
        //터치 이벤트
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.i("test","터치:"+event.getAction());
            //터치하면
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                //좌표 추가
                points.add(new Point(event.getX(),event.getY(),false));
                return true;
            }
            //move 상태
            if(event.getAction()==MotionEvent.ACTION_MOVE){

                points.add(new Point(event.getX(),event.getY(),true));
                invalidate(); //화면 갱신 요청
                return true;
            }
            return false; //터치 이벤트 무시
        }
    }
}