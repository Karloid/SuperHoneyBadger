package com.krld.model.flora;

import com.krld.model.AbstractAxe;
import com.krld.model.Equip;
import com.krld.model.live.Player;
import com.krld.model.items.FirBranch;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fir extends AbstractTree {
    private static Image cutDownImg;
    private static Image aliveImg;

    static {
        try {
            setCutDownImg(new Image("res/firstump.png"));
            setAliveImg(new Image("res/fir.png"));
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
            //TODO рубрика дерева
        } else {
            p.getInventory().getItems().add(new FirBranch());
        }
    }
}
