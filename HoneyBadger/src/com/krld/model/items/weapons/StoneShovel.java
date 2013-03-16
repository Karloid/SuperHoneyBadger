package com.krld.model.items.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StoneShovel extends AbstractShovel {
    private static Image img;

    @Override
    public String toString() {
        return "Stone Shovel";
    }
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/stoneShovel.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }
}
