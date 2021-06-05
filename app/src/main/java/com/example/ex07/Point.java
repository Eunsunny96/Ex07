package com.example.ex07;

public class Point {
    float x,y;
    boolean isDraw;

    public Point(float x, float y, boolean isDraw) {
        this.x = x;
        this.y = y;
        this.isDraw = isDraw;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", isDraw=" + isDraw +
                '}';
    }
}
