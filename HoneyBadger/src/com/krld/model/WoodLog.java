package com.krld.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WoodLog extends Unit implements Dropable {
    private Image img;
    private boolean dropped;

    public WoodLog(int x, int y) {
        setX(x);
        setY(y);
        dropped = true;
    }
    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/log.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }

    @Override
    public void pickUp() {
        dropped = false;
    }

    @Override
    public void drop(int x, int y) {
        dropped = true;
        setX(x);
        setY(y);
    }

    @Override
    public boolean isDropped() {
        return dropped;
    }
}
