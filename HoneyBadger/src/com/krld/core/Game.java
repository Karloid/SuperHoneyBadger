package com.krld.core;

import com.krld.common.*;
import com.krld.core.rmi.Service;
import com.krld.model.*;
import com.krld.model.container.GameState;
import com.krld.model.live.Player;
import com.krld.model.recipe.Recipe;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;

public class Game extends BasicGame {
    public static final int TILE_SIZE = 32;
    public static final int DISTANCE_OF_VIEW_IN_TILES = 15;
    private int runningType;
    public static final int RUNNING_SERVER = 0;
    public static final int RUNNING_CLIENT = 1;
    private static final int DELAY = 50;
    private static long time = System.nanoTime();

    private static int width;
    private static int height;
    private int elapsedTime;
    private Rectangle viewPort;

    private int distanceOfView = 600;
    private GameState gameState;
    private Player player;
    private Client client;
    private Server server;
    private String mode;
    private Service service;

    private Image alphaMap;
    private Color sharedColor = new Color(1f, 1f, 1f, 1f);
    private float zoomFactor = 1f;
    private Image vignettingImage;
    private Image vignettingImageOriginal;
    private Color vignettingFilter;
    private Image nightImage;
    private Image nightImageOriginal;
    private Color nightImageFilter;
    private float brightness;
    private boolean showInfo = false;
    private Image inventoryBackground;
    private boolean showInventory = false;
    private int skipInventoryElements;

    private Music m;
    private Music mLoop;
    private int cursorPosition;
    private int activeInventoryScope;

    public Game(int mode) {
        super("com.krld Server");
        runningType = mode;
    }

