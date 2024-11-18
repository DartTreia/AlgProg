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
            System.out.println("5. Deep loop find(Incident list)");
            System.out.println("6. Deep non-loop find(Incident list)");
            System.out.println("7. Deep loop find(Matrix)");
            System.out.println("8. Deep non-loop find(Matrix)");
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
                    System.out.print("Write down the number of vertex which will be start: ");
                    int name7 = sc.nextInt();
                    System.out.println(Arrays.toString(M.BFSMatWithLibQueue(name7)));
                    break;
                case 6:
                    System.out.print("Write down the name of vertex which will be start: ");
                    String name2 = sc.next();
                    Vertex[] temp2 = null;
                    temp2 = G.BFSVertsLibQueue(name2);
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
                    System.out.print("Write down the number of vertex which will be start: ");
                    int name8 = sc.nextInt();
                    System.out.println(Arrays.toString(M.BFSMatWithMyQueue(name8)));
                    break;
                case 8:

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
