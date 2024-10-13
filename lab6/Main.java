import java.sql.SQLOutput;
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
    public static void printMatrix(int arr1[][],int arr2[][]){
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
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int n=sc.nextInt();
        int[][] M1 = new int[n][n];
        int[][] M2 = new int[n][n];
        createMatrix(M1);
        createMatrix(M2);
        printMatrix(M1,M2);
    }
}