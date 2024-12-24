package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public class Player extends DynamicObject {
    private static final int stepSize = 1;
    public boolean exited=false;
    public boolean dead=false;

    public Player(int xPosition, int yPosition) {
        super(xPosition, yPosition, Configuration.PLAYER_SPRITE);
    }

    public void move(Direction direction) {
        super.move(direction, stepSize);
        if(GameMaster.getInstance().getCoins().stream()
                .anyMatch((coin -> collision(coin.xPosition,coin.yPosition)))){
            GameMaster.getInstance().deleteCoin(this.xPosition,this.yPosition);
        }
        if(GameMaster.getInstance().getExit().stream()
             .anyMatch((exit -> collision(exit.xPosition,exit.yPosition)))){
            exited = true;
        }
        if(GameMaster.getInstance().getEnemies().stream()
                .anyMatch(enemy -> collision(enemy.getXPosition(), enemy.getYPosition()))){
            dead = true;
        }
    }


    @Override
    public String toString() {
        return "Player{[" + xPosition + ":" + yPosition + "]}";
    }
}
