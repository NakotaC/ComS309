package com.example.androidapp.Game;

abstract class GamePieceAbstract {
    int pieceNum;
    /**
     * 0 = Start
     * 1 = track
     * 2 = home row
     * 3 = home
     */
    int location;
    short xInc = 24;
    short yInc = 24;
    int homeLocation, startLocation;

    int currX, currY;

    /**
     * Holds current direction of movement
     * 1 = right
     * 2 = down
     * 3 = left
     * 4 = up
     */
    int direction;

    public int getStartLocation() {
        return startLocation;
    }

    public short getxInc() {
        return xInc;
    }

    public short getyInc() {
        return yInc;
    }

    public boolean isHome(){
        return location == homeLocation;
    }
    public int getPieceNum(){
        return pieceNum;
    }
    public int getLocation(){
        return location;
    }

    abstract int move(int numToMove);

}
