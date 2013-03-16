package com.krld.model.items;

import com.krld.model.Unit;
import com.krld.model.items.Lifting;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WoodLog extends Unit implements Lifting {
    private Image img;

    public WoodLog(int x, int y) {
        setX(x);
        setY(y);
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
}
