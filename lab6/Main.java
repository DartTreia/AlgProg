
import java.util.Arrays;
import java.util.Scanner;

public class Main{

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n=sc.nextInt();
        Matrix M1 = new Matrix(n);
        Matrix M2 = new Matrix(n);
        Vertex G1 = new Vertex("start",null,null,M1);
        Vertex G2 = new Vertex("start",null,null,M2);
        Matrix.printMatrixes(M1,M2,G1,G2);


        System.out.println("G1");
        Vertex.printGraph(G1);
        System.out.println("G2");
        Vertex.printGraph(G2);

        Vertex.deleteVertex(G2,"x3",M2);
        Matrix.printMatrixes(M1,M2,G1,G2);
        System.out.println("G2");
        Vertex.printGraph(G2);


        Vertex.identifyVertexes("x12","x1","x2",M1,G1);
        System.out.println("G1");
        Vertex.printGraph(G1);
        Matrix.printMatrixes(M1,M2,G1,G2);
    }
}