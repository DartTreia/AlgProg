import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private Matrix M;
    private Vertex G;
    public Menu(Matrix M,Vertex G){
        this.M = M;
        this.G = G;
        display(M,G);
    }
    static Scanner sc = new Scanner(System.in);
    public void clearConsole(){
        System.out.print("\n\n");
    }
    public void display(Matrix M,Vertex G) {
        while(true) {
            System.out.println("------------MENU------------");
            System.out.println("1. Show matrix");
            System.out.println("2. Show graph");
            System.out.println("3. Add vertex");
            System.out.println("4. Delete vertex");
            System.out.println("5. BFS dist for matrix");
            System.out.println("6. DFS for incident list");
            System.out.println("7. BFS dist for incident list");
            System.out.println("8. DFS dist for incident list");
            System.out.println("9. Time compare DFS and BFS");
            System.out.println("10. Exit");
            int choice = sc.nextInt();
            clearConsole();
            switch (choice) {
                case 1:
                    M.printMatrix(G);
                    break;
                case 2:
                    System.out.println(" G");
                    G.printGraph();
                    break;
                case 3:
                    System.out.print("Write down the name of new vertex: ");
                    String newName = sc.next();
                    System.out.print("Write down adjacency vertexes: ");
                    String adjList = sc.nextLine();
                    adjList = sc.nextLine();
                    if(G.addVertex(newName, adjList))
                        M.addVertInMx(G);
                    break;
                case 4:
                    System.out.print("Write down the name of vertex which u want to delete: ");
                    String newName2 = sc.next();
                    G.deleteVertex(newName2, M);
                    break;
                case 5:
                    M.printMatrix(G);
                    System.out.print("Write down the number of vertex which will be start: ");
                    int name7 = sc.nextInt();
                    System.out.println(Arrays.toString(M.DISTMatWithLibQueue(name7)));
                    break;
                case 6:
                    M.printMatrix(G);
                    System.out.print("Write down the number of vertex which will be start: ");
                    int name2 = sc.nextInt();
                    System.out.println(Arrays.toString(M.DistDFSMat(name2)));
                    break;
                case 7:
                    System.out.println(" G");
                    G.printGraph();
                    System.out.print("Write down the name of vertex which will be start: ");
                    String name1 = sc.next();
                    System.out.println(Arrays.toString(G.DISTVertsLibQueue(name1)));
                    break;
                case 8:
                    System.out.println(" G");
                    G.printGraph();
                    System.out.print("Write down the name of vertex which will be start: ");
                    String name3 = sc.next();
                    System.out.println(Arrays.toString(G.DistDFSVert(name3)));
                    break;
                case 9:
                    M.printMatrix(G);
                    System.out.print("Write down the number of vertex which will be start: ");
                    int name4 = sc.nextInt();

                    long start = System.nanoTime();
                    int[] arr = M.DISTMatWithLibQueue(name4);
                    long end = System.nanoTime();
                    long duration = end - start;

                    System.out.println("(BFS) "+Arrays.toString(arr) + " Time spent: " + duration/1000000 + "ms");

                    start = System.nanoTime();
                    arr = M.DistDFSMat(name4);
                    end = System.nanoTime();
                    duration = end - start;

                    System.out.println("(DFS) "+Arrays.toString(arr) + " Time spent: " + duration/1000000 + "ms");
                    break;
                case 10:
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
