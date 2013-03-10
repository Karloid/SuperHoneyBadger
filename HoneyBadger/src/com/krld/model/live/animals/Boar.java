package com.krld.model.live.animals;

import com.krld.common.MoveDirection;
import com.krld.model.live.LiveForm;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Boar extends LiveForm {
    private static Image alive;
    private static Image dead;
    private boolean isAlive;

    static {
        try {
            alive = new Image("HoneyBadger/res/boar.png");
            dead = new Image("HoneyBadger/res/boar.png");
            alive.setFilter(Image.FILTER_NEAREST);
            dead.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private int maxSpeed = 5;

    public Boar(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
        setSpeed(2);
        noMoveChance = 96;
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
