package com.krld.model;

import com.krld.model.live.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AxeRecipe extends Recipe {
    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/axe.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);

    }

    @Override
    public String toString() {
        return "Axe recipe: 2xBranch, 1xFlint";
    }

    @Override
    public void craft(Player player) {

    }
}
