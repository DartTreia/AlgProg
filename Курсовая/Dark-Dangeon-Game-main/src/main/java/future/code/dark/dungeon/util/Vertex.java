package future.code.dark.dungeon.util;

import future.code.dark.dungeon.domen.Enemy;
import future.code.dark.dungeon.domen.Map;
import future.code.dark.dungeon.domen.Player;

import java.util.*;

import static future.code.dark.dungeon.config.Configuration.*;

public class Vertex {
    private int x;
    private int y;
    private Vertex next;
    private Vertex[] adjVerts;
    private int distId;
    private Vertex[] shortPath;

    public Vertex(int x,int y, Vertex[] adjVerts, int distId) {
        this.x = x;
        this.y = y;
        this.adjVerts = adjVerts;
        this.distId = distId;
    }

    public static Vertex createGraph(Map map){
        char[][] array = map.getMap();

        int k=0;
        for(int i = 1; i < array.length; i++){
            for(int j = 1; j < array[i].length; j++){
                if(array[i][j] != WALL_CHARACTER) k++;
            }
        }

        Vertex[] verts = new Vertex[k];
        k=0;

        for(int i=1;i<array.length;i++){
            for(int j=1;j<array[i].length;j++){
                if(array[i][j] != WALL_CHARACTER){
                    verts[k] = new Vertex(j,i,null,k);
                    k++;
                }
            }
        }

        for(int i=0;i<verts.length;i++){
            if(i!=verts.length-1)
                verts[i].next = verts[i+1];
        }

        for(int i=0;i<verts.length;i++) {
            Vertex vert = verts[i];
            Vertex[] adj = new Vertex[4];
            k=0;
            int x=vert.x;
            int y=vert.y;

            if(array[y][x-1] != WALL_CHARACTER) {
                Vertex temp = Arrays.stream(verts).filter(n->n.y==y && n.x==x-1).findFirst().get();
                adj[k] = temp;
                k++;
            }
            if(array[y-1][x] != WALL_CHARACTER) {
                Vertex temp = Arrays.stream(verts).filter(n->n.y==y-1 && n.x==x).findFirst().get();
                adj[k] = temp;
                k++;
            }
            if(array[y][x+1] != WALL_CHARACTER) {
                Vertex temp = Arrays.stream(verts).filter(n->n.y==y && n.x==x+1).findFirst().get();
                adj[k] = temp;
                k++;
            }
            if(array[y+1][x] != WALL_CHARACTER) {
                Vertex temp = Arrays.stream(verts).filter(n->n.y==y+1 && n.x==x).findFirst().get();
                adj[k] = temp;
                k++;
            }

            vert.adjVerts = Arrays.copyOf(adj, k);
        }
        return verts[0];
    }

    public Vertex findNextWay(Player player, Enemy enemy, List<Enemy> otherEnemies){
        Vertex tmp = this;

        int k=0;
        while(tmp!=null){
            tmp.shortPath=null;
            k++;
            tmp = tmp.next;
        }

        tmp = this;
        int[] dists=new int[k];
        Arrays.fill(dists,-1);

        tmp = this;
        while(tmp!=null){
            if(tmp.x==enemy.getXPosition() && tmp.y==enemy.getYPosition())
                break;
            tmp=tmp.next;
        }
        if(player.dead) return tmp;
        Vertex[] visVerts=null;

        Queue<Vertex> libQueue = new LinkedList<Vertex>();

        libQueue.add(tmp);
        visVerts=new Vertex[1];
        visVerts[0]=tmp;

        while(!libQueue.isEmpty()){
            Vertex newVert = libQueue.poll();
            if(newVert.adjVerts!=null) {
                for (int i = 0; i < newVert.adjVerts.length; i++) {
                    final Vertex vertex = newVert.adjVerts[i];
                    if (Arrays.stream(visVerts).noneMatch(n -> n == vertex) && otherEnemies.stream().noneMatch(n->n.collision(vertex.x, vertex.y))) {
                        libQueue.add(vertex);
                        Vertex[] tempVerts = new Vertex[visVerts.length + 1];
                        tempVerts = Arrays.copyOf(visVerts, visVerts.length + 1);
                        tempVerts[visVerts.length] = vertex;
                        visVerts = tempVerts;
                        dists[vertex.distId]=dists[newVert.distId]+1;
                        Vertex[] shortPath;
                        if(newVert.shortPath!=null)
                            shortPath = Arrays.copyOf(newVert.shortPath, newVert.shortPath.length+1);
                        else shortPath = new Vertex[1];
                        shortPath[shortPath.length-1] = vertex;
                        vertex.shortPath = shortPath;
                    }
                }
            }
        }

        tmp = this;
        while(tmp!=null){
            if(tmp.x==player.getXPosition() && tmp.y==player.getYPosition())
                break;
            tmp=tmp.next;
        }
        if(tmp.shortPath==null) return null;
        return tmp.shortPath[0];
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
}

