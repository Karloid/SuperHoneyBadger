package com.krld.model.container;

import java.io.Serializable;
import java.util.ArrayList;

public class WebContainer implements Serializable {
    public UnitView player;
    public ArrayList<UnitView> tiles;
    public ArrayList<UnitView> objects;
    public ArrayList<UnitView> players;
    public ArrayList<UnitView> moveables;
    public ArrayList<UnitView> drops;
}
