package com.krld.model.container;

import com.krld.common.MoveDirection;
import com.krld.model.Unit;

import java.io.Serializable;

public class UnitView implements Serializable {
    public String name;
    public int x;
    public int y;
    public MoveDirection mD;

    public UnitView(Unit unit) {
       name = unit.getClass().toString();
       x = unit.getX();
       y = unit.getY();
       mD = unit.getMoveDirection();
    }

    public UnitView(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}
