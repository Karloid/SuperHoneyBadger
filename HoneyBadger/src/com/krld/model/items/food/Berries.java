package com.krld.model.items.food;

import com.krld.model.Collective;
import com.krld.model.Dropable;
import com.krld.model.character.Player;
import com.krld.model.Unit;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Berries extends Unit implements Collective, Serializable, Dropable, Eatable {
    private boolean dropped;
    private static Image img;
    private int hpBonus;
    private int hungerBonus;

    public Berries() {
        this.dropped = false;
        hpBonus = 2;
        hungerBonus = 5;
    }

    @Override
    public String toString() {
        return "Berries";
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
                img = new Image("HoneyBadger/res/berries.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);

    }

    @Override
    public void useFromInventory(Player p) {
       p.setHp(hpBonus + p.getHp());
       p.setHunger(hungerBonus + p.getHunger());
       p.getInventory().getItems().remove(this);
    }

    @Override
    public int getHpBonus() {
        return hpBonus;
    }

    @Override
    public int getHungerBonus() {
        return hungerBonus;
    }
}
