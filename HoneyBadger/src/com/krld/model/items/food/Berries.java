package com.krld.model.items.food;

import com.krld.model.character.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Berries extends AbstractFood {

    public Berries() {
        this.dropped = false;
        hpBonus = 2;
        hungerBonus = 5;
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
