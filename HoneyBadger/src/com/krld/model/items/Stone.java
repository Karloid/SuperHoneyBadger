package com.krld.model.items;

import com.krld.model.character.Player;

public class Stone extends AbstractStone {
    @Override
    public String toString() {
        return "Stone";
    }

    @Override
    public void useFromInventory(Player p) {

    }

    public Stone(int x, int y){
        setX(x);
        setY(y);
        dropped = true;
    }
}
