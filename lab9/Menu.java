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
            System.out.println("5. BFS for matrix");
            System.out.println("6. BFS for incident list");
            System.out.println("7. BFS with non-library queue");
            System.out.println("8. Compare time of library queue func ant non-library queue");
            System.out.println("9. Exit");
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
                    System.out.println(" G");
                    G.printGraph();
                    M.printMatrix(G);
                    System.out.print("Write down the number of vertex which will be start: ");
                    String name1 = sc.next();
                    System.out.println(Arrays.toString(G.DISTVertsLibQueue(name1)));
                    break;
                case 7:

                    break;
                case 8:
                    M.printMatrix(G);

                    System.out.print("Write down the number of vertex which will be start: ");
                    int name9 = sc.nextInt();

                    long startTime = System.nanoTime();
                    int[] arr = M.BFSMatWithLibQueue(name9);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);

                    System.out.println("Library queue: "+Arrays.toString(arr)+ "  Time spent: "+duration/1000000+" ms");

                    startTime = System.nanoTime();
                    endTime = System.nanoTime();
                    duration = (endTime - startTime);

                    System.out.println("My queue: "+Arrays.toString(arr)+ "  Time spent: "+duration/1000000+" ms");
                    break;
                case 9:
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
