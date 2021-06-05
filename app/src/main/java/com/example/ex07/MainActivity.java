package com.example.ex07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.button1:
                intent=new Intent(this, ShapeActivity.class);
                break;
            case R.id.button2:
                intent=new Intent(this, KeyActivity.class);
                break;
            case R.id.button3:
                intent=new Intent(this, LineActivity.class);
                break;
            case R.id.button4:
                intent=new Intent(this, GameActivity.class);
                break;
            case R.id.button5:
                intent=new Intent(this, ImageRotateActivity.class);
                break;
            case R.id.button6:
                intent=new Intent(this, MoveActivity.class);
                break;
            case R.id.button7:
                intent=new Intent(this, ClockActivity.class);
                break;
        }
        startActivity(intent);
    }
}