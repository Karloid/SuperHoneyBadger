package com.krld.model.flora;

import com.krld.model.Collective;
import com.krld.model.live.Player;
import com.krld.model.Unit;
import com.krld.model.items.food.Berries;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Brush extends Unit implements Collective {
    private static Image empty;
    private static Image full;

    static {
        try {
            empty = new Image("res/brush.png");
            full = new Image("res/brushberry.png");
            empty.setFilter(Image.FILTER_NEAREST);
            full.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    private boolean haveBerrys;

    public Brush(int x, int y) {
        this.setX(x);
        this.setY(y);
        setHaveBerrys(true);
    }

    @Override
    public void draw() {
        if (isHaveBerrys()) {
            full.drawCentered(getX(), getY());
        } else {
            empty.drawCentered(getX(), getY());
        }
    }

    public boolean isHaveBerrys() {
        return haveBerrys;
    }

    public void setHaveBerrys(boolean haveBerrys) {
        this.haveBerrys = haveBerrys;
    }

    public String toString() {
        if (isHaveBerrys()) {
            return "Brushberrys";
        } else {
            return "Empty brush";
        }
    }

    @Override
    public void use(Player p) {
        if (isHaveBerrys()) {
            this.setHaveBerrys(false);
            p.getInventory().getItems().add(new Berries());
        }
    }

    @Override
    public void useFromInventory(Player p) {

    }
}
