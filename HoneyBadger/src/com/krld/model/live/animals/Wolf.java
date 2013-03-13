package com.krld.model.live.animals;

import com.krld.common.MoveDirection;
import com.krld.model.live.LiveForm;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Wolf extends LiveForm {
    private static Image alive;
    private static Image dead;
    private boolean isAlive;

    /*
    static {
        try {
            alive = new Image("HoneyBadger/res/wolf.png");
            dead = new Image("HoneyBadger/res/wolf.png");
            dead.setFilter(Image.FILTER_NEAREST);
            alive.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
        */

    private int maxSpeed = 8;
    private static Animation leftAnimation;
    private static Animation rightAnimation;
    //   private Point moveTarget;

    public Wolf(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
        setSpeed(3);
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

   /* @Override
    public void draw() {
        Image img;
        if (isAlive()) {
            img = alive;
        } else {
            img = dead;
        }
        img.drawCentered(getX(), getY());
    }*/

    @Override
    public void draw() {
        if (leftAnimation == null) {
            try {
                Image left = new Image("HoneyBadger/res/wolfAnimation.png");
                Image right = left.getFlippedCopy(true, false);
                SpriteSheet leftSpriteSheet = new SpriteSheet(left, 32, 32, 0, 0);
                SpriteSheet rightSpriteSheet = new SpriteSheet(right, 32, 32, 0, 0);

                leftSpriteSheet.setFilter(Image.FILTER_NEAREST);
                rightSpriteSheet.setFilter(Image.FILTER_NEAREST);

                leftAnimation = new Animation(leftSpriteSheet, 300);
                rightAnimation = new Animation(rightSpriteSheet, 300);
                rightAnimation.setAutoUpdate(true);
                leftAnimation.setAutoUpdate(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Animation imgToDraw = null;
        if (getMovieDirection() == MoveDirection.LEFT) {
            imgToDraw = leftAnimation;
        } else {
            imgToDraw = rightAnimation;
        }
        if (moveTarget == null) {
            int stopFrameIndex;
            if (getMoveDirection() == MoveDirection.LEFT) {
                stopFrameIndex = 0;
            } else {
                stopFrameIndex = 4;
            }
            imgToDraw.getImage(stopFrameIndex).draw(getX(), getY());
        } else {
            imgToDraw.draw(getX(), getY());
        }
    }
}
