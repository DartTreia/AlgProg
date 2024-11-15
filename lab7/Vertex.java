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
    private Vertex findVertex(String name){
        Vertex tmp  = this;
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
public int findId(String name){
    Vertex tempGrpah = this.getNext();
    int k=0;
    while(tempGrpah!=null){
        if(tempGrpah.getVertex().equals(name)){
            return k;
        }
        k++;
        tempGrpah=tempGrpah.getNext();
    }
    return -1;
}
    public void printGraph(){
        Vertex temp=this;
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

    public Vertex deleteVertex(String vertex,Matrix M){
        Vertex temp=this;
        temp = temp.next;
        Vertex last=null;
        int count = 1;
        while(temp!=null){
            if (temp.vertex.equals(vertex)) {
                if (last != null)
                    last.next = temp.next;
                else this.next = temp.next;
                M.deleteVertInMx(count);
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
            if(this!=temp.next)
            last=temp;
            temp=temp.next;
            count++;
        }
        return this;
    }
    public boolean addVertex(String name,String adjVert){
        if(this.findVertex(name)!=null) return false;
        Vertex temp=this;
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
    public Vertex addVertex(String name,Vertex adjVert[]){
        Vertex tmp = this;
        while(tmp.next!=null){
            tmp=tmp.next;
        }
        Vertex newVert = new Vertex(name,null,adjVert,null);
        tmp.setNext(newVert);
        return newVert;
    }
   public Vertex[] stepsLoopVert(String name, Vertex[] visVerts){
       Vertex tmp = this.next;
       while(tmp!=null){
           if(tmp.vertex.equals(name)) break;
           tmp=tmp.next;
       }
       if(tmp==null){
           return null;
       }
       if(visVerts!=null && Arrays.stream(visVerts).anyMatch(n->n.vertex.equals(name))) {
           return visVerts;
       }else{
           int k=1;
           if(visVerts!=null) k+=visVerts.length;
           Vertex[] temp=new Vertex[k];
           if(visVerts!=null) temp=Arrays.copyOf(visVerts, k);
           temp[temp.length-1]=tmp;
           visVerts=temp;
       }
       if(tmp.adjVertexes==null){
           return visVerts;
       }
       for(int i=0;i<tmp.adjVertexes.length;i++){
           visVerts=this.stepsLoopVert(tmp.adjVertexes[i].vertex,visVerts);
       }
       return visVerts;
   }
    public Vertex[] stepsVert(String name){
        Vertex tmp = this.next;
        while(tmp!=null){
            if(tmp.vertex.equals(name)) break;
            tmp=tmp.next;
        }
        if(tmp==null){return null;}
        Vertex[] visVerts=null;
        if(tmp.adjVertexes==null){
            visVerts=new Vertex[1];
            visVerts[0]=tmp;
            return visVerts;
        }

        Stack stack = new Stack(null,null);
        stack = stack.stackPush(tmp);
        while(stack!=null){
            final Stack finVert = stack.stackPop();
            stack=stack.head;
            if(visVerts==null || Arrays.stream(visVerts).noneMatch(n->n.vertex.equals(finVert.vert.vertex))){
                int k=1;
                if(visVerts!=null) k+=visVerts.length;
                Vertex[] tempVerts =new Vertex[k];
                if(visVerts!=null) tempVerts = Arrays.copyOf(visVerts, k);
                tempVerts[k-1]=finVert.vert;
                visVerts=tempVerts;

                if(finVert.vert.adjVertexes!=null) {
                    for (int i = 0; i <finVert.vert.adjVertexes.length;i++) {
                        final Vertex vert = finVert.vert.adjVertexes[i];
                        if(Arrays.stream(visVerts).noneMatch(n->n.vertex.equals(vert.vertex))){
                            if(stack==null) stack = new Stack(null,null);
                            stack=stack.stackPush(vert);
                        }

                    }
                }
            }
        }

        return visVerts;
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
