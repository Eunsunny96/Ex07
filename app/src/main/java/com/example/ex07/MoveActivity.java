package com.example.ex07;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {
        int width, height; //화면 크기
        int cx, cy; //회전축
        int turnW, turnH; //오뚜기의 회전축
        int sw, sh; //그림자의 크기
        int ang; //현재 각도
        int dir;// 회전 방향
        int limit1, limit2; //좌우의 한계점
        Bitmap imgBack, imgToy, imgShadow;

        public MyView(Context context) {
            super(context);
            //화면 사이즈 계산
// getApplicationContext() : 현재 실행중인 액티비티
            DisplayMetrics disp = getApplicationContext()
                    .getResources().getDisplayMetrics();
            width = disp.widthPixels; //화면의 가로길이
            height = disp.heightPixels; //화면의 세로길이
            //중심점
            cx = width / 2;
            cy = height / 2 + 100;
            //원본 배경 이미지
            imgBack = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.back);
            Log.i("test",
"width:"+imgBack.getWidth()+",height:"+imgBack.getHeight());
            //뷰의 크기에 맞게 이미지 사이즈를 조절
            imgBack = Bitmap.createScaledBitmap(
                    imgBack, width, height, true);
            Log.i("test",
"width:"+imgBack.getWidth()+",height:"+imgBack.getHeight());
            //오뚜기 이미지
            imgToy = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.toy);
            //그림자 이미지
            imgShadow = BitmapFactory.decodeResource(
                    context.getResources(), R.drawable.shadow);
            //오뚜기의 중심점
            turnW = imgToy.getWidth() / 2;
            turnH = imgToy.getHeight();
            //그림자의 중심좌표
            sw = imgShadow.getWidth() / 2;
            sh = imgShadow.getHeight() / 2;
            ang = 0; //회전 각도
            dir = 0; //회전 방향
// sendEmptyMessageDelayed( 메시지, 지연시간 )
// 지연시간 후 메시지가 전달됨( handleMessage method 호출 )
            handler.sendEmptyMessageDelayed(1, 10);
        }

        //핸들러 선언
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    invalidate(); //화면 갱신 onDraw()가 호출됨
                    //핸들러를 다시 호출
                    handler.sendEmptyMessageDelayed(1, 10);
                }
            }
        };

        //그래픽 처리
        @Override
        protected void onDraw(Canvas canvas) {
            rotateToy();
            //배경이미지
            canvas.drawBitmap(imgBack, 0, 0, null);
            //그림자
            canvas.drawBitmap(imgShadow, cx - sw, cy - sh, null);
            //캔버스를 회전시킴
            canvas.rotate(ang, cx, cy);
            //오뚜기
            canvas.drawBitmap(imgToy, cx - turnW, cy - turnH, null);
            //캔버스 회전 취소
            canvas.rotate(-ang, cx, cy);
        }

        void rotateToy() { //오뚜기를 회전시킴
            ang += dir; //각도 증가,감소
            //좌우 한계점에 도달하면
            if (ang <= limit1 || ang >= limit2) {
                limit1++; //왼쪽 증가
                limit2--; //오른쪽 감소
                dir = -dir; //회전 방향 바꾸기
                ang += dir; // 각도 변경
            }
//            Log.i("test", "limit1:" + limit1 + ",limit2:" + limit2
//                    + ",dir:" + dir + ",ang:" + ang);
        }
//터치 이벤트 추가

        @Override
        public boolean onTouchEvent(MotionEvent event) {
//화면을 터치하면
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                limit1 = -30; //왼쪽 한계
                limit2 = 30; //오른쪽 한계
                if (dir == 0) { // dir이 0이면 -1로
                    dir = -1; //왼쪽으로 기울임 처리
                }
                Log.i("test", "limit1:" + limit1 + ",limit2:" + limit2);
            }
            return true;
        }
    }
}