package com.krld.model.items;

import com.krld.model.Located;
import com.krld.model.MyDrawable;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 05.03.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public interface Dropable extends MyDrawable, Located {
    void pickUp();
    void drop(int x, int y);
    boolean isDropped();
}
