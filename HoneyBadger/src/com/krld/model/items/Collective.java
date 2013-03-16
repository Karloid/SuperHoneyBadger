package com.krld.model.items;

import com.krld.model.MyDrawable;
import com.krld.model.character.Player;

public interface Collective extends MyDrawable {
    void useFromInventory(Player p);
}
