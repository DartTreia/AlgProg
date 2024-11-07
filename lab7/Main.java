import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main{

    static Scanner sc = new Scanner(System.in);
    public static void clearConsole(){
            System.out.print("\n\n");
    }
    public static void main(String[] args) {
        System.out.println("To start program write down size of 1st and 2nd matrixes: ");
        int n=sc.nextInt();
        Matrix M1 = new Matrix(n);
        int b=sc.nextInt();
        Matrix M2 = new Matrix(b);
        Vertex G1 = new Vertex("start",null,null,M1);
        Vertex G2 = new Vertex("start",null,null,M2);

        while(true) {
            System.out.println("------------MENU------------");
            System.out.println("1. Show matrixes");
            System.out.println("2. Show graph");
            System.out.println("3. Add vertex");
            System.out.println("4. Delete vertex");
            System.out.println("5. Deep loop find");
            System.out.println("6. Deep non-loop find");
            System.out.println("7. Exit");
            int choice = sc.nextInt();
            clearConsole();
            switch (choice) {
                case 1:
                    Matrix.printMatrixes(M1, M2, G1, G2);
                    break;
                case 2:
                    System.out.println("Which graph do you want to see? (1 or 2)");
                    int choice2 = sc.nextInt();
                    switch (choice2) {
                        case 1:
                            System.out.println("G1");
                            Vertex.printGraph(G1);
                            break;
                        case 2:
                            System.out.println("G2");
                            Vertex.printGraph(G2);
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.print("Write down the name of new vertex: ");
                    String newName = sc.next();
                    System.out.print("Write down adjacency vertexes: ");
                    String adjList = sc.nextLine();
                    adjList = sc.nextLine();
                    System.out.println("In which graph do u want to add vertex? (1 or 2)");
                    int choice3 = sc.nextInt();
                    switch (choice3) {
                        case 1:
                            if(Vertex.addVertex(G1, newName, adjList)) Matrix.addVertInMx(M1,G1);
                            break;
                        case 2:
                            if(Vertex.addVertex(G2, newName, adjList)) Matrix.addVertInMx(M2,G2);
                            break;
                        default:
                            break;

                    }
                    break;
                case 4:
                    System.out.print("Write down the name of vertex which u want to delete: ");
                    String newName2 = sc.next();
                    System.out.println("In which graph do u want to delete vertex? (1 or 2)");
                    int choice4 = sc.nextInt();
                    switch (choice4) {
                        case 1:
                            Vertex.deleteVertex(G1, newName2, M1);
                            break;
                        case 2:
                            Vertex.deleteVertex(G2, newName2, M2);
                            break;
                        default:
                            break;

                    }
                    break;
                case 5:
                    System.out.println("In which graph do u want to search the end? (1 or 2)");
                    int choice5 = sc.nextInt();
                    System.out.print("Write down the name of vertex which will be start: ");
                    String name = sc.next();
                    Vertex[] temp = null;
                    switch (choice5) {
                        case 1:
                            temp = Vertex.stepsLoopVert(G1, name, null);
                            break;
                        case 2:
                            temp=Vertex.stepsLoopVert(G2, name, null);
                            break;
                        default:
                            break;
                    }
                    if(temp!=null) {
                        for (int i = 0; i < temp.length; i++) {
                            System.out.print(temp[i].getVertex()+" ");
                        }
                    }
                    else{
                        System.out.println("Vertex not found");
                    }
                    break;
                case 6:
                    System.out.println("In which graph do u want to search the end? (1 or 2)");
                    int choice6 = sc.nextInt();
                    System.out.print("Write down the name of vertex which will be start: ");
                    String name2 = sc.next();
                    Vertex[] temp2 = null;
                    switch (choice6) {
                        case 1:
                            temp2 = Vertex.stepsVert(G1, name2);
                            break;
                        case 2:
                            temp2=Vertex.stepsVert(G2, name2);
                            break;
                        default:
                            break;
                    }
                    if(temp2!=null) {
                        for (int i = 0; i < temp2.length; i++) {
                            if(temp2[i]!=null)
                                System.out.print(temp2[i].getVertex()+" ");
                        }
                    }
                    else{
                        System.out.println("Vertex not found");
                    }
                    break;
                case 7:
                    Runtime.getRuntime().exit(0);
                    break;
                    default:
                        break;
            }

            System.out.println("\nWrite any symbol to continue...");
            String tmp = sc.next();
            clearConsole();
        }
    }
}