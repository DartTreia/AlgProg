import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Matrix {
    private int[][] matrix;
    private int size;
    public Matrix(int size){
        this.size = size;
        matrix = new int[size][size];
        createMatrix();
    }

    public void createMatrix(){
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = i; j < this.matrix.length; j++) {
                this.matrix[i][j] = (int)(Math.random()*2);
                this.matrix[j][i] = this.matrix[i][j];
                if(j==i) this.matrix[i][i]=0;
            }
        }
    }

    public void printMatrix(Vertex G){
        if(this.size<=0){
            System.out.println("Matrix is empty");
            return;
        }
        System.out.print("M  ");
        Vertex tmp1=G;
        tmp1=tmp1.getNext();
        while(tmp1!=null){
            System.out.print(tmp1.getVertex()+" ");
            tmp1=tmp1.getNext();
        }
        tmp1=G;
        tmp1=tmp1.getNext();
        for (int i = 0; i < this.size; i++) {
            System.out.print("\n"+tmp1.getVertex()+"  ");
            tmp1=tmp1.getNext();
            for (int j = 0; j < this.size; j++) {
                System.out.print(this.matrix[i][j]+"  ");
            }
        }
        System.out.println();
    }

    public void deleteVertInMx(int value){
        int[][] tmp = new int[this.getMatrix().length-1][this.getMatrix()[0].length-1];
        int x=0;
        for (int i = 0; i < this.getMatrix().length; i++) {
            if(i!=value-1) {
                int y=0;
                for (int j = 0; j < this.getMatrix()[0].length; j++) {
                    if (j != value - 1) {
                        tmp[x][y] = this.getMatrix()[i][j];
                        y++;
                    }
                }
                x++;
            }
        }
        this.size--;
        this.matrix=tmp;
    }

    public void addVertInMx(Vertex G){
        Vertex tmp=G;
        while(tmp.getNext()!=null){
            tmp=tmp.getNext();
        }

        int k=1;
        if(this.size!=0)
            k=1+this.size; // размер массива смежных вершин
        int[][] tmp2 = new int[k][k];
        if(this.size!=0) {
            Vertex nxt = G;
            nxt=nxt.getNext();
            for (int i = 0; i < k; i++) {
                final Vertex forCheck = nxt;
                if(i!=k-1) {
                    for (int j = 0; j < k; j++) {
                        if (j != k - 1) {
                            tmp2[i][j] = this.getMatrix()[i][j];
                        } else {
                            if(tmp.getAdjVertexes()!=null && forCheck!=null)
                            tmp2[i][j] = Arrays.stream(tmp.getAdjVertexes()).anyMatch(n -> n.getVertex().equals(forCheck.getVertex())) ? 1 : 0;
                            else tmp2[i][j]=0;
                        }
                    }
                }
                else{
                    Vertex nxt2 = G;
                    nxt2=nxt2.getNext();
                    for (int j = 0; j < k; j++) {
                        final Vertex forCheck2 = nxt2;
                        if(tmp.getAdjVertexes()!=null && forCheck2!=null)
                        tmp2[i][j] = Arrays.stream(tmp.getAdjVertexes()).anyMatch(n -> n.getVertex().equals(forCheck2.getVertex())) ? 1 : 0;
                        else tmp2[i][j]=0;
                        nxt2=nxt2.getNext();
                    }
                }
                nxt = nxt.getNext();
            }
        }
        this.size++;
        this.matrix = tmp2;
    }
    public int[] DFSloop(int vert, int[] visVerts){
        if(vert>this.size)return null;
        if(visVerts!=null && Arrays.stream(visVerts).anyMatch(n->n==vert)) {
            return visVerts;
        }else{
            int k=1;
            if(visVerts!=null) k+=visVerts.length;
            int[] temp = new int[k];
            if(visVerts!=null) temp=Arrays.copyOf(visVerts, k);
            temp[k-1]=vert;
            visVerts=temp;
        }
        int k=0;
        for(int i=0;i<this.size;i++){
            if(this.matrix[vert-1][i]==1) k++;
        }
        if(k==0) return visVerts;
        for(int i=0;i<this.size;i++){
            if(k>0 && this.matrix[vert-1][i]==1) {
                visVerts = this.DFSloop(i+1, visVerts);
                k--;
            }
        }
        return visVerts;
    }
    public int[] DFSMat(int vert){
        if(vert>this.size)return null;
        int[] visVerts=null;
        int k=0;
        for(int i=0;i<this.size;i++){
            if(this.matrix[vert-1][i]==1) k++;
        }
        if(k==0){
            visVerts=new int[1];
            visVerts[0]=vert;
            return visVerts;
        }
        Stack stack = new Stack(0,null);
        stack = stack.stackPush(vert);
        while(stack!=null){
            Stack temp = stack.stackPop();
            stack=stack.head;
            if(visVerts==null || Arrays.stream(visVerts).noneMatch(n->n==temp.index)){
                int k2=1;
                if(visVerts!=null) k2+=visVerts.length;
                int[] tempVerts =new int[k2];
                if(visVerts!=null) tempVerts = Arrays.copyOf(visVerts, k2);
                tempVerts[k2-1]=temp.index;
                visVerts=tempVerts;
                int count=0;
                for(int i=0;i<this.size;i++){
                    if(this.matrix[temp.index-1][i]==1) count++;
                }
                if(count!=0){
                    for (int i = 0; i < this.size;i++) {
                        if(count>0 && this.matrix[temp.index-1][i]==1) {
                            int vertex = i+1;
                            if (Arrays.stream(visVerts).noneMatch(n -> n==vertex)){
                                if(stack==null) stack = new Stack(0,null);
                                stack = stack.stackPush(vertex);
                            }

                            count--;
                        }
                    }
                }
            }
        }

        return visVerts;
    }
    public int[] BFSMatWithLibQueue(int vert){
        if(vert>this.size)return null;

        int[] visVerts=null;
        int k=0;

        for(int i=0;i<this.size;i++){
            if(this.matrix[vert-1][i]==1) k++;
        }
        if(k==0){
            visVerts=new int[1];
            visVerts[0]=vert;
            return visVerts;
        }
        Queue<Integer> libQueue = new LinkedList<Integer>();
        libQueue.add(vert);
        visVerts=new int[1];
        visVerts[0]=vert;

        while(!libQueue.isEmpty()){
           int newVert = libQueue.poll();
            for(int i=0;i<this.size;i++){
                final int vertex = i+1;
                if(this.matrix[newVert-1][i]==1 && Arrays.stream(visVerts).noneMatch(n->n==vertex)){
                    libQueue.add(vertex);
                    int[] tempVerts =new int[visVerts.length+1];
                    tempVerts = Arrays.copyOf(visVerts, visVerts.length+1);
                    tempVerts[visVerts.length]=vertex;
                    visVerts=tempVerts;
                }
            }
        }
        return visVerts;
    }
    public int[] DISTMatWithLibQueue(int vert){
        int[] dists = new int[this.size];
        Arrays.fill(dists, 0);

        if(vert>this.size)return dists;

        int[] visVerts=null;
        int k=0;

        for(int i=0;i<this.size;i++){
            if(this.matrix[vert-1][i]==1) k++;
        }
        if(k==0) return dists;
        Queue<Integer> libQueue = new LinkedList<Integer>();
        libQueue.add(vert);
        visVerts=new int[1];
        visVerts[0]=vert;

        while(!libQueue.isEmpty()){
            int newVert = libQueue.poll();
            for(int i=0;i<this.size;i++){
                final int vertex = i+1;
                if(this.matrix[newVert-1][i]==1 && Arrays.stream(visVerts).noneMatch(n->n==vertex)){
                    libQueue.add(vertex);
                    int[] tempVerts =new int[visVerts.length+1];
                    tempVerts = Arrays.copyOf(visVerts, visVerts.length+1);
                    tempVerts[visVerts.length]=vertex;
                    visVerts=tempVerts;
                    dists[i]=dists[newVert-1]+1;
                }
            }
        }
        return dists;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int[][] getMatrix(){
        return matrix;
    }
    public void setMatrix(int[][] matrix){
        this.matrix = matrix;
    }
}
