import java.io.*;
import java.util.Scanner;

public class Main{

    static Scanner sc = new Scanner(System.in);
    public static void clearConsole(){
        for(int i=0;i<100;i++){
            System.out.print("\n\n");
        }
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
            System.out.println("5. Identify vertexes");
            System.out.println("6. Constrict edge");
            System.out.println("7. Split vertex");
            System.out.println("8. Decurt multiplication of graphs");
            System.out.println("9. Merge matrixes");
            System.out.println("10. Intersection of matrixes");
            System.out.println("11. Ring sum of matrixes");
            System.out.println("12. Exit");
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
                    System.out.print("Write down the names of 2 vertexes which u want to identify: ");
                    String oldName1 = sc.next();
                    String oldName2 = sc.next();
                    System.out.print("Write down the new name of vertex: ");
                    String newName3 = sc.next();
                    System.out.print("In which graph do u want to identify vertex? (1 or 2): ");
                    int choice5 = sc.nextInt();
                    switch (choice5) {
                        case 1:
                            if (Vertex.identifyVertexes(newName3, oldName1, oldName2, M1, G1))
                                System.out.println("Identify completed");
                            else System.out.println("Identify failed");
                            break;
                        case 2:
                            if (Vertex.identifyVertexes(newName3, oldName1, oldName2, M2, G2))
                                System.out.println("Identify completed");
                            else System.out.println("Identify failed");
                            break;
                        default:
                            break;
                    }
                    break;
                case 6:
                    System.out.print("Write down the names of 2 vertexes which are linked: ");
                    String oldName3 = sc.next();
                    String oldName4 = sc.next();
                    System.out.print("Write down the new name of vertex: ");
                    String newName4 = sc.next();
                    System.out.print("In which graph do u want to constrict the edge? (1 or 2): ");
                    int choice6 = sc.nextInt();
                    switch (choice6) {
                        case 1:
                            if (Vertex.constrictionEdge(newName4, oldName3, oldName4, M1, G1))
                                System.out.println("Constriction completed");
                            else System.out.println("Constriction failed");
                            break;
                        case 2:
                            if (Vertex.constrictionEdge(newName4, oldName3, oldName4, M2, G2))
                                System.out.println("Constriction completed");
                            else System.out.println("Constriction failed");
                            break;
                        default:
                            break;
                    }
                    break;
                case 7:
                    System.out.print("Write down the name of vertex which u want to split: ");
                    String oldName5 = sc.next();
                    System.out.print("Write down new names of 2 vertexes: ");
                    String newName5 = sc.next();
                    String newName6 = sc.next();
                    System.out.print("In which graph do u want to split vertex? (1 or 2): ");
                    int choice7 = sc.nextInt();
                    switch (choice7) {
                        case 1:
                            if (Vertex.splittingVertex(G1, M1, newName5, newName6, oldName5))
                                System.out.println("Splitting completed");
                            else System.out.println("Splitting failed");
                            break;
                        case 2:
                            if (Vertex.splittingVertex(G2, M2, newName5, newName6, oldName5))
                                System.out.println("Splitting completed");
                            else System.out.println("Splitting failed");
                            break;
                        default:
                            break;
                    }
                    break;
                case 8:
                    Vertex decNew = Vertex.decMulti(G1, G2);
                    if (decNew == null) {
                        System.out.println("Decurt failed");
                    } else {
                        System.out.println("Decurt multiplication:");
                        Vertex.printGraph(decNew);
                    }
                    break;
                case 9:
                    Matrix newM = Matrix.mergeMatrixes(M1, M2, G1, G2);
                    if (newM == null)
                        System.out.println("Merge failed");
                    else Matrix.printMatrix(newM, G1);
                    break;
                case 10:
                    Matrix intM = Matrix.intersectionMatrixes(M1,M2,G1,G2);
                    if (intM == null)
                        System.out.println("Intersection failed");
                    else Matrix.printMatrix(intM, G1);
                    break;
                case 11:

                    Matrix ringM = Matrix.ringSumMatrixes(M1,M2,G1,G2);
                    if(ringM == null)
                        System.out.println("Ring sum failed");
                    break;
                case 12:
                    Runtime.getRuntime().exit(0);
                    break;
                    default:
                        break;
            }

            System.out.println("Write any symbol to continue...");
            String tmp = sc.next();
            clearConsole();
        }
    }
}