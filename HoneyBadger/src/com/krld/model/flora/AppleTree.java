package com.krld.model.flora;

import com.krld.model.character.Player;
import com.krld.model.items.food.Apple;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AppleTree extends AbstractTree {
    private static SpriteSheet spriteSheet;
    private static Image cutDownImg;
    private boolean haveApples;

    public AppleTree(int x, int y) {
        super(x, y);
        setHaveApples(true);
    }

    @Override
    public void draw() {
        if (spriteSheet == null) {
            initSprite();
        }
        Image img;
        if (!isCutDown()) {
            if (isHaveApples()) {
                img = spriteSheet.getSprite(1, 0);
            } else {
                img = spriteSheet.getSprite(0, 0);
            }
        } else {
            img = getCutDownImg();
        }
        img.drawCentered(getX(), getY());
    }

    private void initSprite() {
        try {
            Image img = new Image("HoneyBadger/res/appleTree.png");
            spriteSheet = new SpriteSheet(img, 32, 32, 0, 0);
            spriteSheet.setFilter(Image.FILTER_NEAREST);
            setCutDownImg(new Image("HoneyBadger/res/firstump.png"));
            getCutDownImg().setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void setCutDownImg(Image cutDownImg) {
        this.cutDownImg = cutDownImg;
    }

    public Image getCutDownImg() {
        return cutDownImg;
    }

    public boolean isHaveApples() {
        return haveApples;
    }

    public void setHaveApples(boolean haveApples) {
        this.haveApples = haveApples;
    }

    @Override
    public void use(Player p) {
        if (isHaveApples()) {
            if (Math.random() < 0.3f) {
            this.setHaveApples(false); }
            p.getInventory().getItems().add(new Apple());
        }
    }
}
