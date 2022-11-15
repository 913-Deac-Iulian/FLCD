import scanner.FileScanner;
import scanner.Scanner;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO change inputFile - first parameter of Scanner constructor for different input file
        Scanner scanner = new Scanner("src/input/p2.in", "src/input/tokens.in", "src/input/idFA.in", "src/input/constFA.in");
        FileScanner.writePIFToFile("src/output/PIF.out", scanner.run());
        FileScanner.writeSTToFile("src/output/idST.out", scanner.idSymbolTable.toString());
        FileScanner.writeSTToFile("src/output/constST.out", scanner.constSymbolTable.toString());
        System.out.println("Lexically correct");
        String option = "";
        while (!"6".equals(option))
        {
            showMenu();
            java.util.Scanner scanner1 = new java.util.Scanner(System.in);
            option = scanner1.next();
            switch (option) {
                case "1":
                    System.out.println(Arrays.toString(scanner.idFA.getStates()));
                    break;
                case "2":
                    System.out.println(Arrays.toString(scanner.idFA.getAlphabet()));
                    break;
                case "3":
                    System.out.println(Arrays.toString(scanner.idFA.getInitialStates()));
                    break;
                case "4":
                    System.out.println(Arrays.toString(scanner.idFA.getFinalStates()));
                    break;
                case "5":
                    for (var elem : scanner.idFA.getTransitions()) {
                        System.out.println(elem);
                    }
                    break;
                case "6":
                    exit(0);
                default:
                    System.out.println("Sorry, please enter valid option");
            }
        }
    }

    private static void showMenu() {
        System.out.println("1. set of states");
        System.out.println("2. alphabet");
        System.out.println("3. initial state");
        System.out.println("4. set of final states");
        System.out.println("5. transitions");
        System.out.println("6. exit");
    }
}