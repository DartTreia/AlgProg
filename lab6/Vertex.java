import java.util.Arrays;

public class Vertex {
    private String vertex;
    private Vertex next;
    private Vertex[] adjVertexes;
    
    public Vertex(String vertex, Vertex next, Vertex[] adjVertexes, int arr[][]) {
        this.vertex = vertex;
        this.next = next;
        this.adjVertexes = adjVertexes;
        if(vertex.equals("x1"))
            fillGraph(arr);
    }

private void fillGraph(int arr[][]){
    Vertex[] vert = new Vertex[arr.length];
    vert[0]=this;
    for(int i=1;i<arr.length;i++){
        vert[i] = new Vertex("x"+(i+1),null,null,arr);
    }
    for(int i=0;i<arr.length-1;i++){
        vert[i].setNext(vert[i+1]);
    }
    for(int i = 0; i < arr.length; i++){
        for(int j = 0; j < arr.length; j++){
            if(arr[i][j]==1){
                int k=1;
                if(vert[i].getAdjVertexes()!=null)
                    k=1+vert[i].getAdjVertexes().length; // размер массива смежных вершин
                Vertex[] tmp = new Vertex[k];
                if(vert[i].getAdjVertexes()!=null)
                    tmp= Arrays.copyOf(vert[i].getAdjVertexes(), k);
                tmp[tmp.length-1]=vert[j];
                vert[i].setAdjVertexes(tmp);
            }
        }
    }
}
    public static void printGraph(Vertex graph){
        Vertex temp=graph;
        while(temp!=null){
            System.out.print(temp.getVertex()+": ");
            if(temp.getAdjVertexes()!=null)
                Arrays.stream(temp.getAdjVertexes()).forEach(n -> System.out.print(n.getVertex()+" "));
            else{
                System.out.print(" none ");
            }
            System.out.println();
            temp = temp.getNext();
        }
    }

    public static void deleteVertex(Vertex graph,String vertex){
        Vertex temp=graph;
        while(temp!=null){
            if(temp.getVertex().equals(vertex)){
                temp.setNext(temp.getNext().getNext());
                return;
            }
            temp=temp.getNext();
        }
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
