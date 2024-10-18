import java.util.Arrays;

public class Vertex {
    private String vertex;
    private Vertex next;
    private Vertex[] adjVertexes;
    
    public Vertex(String vertex, Vertex next, Vertex[] adjVertexes, Matrix M) {
        this.vertex = vertex;
        this.next = next;
        this.adjVertexes = adjVertexes;
        if(vertex.equals("start"))
            fillGraph(M);
    }
    private static Vertex findVertex(Vertex G,String name){
        Vertex tmp  = G;
        while(tmp != null){
            if(tmp.getVertex().equals(name)) return tmp;
            tmp = tmp.getNext();
        }
        return null;
    }
private void fillGraph(Matrix M){
        int[][] arr = M.getMatrix();
    Vertex[] vert = new Vertex[arr.length+1];
    vert[0]=this;
    for(int i=1;i<arr.length+1;i++){
        vert[i] = new Vertex("x"+i,null,null,null);
    }
    for(int i=0;i<arr.length;i++){
        vert[i].setNext(vert[i+1]);
    }
    for(int i = 0; i < arr.length; i++){
        for(int j = 0; j < arr.length; j++){
            if(arr[i][j]==1){
                int k=1;
                if(vert[i+1].adjVertexes!=null)
                    k=1+vert[i+1].adjVertexes.length;
                Vertex[] tmp = new Vertex[k];
                if(vert[i+1].adjVertexes!=null)
                    tmp= Arrays.copyOf(vert[i+1].adjVertexes, k);
                tmp[tmp.length-1]=vert[j+1];
                vert[i+1].setAdjVertexes(tmp);
            }
        }
    }
}
    public static void printGraph(Vertex graph){
        Vertex temp=graph;
        temp=temp.next;
        while(temp!=null){
            System.out.print(temp.vertex+": ");
            if(temp.adjVertexes!=null)
                Arrays.stream(temp.adjVertexes).forEach(n -> System.out.print(n.vertex+" "));
            else{
                System.out.print(" none ");
            }
            System.out.println();
            temp = temp.next;
        }
    }

