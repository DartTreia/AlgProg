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
                System.out.print("\n    ");
                for (int j = 0; j < M1.getMatrix().length; j++) {
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

    public static void printMatrix(Matrix M, Vertex G){
        if(M==null || M.size<=0){
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
        for (int i = 0; i < M.size; i++) {
            System.out.print("\n"+tmp1.getVertex()+"  ");
            tmp1=tmp1.getNext();
            for (int j = 0; j < M.size; j++) {
                System.out.print(M.matrix[i][j]+"  ");
            }
        }
        System.out.println();
    }

    public static void printMatrix(Matrix M, Vertex G,String[] exVert){
        if(M==null || M.size<=0){
            System.out.println("Matrix is empty");
            return;
        }
        System.out.print("M  ");
        Vertex tmp1=G;
        tmp1=tmp1.getNext();
        while(tmp1!=null){
            final String finStr = tmp1.getVertex();
            if(exVert==null || Arrays.stream(exVert).noneMatch(n->n.equals(finStr))) {
                System.out.print(tmp1.getVertex() + " ");
            }
            tmp1 = tmp1.getNext();
        }
        tmp1=G.getNext();
        for (int i = 0; i < M.size; i++) {
            final String finStr = tmp1.getVertex();
            if(exVert==null || Arrays.stream(exVert).noneMatch(n->n.equals(finStr))) {
                System.out.print("\n" + tmp1.getVertex() + "  ");
                Vertex tmp2=G.getNext();
                for (int j = 0; j < M.size; j++) {
                    final String finStr2 = tmp2.getVertex();
                    if(exVert==null || Arrays.stream(exVert).noneMatch(n->n.equals(finStr2)))
                    System.out.print(M.matrix[i][j] + "  ");
                    tmp2=tmp2.getNext();
                }
            }
            tmp1 = tmp1.getNext();
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
        M.size++;
        M.matrix = tmp2;
    }
    public static int[] DFSloop(Matrix M, int vert, int[] visVerts){
        if(vert>M.size)return null;
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
        for(int i=0;i<M.size;i++){
            if(M.matrix[vert-1][i]==1) k++;
        }
        if(k==0) return visVerts;
        for(int i=0;i<M.size;i++){
            if(k>0 && M.matrix[vert-1][i]==1) {
                visVerts = DFSloop(M, i+1, visVerts);
                k--;
            }
        }
        return visVerts;
    }
    public static int[] DFSMat(Matrix M, int vert){
        if(vert>M.size)return null;
        int[] visVerts=null;
        int k=0;
        for(int i=0;i<M.size;i++){
            if(M.matrix[vert-1][i]==1) k++;
        }
        if(k==0){
            visVerts=new int[1];
            visVerts[0]=vert;
            return visVerts;
        }
        Stack stack = null;
        stack = Stack.stackPush(stack,vert);
        while(stack!=null){
            Stack temp = Stack.stackPop(stack);
            stack=stack.head;
            if(visVerts==null || Arrays.stream(visVerts).noneMatch(n->n==temp.index)){
                int k2=1;
                if(visVerts!=null) k2+=visVerts.length;
                int[] tempVerts =new int[k2];
                if(visVerts!=null) tempVerts = Arrays.copyOf(visVerts, k2);
                tempVerts[k2-1]=temp.index;
                visVerts=tempVerts;
                int count=0;
                for(int i=0;i<M.size;i++){
                    if(M.matrix[temp.index-1][i]==1) count++;
                }
                if(count!=0){
                    for (int i = 0; i < M.size;i++) {
                        if(count>0 && M.matrix[temp.index-1][i]==1) {
                            int vertex = i+1;
                            if (Arrays.stream(visVerts).noneMatch(n -> n==vertex))
                                stack = Stack.stackPush(stack, vertex);
                            count--;
                        }
                    }
                }
            }
        }

        return visVerts;
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
