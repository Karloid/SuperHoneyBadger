package com.krld.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Cobble extends Unit implements Located {
    private static Image img;

    static {
        try {
            img = new Image("HoneyBadger/res/cobble.png");
            img.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Cobble(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public void draw() {
        img.drawCentered(getX(), getY());
    }
}
