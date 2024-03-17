package com.example.ballmazegamefinal;


import static com.example.ballmazegamefinal.MazeObject.Type.WALL;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.ballmazegamefinal.MazeObject.Type;


public class DrawingView extends View {

    private List<MazeObject> mazeObstacles = new ArrayList<MazeObject>();
    private Maze maze;
    private MazeObject marble = null;
    private MazeObject goal = null;
    private MainActivity mainActivity = (MainActivity) getContext();

    Bitmap bitmap;


    private RectF rect = new RectF();


    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public DrawingView(Context context) {
        super(context);

    }

    //inicjalizuje, ustawia tekstury
    public void init() {

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.marble_2);
        Bitmap scaledMarble = scaleBitmapToMazeCell(bitmap);
        marble = new MazeObject(Type.MARBLE, scaledMarble);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        Bitmap scaledFloppyDisk = scaleBitmapToMazeCell(bitmap);
        goal = new MazeObject(Type.GOAL, scaledFloppyDisk);

    }

    //rysuje caly widok
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float viewX = 0;
        float viewY = 0;

        if (marble == null) {

            initMaze();
            init();
            maze.setMazeLayoutObjects(canvas, viewX, viewY, mazeObstacles);
            startMarblePosition(canvas);
            setMarbleGoal(canvas);
            rect.set(marble.getBounds());

        } else {
            // Program was running: redraw

            maze.drawMaze(canvas, viewX, viewY);
            marble.drawMazeObject(canvas, marble.getBounds());
            goal.drawMazeObject(canvas, goal.getBounds());

            if (marble.getBounds().intersect(goal.getBounds())) {
                mainActivity.wonGameOption();
            }
            if (findIntersection(marble).getType() == Type.OBSTACLE) {
                mainActivity.gameOverOption();
            }
        }
    }

    //poruszanie zgodznie z accelerometrem
    public void moveMarble(float[] accelerometer) {
        float ax = accelerometer[0];
        float ay = accelerometer[1];

        // Y coordinate
        float bottom = marble.getBounds().bottom;
        // Y coordinate
        float top = marble.getBounds().top;
        // X coordinate
        float left = marble.getBounds().left;
        // X coordinate
        float right = marble.getBounds().right;

        float b = bottom + ay ;
        float t = top + ay ;
        float l = left - ax ;
        float r = right - ax ;

        marble.setBounds(new RectF(l, t, r, b));
        MazeObject intersecting = findIntersection(marble);
        if (intersecting.getType() == WALL) {
            marble.setBounds(new RectF(left, top, right, bottom));
        }


    }



    //resetuje view
    public void resetView() {
        invalidate();
    }

    //skaluje teksture zgodnie do rozmiaru komorki labiryntu
    private Bitmap scaleBitmapToMazeCell(Bitmap bitmap) {

        int w = Math.round(maze.getCellWidth());
        int h = Math.round(maze.getCellHeight());
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, w, h, true);
        return scaled;

    }


    //pozycja kulki
    private void startMarblePosition(Canvas canvas) {
        for (int i = mazeObstacles.size() - 1; i >= 0; i--) {
            MazeObject mazeObject = mazeObstacles.get(i);
            if (mazeObject.getProperty() == "STARTING_POSITION") {
                marble.drawMazeObject(canvas, mazeObject.getBounds());
            }
        }
    }

    //pozycja mety
    private void setMarbleGoal(Canvas canvas) {
        for (int i = mazeObstacles.size() - 1; i >= 0; i--) {
            MazeObject mazeObject = mazeObstacles.get(i);
            if (mazeObject.getProperty() == "FINISH_POSITION") {
                goal.drawMazeObject(canvas, mazeObject.getBounds());
            }
        }

    }


    //sprawdza kolzicje
    private MazeObject findIntersection(MazeObject movingMazeObject) {
        float left = movingMazeObject.getBounds().left;
        float top = movingMazeObject.getBounds().top;
        float right = movingMazeObject.getBounds().right;
        float bottom = movingMazeObject.getBounds().bottom;
       for (int i = mazeObstacles.size() - 1; i >= 0; i--) {
           MazeObject mazeObject = mazeObstacles.get(i);

           if (mazeObject.getBounds().intersects(left, top, right, bottom)) {
                return mazeObject;
            }
        }
        return movingMazeObject;

    }

    //czy kulka zainicjalizowana
    public boolean isMarbleInit() {
        if (marble == null)
            return false;
        return true;
    }

    //inicjalizacja labiryntu
    public void initMaze() {

        int[][] mazeArray = {{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                            { 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                            { 1, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1 },
                            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1 },
                            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                            { 1, 1, 1, 1, 1, 1, 1, 1, 2, 0, 0, 1, 1, 1, 1, 1, 1 },
                            { 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
                            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                            { 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1 },
                            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1 },
                            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1 },
                            { 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 1, 1, 1 },
                            { 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 1 },
                            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },};

        Bitmap[] bitmaps = {
                BitmapFactory.decodeResource(getResources(), R.drawable.floor),
                BitmapFactory.decodeResource(getResources(), R.drawable.wall),
                BitmapFactory.decodeResource(getResources(), R.drawable.obstacles) };

        // Chance the 480 and 320 to match the screen size of your device
        maze = new Maze(bitmaps, mazeArray, 17, 15, getWidth(), getHeight());

    }

}
