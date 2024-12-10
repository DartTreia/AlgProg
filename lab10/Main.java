import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean direct=false, weight=false;
        System.out.println("To start program write down size of matrix: ");
        int n=sc.nextInt();

        if(args[0].equalsIgnoreCase("direct")){
            direct = true;
        }

        if(args[1].equalsIgnoreCase("weight")){
            weight = true;
        }

        Matrix M = new Matrix(n,weight,direct);

        Menu menu = new Menu(M);
    }
}