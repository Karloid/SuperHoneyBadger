package com.krld.model;

import com.krld.common.LifeTimeException;
import com.krld.common.Light;
import com.krld.common.MoveDirection;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 30.01.13
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface Moveable extends Located {
    public MoveDirection getMovieDirection();

    public int getMaxSpeed() ;

    public void setMaxSpeed(int maxSpeed) ;

    public boolean moveTo(int x, int y) ;

    public boolean move() throws LifeTimeException;

    public Light getGlow();
}
