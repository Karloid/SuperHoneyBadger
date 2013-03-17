package com.krld.model.items.seeds;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AppleCore extends AbstractSeed{
    private static SpriteSheet spriteSheet;

    @Override
    public String toString() {
        return "Apple core";
    }
    @Override
    public void draw(int x, int y) {
        if (spriteSheet == null) {
            initSprite();
        }
        spriteSheet.getSprite(1,0).drawCentered(x, y);

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
