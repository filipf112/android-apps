package com.example.ballmazegamefinal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

//class to set a MazeObject with a Bitmap.
public class MazeObject {
    private Bitmap bitmap;
    private RectF bounds = new RectF();
    private Type type;
    private String property = null;
    private int MazeArrayX;
    private int MazeArrayY;

    public static enum Type {
        MARBLE, FLOOR, WALL, GOAL, OBSTACLE;
    }

    public MazeObject(Type type, Bitmap bitmap) {
        super();
        this.type = type;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }

    public Type getType() {
        return type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }



    //aktualna lokalizacja obiektu
    public void setCurrentLocation(float x, float y, RectF rect) {

        float bitmapWidth = rect.width();
        float bitmapHeight = rect.height();

        float left = x - bitmapWidth / 2;
        float top = y - bitmapHeight / 2;
        float right = x + bitmapWidth / 2;
        float bottom = y + bitmapHeight / 2;
        this.getBounds().set(left, top, right, bottom);

    }

    //rysuje obiekt
    public void drawMazeObject(Canvas canvas, RectF rect) {

        setCurrentLocation(rect.centerX(), rect.centerY(), rect);
        canvas.drawBitmap(this.getBitmap(), null, rect, null);

    }

    public void setMazeArrayX(int mazeArrayX) {
        MazeArrayX = mazeArrayX;
    }


    public void setMazeArrayY(int mazeArrayY) {
        MazeArrayY = mazeArrayY;
    }

}
