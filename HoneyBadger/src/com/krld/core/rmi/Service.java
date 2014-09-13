package com.krld.core.rmi;

import com.krld.model.container.GameState;
import com.krld.common.MoveDirection;
import com.krld.model.character.Player;
import com.krld.model.container.WebContainer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 25.02.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public interface Service extends Remote {
    String sayHello() throws RemoteException;

    GameState getGameState() throws RemoteException;

    WebContainer getGameTiles(long id) throws RemoteException;

    WebContainer getWebGameState(String email) throws RemoteException;

    Player getNewPlayer() throws RemoteException;

    //  public void castFireball(Player player) throws RemoteException;

    void move(long id, MoveDirection moveDirection) throws RemoteException;

    WebContainer webMove(long id, MoveDirection moveDirection) throws RemoteException;

    void castFireball(Player player) throws RemoteException;

    void useAction(long id) throws RemoteException;

    void dropItem(long id, int cursorPosition) throws RemoteException;

    void useItemFromInventory(long id, int cursorPosition) throws RemoteException;

    void useRecipe(long id, int cursorPosition) throws RemoteException;

    void setEquippedItem(long id, int cursorPosition) throws RemoteException;
}
