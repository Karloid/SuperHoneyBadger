package com.krld.model.flora;

import com.krld.model.character.Player;
import com.krld.model.items.AppleBranch;
import com.krld.model.items.Equip;
import com.krld.model.items.FirBranch;
import com.krld.model.items.WoodLog;
import com.krld.model.items.food.Apple;
import com.krld.model.items.weapons.AbstractAxe;
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
        img.drawCentered(getX(), getY() - 16);
        // img.drawCentered(getX(), getY());
    }

    private void initSprite() {
        try {
            Image img = new Image("HoneyBadger/res/appleTree.png");
            spriteSheet = new SpriteSheet(img, 64, 64, 0, 0);
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
        Equip equip = p.getEquipped();
        if (equip != null && equip instanceof AbstractAxe) {
            if (!isCutDown()) {
                setCutDown(true);
                gameState.getLifting().add(new WoodLog(getX(), getY() - 1));
            }
        } else if (!isCutDown()) {
            if (isHaveApples()) {
                if (Math.random() < 0.3f) {
                    this.setHaveApples(false);
                }
                p.getInventory().getItems().add(new Apple());
            } else {
                p.getInventory().getItems().add(new AppleBranch());
            }
        }
    }
}
