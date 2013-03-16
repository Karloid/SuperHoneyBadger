package com.krld.model.items;

import com.krld.model.Collective;
import com.krld.model.Dropable;
import com.krld.model.Equip;
import com.krld.model.Unit;
import com.krld.model.character.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class AbstractAxe extends Unit implements Collective, Serializable, Dropable, Equip {
    private static Image img;
    private boolean dropped;

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

    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/stoneAxe.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }

    @Override
    public void useFromInventory(Player p) {

    }
}
