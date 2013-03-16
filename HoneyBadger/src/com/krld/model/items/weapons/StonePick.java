package com.krld.model.items.weapons;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class StonePick extends AbstractPick{
    private static Image img;
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/stonePick.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }

    @Override
    public String toString() {
        return "Stone Pick";
    }
}
