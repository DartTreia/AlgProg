package future.code.dark.dungeon.service;

import future.code.dark.dungeon.config.Configuration;
import future.code.dark.dungeon.domen.Coin;
import future.code.dark.dungeon.domen.DynamicObject;
import future.code.dark.dungeon.domen.Enemy;
import future.code.dark.dungeon.domen.Exit;
import future.code.dark.dungeon.domen.GameObject;
import future.code.dark.dungeon.domen.Map;
import future.code.dark.dungeon.domen.Player;
import future.code.dark.dungeon.util.FileUtils;
import future.code.dark.dungeon.util.Vertex;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static future.code.dark.dungeon.config.Configuration.*;
import static future.code.dark.dungeon.domen.DynamicObject.Direction.*;

public class GameMaster {

    private static GameMaster instance;

    private final Map map;
    public Integer enemyCounter = 0;
    public Integer endGameCounter = 0;
    private List<GameObject> gameObjects;
    Integer score=0;
    Image victory = new ImageIcon(VICTORY).getImage();
    Image defeat = new ImageIcon(DEFEAT).getImage();
    Image start = new ImageIcon(START).getImage();
    Image quit = new ImageIcon(QUIT).getImage();
    private Vertex graph;
    private boolean menu=true;
    private long startTime;
    private long endTime;
    private long duration;

    public static synchronized GameMaster getInstance() {
        if (instance == null) {
            instance = new GameMaster();
        }
        return instance;
    }

    private GameMaster() {
        try {
            this.map = new Map(Configuration.MAP_FILE_PATH);
            this.gameObjects = initGameObjects(map.getMap());
            this.graph = Vertex.createGraph(map);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GameObject> initGameObjects(char[][] map) {
        List<GameObject> gameObjects = new ArrayList<>();
        Consumer<GameObject> addGameObject = gameObjects::add;
        Consumer<Enemy> addEnemy = enemy -> {if (ENEMIES_ACTIVE) gameObjects.add(enemy);};

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case EXIT_CHARACTER -> addGameObject.accept(new Exit(j, i));
                    case COIN_CHARACTER -> addGameObject.accept(new Coin(j, i));
                    case ENEMY_CHARACTER -> addEnemy.accept(new Enemy(j, i));
                    case PLAYER_CHARACTER -> addGameObject.accept(new Player(j, i));
                }
            }
        }