    public static Vertex deleteVertex(Vertex graph,String vertex,Matrix M){
        Vertex temp=graph;
        Vertex last=null;
        int count = 1;
        while(temp!=null){
            if (temp.vertex.equals(vertex)) {
                if (last != null)
                    last.next = temp.next;
                else graph.next = temp.next;
                Matrix.deleteVertInMx(M,count);
            }
            else if(temp.adjVertexes!=null) {
                for (int i = 0; i < temp.adjVertexes.length; i++) {
                    if (temp.adjVertexes[i].vertex.equals(vertex)) {
                        Vertex[] tmpVertexes;
                        if(temp.adjVertexes.length - 1!=0){
                            tmpVertexes = new Vertex[temp.adjVertexes.length - 1];
                            int k = 0;
                            for (int j = 0; j < temp.adjVertexes.length; j++) {
                                if (i != j) {
                                    tmpVertexes[k] = temp.adjVertexes[j];
                                    k++;
                                }
                            }
                            temp.setAdjVertexes(tmpVertexes);
                        }
                        else temp.setAdjVertexes(null);
                        break;
                    }

                }
            }
            if(graph!=temp.next)
            last=temp;
            temp=temp.next;
            count++;
        }
        return graph;
    }
    public static void addVertex(Vertex G, String name,String adjVert){
        Vertex temp=G;
        String[] adj = adjVert.trim().split(" ");
        Vertex[] tmp = new Vertex[adj.length];
        Vertex newVert = new Vertex(name,null,null,null);
        while(temp.next!=null){
            for(int i=0;i<adj.length;i++){
                if(temp.vertex.equals(adj[i])){
                    //старая
                    int k=1;
                    if(temp.adjVertexes!=null)
                        k=1+temp.adjVertexes.length; // размер массива смежных вершин
                    Vertex[] tmp2 = new Vertex[k];
                    if(temp.adjVertexes!=null)
                        tmp2= Arrays.copyOf(temp.adjVertexes, k);
                    tmp2[tmp2.length-1]=newVert;
                    temp.setAdjVertexes(tmp2);

                    //новая
                    k=1;
                    if(newVert.adjVertexes!=null)
                        k=1+newVert.adjVertexes.length; // размер массива смежных вершин
                    tmp2 = new Vertex[k];
                    if(newVert.adjVertexes!=null)
                        tmp2= Arrays.copyOf(newVert.adjVertexes, k);
                    tmp2[tmp2.length-1]=temp;
                    newVert.setAdjVertexes(tmp2);
                }
            }
            temp=temp.next;
        }
        temp.next=newVert;
    }
    public static Vertex addVertex(Vertex G, String name,Vertex adjVert[]){
        Vertex tmp = G;
        while(tmp.next!=null){
            tmp=tmp.next;
        }
        Vertex newVert = new Vertex(name,null,adjVert,null);
        tmp.setNext(newVert);
        return newVert;
    }
    public static boolean identifyVertexes(String newName,String vert1,String vert2, Matrix M, Vertex G){
        if(findVertex(G,vert1)==null || findVertex(G,vert2)==null){
            return false;
        }

        Vertex[] tmp1 = findVertex(G,vert1).adjVertexes;
        Vertex[] tmp2 = findVertex(G,vert2).adjVertexes;
        Vertex[] newAdjVert=new Vertex[M.getSize()];
        Vertex nwVert = addVertex(G,newName,newAdjVert);
        int k=M.getSize();
        if(tmp1!=null) {
            newAdjVert= Arrays.copyOf(tmp1, k);
            k-=tmp1.length;
            if(tmp2!=null){
                for (int i = 0; i < tmp2.length; i++) {
                    for (int j = 0; j < newAdjVert.length; j++) {
                        if(newAdjVert[j]!=null && (newAdjVert[j].vertex.equals(vert1) || newAdjVert[j].vertex.equals(vert2))){
                            if(Arrays.stream(newAdjVert).noneMatch(n-> n == nwVert)){
                                newAdjVert[j]=nwVert;
                            }
                        }
                        if ((!(tmp2[i] == newAdjVert[j]) && newAdjVert[j] == null) && !(tmp2[i].vertex.equals(vert1) || tmp2[i].vertex.equals(vert2))) {
                            newAdjVert[j] = tmp2[i];
                            k--;
                            break;
                        } else if(tmp2[i] == newAdjVert[j]) j = M.getSize();
                    }
                }
            }
        }
        else if(tmp2!=null) {
            newAdjVert = Arrays.copyOf(tmp2, tmp2.length);
            for (int j = 0; j < newAdjVert.length; j++) {
                if (newAdjVert[j].vertex.equals(vert1) || newAdjVert[j].vertex.equals(vert2)) {
                    newAdjVert[j] = nwVert;
                }
            }
            k-=tmp2.length;
        }
            Vertex[] finAdjVert;
        if(k!=0){
            finAdjVert=new Vertex[M.getSize()-k];
            finAdjVert= Arrays.copyOf(newAdjVert, M.getSize()-k);
        }
        else{
            finAdjVert= Arrays.copyOf(newAdjVert, newAdjVert.length);
        }
        nwVert.adjVertexes=finAdjVert;
        deleteVertex(G,vert1,M);
        deleteVertex(G,vert2,M);
        for (int i = 0; i < finAdjVert.length; i++) {
            if(!finAdjVert[i].vertex.equals(newName)) {
                int size = 1;
                Vertex[] newArrVert = new Vertex[size];
                if (finAdjVert[i].adjVertexes != null) {
                    size += finAdjVert[i].adjVertexes.length;
                    newArrVert = new Vertex[size];
                    newArrVert = Arrays.copyOf(finAdjVert[i].adjVertexes, size);
                }
                newArrVert[size - 1] = nwVert;
                finAdjVert[i].setAdjVertexes(newArrVert);
            }
        }

        Matrix.addVertInMx(M,G);
        return true;
    }

    public String getVertex() {
        return vertex;
    }
    public Vertex[] getAdjVertexes() {
        return adjVertexes;
    }
    public Vertex getNext() {
        return next;
    }
    public void setAdjVertexes(Vertex[] adjVertexes) {
        this.adjVertexes = adjVertexes;
    }
    public void setVertex(String vertex) {
        this.vertex = vertex;
    }
    public void setNext(Vertex next) {
        this.next = next;
    }
}
