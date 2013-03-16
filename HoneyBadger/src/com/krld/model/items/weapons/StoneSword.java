package com.krld.model.items.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StoneSword extends AbstractSword {

    private static Image img;

    @Override
    public String toString() {
        return "Stone Sword";
    }
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/stoneSword.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }

}
