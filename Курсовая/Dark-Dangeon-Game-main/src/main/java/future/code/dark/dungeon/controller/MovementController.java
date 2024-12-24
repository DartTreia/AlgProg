package future.code.dark.dungeon.controller;

import future.code.dark.dungeon.domen.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static future.code.dark.dungeon.domen.DynamicObject.Direction.DOWN;
import static future.code.dark.dungeon.domen.DynamicObject.Direction.LEFT;
import static future.code.dark.dungeon.domen.DynamicObject.Direction.RIGHT;
import static future.code.dark.dungeon.domen.DynamicObject.Direction.UP;

public class MovementController extends KeyAdapter {

    private final Player player;

    public MovementController(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> player.move(UP);
            case KeyEvent.VK_DOWN -> player.move(DOWN);
            case KeyEvent.VK_LEFT -> player.move(LEFT);
            case KeyEvent.VK_RIGHT -> player.move(RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
