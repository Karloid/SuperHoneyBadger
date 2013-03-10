package com.krld.model.recipe;

import com.krld.model.MyDrawable;
import com.krld.model.live.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class Recipe implements Serializable, MyDrawable {
    protected Image img;

    @Override
    public void draw() {
        draw(0, 0);
    }

    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/note.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);

    }
    public abstract void craft(Player player);
}
