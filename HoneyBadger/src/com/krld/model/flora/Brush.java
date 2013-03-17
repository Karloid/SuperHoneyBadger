package com.krld.model.flora;

import com.krld.model.items.Collective;
import com.krld.model.character.Player;
import com.krld.model.Unit;
import com.krld.model.items.food.Berries;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Brush extends Unit implements Collective {
    private boolean haveBerrys;
    private static SpriteSheet spriteSheet;

    public Brush(int x, int y) {
        this.setX(x);
        this.setY(y);
        setHaveBerrys(true);
    }

    @Override
    public void draw() {
        if (spriteSheet == null) {
            initSprite();
        }
        if (isHaveBerrys()) {
            spriteSheet.getSprite(1,0).drawCentered(getX(), getY());
        } else {
            spriteSheet.getSprite(0,0).drawCentered(getX(), getY());
        }
    }

    private void initSprite() {
        try {
            Image img = null;
            img = new Image("HoneyBadger/res/brushberry.png");
            spriteSheet = new SpriteSheet(img, 32, 32, 0, 0);
            spriteSheet.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
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
