package com.krld.model.live.animals;

import com.krld.common.LifeTimeException;
import com.krld.common.MoveDirection;
import com.krld.model.live.LiveForm;
import com.krld.model.character.Player;
import org.lwjgl.util.Point;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.rmi.RemoteException;

public class Rabbit extends LiveForm {
    private static Image aliveRight;
    private static Image aliveLeft;
    private static Image dead;
    private boolean isAlive;

    static {
        try {
            aliveRight = new Image("HoneyBadger/res/rabbitRight.png");
            aliveLeft = new Image("HoneyBadger/res/rabbitLeft.png");
            dead = new Image("HoneyBadger/res/rabbitLeft.png");
            aliveLeft.setFilter(Image.FILTER_NEAREST);
            aliveRight.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private int maxSpeed = 10;


    public Rabbit(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
        setSpeed(4);
        noMoveChance = 90;
    }

    @Override
    public MoveDirection getMovieDirection() {
        return null;
    }

/*    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        return false;
    }*/

    @Override
    public void draw() {
        Image img = null;
        if (isAlive()) {
            if (getMoveDirection() == MoveDirection.RIGHT) {
                img = aliveRight;
            } else //if (getMovieDirection() == MoveDirection.LEFT)
             {
                img = aliveLeft;
            }
        } else {
            img = dead;
        } img.drawCentered(getX(), getY());
    }

    @Override
    public boolean move() throws LifeTimeException {
        for (Player player : gameState.getPlayers()) {
            try {
                if (getDistanceTo(player) < 100) {
                    int distanceX = player.getX() - getX();
                    int distanceY = player.getY() - getY();
                    moveTarget = new Point(getX() - (distanceX * 3), getX() - (distanceY * 3));
               //     Log.i(this.hashCode() + " RUN FOM PLAYER RABBIT to x y" + (getX() - distanceX) + " " + (getX() - distanceY));
                    setSpeed(14);
                    moveToTarget();
                    return true;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        ;

        return super.move();
    }


/*    @Override
    public boolean move() {
        int random = (int) (Math.random() * 10);
        if (random > 2) {
            return false;
        }
        moveTo(((getX() +  (Math.random() <= 0.5 ? 1 : -1))), (int) (getY() + (Math.random() <= 0.5 ? 1 : -1)));
        return true;
    }*/
}
