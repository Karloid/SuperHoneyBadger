package com.krld.common;

import com.krld.model.Unit;

import java.util.Comparator;

public class RenderComparator implements Comparator<Unit> {
    @Override
    public int compare(Unit o1, Unit o2) {
        return o1.getY() - o2.getY() ;
    }
}
