package com.krld.model;

import com.krld.model.live.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class AbstractAxe extends Unit implements  Collective, Serializable, Dropable {
    private static Image img;

    @Override
    public void pickUp() {

    }

    @Override
    public void drop(int x, int y) {

    }

    @Override
    public boolean isDropped() {
        return false;
    }

    @Override
    public void draw() {
        if (img == null) {
            try {
                img = new Image("res/axe.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(getX(), getY());

    }

    @Override
    public void useFromInventory(Player p) {

    }
}
