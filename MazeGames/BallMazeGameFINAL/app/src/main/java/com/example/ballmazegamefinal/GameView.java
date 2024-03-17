package com.example.ballmazegamefinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import  android.view.View;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameView extends View{
    public Cell[][] cells;
    private Cell player, exit;
    private static final int cols = 10,  rows = 10;
    private static final float wall_thickness=6;
    private float  cellSize, hMargin, vMargin;
    private Drawable playerDrawable, finishDrawable, wallDrawable;
    private Random random;
    private Paint wallPaint, playerPaint, exitPaint;


    //konstruktor tworzy: gracza, metę, ściany,
    @SuppressLint("UseCompatLoadingForDrawables")
    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(wall_thickness);


        playerDrawable = getResources().getDrawable(R.drawable.player);
        finishDrawable = getResources().getDrawable(R.drawable.goal);

        // Set bounds for drawables (you may need to adjust these bounds based on your icons)
        playerDrawable.setBounds(0, 0, (int) cellSize, (int) cellSize);
        finishDrawable.setBounds(0, 0, (int) cellSize, (int) cellSize);



        random = new Random();



        CreateMaze();
    }

    private enum Direction{
        UP,DOWN,LEFT,RIGHT;
    }


    //uzyto algorytmu recursive backtracking do generowania labiryntu, przeszukuje wszystkich sasiadow

    //znajduje sąsiada dla danej komórki. Sprawdza komórki sąsiednie w kierunkach: góra, dół, lewo, prawo
    public Cell getNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();

        //left
        if (cell.col > 0)
            if (!cells[cell.col - 1][cell.row].visited)
                neighbours.add(cells[cell.col - 1][cell.row]);
        //right
        if (cell.col < cols - 1)
            if (!cells[cell.col + 1][cell.row].visited)
                neighbours.add(cells[cell.col + 1][cell.row]);
        //top
        if (cell.row > 0)
            if (!cells[cell.col][cell.row - 1].visited)
                neighbours.add(cells[cell.col][cell.row - 1]);

        if (cell.row < rows - 1)
            if (!cells[cell.col][cell.row + 1].visited)
                neighbours.add(cells[cell.col][cell.row + 1]);

        if (neighbours.size() > 0){
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    //usuwa sciane miedzy current i next
    private void removeWall(Cell current, Cell next){
        if(current.col == next.col && current.row == next.row+1){
            current.topWall = false;
            next.bottomWall = false;
        }
        if(current.col == next.col && current.row == next.row-1){
            current.bottomWall = false;
            next.topWall = false;
        }
        if(current.col == next.col+1 && current.row == next.row){
            current.leftWall = false;
            next.rightWall = false;
        }
        if(current.col == next.col-1 && current.row == next.row){
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    //generuje labirynt na podstawie „recursive backtracking”
    private void CreateMaze(){

        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[cols][rows];

        for(int x = 0; x<cols; x++){
            for(int y=0; y<rows; y++) {
                cells[x][y] = new Cell(x,y);
            }
        }

        player = cells[0][0];
        exit = cells[cols-1][rows -1];

        current = cells[0][0];
        current.visited = true;

        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else
                current = stack.pop();
        }while(!stack.empty());
    }


    //odpowiada za poruszanie, oblicza gdzie polozony zostal palec
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
            return true;
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = hMargin + (player.col+0.5f)*cellSize;
            float playerCenterY = vMargin+ (player.col+0.5f)*cellSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);
            if(absDx> cellSize || absDy > cellSize) {

                if (absDx > absDy) {
                    if (dx > 0)
                        movePlayer(Direction.RIGHT);
                    else
                        movePlayer(Direction.LEFT);
                    ;

                } else {
                    if (dy > 0)
                        movePlayer(Direction.DOWN);
                    else
                        movePlayer(Direction.UP);
                }

            }
            return true;

        }
        return super.onTouchEvent(event);
    }

    //rysuje caly labirynt
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.GRAY);

        int width = getWidth();
        int height = getHeight();

        if(width/height < cols/rows)
            cellSize = width/(cols+1);
        else
            cellSize = height/(rows+1);

        hMargin = (width-cols*cellSize)/2;
        vMargin = (height-rows*cellSize)/2;


        canvas.translate(hMargin,vMargin);


        for(int x = 0; x<cols; x++){
            for(int y=0; y<rows; y++) {
                if (cells[x][y].topWall) {
                    canvas.drawLine(
                            x * cellSize, y * cellSize, (x + 1) * cellSize, y * cellSize, wallPaint);
                }
                if (cells[x][y].leftWall) {
                    canvas.drawLine(
                            x * cellSize, y * cellSize, x * cellSize, (y + 1) * cellSize, wallPaint);
                }
                if (cells[x][y].bottomWall) {
                    canvas.drawLine(
                            x * cellSize, (y + 1) * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
                }
                if (cells[x][y].rightWall) {
                    canvas.drawLine(
                            (x + 1) * cellSize, y * cellSize, (x + 1) * cellSize, (y + 1) * cellSize, wallPaint);
                }
                float margin = cellSize / 10;
                if (x == player.col && y == player.row) {
                    float playerLeft = x * cellSize + margin;
                    float playerTop = y * cellSize + margin;
                    playerDrawable.setBounds((int) playerLeft, (int) playerTop,
                            (int) (playerLeft + cellSize - 2 * margin), (int) (playerTop + cellSize - 2 * margin));
                    playerDrawable.draw(canvas);
                }

                if (x == exit.col && y == exit.row) {
                    float exitLeft = x * cellSize + margin;
                    float exitTop = y * cellSize + margin;
                    finishDrawable.setBounds((int) exitLeft, (int) exitTop,
                            (int) (exitLeft + cellSize - 2 * margin), (int) (exitTop + cellSize - 2 * margin));
                    finishDrawable.draw(canvas);
                }
            }

        }

    }


    //aktualizowanie lokalizacji gracza
    private void movePlayer(Direction direction)
    {
        switch(direction) {
            case UP:
                if(!player.topWall)
                    player = cells[player.col][player.row-1];
                break;
            case DOWN:
                if(!player.bottomWall)
                    player = cells[player.col][player.row+1];
                break;
            case LEFT:
                if(!player.leftWall)
                    player = cells[player.col-1][player.row];
                break;
            case RIGHT:
                if(!player.rightWall)
                    player = cells[player.col+1][player.row];
                break;


        }
        invalidate();
        checkExit();
    }


    //koniec gry
    private void checkExit(){
        if (playerDrawable == finishDrawable)
            CreateMaze();
    }

}