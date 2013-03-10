package com.krld.core.rmi;

import com.krld.common.Light;
import com.krld.common.MoveDirection;
import com.krld.core.Game;
import com.krld.model.Collective;
import com.krld.model.FireBall;
import com.krld.model.Located;
import com.krld.model.live.Player;
import com.krld.model.container.GameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

public class ServiceImpl extends UnicastRemoteObject implements Service {
    private static Game game;
    private Random random = new Random();

    protected ServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Server say: Hello!";
    }

    @Override
    public GameState getGameState() throws RemoteException {
        return game.getGameState();
    }

    @Override
    public Player getNewPlayer() throws RemoteException {
        Player player = new Player(2000, 2000);
        float randSize = random.nextInt(15) * .1f + .5f;
        Color randColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        // game.getGameState().getLights().add(new Light(2000, 2000, randSize, randColor));
        game.getGameState().getPlayers().add(player);
        return player;
    }

    @Override
    public void castFireball(Player player) throws RemoteException {
        FireBall fireBall = new FireBall(player);
        game.getGameState().getShells().add(fireBall);
        Light light = new Light(fireBall);
        game.getGameState().getLights().add(light);
    }

    @Override
    public void useAction(long id) throws RemoteException {
        Player p;
        p = findPlayerById(id);
        if (p == null) {
            return;
        }
        int x = 0;
        int y = 0;
        MoveDirection moveDirection = p.getMoveDirection();
        switch (moveDirection) {
            case TOP:
                x = p.getX();
                y = p.getY() - 32;
                break;
            case LEFT:
                x = p.getX() - 32;
                y = p.getY();
                break;
            case RIGHT:
                x = p.getX() + 32;
                y = p.getY();
                break;
            case BOT:
                x = p.getX();
                y = p.getY() + 32;
                break;
        } for (Located located : getGameState().getObjects()) {
            if (located.getX() < x + 16 && located.getX() > x - 16 && located.getY() < y + 16 && located.getY() > y - 16) {
                located.use(p);
            }
        }
    }

    @Override
    public void dropItem(long id) throws RemoteException{
        Player p = findPlayerById(id);
        p.dropItem();
    }

    @Override
    public void useItemFromInventory(long id, int cursorPosition) throws RemoteException {
        Player p = findPlayerById(id);
        int i = 0;
        for (Collective item : p.getInventory().getItems()) {
            if (i == cursorPosition) {
                item.useFromInventory(p);
                break;
            }
            i++;
        };
    }

    private Player findPlayerById(long id) {
        for (Player player : game.getGameState().getPlayers()) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }


    @Override
    public void move(long id, MoveDirection moveDirection) throws RemoteException {
        Player player = findPlayerById(id);
        if (player == null) {
            return;
        }
        player.moveTo(moveDirection);
    }

    public static void main(String[] args) {
        try {
            Service service = new ServiceImpl();
            Naming.rebind("HoneyBadgerRemote", service);
            runGameServer(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runGameServer(Service service) {
        game = new Game(Game.RUNNING_SERVER);
        game.setWidth(80);
        game.setHeight(60);
        try {
            AppGameContainer app = new AppGameContainer(game);
            app.setDisplayMode(game.getWidth(), game.getHeight(), false);
            app.setTargetFrameRate(100);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
