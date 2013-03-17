package com.krld.model.flora;

import com.krld.model.Unit;

public abstract class AbstractTree extends Unit {

    private boolean cutDown;

    public AbstractTree(int x, int y) {
        cutDown = false;
        setX(x);
        setY(y);
    }

    public boolean isCutDown() {
        return cutDown;
    }

    public void setCutDown(boolean cutDown) {
        setMakeCollisions(!cutDown);
        this.cutDown = cutDown;
    }
}
