package com.krld.model.live;

import com.krld.common.MoveDirection;
import com.krld.model.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

public class Player extends LiveForm implements Living {
    transient private static Image imgLeft;
    transient private static Image imgRight;
    transient private static Image imgTop;
    transient private static Image imgBot;
    private Inventory inventory;
    private long id;
    private MoveDirection moveDirection;
    private ArrayList<Recipe> recipes;

    public Player(int x, int y) {
        setId(Calendar.getInstance().getTimeInMillis());
        setId(Unit.getNextId());
        setX(x);
        setY(y);
        setSpeed(10);
        setInventory(new Inventory());
        moveDirection = MoveDirection.LEFT;
        setMaxHp(100);
        setMaxHunger(100);
        setHp(50);
        setHunger(50);
        setRecipes(new ArrayList<Recipe>());
        getRecipes().add(new StoneAxeRecipe());
    }

    public static Image getImgLeft() {
        return imgLeft;
    }

    public static void setImgLeft(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        Player.imgLeft = img;
    }

    public static Image getImgRight() {
        return imgRight;
    }

    public static void setImgRight(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        Player.imgRight = img;
    }

    public static Image getImgTop() {
        return imgTop;
    }

    public static void setImgTop(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        Player.imgTop = img;
    }

    public static Image getImgBot() {
        return imgBot;
    }

    public static void setImgBot(Image img) {
        img.setFilter(Image.FILTER_NEAREST);
        Player.imgBot = img;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }


    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }


    public void draw() {
        if (imgLeft == null) {
            try {
                setImgLeft(new Image("HoneyBadger/res/ghost_left.png"));
                setImgRight(new Image("HoneyBadger/res/ghost_right.png"));
                setImgTop(new Image("HoneyBadger/res/ghost_top.png"));
                setImgBot(new Image("HoneyBadger/res/ghost_bot.png"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        switch (moveDirection) {
            case TOP:
                getImgTop().drawCentered(getX(), getY());
                break;
            case LEFT:
                getImgLeft().drawCentered(getX(), getY());
                break;
            case RIGHT:
                getImgRight().drawCentered(getX(), getY());
                break;
            case BOT:
                getImgBot().drawCentered(getX(), getY());
                break;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Deprecated
    public void grabStuff(ArrayList<Located> units) {
        for (Iterator<Located> iter = units.iterator(); iter.hasNext(); ) {
            Located currentUnit = iter.next();
            if (currentUnit instanceof Collective) {
                try {
                    if (getDistanceTo(currentUnit.getX(), currentUnit.getY()) < 100) {
                        this.getInventory().getItems().add((Collective) currentUnit);
                        iter.remove();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }  /*
        for (com.krld.model.Unit unit : units) {

        }  */
    }

    @Deprecated
    public void dropStuff(ArrayList<Located> units) {
        for (Iterator<Collective> iter = this.getInventory().getItems().iterator(); iter.hasNext(); ) {
            Collective currentUnit = iter.next();
            if (currentUnit instanceof Located) {
                ((Located) currentUnit).setX((int) (getX() + Math.random() * 100 - 50));
                ((Located) currentUnit).setY((int) (getY() + Math.random() * 100 - 50));
                units.add((Located) currentUnit);
                iter.remove();
            }
        }
    }

    @Override
    public MoveDirection getMovieDirection() {
        return moveDirection;
    }

    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        return true;
    }

    @Override
    public boolean move() {
        return false;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public void moveTo(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
        int y = 0;
        int x = 0;
        switch (moveDirection) {
            case TOP:
                x = this.getX();
                y = getY() - getSpeed();
                break;
            case LEFT:
                x = getX() - getSpeed();
                y = getY();
                break;
            case RIGHT:
                x = getX() + getSpeed();
                y = getY();
                break;
            case BOT:
                x = getX();
                y = getY() + getSpeed();
                break;
        }
        if (checkNoCollision(x, y)) {
            moveTo(x, y);
        }
        pickUp();
    }

    private void pickUp() {
        HashSet<Dropable> dropsToRemove = new HashSet<Dropable>();
        for (Dropable drop : getGameState().getDrops()) {
            if (drop.getX() < getX() + 16 && drop.getX() > getX() - 16 && drop.getY() < getY() + 16 && drop.getY() > getY() - 16) {
                drop.pickUp();
                dropsToRemove.add(drop);
                getInventory().getItems().add((Collective) drop);
            }
        }
        getGameState().getDrops().removeAll(dropsToRemove);
    }

    @Override
    public void use(Player p) {

    }

    public void dropItem() {
        //TODO сделать зависимость от направления взгляда
        int x = 0;
        int y = 0;
        this.moveDirection = moveDirection;
        switch (moveDirection) {
            case TOP:
                x = this.getX();
                y = getY() - 32;
                break;
            case LEFT:
                x = getX() - 32;
                y = getY();
                break;
            case RIGHT:
                x = getX() + 32;
                y = getY();
                break;
            case BOT:
                x = getX();
                y = getY() + 32;
                break;
        }
        HashSet<Collective> toRemoveItems = new HashSet<Collective>();
        for (Collective item : getInventory().getItems()) {
            if (item instanceof Dropable) {
                ((Dropable) item).drop(x, y);
                gameState.getDrops().add((Dropable) item);
                toRemoveItems.add(item);
                break;
            }
        }
        getInventory().getItems().removeAll(toRemoveItems);
    }

    public Equip getEquipped() {
        return null;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
