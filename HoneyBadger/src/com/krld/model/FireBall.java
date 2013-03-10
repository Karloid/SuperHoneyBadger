package com.krld.model;

import com.krld.common.LifeTimeException;
import com.krld.common.MoveDirection;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FireBall extends Unit implements Temporary, Moveable {
    transient private static Image imgLeft;
    transient private static Image imgRight;
    transient private static Image imgTop;
    transient private static Image imgBot;
    private long birthTime;
    private final int lifeTime;
    private MoveDirection moveDirection;

    public FireBall(Unit unit) {
        birthTime = System.currentTimeMillis();
        lifeTime = 1 * 1000;
        this.setX(unit.getX());
        this.setY(unit.getY());
        moveDirection = unit.getMoveDirection();
        setSpeed(20);
    }

    public static Image getImgLeft() {
        return imgLeft;
    }

    public void setImgLeft(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        FireBall.imgLeft = img;
    }

    @Override
    public long getBirthTime() {
        return birthTime;
    }

    @Override
    public long getLifeTime() {
        return lifeTime;
    }

    @Override
    public void draw() {
        if (imgLeft == null) {
            try {
                setImgLeft(new Image("res/fireball_left.png"));
                setImgRight(new Image("res/fireball_right.png"));
                setImgTop(new Image("res/fireball_top.png"));
                setImgBot(new Image("res/fireball_bot.png"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        switch (moveDirection) {
            case TOP:
                getImgTop().drawCentered(getX(), getY());
                break;
            case LEFT:
                getImgLeft().drawCentered(getX(), getY());
                break;
            case RIGHT:
                getImgRight().drawCentered(getX(), getY());
                break;
            case BOT:
                getImgBot().drawCentered(getX(), getY());
                break;
        }
    }

    @Override
    public MoveDirection getMovieDirection() {
        return moveDirection;
    }

    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        return true;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }

    @Override

    public boolean move() throws LifeTimeException {
        if (System.currentTimeMillis() - getBirthTime() > lifeTime) {
            //TODO Explosion and destroy object
            throw new LifeTimeException();
        }
        switch (moveDirection) {
            case TOP:
                moveTo(this.getX(), getY() - getSpeed());
                break;
            case LEFT:
                moveTo(getX() - getSpeed(), getY());
                break;
            case RIGHT:
                moveTo(getX() + getSpeed(), getY());
                break;
            case BOT:
                moveTo(getX(), getY() + getSpeed());
                break;
        }
        return false;
    }

    public static void setImgRight(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        FireBall.imgRight = img;
    }

    public static Image getImgTop() {
        return imgTop;
    }

    public static void setImgTop(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        FireBall.imgTop = img;
    }

    public static Image getImgBot() {
        return imgBot;
    }
    public static Image getImgRight() {
        return imgRight;
    }

    public static void setImgBot(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        FireBall.imgBot = img;
    }
}