    public Game(int runningClient, Service service) {
        super("com.krld Client");
        runningType = runningClient;
        this.service = service;
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game(RUNNING_CLIENT, args[0], args[1], null));
        setWidth(800);
        setHeight(600);
        app.setDisplayMode(getWidth(), getHeight(), false);
        app.start();
    }

    public Game(int runningType, String host, String port, Server server) {
        super("com.krld.core.Game!");
        //    this.host = host;
        //   this.port = port;
        setMode(runningType);
        this.server = server;
    }

    public int getRunningType() {
        return runningType;
    }

    public void setMode(int runningType) {
        this.runningType = runningType;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

      /*  FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("temp.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.gameState.getGameObjects().moveables);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  */
        if (runningType == RUNNING_CLIENT) {
            try {
                inventoryBackground = new Image("HoneyBadger/res/inventoryBackground.png");
                alphaMap = new Image("HoneyBadger/res/playerGlow.png");
                vignettingImage = new Image("HoneyBadger/res/vignetting.png");
                nightImage = new Image("HoneyBadger/res/black.png");
                nightImageOriginal = nightImage;
                vignettingFilter = new Color(1, 1, 1, 0.5f);
                brightness = 0f;
                nightImageFilter = new Color(1, 1, 1, brightness);
                vignettingImageOriginal = vignettingImage;
                gameContainer.setAlwaysRender(true);
                this.gameState = service.getGameState();
                setPlayer(service.getNewPlayer());

                m = new Music("HoneyBadger/res/MainTheme.wav");
                mLoop = new Music("HoneyBadger/res/MainThemeLoop.wav");
                m.addListener(new MusicListener() {
                    @Override
                    public void musicEnded(Music music) {
                        mLoop.loop();
                    }

                    @Override
                    public void musicSwapped(Music music, Music music2) {
                    }
                });
                m.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            viewPort = new Rectangle(getPlayer().getX() - width / 2, getPlayer().getY() - height / 2, width, height);
        } else {
            initGameState();
        }
    }

    public void initGameState() {
        setGameState(new GameState());
        Unit.setGameState(getGameState());
        getGameState().setObjects(new ArrayList<Located>());
        getGameState().setMoveables(new ArrayList<Moveable>());
        getGameState().setShells(new ArrayList<Moveable>());
        getGameState().setPlayers(new ArrayList<Player>());
        getGameState().setLights(new ArrayList<Light>());
        getGameState().setDrops(new ArrayList<Dropable>());

        getGameState().setTileMap(WorldGenerator.generateTiles());
        WorldGenerator.generateUnits(getGameState().getObjects(), getGameState().getTileMap(), getGameState().getMoveables(), getGameState());
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        setElapsedTime(getElapsedTime() + delta);

        // System.out.println("elapsedTime: " + elapsedTime);
        if (getElapsedTime() <= getDelay()) {
            return;
        }
        setElapsedTime(0);
        if (runningType == RUNNING_CLIENT) {
            try {
                gameState.setGameObjects(service.getGameState().getGameObjects());
                for (Player pl : gameState.getPlayers()) {
                    if (pl.getId() == getPlayer().getId()) {
                        setPlayer(pl);
                        break;
                    }
                }
                handleInput(container);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (runningType == RUNNING_SERVER) {
            //   Log.i("moveMoveables");
            moveMoveables();
            //            server.send();
        }
    }

    private void handleInput(GameContainer container) throws SlickException, RemoteException {
        Input input = container.getInput();
        Graphics g = container.getGraphics();
        if (input.isKeyPressed(Input.KEY_P)) {
            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target.getFlippedCopy(false, false), "screenshot.png", false);
            target.destroy();
        }
        if (input.isKeyDown(Input.KEY_W)) {
            service.move(getPlayer().getId(), MoveDirection.TOP);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            service.move(getPlayer().getId(), MoveDirection.BOT);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            service.move(getPlayer().getId(), MoveDirection.LEFT);
        }
        if (input.isKeyDown(Input.KEY_D)) {
            service.move(getPlayer().getId(), MoveDirection.RIGHT);
        }
        if (input.isKeyDown(Input.KEY_MINUS)) {
            brightness -= 0.01f;
            if (brightness < 0) {
                brightness = 0f;
            }
            nightImageFilter = new Color(1f, 1f, 1f, brightness);
        }
        if (input.isKeyDown(Input.KEY_EQUALS)) {
            brightness += 0.01f;
            if (brightness > 1) {
                brightness = 1f;
            }
            nightImageFilter = new Color(1f, 1f, 1f, brightness);
        }
        if (input.isKeyPressed(Input.KEY_F2)) {
            showInfo = !showInfo;
        }
        if (input.isKeyPressed(Input.KEY_B) || (showInventory && input.isKeyDown(Input.KEY_ESCAPE))) {
            showInventory = !showInventory;
            skipInventoryElements = 0;
            cursorPosition = 0;
            activeInventoryScope = InventoryRenderSettings.ITEMS_SCOPE;
        }
        if (showInventory && input.isKeyPressed(Input.KEY_TAB)) {
            if (activeInventoryScope == InventoryRenderSettings.ITEMS_SCOPE) {
                activeInventoryScope = InventoryRenderSettings.RECIPE_SCOPE;
                cursorPosition = 0;
            } else {
                activeInventoryScope = InventoryRenderSettings.ITEMS_SCOPE;
                cursorPosition = 0;
            }
        }
        if (showInventory && input.isKeyPressed(Input.KEY_LEFT)) {
    /*        skipInventoryElements--;
            if (skipInventoryElements < 0) {
                skipInventoryElements = 0;
            }
            */
            cursorPosition--;
            if (cursorPosition < 0) {
                cursorPosition = 0;
            }
        }
        if (showInventory && input.isKeyPressed(Input.KEY_UP)) {
    /*        skipInventoryElements--;
            if (skipInventoryElements < 0) {
                skipInventoryElements = 0;
            }
            */
            cursorPosition -= 8;
            if (cursorPosition < 0) {
                cursorPosition = 0;
            }
        }
        if (showInventory && input.isKeyPressed(Input.KEY_RIGHT)) {
            //  skipInventoryElements++;
            if (player.getInventory().getItems().size() - 1 > cursorPosition) {
                cursorPosition++;
            }
        }
        if (showInventory && input.isKeyPressed(Input.KEY_DOWN)) {
            //  skipInventoryElements++;
            cursorPosition += 8;
            if (player.getInventory().getItems().size() - 1 < cursorPosition) {
                cursorPosition = player.getInventory().getItems().size() - 1;
            }
        }
        if (showInventory && input.isKeyPressed(Input.KEY_X)) {
            if (activeInventoryScope == InventoryRenderSettings.ITEMS_SCOPE) {
                service.useItemFromInventory(getPlayer().getId(), cursorPosition);
            } else if (activeInventoryScope == InventoryRenderSettings.RECIPE_SCOPE) {
                service.useRecipe(getPlayer().getId(), cursorPosition);
            }
            ;
        }
        if (showInventory && input.isKeyPressed(Input.KEY_C)) {
            if (activeInventoryScope == InventoryRenderSettings.ITEMS_SCOPE) {
                service.setEquippedItem(getPlayer().getId(), cursorPosition);
            }
        }
        getViewPort().setX((int) (getPlayer().getX() - getWidth() / (2 * zoomFactor)));
        getViewPort().setY((int) (getPlayer().getY() - getHeight() / (2 * zoomFactor)));

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            service.castFireball(getPlayer());
        }
        if (input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON)) {
            service.dropItem(getPlayer().getId());
        }
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            /*
            try {
                System.out.println(service.sayHello());
            } catch (RemoteException e) {
                e.printStackTrace();
            }  */
        }
        if (input.isKeyPressed(Input.KEY_E)) {
            service.useAction(getPlayer().getId());
        }

        if (input.isKeyPressed(Input.KEY_O)) {
            if (m.playing() || mLoop.playing()) {
                m.stop();
                mLoop.stop();
            } else {
                m.play();
            }
        }
    }

    public void mouseWheelMoved(int change) {
        if (runningType != RUNNING_CLIENT) {
            return;
        }
        float amount = 0.1f;
        if (change > 1) {
            zoomFactor = zoomFactor + 0.05f;
        } else if (change < 1) {
            zoomFactor = zoomFactor - 0.05f;
        }
        if (zoomFactor < 0.01f) {
            zoomFactor = 0.02f;
        }
        vignettingImage = vignettingImageOriginal.getScaledCopy(1 / zoomFactor);
        nightImage = nightImageOriginal.getScaledCopy(1 / zoomFactor);
    }

    private void moveMoveables() {
        try {
            HashSet<Moveable> toRemoveMoveables = new HashSet<Moveable>();
            HashSet<Light> toRemoveLights = new HashSet<Light>();
            for (Moveable shell : getGameState().getShells()) {
                try {
                    shell.move();
                } catch (LifeTimeException e) {
                    toRemoveMoveables.add(shell);
                    toRemoveLights.add(shell.getGlow());

                    // getGameState().getMoveables().remove(shell);
                }
            }
            getGameState().getShells().removeAll(toRemoveMoveables);
            getGameState().getLights().removeAll(toRemoveLights);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Moveable moveable : getGameState().getMoveables()) {
            try {
                moveable.move();
            } catch (LifeTimeException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        if (runningType == RUNNING_SERVER) {
            return;
        }
        //  zoomFactor =
        g.scale(zoomFactor, zoomFactor);
        g.translate(-getViewPort().getX() /** zoomFactor*/, -getViewPort().getY() /** zoomFactor*/);

        int playerX = getPlayer().getX();
        int playerY = getPlayer().getY();
        g.setDrawMode(Graphics.MODE_NORMAL);
        {
            for (int x = playerX / 32 - 15; x < playerX / 32 + 15; x++) {
                for (int y = playerY / 32 - 15; y < playerY / 32 + 15; y++) {
                    int realX = x * 32 - 1;
                    int realY = y * 32 - 1;
                    // if (getPlayer().getDistanceTo(realX, realY) < getDistanceOfView()) {
                    try {
                        MyTile.draw(getGameState().getTileMap()[x][y], realX, realY, gameContainer);
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    //}
                }
            }
            for (Dropable drop : gameState.getDrops()) {
                if (playerX - 15 * 32 < drop.getX() && playerX + 15 * 32 > drop.getX() &&
                        playerY - 15 * 32 < drop.getY() && playerY + 15 * 32 > drop.getY()) {
                    drop.draw();
                }
            }
            for (Located unit : getObjects()) {
                if (playerX - 15 * 32 < unit.getX() && playerX + 15 * 32 > unit.getX() &&
                        playerY - 15 * 32 < unit.getY() && playerY + 15 * 32 > unit.getY()) {
                    unit.draw();
                  //  g.drawString(((Unit)unit).isMakeCollisions() + "", unit.getX(),unit.getY());
                }
            }
            for (Moveable unit : getGameState().getMoveables()) {
                if (playerX - 15 * 32 < unit.getX() && playerX + 15 * 32 > unit.getX() &&
                        playerY - 15 * 32 < unit.getY() && playerY + 15 * 32 > unit.getY()) {
                    unit.draw();
                }
            }
            for (Moveable shell : getGameState().getShells()) {
                if (playerX - 15 * 32 < shell.getX() && playerX + 15 * 32 > shell.getX() &&
                        playerY - 15 * 32 < shell.getY() && playerY + 15 * 32 > shell.getY()) {
                    shell.draw();
                }
            }
            for (Player player1 : gameState.getPlayers()) {
                if (!getPlayer().equals(player1)) {
                    player1.draw();
                    g.drawString("id: " + player1.getId(), player1.getX(), player1.getY() - 40);
                }
            }

            //g.drawString("Hello World! x: y: " + viewPort.getX() + " " + viewPort.getY(), 100, 100);
            // g.drawString(getPlayer().getInventory().printItems(), 100, 100);
            getPlayer().draw();
        }
        ;
        vignettingImage.draw(getViewPort().getX(), getViewPort().getY(), vignettingFilter);
        nightImage.draw(getViewPort().getX(), getViewPort().getY(), nightImageFilter);
        g.setDrawMode(Graphics.MODE_ADD);
        for (Light light : getGameState().getLights()) {
            if ((playerX - DISTANCE_OF_VIEW_IN_TILES * TILE_SIZE < light.getX() && playerX + DISTANCE_OF_VIEW_IN_TILES * TILE_SIZE > light.getX() &&
                    playerY - DISTANCE_OF_VIEW_IN_TILES * TILE_SIZE < light.getY() && playerY + DISTANCE_OF_VIEW_IN_TILES * TILE_SIZE > light.getY())) {
                light.draw();
            }
        }
        g.setDrawMode(Graphics.MODE_NORMAL);
        // TODO render inventory
        if (showInventory) {
            drawInventory(g);
        }
        g.setColor(Color.orange);
        g.drawString("HP: " + getPlayer().getHp(), viewPort.getX(), viewPort.getY() + 584);
        g.drawString("HUNGER: " + getPlayer().getHunger(), viewPort.getX() + 100, viewPort.getY() + 584);
        g.setColor(Color.white);
        // DEBUGG INFO
        if (showInfo) {
            g.drawString("id: " + getPlayer().getId(), getPlayer().getX(), getPlayer().getY() - 40);
            g.drawString("x y: " + getPlayer().getX() + " " + getPlayer().getY(), getPlayer().getX() + 40, getPlayer().getY());
            g.drawString("Tile X Y " + ((getPlayer().getX() + 16) / 32) +
                    " " + Math.round((getPlayer().getY() + 16) / 32), getPlayer().getX() + 40, getPlayer().getY() + 16);
            g.drawString("brightness " + brightness, getPlayer().getX() + 40, getPlayer().getY() + 50);
            g.drawString("inv: " + getPlayer().getInventory().getItems().toString(), getPlayer().getX() + 40, getPlayer().getY() + 60);
        }
    }

    private void drawInventory(Graphics g) {
        Player player = getPlayer();
        ArrayList<Collective> items = player.getInventory().getItems();
        // inventoryBackground.draw(viewPort.getX() + InventoryRenderSettings.X_MARGIN, viewPort.getY() + InventoryRenderSettings.Y_MARGIN);
        //фон для инвентаря
        g.setColor(Color.gray);
        g.fillRoundRect(viewPort.getX() + InventoryRenderSettings.X_MARGIN, viewPort.getY() + InventoryRenderSettings.Y_MARGIN, 282, 300, 5);
        g.setColor(Color.black);
        g.drawRoundRect(viewPort.getX() + InventoryRenderSettings.X_MARGIN, viewPort.getY() + InventoryRenderSettings.Y_MARGIN, 282, 300, 5);
        g.setColor(Color.white);
        g.drawString("Arrow keys - navigate; x - use item; c - equip item;", viewPort.getX() + 100, viewPort.getY() + 554);
        int shiftX = 0;
        int shiftY = 0;
        int i = 0;
        int drawX = 0;
        int drawY = 0;
        for (Collective item : items) {
            drawX = viewPort.getX() + InventoryRenderSettings.X_PADDING + shiftX;
            drawY = viewPort.getY() + InventoryRenderSettings.Y_PADDING + shiftY;
            if (i == cursorPosition && activeInventoryScope == InventoryRenderSettings.ITEMS_SCOPE) {
                g.setColor(Color.orange);
                g.fillRect((float) drawX - 16, (float) drawY - 16, 32f, 32f);
                g.setColor(Color.gray);
                g.fillRoundRect(viewPort.getX() + InventoryRenderSettings.X_INFO - 3, viewPort.getY() + InventoryRenderSettings.Y_INFO, 200f, 64f, 5);
                g.setColor(Color.black);
                g.drawRoundRect(viewPort.getX() + InventoryRenderSettings.X_INFO - 3, viewPort.getY() + InventoryRenderSettings.Y_INFO, 200f, 64f, 5);
                g.setColor(Color.orange);
                g.drawString(item.toString(), viewPort.getX() + InventoryRenderSettings.X_INFO, viewPort.getY() + InventoryRenderSettings.Y_INFO);

                g.setColor(Color.white);
            }
            item.draw(drawX, drawY);
            shiftX = shiftX + InventoryRenderSettings.Y_INDENT_BETWEEN_ELEMENTS;
            i++;
            if (i % 8 == 0) {
                shiftX = 0;
                shiftY = shiftY + InventoryRenderSettings.Y_INDENT_BETWEEN_ELEMENTS;
            }
        }
        //фон для рецептов
        g.setColor(Color.gray);
        g.fillRoundRect(viewPort.getX() + InventoryRenderSettings.X_RECIPES_MARGIN, viewPort.getY() + InventoryRenderSettings.Y_MARGIN, 282, 300, 5);
        g.setColor(Color.black);
        g.drawRoundRect(viewPort.getX() + InventoryRenderSettings.X_RECIPES_MARGIN, viewPort.getY() + InventoryRenderSettings.Y_MARGIN, 282, 300, 5);
        g.setColor(Color.white);

        shiftX = 0;
        shiftY = 0;
        i = 0;
        drawX = 0;
        drawY = 0;

        for (Recipe recipe : player.getRecipes()) {
            drawX = viewPort.getX() + InventoryRenderSettings.X_RECIPES_PADDING + shiftX;
            drawY = viewPort.getY() + InventoryRenderSettings.Y_PADDING + shiftY;
            if (i == cursorPosition && activeInventoryScope == InventoryRenderSettings.RECIPE_SCOPE) {
                g.setColor(Color.orange);
                g.fillRect((float) drawX - 16, (float) drawY - 16, 32f, 32f);
                g.setColor(Color.gray);
                g.fillRoundRect(viewPort.getX() + InventoryRenderSettings.X_RECIPES_INFO - 3, viewPort.getY() + InventoryRenderSettings.Y_INFO, 300f, 64f, 5);
                g.setColor(Color.black);
                g.drawRoundRect(viewPort.getX() + InventoryRenderSettings.X_RECIPES_INFO - 3, viewPort.getY() + InventoryRenderSettings.Y_INFO, 300f, 64f, 5);
                g.setColor(Color.orange);
                g.drawString(recipe.toString(), viewPort.getX() + InventoryRenderSettings.X_RECIPES_INFO, viewPort.getY() + InventoryRenderSettings.Y_INFO);
                g.setColor(Color.white);
            }
            recipe.draw(drawX, drawY);
            shiftX = shiftX + InventoryRenderSettings.Y_INDENT_BETWEEN_ELEMENTS;
            i++;
            if (i % 8 == 0) {
                shiftX = 0;
                shiftY = shiftY + InventoryRenderSettings.Y_INDENT_BETWEEN_ELEMENTS;
            }
        }

        Equip equip = player.getEquipped();
        g.setColor(Color.gray);
        g.fillRoundRect(viewPort.getX() + InventoryRenderSettings.X_EQUIP, viewPort.getY() + InventoryRenderSettings.Y_INFO, 60, 55, 5);
        g.setColor(Color.black);
        g.drawRoundRect(viewPort.getX() + InventoryRenderSettings.X_EQUIP, viewPort.getY() + InventoryRenderSettings.Y_INFO, 60, 55, 5);
        g.setColor(Color.white);
        g.drawString("Hands:", viewPort.getX() + InventoryRenderSettings.X_EQUIP + 4, viewPort.getY() + InventoryRenderSettings.Y_INFO );
        if (equip != null) {

            equip.draw(viewPort.getX() + InventoryRenderSettings.X_EQUIP + 3 + 16, viewPort.getY() + InventoryRenderSettings.Y_INFO + 18 + 16);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Rectangle getViewPort() {
        return viewPort;
    }

    public void setViewPort(Rectangle viewPort) {
        this.viewPort = viewPort;
    }

    public ArrayList<Located> getObjects() {
        return getGameState().getObjects();
    }

    public void setObjects(ArrayList<Located> objects) {
        getGameState().setObjects(objects);
    }

    public int getDistanceOfView() {
        return distanceOfView;
    }

    public void setDistanceOfView(int distanceOfView) {
        this.distanceOfView = distanceOfView;
    }

    public static int getDelay() {
        return DELAY;
    }

    public static long getTime() {
        return time;
    }

    public static void setTime(long time) {
        Game.time = time;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Game.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Game.height = height;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
