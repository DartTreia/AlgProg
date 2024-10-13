
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
        for (int i = 0; i < arr2.length; i++) {
            System.out.print("x"+(i+1)+" ");
        }
        for (int i = 0; i < Math.max(arr1.length,arr2.length); i++) {
            if(i<arr1.length){
                System.out.print("\nx"+(i+1)+"  ");
                for (int j = 0; j < arr1.length; j++) {
                    System.out.print(arr1[i][j]+"  ");
                }
            }
            else{
                System.out.print("\n ");
                for (int j = 0; j < arr2.length; j++) {
                    System.out.print("   ");
                }
            }
            if(i<arr2.length) {
                System.out.print("  x" + (i + 1) + "  ");
                for (int j = 0; j < arr2.length; j++) {
                    System.out.print(arr2[i][j] + "  ");
                }
            }
        }
        System.out.println();
    }
    public static int[][] deleteVertInMx(int arr[][],int value){
        int[][] tmp = new int[arr.length-1][arr[0].length-1];
        int x=0;
        for (int i = 0; i < arr.length; i++) {
            if(i!=value-1) {
                int y=0;
                for (int j = 0; j < arr[0].length; j++) {
                    if (j != value - 1) {
                        tmp[x][y] = arr[i][j];
                        y++;
                    }
                }
                x++;
            }
        }
        return tmp;
    }
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n=sc.nextInt();
        int[][] M1 = new int[n][n];
        int[][] M2 = new int[n][n];
        createMatrix(M1);
        createMatrix(M2);
        printMatrixes(M1,M2);
        M2=deleteVertInMx(M2,3);
        printMatrixes(M1,M2);
        Vertex G1 = new Vertex("x1",null,null,M1);
        Vertex G2 = new Vertex("x1",null,null,M2);

        System.out.println("G1");
        Vertex.printGraph(G1);
        System.out.println("G2");
        Vertex.printGraph(G2);
    }
}