import java.util.Arrays;
import java.util.Scanner;

public class Main{
    public static void createMatrix(int arr[][]){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                arr[i][j] = (int)(Math.random()*2);
                arr[j][i] = arr[i][j];
                if(j==i) arr[i][i]=0;
            }
        }
    }
    public static void printMatrixes(int arr1[][],int arr2[][]){
        System.out.print("M1 ");
        for (int i = 0; i < arr1.length; i++) {
            System.out.print("x"+(i+1)+" ");
        }
        System.out.print("   M2 ");
        for (int i = 0; i < arr1.length; i++) {
            System.out.print("x"+(i+1)+" ");
        }
        for (int i = 0; i < arr1.length; i++) {
            System.out.print("\nx"+(i+1)+"  ");
            for (int j = 0; j < arr1.length; j++) {
                System.out.print(arr1[i][j]+"  ");
            }
            System.out.print("  x"+(i+1)+"  ");
            for (int j = 0; j < arr2.length; j++) {
                System.out.print(arr2[i][j]+"  ");
            }
        }
        System.out.println();
    }
    public static void fillGraph(Verticle[] graph,int arr[][]){
       for(int i = 0; i < graph.length; i++){
           graph[i]=new Verticle("x"+(i+1),null);
       }
       for(int i = 0; i < arr.length; i++){
           for(int j = 0; j < arr.length; j++){
               if(arr[i][j]==1){
                   int k=1;
                   if(graph[i].getAdjVerticles()!=null)
                   k=1+graph[i].getAdjVerticles().length; // размер массива смежных вершин
                   Verticle[] tmp = new Verticle[k];
                   if(graph[i].getAdjVerticles()!=null)
                   tmp=Arrays.copyOf(graph[i].getAdjVerticles(), k);
                   tmp[tmp.length-1]=graph[j];
                   graph[i].setAdjVerticles(tmp);
               }
           }
       }
    }
    public static void printGraph(Verticle[] graph){
        for(int i = 0; i < graph.length; i++){
            System.out.print(graph[i].getVertex()+": ");
            if(graph[i].getAdjVerticles()!=null)
            Arrays.stream(graph[i].getAdjVerticles()).forEach(n -> System.out.print(n.getVertex()+" "));
            else{
                System.out.print(" none ");
            }
            System.out.println();
        }
    }
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n=sc.nextInt();
        int[][] M1 = new int[n][n];
        int[][] M2 = new int[n][n];
        createMatrix(M1);
        createMatrix(M2);
        printMatrixes(M1,M2);
        Verticle[] G1 = new Verticle[n];
        Verticle[] G2 = new Verticle[n];
        fillGraph(G1,M1);
        fillGraph(G2,M2);
        System.out.println("G1");
        printGraph(G1);
        System.out.println("G2");
        printGraph(G2);
    }
}