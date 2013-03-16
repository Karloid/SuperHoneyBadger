package com.krld.model.container;

import com.krld.common.Light;
import com.krld.model.items.Dropable;
import com.krld.model.Located;
import com.krld.model.Moveable;
import com.krld.model.character.Player;
import com.krld.model.items.Lifting;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private  GameObjects gameObjects = new GameObjects();
    private int[][] tileMap;

    //     private Player player;

    public  int[][] getTileMap() {
        return tileMap;
    }

    public  void setTileMap(int[][] tileMap) {
        this.tileMap = tileMap;
    }

    public ArrayList<Located> getObjects() {
        return getGameObjects().objects;
    }

    public void setObjects(ArrayList<Located> objects) {
        this.getGameObjects().objects = objects;
    }

    public ArrayList<Moveable> getMoveables() {
        return getGameObjects().moveables;
    }

    public void setMoveables(ArrayList<Moveable> moveables) {
        this.getGameObjects().moveables = moveables;
    }

    public ArrayList<Player> getPlayers() {
        return getGameObjects().players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.getGameObjects().players = players;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(GameObjects gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void setLights(ArrayList<Light> lights) {
        this.getGameObjects().lights = lights;
    }

    public ArrayList<Light> getLights() {
        return this.getGameObjects().lights;
    }

    public ArrayList<Dropable> getDrops() {
        return getGameObjects().drops;
    }

    public void setDrops(ArrayList<Dropable> drops) {
       getGameObjects().drops = drops;
    }

    public ArrayList<Moveable> getShells() {
        return getGameObjects().shells;
    }

    public void setShells(ArrayList<Moveable> shells) {
        getGameObjects().shells = shells;

    }

    public ArrayList<Lifting> getLifting() {
        return getGameObjects().lifting;
    }

    public void setLifting(ArrayList<Lifting> lifting) {
        getGameObjects().lifting = lifting;

    }
}