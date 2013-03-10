package com.krld.model.flora;

import com.krld.model.Equip;
import com.krld.model.WoodLog;
import com.krld.model.items.AbstractAxe;
import com.krld.model.items.FirBranch;
import com.krld.model.live.Player;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fir extends AbstractTree {
    private static Image cutDownImg;
    private static Image aliveImg;

    static {
        try {
            setCutDownImg(new Image("HoneyBadger/res/firstump.png"));
            setAliveImg(new Image("HoneyBadger/res/fir.png"));
            getCutDownImg().setFilter(Image.FILTER_NEAREST);
            getAliveImg().setFilter(Image.FILTER_NEAREST);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Fir(int x, int y) {
        super();
        setX(x);
        setY(y);
        setCutDown(false);
    }

    @Override
    public void draw() {
        Image img;
        if (isCutDown()) {
            img = getCutDownImg();
        } else {
            img = getAliveImg();
        }
        img.drawCentered(getX(), getY());
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
                gameState.getDrops().add(new WoodLog(getX(), getY()));
            }
        } else if (!isCutDown()) {
            p.getInventory().getItems().add(new FirBranch());
        }
    }
}
