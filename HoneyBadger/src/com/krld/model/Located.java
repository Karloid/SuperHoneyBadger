package com.krld.model;

import com.krld.model.character.Player;

public interface Located extends MyDrawable{
    public int getX()  ;
    public int getY()  ;
    public void setX(int x)  ;
    public void setY(int y) ;
    boolean isMakeCollisions();

    void use(Player p);
}
