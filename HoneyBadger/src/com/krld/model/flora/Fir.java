package com.krld.model.flora;

import com.krld.model.character.Player;
import com.krld.model.items.Equip;
import com.krld.model.items.FirBranch;
import com.krld.model.items.WoodLog;
import com.krld.model.items.weapons.AbstractAxe;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fir extends AbstractTree {
    private static Image cutDownImg;
    private static Image aliveImg;

    public Fir(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        if (aliveImg == null) {
            initSprite();
        }
        Image img;
        if (isCutDown()) {
            img = getCutDownImg();
        } else {
            img = getAliveImg();
        }
        img.draw(getX(), getY(), 2);
  //      img.drawCentered(getX(), getY());
    }

    private void initSprite() {
        try {
            setCutDownImg(new Image("HoneyBadger/res/firstump.png"));
            setAliveImg(new Image("HoneyBadger/res/fir.png"));
            getCutDownImg().setFilter(Image.FILTER_NEAREST);
            getAliveImg().setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static Image getCutDownImg() {
        return cutDownImg;
    }

    public static void setCutDownImg(Image cutDownImg) {
        Fir.cutDownImg = cutDownImg;
    }

    public static Image getAliveImg() {
        return aliveImg;
    }

    public static void setAliveImg(Image aliveImg) {
        Fir.aliveImg = aliveImg;
    }

    @Override
    public void use(Player p) {
        Equip equip = p.getEquipped();
        if (equip != null && equip instanceof AbstractAxe) {
            if (!isCutDown()) {
                setCutDown(true);
                gameState.getLifting().add(new WoodLog(getX(), getY()));
            }
        } else if (!isCutDown()) {
            p.getInventory().getItems().add(new FirBranch());
        }
    }
}
