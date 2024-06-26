package com.example.androidapp.Game.Pieces;

abstract class GamePieceAbstract {
    int pieceNum;



    /**
     * 0 = Start
     * 1 = track
     * 2 = home row
     * 3 = home
     */
    int currArea;
    int location, locationInHome;
    short xInc = 24;
    short yInc = 24;


    public float getDeltaXFromLastMove() {
        return deltaXFromLastMove;
    }

    public float getDeltaYFromLastMove() {
        return deltaYFromLastMove;
    }

    float deltaXFromLastMove, deltaYFromLastMove;

    public float getCurrX() {
        return currX;
    }

    public float getCurrY() {
        return currY;
    }

    float currX, currY;
    int[] corners = {0, 15, 30, 45};
    /**
     * Holds current direction of movement
     * 0 = right
     * 1 = down
     * 2 = left
     * 3 = up
     */
    int direction;



    public short getxInc() {
        return xInc;
    }

    public short getyInc() {
        return yInc;
    }
    public int getCurrArea() {
        return currArea;
    }

    public int getPieceNum(){
        return pieceNum;
    }
    public int getLocation(){
        return location;
    }

    public boolean onCorner(){
        boolean result = false;
        for(int i = 0; i < corners.length; i++){
            if (this.location == corners[i]) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void turnCorner(){
        for(int i = 0; i < corners.length; i++){
            if(corners[i] == location){
                direction = i;
            }
        }
    }

    abstract int move(int numToMove);

    public void incLocation(){
        if(location < 59){
            location++;
        }else{
            location = 0;
        }
    }

}
