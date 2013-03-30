package com.krld.model.container;

import java.io.Serializable;
import java.util.ArrayList;

public class WebContainer implements Serializable {
    public ArrayList<UnitView> tiles;
    public ArrayList<UnitView> objects;
    public UnitView player;
}
