package com.krld.model;

import com.krld.common.LifeTimeException;
import com.krld.common.Light;
import com.krld.common.MoveDirection;
import com.krld.model.container.GameState;
import com.krld.model.live.Player;
import org.lwjgl.util.Point;
import org.newdawn.slick.Image;

import java.io.Serializable;
import java.rmi.RemoteException;

import static java.lang.StrictMath.hypot;

public abstract class Unit implements MyDrawable, Located, Serializable, Moveable {
    protected static GameState gameState;
    private int maxSpeed;
    private int x;
    private int y;
    private int speed;
    private MoveDirection moveDirection;
    private Light glow;
    protected Point moveTarget;
    protected int noMoveChance = 99;
    protected int minimalDistanceAxisMove = 10;
    private static long nextId = 0;

    public static long getNextId() {
        nextId++;
        return nextId;
    }

    @Override
    public void use(Player p) {

    }

    public static void setNextId(long nextId) {
        Unit.nextId = nextId;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public double getDistanceTo(double x, double y) {
        return hypot(x - this.x, y - this.y);
    }

    public double getDistanceTo(Located unit) throws RemoteException {
        return getDistanceTo(unit.getX(), unit.getY());
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public static Image getImgRight() {
        return null;
    }

    public static Image getImgLeft() {
        return null;
    }

    public static Image getImgTop() {
        return null;
    }

    public static Image getImgBot() {
        return null;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public void setGlow(Light glow) {
        this.glow = glow;
    }

    public Light getGlow() {
        return glow;
    }

    public MoveDirection getMovieDirection() {
        return this.moveDirection;
    }

    public boolean moveTo(int x, int y) {
        moveTarget = new Point(x, y);
        moveToTarget();
        return true;
    }

    public boolean move() throws LifeTimeException {
        //   Log.i(this.getClass() + "");
        if (moveTarget != null) {
            moveToTarget();
        } else {
            setSpeed(4);
            int random = (int) (Math.random() * 100);
            if (random < noMoveChance) {
                return false;
            }

            int distanceX = (int) (Math.random() * 50 + minimalDistanceAxisMove);
            int distanceY = (int) (Math.random() * 50 + minimalDistanceAxisMove);
            moveTo(getX() + (Math.random() > 0.5f ? distanceX : -distanceX), getY() + (Math.random() > 0.5f ? distanceY : -distanceY));
        }

        //  moveTo((int) (getX() + Math.random() * maxSpeed * 2 - maxSpeed + 1), (int) (getY() + Math.random() * maxSpeed * 2 - maxSpeed + 1));
        return true;
    }

    public boolean moveToTarget() {
        int movX = moveTarget.getX();
        int movY = moveTarget.getY();
        int thisX = getX();
        int thisY = getY();
       /* int distanceX = Math.max(thisX, movX) - Math.min(thisX, movX);
        int distanceY = Math.max(thisY, movY) - Math.min(thisY, movY);  */
        int distanceX = thisX - movX;
        int distanceY = thisY - movY;
/*        if (Math.abs(distanceX) < 11 && Math.abs(distanceY) < 11 ) {
            moveTarget = null;
            return false;
        }*/
        double timesX = Math.abs(distanceX / getSpeed());
        double timesY = Math.abs(distanceY / getSpeed());
        double time = Math.max(timesX, timesY);
        double deltaX = distanceX / time;
        double deltaY = distanceY / time;
        //  Log.i(this.hashCode() + " delta x" + deltaX + " y" + deltaY + " distance x" + distanceX + " y" + distanceY + " times " + time + " x" + timesX + " y" + timesY);
        if ((Math.abs(distanceX) <= getSpeed()) && (Math.abs(distanceY) <= getSpeed())) {
            if (checkNoCollision(movX, movY)) {
                setX(movX);
                setY(movY);
            }
            //        Log.i(this.hashCode() + " Цели достиг");
            moveTarget = null;
            return true;
        } else {
            if (deltaX > 0) {
                moveDirection = MoveDirection.LEFT;
            } else {
                moveDirection = MoveDirection.RIGHT;
            }
            if (checkNoCollision((int) (thisX - deltaX), (int) (thisY - deltaY))) {
                setX((int) (thisX - deltaX));
                setY((int) (thisY - deltaY));
            } else {
                moveTarget = null;
            }

            //         Log.i(this.hashCode() + " Иду к цели");
            return true;
        }
    }

    public boolean checkNoCollision(int x, int y) {
        int xTile = Math.round((x + 16) / 32);
        int yTile = Math.round((y + 16) / 32);
        //  Log.i("Check obstacles! " + x + " " + y + " tile xy " + xTile + " " + yTile + " this class: " + this.getClass());
        int[][] tileMap = getGameState().getTileMap();
        try {
            if (tileMap[xTile][yTile] == MyTile.WATER) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        for (Located locatedObject : getGameState().getObjects()) {
            if (locatedObject.getX() < x + 16 && locatedObject.getX() > x - 16 && locatedObject.getY() < y + 16 && locatedObject.getY() > y - 16) {
                return false;
            }
        }
        return true;
    }

    public GameState getGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        Unit.gameState = gameState;
    }

    @Override
    public void draw() {
      draw(getX(), getY());
    }

    @Override
    public void draw(int x, int y) {

    }
}
