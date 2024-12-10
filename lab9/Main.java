import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("To start program write down size of matrix: ");
        int n=sc.nextInt();

        Matrix M = new Matrix(n);
        Vertex G = new Vertex("start",null,null,M);

        Menu menu = new Menu(M,G);
    }
}