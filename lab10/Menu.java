import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    private Matrix M;
    public Menu(Matrix M){
        this.M = M;
        display(M);
    }
    static Scanner sc = new Scanner(System.in);
    public void clearConsole(){
        System.out.print("\n\n");
    }
    public void display(Matrix M) {
        while(true) {
            System.out.println("------------MENU------------");
            System.out.println("1. Show matrix");
            System.out.println("2. Find eccentricity");
            System.out.println("3. Find diameter");
            System.out.println("4. Find radius");
            System.out.println("5. Show central vertexes");
            System.out.println("6. Show periphery vertexes");
            System.out.println("7. Exit");
            int choice = sc.nextInt();
            clearConsole();
            switch (choice) {
                case 1:
                    M.printMatrix();
                    break;
                case 2:
                    M.findEccentricity();
                    M.writeDists();
                    System.out.println("\n"+"Eccentricities: "+Arrays.toString(M.getEccentricities()));
                    break;
                case 3:
                    M.writeDists();
                    System.out.println("\n"+"Eccentricities: "+Arrays.toString(M.getEccentricities()));
                    System.out.println("Graph's diameter is "+M.findDiameter());
                    break;
                case 4:
                    M.writeDists();
                    System.out.println("\n"+"Eccentricities: "+Arrays.toString(M.getEccentricities()));
                    System.out.println("Graph's radius is "+M.findRadius());
                    break;
                case 5:
                    M.writeDists();
                    System.out.println("\n"+"Eccentricities: "+Arrays.toString(M.getEccentricities()));
                    System.out.print("Central vertexes are "+Arrays.toString(Arrays.stream(M.findCentralVertex()).filter(n->n!=0).toArray()));
                    break;
                case 6:
                    M.writeDists();
                    System.out.println("\n"+"Eccentricities: "+Arrays.toString(M.getEccentricities()));
                    System.out.print("Periphery vertexes are "+Arrays.toString(Arrays.stream(M.findPeripheryVertex()).filter(n->n!=0).toArray()));
                    break;
                case 7:
                    System.exit(0);
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
