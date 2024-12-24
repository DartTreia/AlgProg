package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enemy extends DynamicObject {
    public static final int stepSize = 1;
    public Enemy(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.GHOST_SPRITE);
    }
    public void move(Direction direction){
        super.move(direction, stepSize);
        Player player = GameMaster.getInstance().getPlayer();
        if(collision(player.getXPosition(), player.getYPosition())){
            player.dead = true;
        }
    }
    public Boolean moveAvailuable(int x, int y){
        return super.enemyMoveAvailuable(x,y);
    }
}