        return gameObjects;
    }

    public void renderFrame(Graphics graphics) {
        if(menu){
            graphics.fillRect(0,0, this.getMap().getWidth() * SPRITE_SIZE, this.getMap().getHeight() * SPRITE_SIZE);
            graphics.drawImage(start,
                            this.getMap().getWidth() * SPRITE_SIZE/3,
                            this.getMap().getHeight() * SPRITE_SIZE/5,
                            this.getMap().getWidth()*SPRITE_SIZE/3,
                            this.getMap().getHeight()*SPRITE_SIZE/7,
                            null);
            graphics.drawImage(quit,
                            this.getMap().getWidth() * SPRITE_SIZE/3,
                            this.getMap().getHeight() * SPRITE_SIZE/2,
                            this.getMap().getWidth()*SPRITE_SIZE/3,
                            this.getMap().getHeight()*SPRITE_SIZE/7,
                            null);

            startTime = System.currentTimeMillis();
        }
        else if(getPlayer().dead){
            endTime=System.currentTimeMillis();
            if(duration == 0) duration = endTime - startTime;

            graphics.fillRect(0,0, this.getMap().getWidth() * SPRITE_SIZE, this.getMap().getHeight() * SPRITE_SIZE);
            graphics.drawImage(defeat,this.getMap().getWidth() * SPRITE_SIZE/6,0,null);

            endGameCounter++;
            if(endGameCounter == 120){
                try {
                    FileUtils.writeFile(RESULT_FILE_PATH,(int)duration/1000,false);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                duration=0;

                menu=true;
                getPlayer().dead=false;
                Player pl = getPlayer();
                for (int i = 0; i < map.getHeight(); i++) {
                    for (int j = 0; j < map.getWidth(); j++) {
                        switch (map.getMap()[i][j]) {
                            case PLAYER_CHARACTER -> {
                                pl.setXPosition(j);
                                pl.setYPosition(i);
                            }
                        }
                    }
                }
                this.gameObjects = initGameObjects(map.getMap());
                this.graph = Vertex.createGraph(map);
                setPlayer(pl);
                endGameCounter = 0;
            }
        }
        else if(!getPlayer().exited) {
            getMap().render(graphics);
            getStaticObjects().forEach(gameObject -> gameObject.render(graphics));
            getEnemies().forEach(gameObject -> gameObject.render(graphics));
            getPlayer().render(graphics);
            graphics.setColor(Color.WHITE);
            graphics.drawString(getPlayer().toString(), 10, 20);
            graphics.drawString("Collected coins: " + score.toString(), 120, 20);
            graphics.drawString("Coins lost: " + getCoins().size(), 240, 20);
            enemyCounter++;
            if(enemyCounter==ENEMY_SPEED){
                for(Enemy enem: getEnemies()) {
                    Vertex way = graph.findNextWay(this.getPlayer(),enem, getInstance().getEnemies());

                    if(way==null && enem.collision(getPlayer().getXPosition(), getPlayer().getYPosition())){
                        getPlayer().dead = true;
                        break;
                    }
                    if(way!=null) {
                        if (enem.getXPosition() < way.getX()) enem.move(RIGHT);
                        else if (enem.getXPosition() > way.getX()) enem.move(LEFT);
                        else if (enem.getYPosition() < way.getY()) enem.move(DOWN);
                        else enem.move(UP);
                    }
                }
                enemyCounter=0;
            }
        }
        else{
            endTime=System.currentTimeMillis();
            if(duration == 0) duration = endTime - startTime;

            graphics.fillRect(0,0, this.getMap().getWidth() * SPRITE_SIZE, this.getMap().getHeight() * SPRITE_SIZE);
            graphics.drawImage(victory,this.getMap().getWidth() * SPRITE_SIZE/6,0,null);

            endGameCounter++;
            if(endGameCounter == 120){
                try {
                    FileUtils.writeFile(RESULT_FILE_PATH,duration/1000,true);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                duration=0;
                menu=true;
                getPlayer().exited=false;
                Player pl = getPlayer();
                for (int i = 0; i < map.getHeight(); i++) {
                    for (int j = 0; j < map.getWidth(); j++) {
                        switch (map.getMap()[i][j]) {
                            case PLAYER_CHARACTER -> {
                                pl.setXPosition(j);
                                pl.setYPosition(i);
                            }
                        }
                    }
                }
                this.gameObjects = initGameObjects(map.getMap());
                this.graph = Vertex.createGraph(map);
                setPlayer(pl);
                endGameCounter = 0;
            }
        }
    }

    public Player getPlayer() {
        return (Player) gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Player)
                .findFirst()
                .orElseThrow();
    }
    private void setPlayer(Player player){
        gameObjects.removeIf((gameObject -> gameObject instanceof Player));
        gameObjects.add(player);
    }
    private List<GameObject> getStaticObjects() {
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof DynamicObject))
                .collect(Collectors.toList());
    }

    public List<Enemy> getEnemies() {
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof Enemy)
                .map(gameObject -> (Enemy) gameObject)
                .collect(Collectors.toList());
    }
    public List<Coin>  getCoins(){
        return gameObjects.stream()
                .filter(gameObject ->gameObject instanceof Coin)
                .map(gameObject -> (Coin) gameObject)
                .collect(Collectors.toList());
    }

    public List<Exit> getExit(){
        return gameObjects.stream()
                .filter(gameObject -> gameObject instanceof  Exit)
                .map(gameObject -> (Exit) gameObject)
                .collect(Collectors.toList());
    }
    public void deleteCoin(int x, int y){
        this.gameObjects.removeIf(coin-> coin instanceof Coin && coin.getXPosition() == x && coin.getYPosition() == y);
        score++;
    }
    public Map getMap() {
        return map;
    }
    public void setMenu(boolean menu) {
        this.menu = menu;
    }
    public boolean getMenu(){return menu;}

}
