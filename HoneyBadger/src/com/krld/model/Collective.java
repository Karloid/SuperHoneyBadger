package com.krld.model;

import com.krld.model.character.Player;

public interface Collective extends MyDrawable{
    void useFromInventory(Player p);
}
