package com.example.ballmazegamefinal;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import com.example.ballmazegamefinal.MazeObject.Type;

public class Maze {
    //prostokąt używany do rysowania elementów labiryntu
    private RectF drawRect = new RectF();
    //tablica przechowująca tekstury używane do rysowania labiryntu.
    private Bitmap[] bitmaps;
    //reprezentuje typy poszczególnych płytek labiryntu.
    private int[][] tileType;
    //start
    private final int STARTING_POSITION = 10;
    //finish
    private final int FINISH_POSITION = 20;
    //mozliwosci dla komorek labiryntu
    private List<Integer> mazeValues = new ArrayList<>(Arrays.asList(0, 1, 2, STARTING_POSITION, FINISH_POSITION));
    //szerokosc i wysokosc ekranu
    private float screenWidth, screenHeight;


    public Maze(Bitmap[] bitmaps, int[][] tileType, float xCellCountOnScreen, float yCellCountOnScreen, float screenWidth, float screenHeight) {
        this.bitmaps = bitmaps;
        this.tileType = tileType;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        drawRect.set(0, 0, screenWidth / xCellCountOnScreen, screenHeight / yCellCountOnScreen);
    }


    public float getCellWidth() {
        return drawRect.width();
    }

    public float getCellHeight() {
        return drawRect.height();
    }

    //rysuje labirynt
    public void drawMaze(Canvas canvas, float viewX, float viewY) {
        int tileX = 0;
        int tileY = 0;
        float xCoord = -viewX;
        float yCoord = -viewY;

        while (tileY < tileType.length && yCoord <= screenHeight) {
            // Begin drawing a new column
            tileX = 0;
            xCoord = -viewX;

            while (tileX < tileType[tileY].length && xCoord <= screenWidth) {
                // Check if the tile is not null
                if (mazeValues.contains(tileType[tileY][tileX])) {

                    // This tile is not null, so check if it has to be drawn
                    if (xCoord + drawRect.width() >= 0
                            && yCoord + drawRect.height() >= 0) {

                        // The tile actually visible to the user, so draw it
                        drawRect.offsetTo(xCoord, yCoord); // Move the rectangle
                        // to the
                        // coordinates
                        if (STARTING_POSITION == tileType[tileY][tileX]) {
                            tileType[tileY][tileX] = 0;
                        }
                        if (FINISH_POSITION == tileType[tileY][tileX]) {
                            tileType[tileY][tileX] = 0;
                        }

                        canvas.drawBitmap(bitmaps[tileType[tileY][tileX]],null, drawRect, null);
                    }
                }
                // Move to the next tile on the X axis
                tileX++;
                xCoord += drawRect.width();
            }
            // Move to the next tile on the Y axis
            tileY++;
            yCoord += drawRect.height();
        }
    }

    //ustawia obiekty na planszy
    public void setMazeLayoutObjects(Canvas canvas, float viewX, float viewY, List<MazeObject> mazeObjects) {
        int tileX = 0;
        int tileY = 0;
        float xCoord = -viewX;
        float yCoord = -viewY;
        MazeObject mazeObject = null;
        while (tileY < tileType.length && yCoord <= screenHeight) {
            // Begin drawing a new column
            tileX = 0;
            xCoord = -viewX;

            while (tileX < tileType[tileY].length && xCoord <= screenWidth) {
                // Check if the tile is not null
                boolean c = mazeValues.contains(tileType[tileY][tileX]);

                if (mazeValues.contains(tileType[tileY][tileX])) {

                    // This tile is not null, so check if it has to be drawn
                    if (xCoord + drawRect.width() >= 0 && yCoord + drawRect.height() >= 0) {

                        // The tile actually visible to the user, so draw it
                        drawRect.offsetTo(xCoord, yCoord); // Move the rectangle
                        // to the
                        // coordinates
                        switch (tileType[tileY][tileX]) {
                            case 0:

                                mazeObject = new MazeObject(Type.FLOOR, bitmaps[tileType[tileY][tileX]]);
                                mazeObject.setMazeArrayX(tileX);
                                mazeObject.setMazeArrayY(tileY);
                                break;
                            case 1:
                                mazeObject = new MazeObject(Type.WALL, bitmaps[tileType[tileY][tileX]]);
                                mazeObject.setMazeArrayX(tileX);
                                mazeObject.setMazeArrayY(tileY);
                                break;
                            case 2:
                                mazeObject = new MazeObject(Type.OBSTACLE,bitmaps[tileType[tileY][tileX]]);
                                mazeObject.setMazeArrayX(tileX);
                                mazeObject.setMazeArrayY(tileY);
                                break;
                            case STARTING_POSITION:
                                tileType[tileY][tileX] = 0;
                                mazeObject = new MazeObject(Type.FLOOR, bitmaps[tileType[tileY][tileX]]);
                                mazeObject.setMazeArrayX(tileX);
                                mazeObject.setMazeArrayY(tileY);
                                mazeObject.setProperty("STARTING_POSITION");
                                break;
                            case FINISH_POSITION:
                                tileType[tileY][tileX] = 0;
                                mazeObject = new MazeObject(Type.FLOOR, bitmaps[tileType[tileY][tileX]]);
                                mazeObject.setMazeArrayX(tileX);
                                mazeObject.setMazeArrayY(tileY);
                                mazeObject.setProperty("FINISH_POSITION");
                                break;

                        }
                        if (mazeObject != null) {
                            mazeObject.drawMazeObject(canvas, drawRect);
                            mazeObjects.add(mazeObject);
                        }
                    }
                }
                // Move to the next tile on the X axis
                tileX++;
                xCoord += drawRect.width();
            }
            // Move to the next tile on the Y axis
            tileY++;
            yCoord += drawRect.height();
        }
    }
}
