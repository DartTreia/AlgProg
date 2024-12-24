import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Matrix {
    private int[][] matrix;
    private int size;
    private boolean graphWeight;
    private boolean graphDirection;
    private int[] eccentricities;

    public Matrix(int size, boolean graphWeight, boolean graphDirection) {
        this.size = size;
        this.matrix = new int[size][size];
        this.graphWeight = graphWeight;
        this.graphDirection = graphDirection;
        eccentricities = new int[size];
        if(!graphDirection && !graphWeight) createNoDirectNoWeightMatrix();
        else if(graphDirection && !graphWeight) createDirectNoWeightMatrix();
        else if(!graphDirection && graphWeight) createNoDirectWeightMatrix();
        else createDirectWeightMatrix();
    }
    public void createNoDirectNoWeightMatrix(){
        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                this.matrix[i][j] = (int)(Math.random()*2);
                this.matrix[j][i] = this.matrix[i][j];

                if(j==i) this.matrix[i][i]=0;
            }
        }
    }

    public void createDirectNoWeightMatrix(){
        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                this.matrix[i][j] = (int)(Math.random()*2);
                this.matrix[j][i] = this.matrix[i][j]*(-1);

                if(j==i) this.matrix[i][i]=0;
            }
        }
    }

    public void createNoDirectWeightMatrix(){
        for (int i = 0; i < this.size; i++) {
            for (int j = i; j < this.size; j++) {
                this.matrix[i][j] = (int)(Math.random()*6);
                this.matrix[j][i] = this.matrix[i][j];

                if(j==i) this.matrix[i][i]=0;
            }
        }
    }

    public void createDirectWeightMatrix(){
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {

                if((int)(Math.random()*2)==1 && this.matrix[j][i]==0){
                    this.matrix[i][j] = (int)(Math.random()*6);
                }
                else this.matrix[i][j] = 0;

                if(j==i) this.matrix[i][i]=0;
            }
        }
    }
    public void printMatrix(){
        if(this.size<=0){
            System.out.println("Matrix is empty");
            return;
        }
        System.out.print("M  ");
        for(int i=0;i<this.size;i++){
            System.out.print("x"+(i+1)+" ");
        }
        for (int i = 0; i < this.size; i++) {
            System.out.print("\n"+"x"+(i+1)+"  ");
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.matrix[i][j]+"  ");
            }
        }
        System.out.println();
    }

    public int[] findDists(int vert){
        int[] dists = new int[this.size];
        Arrays.fill(dists, Integer.MAX_VALUE);

        if(vert>this.size)return dists;

        int k=0;

        for(int i=0;i<this.size;i++){
            if(this.matrix[vert-1][i]!=0) k++;
        }

        if(k==0) return dists;

        Queue<Integer> libQueue = new LinkedList<Integer>();
        libQueue.add(vert);

        while(!libQueue.isEmpty()){
            int newVert = libQueue.poll();
            for(int i=0;i<this.size;i++){
                final int vertex = i+1;
                if(this.matrix[newVert-1][i]>0 && dists[i]>dists[newVert-1]+this.matrix[newVert-1][i]){
                    libQueue.add(vertex);
                    dists[i]=dists[newVert-1]+this.matrix[newVert-1][i];
                }
            }
        }
        return dists;
    }

    public void findEccentricity(){
        for(int i=0;i<this.size;i++){
            this.eccentricities[i]=Arrays.stream(findDists(i+1)).max().getAsInt();
        }
    }

    public int findDiameter(){
        return Arrays.stream(this.eccentricities).max().getAsInt();
    }

    public int findRadius(){
        return Arrays.stream(this.eccentricities).filter(n->n>0).min().getAsInt();
    }

    public int[] findPeripheryVertex(){
        int d = findDiameter();
        int k=0;

        int[] peripheryVertexes = new int[this.size];
        Arrays.fill(peripheryVertexes, 0);

        for(int i=0;i<this.size;i++){
            if(d==this.eccentricities[i]){
                peripheryVertexes[k]=i+1;
                k++;
            }
        }
        return peripheryVertexes;
    }

    public int[] findCentralVertex(){
        int r = findRadius();
        int k=0;

        int[] centralVertexes = new int[this.size];
        Arrays.fill(centralVertexes, 0);

        for(int i=0;i<this.size;i++){
            if(r==this.eccentricities[i]){
                centralVertexes[k]=i+1;
                k++;
            }
        }
        return centralVertexes;
    }
    public void writeDists(){
        for(int i=0;i<this.size;i++){
            System.out.println(i+1+": "+Arrays.toString(findDists(i+1)));
        }
    }
    public int[] getEccentricities() {return eccentricities;}
    public boolean getGraphWeight(){return graphWeight;}
    public boolean getGraphDirection(){return graphDirection;}
    public int getSize(){return size;}
    public void setSize(int size){this.size = size;}
    public int[][] getMatrix(){return matrix;}
    public void setMatrix(int[][] matrix){this.matrix = matrix;}
}
