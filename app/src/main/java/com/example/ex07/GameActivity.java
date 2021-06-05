package com.example.ex07;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView view = new MyView(this);
        view.setFocusable(true); //키이벤트를 받을 수 있도록 설정
        //타이틀바 숨김
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //xml이 아닌 내부클래스(커스텀 뷰)로 화면 사용
        setContentView(view);
    }

    //내부 클래스
    class MyView extends View implements Runnable {
        Drawable backImg; //배경이미지
        Drawable gunship; //비행기이미지
        Drawable missile; //총알이미지
        Drawable enemy; //적 이미지
        Drawable explosure; //폭발이미지
        // SoundPool 사운드(1M), MediaPlayer 사운드(1M 이상),동영상
        MediaPlayer fire, hit; //발사음, 폭발음
        int width, height; //화면의 가로,세로 길이
        int gunshipWidth, gunshipHeight; //비행기의 가로,세로
        int missileWidth, missileHeight; //총알의 가로,세로
        int enemyWidth, enemyHeight; //적의 가로,세로
        int hitWidth, hitHeight; //폭발이미지의 가로,세로
        int x, y; // 비행기 좌표
        int mx, my; //총알 좌표
        int ex, ey; //적 좌표
        int hx, hy; // 폭발 좌표
        int point = 1000; //점수
        boolean isFire; // 총알 발사여부
        boolean isHit; // 폭발 여부
        List<Missile> mlist; //총알 리스트

        //생성자
        public MyView(Context context) {
            super(context);
            //이미지 생성
            backImg = getResources().getDrawable(R.drawable.back0);
            gunship = getResources().getDrawable(R.drawable.gunship);
            missile = getResources().getDrawable(R.drawable.missile);
            enemy = getResources().getDrawable(R.drawable.enemy);
            explosure = getResources().getDrawable(R.drawable.hit);
            //사운드 생성
            fire = MediaPlayer.create(GameActivity.this, R.raw.fire);
            hit = MediaPlayer.create(GameActivity.this, R.raw.hit);
            //리스트 생성
            mlist = new ArrayList<>();
            //백그라운드 스레드 생성
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            while (true) {
                //적 좌표
                ex -= 3;
                if (ex < 0) {
                    ex = width - enemyWidth;
                }
                //총알 좌표
                for (int i = 0; i < mlist.size(); i++) {
                    Missile m = mlist.get(i); //i번째 총알
                    m.setMy(m.getMy() - 5); //y좌표 감소 처리
                    if (m.getMy() < 0) { //y좌표가 0이면
                        mlist.remove(i); //리스트에서 제거
                    }
                    //충돌여부 판정
                    //적의 사각영역
                    Rect rect1= new Rect(ex, ey, ex + enemyWidth,
                            ey + enemyHeight);
                    //총알의 사각영역
                    Rect rect2 = new Rect(m.getMx(), m.getMy()
                            , m.getMx() + missileWidth,
                            m.getMy() + missileHeight);
                    if (rect1.intersect(rect2)) { //겹친 부분이 있으면
                        hit.start(); //폭발음 플레이
                        isHit = true; //폭발 상태로 변경
                        point += 100; //점수 증가
                        hx = ex; //폭발한 좌표 저장
                        hy = ey;
                        mlist.remove(i); //총앍 리스트에서 제거
                        ex = width - enemyWidth; //적 좌표 초기화
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate(); //화면 갱신 => onDraw()가 호출됨
            }
        }

        //화면 사이즈가 변경될 때(최초 표시, 가로/세로 전환)
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            //화면의 가로,세로
            width = getWidth();
            height = getHeight();
            //이미지의 가로,세로 길이
            gunshipWidth = gunship.getIntrinsicWidth();
            gunshipHeight = gunship.getIntrinsicHeight();
            missileWidth = missile.getIntrinsicWidth();
            missileHeight = missile.getIntrinsicHeight();
            enemyWidth = enemy.getIntrinsicWidth();
            enemyHeight = enemy.getIntrinsicHeight();
            hitWidth = explosure.getIntrinsicWidth();
            hitHeight = explosure.getIntrinsicHeight();
            //비행기 좌표
            x = width / 2 - gunshipWidth / 2;
            y = height - 50;
            //총알 좌표
            mx = x + 20;
            my = y;
            //적 좌표
            ex = width - enemyWidth;
            ey = 50;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //배경이미지 출력
            // setBounds(x1,y1,x2,y2) 영역 지정
            backImg.setBounds(0, 0, width, height);
            backImg.draw(canvas); //이미지를 캔버스에 출력시킴
            //비행기 출력
            gunship.setBounds(x, y, x + gunshipWidth,
                    y + gunshipHeight);
            gunship.draw(canvas);
            //적 출력
            if (isHit) { //폭발상태
                //폭발 이미지 출력
                explosure.setBounds(hx - 20, hy - 20
                        , hx + hitWidth - 20,
                        hy + hitHeight - 20);
                explosure.draw(canvas);
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                isHit = false; //폭발하지 않은 상태로 전환
            } else { //폭발하지 않은 상태
                enemy.setBounds(ex, ey, ex + enemyWidth,
                        ey + enemyHeight);
                enemy.draw(canvas);
            }
            //총알 출력
            for (int i = 0; i < mlist.size(); i++) {
                Missile m = mlist.get(i); //i번째 총알
                //총알 이미지의 출력범위
                missile.setBounds(m.getMx(), m.getMy()
                        , m.getMx() + missileWidth,
                        m.getMy() + missileHeight);
                missile.draw(canvas); //총알이미지 출력
            }
            //점수 출력
            String str = "Point:" + point;
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(25); //폰트 사이즈
            //텍스트 출력 drawText(문자열,x,y,페인트객체)
            canvas.drawText(str, 0, 30, paint);
        }

        //키이벤트 처리
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) { //키코드값
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    x -= 5;
                    x = Math.max(0, x); //큰 값
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    x += 5;
                    x = Math.min(width - gunshipWidth, x); //작은 값
                    break;
            }
            postInvalidate(); // 그래픽 갱신 요청=>onDraw() 호출
            return super.onKeyDown(keyCode, event);
        }

        //터치 이벤트
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            isFire = true; //발사 상태로 전환
            fire.start(); //사운드 플레이
            //총알 객체 생성
            Missile ms = new Missile(x + gunshipWidth / 2, y);
            mlist.add(ms); //리스트에 추가
            return super.onTouchEvent(event);
        }
    }
}