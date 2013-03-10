package com.krld.model.live;

import com.krld.model.Unit;

public class LiveForm extends Unit implements Living {
    private boolean isAlive;
    private int hp;
    private int maxHp;
    private int hunger;
    private int maxHunger;
    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getMaxHunger() {
        return maxHunger;
    }

    public void setHp(int hp) {
        if (hp > getMaxHp()) {
            this.hp = getMaxHp();
        } else {
            this.hp = hp;
        }
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setHunger(int hunger) {
        if (hunger > getMaxHunger()) {
            this.hunger = getMaxHunger();
        } else {
            this.hunger = hunger;
        }
    }

    public void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;
    }
}
