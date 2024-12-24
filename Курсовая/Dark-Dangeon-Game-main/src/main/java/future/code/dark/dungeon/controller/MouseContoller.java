package future.code.dark.dungeon.controller;

import future.code.dark.dungeon.service.GameMaster;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static future.code.dark.dungeon.config.Configuration.SPRITE_SIZE;

public class MouseContoller implements MouseListener {

    private final GameMaster gameMaster;

    public MouseContoller(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.gameMaster.getMenu()) {
            if (e.getX() >= this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 &&
                    e.getY() >= this.gameMaster.getMap().getHeight() * SPRITE_SIZE / 5 &&
                    e.getX() <= this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 + this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 &&
                    e.getY() <= this.gameMaster.getMap().getHeight() * SPRITE_SIZE / 7 + this.gameMaster.getMap().getHeight() * SPRITE_SIZE / 5) {
                this.gameMaster.setMenu(false);
            } else if (e.getX() >= this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 &&
                    e.getY() >= this.gameMaster.getMap().getHeight() * SPRITE_SIZE / 2 &&
                    e.getX() <= this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 + this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 3 &&
                    e.getY() <= this.gameMaster.getMap().getHeight() * SPRITE_SIZE / 7 + this.gameMaster.getMap().getWidth() * SPRITE_SIZE / 2) {
                System.exit(1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
