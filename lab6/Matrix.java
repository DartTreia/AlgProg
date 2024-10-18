import java.util.Arrays;

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
    public static void printMatrixes(Matrix M1, Matrix M2,Vertex G1, Vertex G2){
        if((M1==null && M2==null) || (M1.getMatrix().length==0 && M2.getMatrix().length==0)){
            System.out.println("Matrixes are empty");
            return;
        }
        System.out.print("M1 ");
        Vertex tmp1=G1;
        tmp1=tmp1.getNext();
        while(tmp1!=null){
            System.out.print(tmp1.getVertex()+" ");
            tmp1=tmp1.getNext();
        }
        System.out.print("   M2 ");
        tmp1=G2;
        tmp1=tmp1.getNext();
        while(tmp1!=null){
            System.out.print(tmp1.getVertex()+" ");
            tmp1=tmp1.getNext();
        }
        tmp1=G1;
        tmp1=tmp1.getNext();
        Vertex tmp2 =G2;
        tmp2=tmp2.getNext();
        for (int i = 0; i < Math.max(M1.getMatrix().length,M2.getMatrix().length); i++) {
            if(i<M1.getMatrix().length){
                System.out.print("\n"+tmp1.getVertex()+"  ");
                tmp1=tmp1.getNext();
                for (int j = 0; j < M1.getMatrix().length; j++) {
                    System.out.print(M1.getMatrix()[i][j]+"  ");
                }
            }
            else{
                System.out.print("\n ");
                for (int j = 0; j < M2.getMatrix().length; j++) {
                    System.out.print("   ");
                }
            }
            if(i<M2.getMatrix().length) {
                System.out.print("  "+tmp2.getVertex()+"  ");
                tmp2=tmp2.getNext();
                for (int j = 0; j < M2.getMatrix().length; j++) {
                    System.out.print(M2.getMatrix()[i][j] + "  ");
                }
            }
        }
        System.out.println();
    }
    public static void deleteVertInMx(Matrix M,int value){
        int[][] tmp = new int[M.getMatrix().length-1][M.getMatrix()[0].length-1];
        int x=0;
        for (int i = 0; i < M.getMatrix().length; i++) {
            if(i!=value-1) {
                int y=0;
                for (int j = 0; j < M.getMatrix()[0].length; j++) {
                    if (j != value - 1) {
                        tmp[x][y] = M.getMatrix()[i][j];
                        y++;
                    }
                }
                x++;
            }
        }
        M.size--;
        M.setMatrix(tmp);
    }

    public static void addVertInMx(Matrix M, Vertex G){
        Vertex tmp=G;
        while(tmp.getNext()!=null){
            tmp=tmp.getNext();
        }

        int k=1;
        if(M.size!=0)
            k=1+M.size; // размер массива смежных вершин
        int[][] tmp2 = new int[k][k];
        if(M.size!=0) {
            Vertex nxt = G;
            nxt=nxt.getNext();
            for (int i = 0; i < k; i++) {
                final Vertex forCheck = nxt;
                if(i!=k-1) {
                    for (int j = 0; j < k; j++) {
                        if (j != k - 1) {
                            tmp2[i][j] = M.getMatrix()[i][j];
                        } else {
                            tmp2[i][j] = Arrays.stream(tmp.getAdjVertexes()).anyMatch(n -> n.getVertex().equals(forCheck.getVertex())) ? 1 : 0;
                        }
                    }
                }
                else{
                    Vertex nxt2 = G;
                    nxt2=nxt2.getNext();
                    for (int j = 0; j < k; j++) {
                        final Vertex forCheck2 = nxt2;
                        tmp2[i][j] = Arrays.stream(tmp.getAdjVertexes()).anyMatch(n -> n.getVertex().equals(forCheck2.getVertex())) ? 1 : 0;
                        nxt2=nxt2.getNext();
                    }
                }
                nxt = nxt.getNext();
            }
        }
        M.setMatrix(tmp2);
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
