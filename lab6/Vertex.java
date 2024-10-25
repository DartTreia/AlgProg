import java.util.Arrays;

public class Vertex {
    private String vertex;
    private Vertex next;
    private Vertex[] adjVertexes;
    
    public Vertex(String vertex, Vertex next, Vertex[] adjVertexes, Matrix M) {
        this.vertex = vertex;
        this.next = next;
        this.adjVertexes = adjVertexes;
        if(vertex.equals("start") && M!=null)
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
        temp = temp.next;
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
    public static boolean addVertex(Vertex G, String name,String adjVert){
        if(findVertex(G,name)!=null) return false;
        Vertex temp=G;
        String[] adj = adjVert.trim().split(" ");
        Vertex[] tmp = new Vertex[adj.length];
        Vertex newVert = new Vertex(name,null,null,null);
        Vertex last=null;
        while(temp!=null){
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
            if(temp.next==null) last=temp;
            temp=temp.next;
        }
        last.next=newVert;
        return true;
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

    public static boolean constrictionEdge(String newName,String vert1,String vert2, Matrix M, Vertex G){
        Vertex v1 = findVertex(G,vert1);
        Vertex v2 = findVertex(G,vert2);
        if(v1==null || v2==null){
            return false;
        }
        Vertex[] tmp1 = v1.adjVertexes;
        Vertex[] tmp2 = v2.adjVertexes;
        if(tmp1==null || Arrays.stream(tmp1).noneMatch(n-> n==v2)){
            return false;
        }

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
        if(Arrays.stream(newAdjVert).anyMatch(n->n.vertex.equals(newName))){
            Vertex[] tmp = new Vertex[newAdjVert.length-1];
            if(tmp.length!=0) {
                int o = 0;
                for (int i = 0; i < newAdjVert.length; i++) {
                    if (newAdjVert[i]!=null && !newAdjVert[i].vertex.equals(newName)) {
                        tmp[o] = newAdjVert[i];
                        o++;
                    }
                }
                newAdjVert = new Vertex[o];
                newAdjVert = Arrays.copyOf(tmp, o);
            }
            else{
                newAdjVert = new Vertex[0];
            }
            k++;
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

        Matrix.addVertInMx(M,G);
        return true;
    }
    public static boolean splittingVertex(Vertex G, Matrix M, String name1, String name2, String oldName){
        Vertex tmp = G;
        while(tmp!=null){
            if(tmp.vertex.equals(oldName)){
                break;
            }
            tmp=tmp.next;
        }
        if(tmp==null){return false;}
        if(Arrays.stream(tmp.adjVertexes).anyMatch(n->n.vertex.equals(oldName))){
            Vertex[] newTmpAdjVert = new Vertex[tmp.adjVertexes.length-1];
            int k=0;
            for (int i = 0; i < tmp.adjVertexes.length; i++) {
                if(!tmp.adjVertexes[i].vertex.equals(oldName)){
                    newTmpAdjVert[k]=tmp.adjVertexes[i];
                    k++;
                }
            }
            tmp.adjVertexes=newTmpAdjVert;
        }
        Vertex[] adj1 = new Vertex[tmp.adjVertexes.length-tmp.adjVertexes.length/2];
        Vertex[] adj2 = new Vertex[tmp.adjVertexes.length/2];
        if(tmp.adjVertexes.length!=0){
            int k=0;
            for(int i=0;i<adj1.length;i++){
                adj1[i]=tmp.adjVertexes[k];
                k++;
            }
            for(int i=0;i<adj2.length;i++){
                adj2[i]=tmp.adjVertexes[k];
                k++;
            }
        }

        Vertex newVert1 = Vertex.addVertex(G,name1,adj1);
        Matrix.addVertInMx(M,G);
        Vertex newVert2 = addVertex(G,name2,adj2);
        Matrix.addVertInMx(M,G);

        if(newVert1.adjVertexes!=null) {
            for (int i = 0;i<newVert1.adjVertexes.length;i++){
                for(int j=0;j<newVert1.adjVertexes[i].adjVertexes.length;j++){
                    if(newVert1.adjVertexes[i].adjVertexes[j].vertex.equals(oldName)){
                        newVert1.adjVertexes[i].adjVertexes[j] = newVert1;
                    }
                }
            }
        }
        if(newVert2.adjVertexes!=null) {
            for (int i = 0;i<newVert2.adjVertexes.length;i++){
                for(int j=0;j<newVert2.adjVertexes[i].adjVertexes.length;j++){
                    if(newVert2.adjVertexes[i].adjVertexes[j].vertex.equals(oldName)){
                        newVert2.adjVertexes[i].adjVertexes[j] = newVert2;
                    }
                }
            }
        }
        deleteVertex(G,oldName,M);
        return true;
    }

    public static Vertex decMulti(Vertex G1,Vertex G2){
        if(G1.next==null || G2.next==null){return null;}
        int count1=0;
        Vertex tmp = G1.next;
        while(tmp!=null){
            count1++;
            tmp=tmp.next;
        }
        tmp=G2.next;
        int count2=0;
        while(tmp!=null){
            count2++;
            tmp=tmp.next;
        }

        Vertex newVert=new Vertex("start",null,null,null);
        Vertex tmp2=newVert;
        Vertex last = null;
        for(int i=0;i<Math.min(count1,count2);i++) {
            if (count1 > count2) tmp = G1.next;
            else tmp = G2.next;

            while (tmp != null) {
                Vertex[] newAdj=tmp.adjVertexes;
                if(last!=null) {
                    int k=1;
                    if(newAdj!=null)
                        k+=newAdj.length;
                    newAdj = new Vertex[k];
                    if(tmp.adjVertexes!=null) newAdj = Arrays.copyOf(tmp.adjVertexes, k);
                    newAdj[k-1] = last;
                }
                Vertex temp = Vertex.addVertex(newVert, tmp.vertex+(i+1), newAdj);
                if(last!=null){
                    int k=1;
                    if(last.adjVertexes!=null)
                        k+=last.adjVertexes.length;
                    newAdj = new Vertex[k];
                    if(last.adjVertexes!=null) newAdj = Arrays.copyOf(last.adjVertexes, k);
                    newAdj[k-1] = temp;
                    last.adjVertexes = newAdj;
                    last=last.next;
                }
                tmp = tmp.next;
            }
            tmp2=tmp2.next;
            last=tmp2;
            while(tmp2.next!=null){
                tmp2=tmp2.next;
            }
        }
        return newVert;
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
