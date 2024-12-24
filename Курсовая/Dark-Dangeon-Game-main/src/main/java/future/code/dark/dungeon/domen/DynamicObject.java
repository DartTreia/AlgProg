package future.code.dark.dungeon.domen;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.service.GameMaster;

public abstract class DynamicObject extends GameObject {

    public DynamicObject(int xPosition, int yPosition, String imagePath) {
        super(xPosition, yPosition, imagePath);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    protected void move(Direction direction, int distance) {
        int tmpXPosition = getXPosition();
        int tmpYPosition = getYPosition();

        switch (direction) {
            case UP -> tmpYPosition -= distance;
            case DOWN -> tmpYPosition += distance;
            case LEFT -> tmpXPosition -= distance;
            case RIGHT -> tmpXPosition += distance;
        }

        if (isAllowedSurface(tmpXPosition, tmpYPosition)) {
            xPosition = tmpXPosition;
            yPosition = tmpYPosition;
        }
    }
    public Boolean enemyMoveAvailuable(int x,int y){
        return isAllowedSurface(x,y);
    }
    public Boolean collision(int x,int y){
        return this.xPosition == x && this.yPosition ==y;
    }
//если позиция не равна стене и выоду
    private Boolean isAllowedSurface(int x, int y) {
        if(GameMaster.getInstance().getMap().getMap()[y][x] == Configuration.EXIT_CHARACTER && GameMaster.getInstance().getCoins().size()!=0)
            return false;
        return GameMaster.getInstance().getMap().getMap()[y][x] != Configuration.WALL_CHARACTER;
    }

}
