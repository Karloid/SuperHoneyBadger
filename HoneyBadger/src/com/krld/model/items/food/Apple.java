package com.krld.model.items.food;

import com.krld.model.character.Player;
import com.krld.model.items.seeds.AppleCore;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Apple extends AbstractFood{
    private SpriteSheet spriteSheet;

    @Override
    public String toString() {
        return "Apple";
    }

    public Apple() {
        this.dropped = false;
        hpBonus = 1;
        hungerBonus = 3;
    }
    @Override
    public void useFromInventory(Player p) {
        p.setHp(hpBonus + p.getHp());
        p.setHunger(hungerBonus + p.getHunger());
        p.getInventory().getItems().remove(this);
        p.getInventory().getItems().add(new AppleCore());
    }

    @Override
    public void draw(int x, int y) {
        if (spriteSheet == null) {
            initSprite();
        }
        spriteSheet.getSprite(0,0).drawCentered(x, y);

    }

    private void initSprite() {
        try {
            Image img = new Image("HoneyBadger/res/apple.png");
            spriteSheet = new SpriteSheet(img, 32,32,0,0);
            spriteSheet.setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
