package com.krld.model.live.animals;

import com.krld.common.MoveDirection;
import com.krld.model.live.LiveForm;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Wolf extends LiveForm {
    private static Image alive;
    private static Image dead;
    private boolean isAlive;

    static {
        try {
            alive = new Image("res/wolf.png");
            dead = new Image("res/wolf.png");
            dead.setFilter(Image.FILTER_NEAREST);
            alive.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private int maxSpeed = 8;
 //   private Point moveTarget;

    public Wolf(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
        setSpeed(3);
    }

    @Override
    public MoveDirection getMovieDirection() {
        return null;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void draw() {
        Image img;
        if (isAlive()) {
            img = alive;
        } else {
            img = dead;
        }
        img.drawCentered(getX(), getY());
    }
}
