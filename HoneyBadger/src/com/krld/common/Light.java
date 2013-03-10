package com.krld.common;

import com.krld.model.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.io.Serializable;

public class Light implements Serializable {
    private float x;
    private float y;
    private static Image img;

    /**
     * The RGB tint of the light, alpha is ignored
     */
    public Color tint;
    /**
     * The alpha value of the light, default 1.0 (100%)
     */
    public float alpha;
    /**
     * The amount to scale the light (use 1.0 for default size).
     */
    public float scale;
    //original scale
    public float scaleOrig;

    public Unit parent;

    public Light(float x, float y, float scale, Color tint) {
        this.setX(x);
        this.setY(y);
        this.scale = scaleOrig = scale;
        this.alpha = 1f;
        this.tint = tint;
    }

    public Light(Unit parent, float scale, Color tint) {
        this.parent = parent;
        this.scale = scaleOrig = scale;
        this.alpha = 1f;
        this.tint = tint;
    }

    public Light(float x, float y, float scale) {
        this(x, y, scale, Color.white);
    }

    public Light(Unit unit) {
        this.parent = unit;
        unit.setGlow(this);
    }

    public void update(float time) {
        //effect: scale the light slowly using a sin func
        scale = scaleOrig + 1f + .5f * (float) Math.sin(time);
    }

    /**
     * The position of the light
     */
    public float getX() {
        return (parent == null ? x : parent.getX());
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return (parent == null ? y : parent.getY());
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw() {
        if (img == null) {
            try {
                img = new Image("res/meteorGlow.png");
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        // TODO рисовать все спрайты так что бы координаты были в центре
        img.drawCentered(getX(), getY());
    }
}