package com.krld.model.recipe;

import com.krld.model.items.Collective;
import com.krld.model.character.Player;
import com.krld.model.items.AbstractBranch;
import com.krld.model.items.AbstractStone;
import com.krld.model.items.weapons.StoneShovel;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashSet;

public class StoneShovelRecipe extends AbstractRecipe {

    @Override
    public String toString() {
        return "Stone Shovel:\n 2x Branch, 1x Stone";
    }
    @Override
    public void draw(int x, int y) {
        if (img == null) {
            try {
                img = new Image("HoneyBadger/res/stoneShovel.png");
                img.setFilter(Image.FILTER_NEAREST);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        img.drawCentered(x, y);
    }

    @Override
    public void craft(Player player) {
        //TODO сделать список предметов необходимых для крафта
        int branchCount = 0;
        int stoneCount = 0;
        int branchNeeded = 2;
        int stoneNeeded = 1;
        HashSet<Collective> itemsConsume = new HashSet<Collective>();
        for (Collective item: player.getInventory().getItems()){

            if (item instanceof AbstractBranch && branchCount < branchNeeded) {
                branchCount++;
                itemsConsume.add(item);
            }
            if (item instanceof AbstractStone && stoneCount < stoneNeeded) {
                stoneCount++;
                itemsConsume.add(item);
            }
            if (stoneCount == stoneNeeded && branchCount == branchNeeded) {
                player.getInventory().getItems().removeAll(itemsConsume);
                player.getInventory().getItems().add(new StoneShovel());
                break;
            }
        }
    }
}
