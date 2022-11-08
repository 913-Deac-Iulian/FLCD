package scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileScanner {
    public static ArrayList<String> readTokens(String filename) throws FileNotFoundException {

        File text = new File(filename);
        Scanner scanner = new Scanner(text);
        ArrayList<String> result = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("SPACE")) {
                result.add(" ");
            } else {
                result.add(line);
            }
        }

        return result;
    }

    public static String readInputFile(String filename) throws FileNotFoundException {

        File text = new File(filename);
        Scanner scanner = new Scanner(text);
        StringBuilder sb = new StringBuilder();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            sb.append(line.strip());
        }

        return sb.toString();
    }

    public static void writePIFToFile(String filename, ArrayList<?> content) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(var c: content) {
            writer.write(c + System.lineSeparator());
        }
        writer.close();
    }

    public static void writeSTToFile(String filename, String content) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write("Used hash table as data structure\n");
        writer.write(content);
        writer.close();
    }

    public static int getErrorLine(String filename, String token) throws IOException {
        File text = new File(filename);
        Scanner scanner = new Scanner(text);

        int i = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(token)) {
                return i;
            }
            i++;
        }

        return -1;
    }
}