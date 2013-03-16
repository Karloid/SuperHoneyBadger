package com.krld.model.container;

import com.krld.common.Light;
import com.krld.model.items.Dropable;
import com.krld.model.Located;
import com.krld.model.Moveable;
import com.krld.model.character.Player;
import com.krld.model.items.Lifting;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObjects implements  Serializable {
    ArrayList<Located> objects;
    ArrayList<Moveable> moveables;
    ArrayList<Moveable> shells;
    ArrayList<Player> players;
    ArrayList<Light> lights;
    ArrayList<Dropable> drops;
    public ArrayList<Lifting> lifting;

    public GameObjects() {
    }
}