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
    public static Matrix mergeMatrixes(Matrix M1,Matrix M2,Vertex G1,Vertex G2){
        Vertex temp1=G1.getNext();
        Vertex temp2=G2.getNext();
        Vertex newGraph = new Vertex("start",null,null,null);
        String[] uniqVert= new String[M1.size+M2.size];
        Arrays.fill(uniqVert,"");
        int uniqVerts=0;
        for(int i=0;i<M1.size;i++){
            final Vertex temp = temp1;
            if(Arrays.stream(uniqVert).noneMatch(n->n.equals(temp.getVertex()))){
                uniqVert[uniqVerts]=temp1.getVertex();
                Vertex.addVertex(newGraph,temp.getVertex(), (Vertex[]) null);
                uniqVerts++;
            }
            temp1=temp1.getNext();
        }
        for(int i=0;i<M2.size;i++){
            final Vertex temp = temp2;
            if(Arrays.stream(uniqVert).noneMatch(n->n.equals(temp.getVertex()))){
                uniqVert[uniqVerts]=temp2.getVertex();
                Vertex.addVertex(newGraph,temp.getVertex(), (Vertex[]) null);
                uniqVerts++;
            }
            temp2=temp2.getNext();
        }

        int[][] newM= new int[uniqVerts][uniqVerts];
        for(int i=0;i<uniqVerts;i++){
            Arrays.fill(newM[i],-1);
        }
        temp1=G1.getNext();

        for(int i=0;i<M1.size;i++){
            temp2=G2.getNext();
            for (int j = 0; j < M2.size; j++) {
                if(temp1.getVertex().equals(temp2.getVertex())){
                    int k=Vertex.findId(newGraph,temp1.getVertex());
                    Vertex tempGr = newGraph.getNext();
                    int l=0;
                    while(tempGr!=null){
                        int y1=Vertex.findId(G1,tempGr.getVertex());
                        int y2=Vertex.findId(G2,tempGr.getVertex());
                        if(y1!=-1 && y2!=-1)
                            newM[k][l]=M1.matrix[i][y1] | M2.matrix[j][y2];
                        else if(y1!=-1)
                            newM[k][l]=M1.matrix[i][y1];
                        else if(y2!=-1)
                            newM[k][l]=M2.matrix[j][y2];

                        l++;
                        tempGr=tempGr.getNext();
                    }
                    break;
                }
                else if(j==M2.size-1){
                    int k=Vertex.findId(newGraph,temp1.getVertex());
                    Vertex tempGr = newGraph.getNext();
                    int l=0;
                    while(tempGr!=null){
                        int y1=Vertex.findId(G1,tempGr.getVertex());
                        if(y1!=-1)
                            newM[k][l]=M1.matrix[i][y1];
                        else
                            newM[k][l]=0;
                        l++;
                        tempGr=tempGr.getNext();
                    }
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }
        temp2=G2.getNext();
        int k=0;
        int start=0;
        for(int j=0;j<newM.length;j++){
            if(newM[j][0]==-1){
                k++;
                if(start==0) start=j;
            }
        }
        for(int i=0;i<M2.size;i++){
            if(newM[Vertex.findId(newGraph,temp2.getVertex())][0]!=-1){
                temp2=temp2.getNext();
                continue;
            }
            Vertex temp = G2.getNext();
            for(int j=0;j<newM.length;j++){
                int x;
                if(temp!=null)
                    x = Vertex.findId(G2,temp.getVertex());
                else x=-1;
                if(x!=-1){
                    newM[start][j]=M2.matrix[i][x];
                }
                else{
                    newM[start][j]=0;
                }
                if(temp!=null)
                    temp=temp.getNext();
            }
            start++;
            temp2=temp2.getNext();
        }
        Matrix Mres = new Matrix(newM.length);
        Mres.matrix=newM;
        temp1=newGraph.getNext();
        for(int i = 0; i < Mres.size; i++){
            temp2=newGraph.getNext();
            for(int j = 0; j < Mres.size; j++){
                if(Mres.matrix[i][j]==1){
                    k=1;
                    if(temp1.getAdjVertexes()!=null)
                        k=1+temp1.getAdjVertexes().length;
                    Vertex[] tmp = new Vertex[k];
                    if(temp1.getAdjVertexes()!=null)
                        tmp= Arrays.copyOf(temp1.getAdjVertexes(), k);
                    tmp[tmp.length-1]=temp2;
                    temp1.setAdjVertexes(tmp);
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }
        printMatrix(Mres,newGraph);
        Vertex.printGraph(newGraph);
        return Mres;
    }
    public static Matrix intersectionMatrixes(Matrix M1,Matrix M2,Vertex G1,Vertex G2){
        Vertex temp1=G1.getNext();
        Vertex temp2=G2.getNext();
        Vertex newGraph = new Vertex("start",null,null,null);
        String[] uniqVert= new String[M1.size+M2.size];
        Arrays.fill(uniqVert,"");
        int uniqVerts=0;
        for(int i=0;i<M1.size;i++){
            temp2=G2.getNext();
            for(int j=0;j<M2.size;j++){
                if(temp1.getVertex().equals(temp2.getVertex())){
                    uniqVert[uniqVerts]=temp1.getVertex();
                    Vertex.addVertex(newGraph, temp1.getVertex(), (Vertex[]) null);
                    uniqVerts++;
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }


        int[][] newM= new int[uniqVerts][uniqVerts];
        for(int i=0;i<uniqVerts;i++){
            Arrays.fill(newM[i],-1);
        }
        temp1=G1.getNext();

        for(int i=0;i<M1.size;i++){
            temp2=G2.getNext();
            for (int j = 0; j < M2.size; j++) {
                if(temp1.getVertex().equals(temp2.getVertex())){
                    int k=Vertex.findId(newGraph,temp1.getVertex());
                    Vertex tempGr = newGraph.getNext();
                    int l=0;
                    while(tempGr!=null){
                        int y1=Vertex.findId(G1,tempGr.getVertex());
                        int y2=Vertex.findId(G2,tempGr.getVertex());
                        if(y1!=-1 && y2!=-1)
                            newM[k][l]=M1.matrix[i][y1] & M2.matrix[j][y2];
                        else if(y1!=-1)
                            newM[k][l]=M1.matrix[i][y1];
                        else if(y2!=-1)
                            newM[k][l]=M2.matrix[j][y2];

                        l++;
                        tempGr=tempGr.getNext();
                    }
                    break;
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }

        Matrix Mres = new Matrix(newM.length);
        Mres.matrix=newM;
        temp1=newGraph.getNext();
        for(int i = 0; i < Mres.size; i++){
            temp2=newGraph.getNext();
            for(int j = 0; j < Mres.size; j++){
                if(Mres.matrix[i][j]==1){
                    int k=1;
                    if(temp1.getAdjVertexes()!=null)
                        k=1+temp1.getAdjVertexes().length;
                    Vertex[] tmp = new Vertex[k];
                    if(temp1.getAdjVertexes()!=null)
                        tmp= Arrays.copyOf(temp1.getAdjVertexes(), k);
                    tmp[tmp.length-1]=temp2;
                    temp1.setAdjVertexes(tmp);
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }
        printMatrix(Mres,newGraph);
        Vertex.printGraph(newGraph);
        return Mres;
    }

    public static Matrix ringSumMatrixes(Matrix M1,Matrix M2,Vertex G1,Vertex G2){
        Vertex temp1=G1.getNext();
        Vertex temp2=G2.getNext();
        Vertex newGraph = new Vertex("start",null,null,null);
        String[] uniqVert= new String[M1.size+M2.size];
        Arrays.fill(uniqVert,"");
        int uniqVerts=0;
        for(int i=0;i<M1.size;i++){
            final Vertex temp = temp1;
            if(Arrays.stream(uniqVert).noneMatch(n->n.equals(temp.getVertex()))){
                uniqVert[uniqVerts]=temp1.getVertex();
                Vertex.addVertex(newGraph,temp.getVertex(), (Vertex[]) null);
                uniqVerts++;
            }
            temp1=temp1.getNext();
        }
        for(int i=0;i<M2.size;i++){
            final Vertex temp = temp2;
            if(Arrays.stream(uniqVert).noneMatch(n->n.equals(temp.getVertex()))){
                uniqVert[uniqVerts]=temp2.getVertex();
                Vertex.addVertex(newGraph,temp.getVertex(), (Vertex[]) null);
                uniqVerts++;
            }
            temp2=temp2.getNext();
        }

        int[][] newM= new int[uniqVerts][uniqVerts];
        for(int i=0;i<uniqVerts;i++){
            Arrays.fill(newM[i],-1);
        }
        temp1=G1.getNext();

        for(int i=0;i<M1.size;i++){
            temp2=G2.getNext();
            for (int j = 0; j < M2.size; j++) {
                if(temp1.getVertex().equals(temp2.getVertex())){
                    int k=Vertex.findId(newGraph,temp1.getVertex());
                    Vertex tempGr = newGraph.getNext();
                    int l=0;
                    while(tempGr!=null){
                        int y1=Vertex.findId(G1,tempGr.getVertex());
                        int y2=Vertex.findId(G2,tempGr.getVertex());
                        if(y1!=-1 && y2!=-1)
                            newM[k][l]=M1.matrix[i][y1] ^ M2.matrix[j][y2];
                        else if(y1!=-1)
                            newM[k][l]=M1.matrix[i][y1];
                        else if(y2!=-1)
                            newM[k][l]=M2.matrix[j][y2];

                        l++;
                        tempGr=tempGr.getNext();
                    }
                    break;
                }
                else if(j==M2.size-1){
                    int k=Vertex.findId(newGraph,temp1.getVertex());
                    Vertex tempGr = newGraph.getNext();
                    int l=0;
                    while(tempGr!=null){
                        int y1=Vertex.findId(G1,tempGr.getVertex());
                        if(y1!=-1)
                            newM[k][l]=M1.matrix[i][y1];
                        else
                            newM[k][l]=0;
                        l++;
                        tempGr=tempGr.getNext();
                    }
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }
        temp2=G2.getNext();
        int k=0;
        int start=0;
        for(int j=0;j<newM.length;j++){
            if(newM[j][0]==-1){
                k++;
                if(start==0) start=j;
            }
        }
        for(int i=0;i<M2.size;i++){
            if(newM[Vertex.findId(newGraph,temp2.getVertex())][0]!=-1){
                temp2=temp2.getNext();
                continue;
            }
            Vertex temp = G2.getNext();
            for(int j=0;j<newM.length;j++){
                int x;
                if(temp!=null)
                    x = Vertex.findId(G2,temp.getVertex());
                else x=-1;
                if(x!=-1){
                    newM[start][j]=M2.matrix[i][x];
                }
                else{
                    newM[start][j]=0;
                }
                if(temp!=null)
                    temp=temp.getNext();
            }
            start++;
            temp2=temp2.getNext();
        }
        Matrix Mres = new Matrix(newM.length);
        Mres.matrix=newM;
        Vertex temp = newGraph.getNext();
        for(int i=0;i<newM.length;i++){
            if(Arrays.stream(newM[i]).noneMatch(n->n==1)){
                Vertex.deleteVertex(newGraph,temp.getVertex(),Mres);
            }
            temp=temp.getNext();
        }
        temp1=newGraph.getNext();
        for(int i = 0; i < Mres.size; i++){
            temp2=newGraph.getNext();
            for(int j = 0; j < Mres.size; j++){
                if(Mres.matrix[i][j]==1){
                    k=1;
                    if(temp1.getAdjVertexes()!=null)
                        k=1+temp1.getAdjVertexes().length;
                    Vertex[] tmp = new Vertex[k];
                    if(temp1.getAdjVertexes()!=null)
                        tmp= Arrays.copyOf(temp1.getAdjVertexes(), k);
                    tmp[tmp.length-1]=temp2;
                    temp1.setAdjVertexes(tmp);
                }
                temp2=temp2.getNext();
            }
            temp1=temp1.getNext();
        }
        printMatrix(Mres,newGraph);
        Vertex.printGraph(newGraph);
        return Mres;
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
