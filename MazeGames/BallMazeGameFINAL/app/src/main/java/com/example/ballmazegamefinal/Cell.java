package com.example.ballmazegamefinal;

public class Cell {
    public boolean topWall = true;
    public boolean bottomWall = true;
    public boolean leftWall = true;
    public boolean rightWall = true;
    public boolean visited = false;
    public int col;
    public int row;

    public Cell(int col, int row)
    {
        this.col = col;
        this.row = row;
    }


}
