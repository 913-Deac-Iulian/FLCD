import scanner.FileScanner;
import scanner.Scanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO change inputFile - first parameter of Scanner constructor for different input file
        Scanner scanner = new Scanner("src/input/p2.in", "src/input/tokens.in");
        FileScanner.writePIFToFile("src/output/PIF.out", scanner.run());
        FileScanner.writeSTToFile("src/output/idST.out", scanner.idSymbolTable.toString());
        FileScanner.writeSTToFile("src/output/constST.out", scanner.constSymbolTable.toString());
        System.out.println("Lexically correct");
    }
}