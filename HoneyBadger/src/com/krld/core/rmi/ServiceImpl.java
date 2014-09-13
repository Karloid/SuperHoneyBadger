package com.krld.core.rmi;

import com.krld.common.Light;
import com.krld.common.MoveDirection;
import com.krld.core.Game;
import com.krld.model.FireBall;
import com.krld.model.Located;
import com.krld.model.Unit;
import com.krld.model.character.Player;
import com.krld.model.container.GameState;
import com.krld.model.container.UnitView;
import com.krld.model.container.WebContainer;
import com.krld.model.items.Collective;
import com.krld.model.items.Equip;
import com.krld.model.recipe.AbstractRecipe;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
    public WebContainer getGameTiles(long id) throws RemoteException {
        WebContainer webContainer = new WebContainer();
        // упаковка тайлов
        Player player = findPlayerById(id); // TODO соответствие аккаунтов и персонажей
        if (player == null) {
            player = getNewPlayer();
            player.setId(id);
            player.setX(2000 + (int) (Math.random() * 5));
            player.setY(2000 + (int) (Math.random() * 5));
        }
        webContainer.player = new UnitView(player);

        int playerX = player.getX();
        int playerY = player.getY();
        int distanceOfViewX = 20;
        int distanceOfViewY = 20;
        ArrayList<UnitView> tiles = new ArrayList<UnitView>();
        webContainer.tiles = tiles;
        int[][] tileMap = game.getGameState().getTileMap();
        for (int x = playerX / 32 - distanceOfViewX; x < playerX / 32 + distanceOfViewX; x++) {
            for (int y = playerY / 32 - distanceOfViewY; y < playerY / 32 + distanceOfViewY; y++) {
                try {
                    tiles.add(new UnitView(Integer.toString(tileMap[x][y]), x, y));
                    //    break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    // ничего не делаем
                }
            }
        }
        return webContainer;
    }

    @Override
    public WebContainer getWebGameState(String email) throws RemoteException {
        WebContainer webContainer = new WebContainer();
        Player player = findPlayerById(email.hashCode()); // TODO соответствие аккаунтов и персонажей
        if (player == null) {
            player = getNewPlayer();
            player.setId(email.hashCode());
            player.setX(2000);
            player.setY(2000);
        }
        webContainer.player = new UnitView(player);

        int playerX = player.getX();
        int playerY = player.getY();
        int distanceOfViewX = 14;
        int distanceOfViewY = 11;

        // Упаковка неподвижных игровых объектов - деревья камни
        ArrayList<UnitView> objects = new ArrayList<UnitView>();
        webContainer.objects = objects;
        for (Located unit : game.getGameState().getObjects()) {
            if (playerX - distanceOfViewX * 32 < unit.getX() && playerX + distanceOfViewX * 32 > unit.getX() &&
                    playerY - distanceOfViewY * 32 < unit.getY() && playerY + distanceOfViewY * 32 > unit.getY()) {
                objects.add(new UnitView((Unit) unit));
            }
        }
        // Упаковка игровой фауны - деревья камни
        ArrayList<UnitView> moveables = new ArrayList<UnitView>();
        webContainer.moveables = moveables;
        for (Located unit : game.getGameState().getMoveables()) {
            if (playerX - distanceOfViewX * 32 < unit.getX() && playerX + distanceOfViewX * 32 > unit.getX() &&
                    playerY - distanceOfViewY * 32 < unit.getY() && playerY + distanceOfViewY * 32 > unit.getY()) {
                moveables.add(new UnitView((Unit) unit));
            }
        }

        // Упаковка игровой фауны - деревья камни
        ArrayList<UnitView> drops = new ArrayList<UnitView>();
        webContainer.drops = drops;
        for (Located unit : game.getGameState().getDrops()) {
            if (playerX - distanceOfViewX * 32 < unit.getX() && playerX + distanceOfViewX * 32 > unit.getX() &&
                    playerY - distanceOfViewY * 32 < unit.getY() && playerY + distanceOfViewY * 32 > unit.getY()) {
                drops.add(new UnitView((Unit) unit));
            }
        }

        // Упаковка персонажей других игроков
        ArrayList<UnitView> players = new ArrayList<UnitView>();
        webContainer.players = players;
        for (Player otherPlayer : game.getGameState().getPlayers()) {
            if (playerX - distanceOfViewX * 32 < otherPlayer.getX() && playerX + distanceOfViewX * 32 > otherPlayer.getX() &&
                    playerY - distanceOfViewY * 32 < otherPlayer.getY() && playerY + distanceOfViewY * 32 > otherPlayer.getY() && player.getId() != otherPlayer.getId()) {
                players.add(new UnitView(otherPlayer));
            }
        }
        return webContainer;
    }

    @Override
    public Player getNewPlayer() throws RemoteException {
        Player player = new Player(2000, 2000);
        //float randSize = random.nextInt(15) * .1f + .5f;
        // Color randColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
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
        }
        for (Located located : getGameState().getObjects()) {
            if (located.getX() < x + 16 && located.getX() > x - 16 && located.getY() < y + 16 && located.getY() > y - 16) {
                located.use(p);
            }
        }
    }

    @Override
    public void dropItem(long id, int cursorPosition) throws RemoteException {
        Player p = findPlayerById(id);
        p.dropItem(cursorPosition);
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
        }
    }

    @Override
    public void useRecipe(long id, int cursorPosition) throws RemoteException {
        Player p = findPlayerById(id);
        int i = 0;
        for (AbstractRecipe recipe : p.getRecipes()) {
            if (i == cursorPosition) {
                recipe.craft(p);
                break;
            }
            i++;
        }
        ;
    }

    @Override
    public void setEquippedItem(long id, int cursorPosition) throws RemoteException {
        Player p = findPlayerById(id);
        int i = 0;
        if (cursorPosition == -1) {
            p.getInventory().getItems().add(p.getEquipped());
            p.setEquipped(null);
            return;
        }
        for (Collective item : p.getInventory().getItems()) {
            if (i == cursorPosition) {
                if (item instanceof Equip) {
                    Equip equip = p.getEquipped();
                    if (equip != null) {
                        p.getInventory().getItems().add(equip);
                    }
                    p.setEquipped((Equip) item);
                    p.getInventory().getItems().remove(item);
                } else {
                    break;
                }
                break;
            }
            i++;
        }
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

    @Override
    public WebContainer webMove(long id, MoveDirection moveDirection) throws RemoteException {
        Player player = findPlayerById(id);
        if (player == null) {
            return null;
        }
        player.moveTo(moveDirection);
        WebContainer response = new WebContainer();
        response.player = new UnitView(player);
        return response;
    }

    public static void main(String[] args) {
        try {
            Service service = new ServiceImpl();
            //   Registry registry = LocateRegistry.getRegistry("192.168.1.144", 1099);
            //    registry.rebind("HoneyBadgerRemote", service);
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
