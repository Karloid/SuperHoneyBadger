package com.krld.model.items.seeds;

import com.krld.model.Unit;
import com.krld.model.character.Player;
import com.krld.model.items.Collective;
import com.krld.model.items.Dropable;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public abstract class AbstractSeed extends Unit implements Collective, Serializable, Dropable, Seed {
    protected boolean dropped;
    protected static Image img;
    protected int hpBonus;
    protected int hungerBonus;

    @Override
    public void useFromInventory(Player p) {

    }
    @Override
    public String toString() {
        return "AbstractSeed";
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

    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/unknown.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);

    }
}
