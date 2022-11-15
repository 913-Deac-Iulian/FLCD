package scanner;

import data.FA;
import data.State;
import data.Transition;

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
        for (var c : content) {
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

    public static FA readFA(String filename) throws FileNotFoundException {
        File text = new File(filename);
        Scanner scanner = new Scanner(text);
        ArrayList<Transition> transitions = new ArrayList<>();
        String[] states = new String[0];
        String[] initialStates = new String[0];
        String[] finalStates = new String[0];
        String[] alphabet = new String[0];

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().strip().split(":");
            if ("states".equals(line[0])) {
                states = line[1].strip().split(",");
            } else if ("alphabet".equals(line[0])) {
                alphabet = line[1].strip().split(",");
            } else if ("initial".equals(line[0])) {
                initialStates = line[1].strip().split(",");
            } else if ("final".equals(line[0])) {
                finalStates = line[1].strip().split(",");
            } else {
                String[] tr = line[0].strip().split(",");
                State state = new State(tr[0]);
                State state2 = new State(tr[1]);
                int statesFound = 0;
                for (String s : states) {
                    if (s.equals(tr[0]) || s.equals(tr[1])) {
                        if (tr[0].equals(tr[1])) {
                            statesFound += 2;
                        } else {
                            statesFound++;
                        }
                    }
                }
                if (statesFound != 2) {
                    throw new RuntimeException("State not found");
                }
                for (String s : initialStates) {
                    if (s.equals(tr[0])) {
                        state.setInitial(true);
                    }
                }
                for (String s : initialStates) {
                    if (s.equals(tr[1])) {
                        state2.setInitial(true);
                    }
                }
                for (String s : finalStates) {
                    if (s.equals(tr[0])) {
                        state.setFinal(true);
                    }
                }
                for (String s : finalStates) {
                    if (s.equals(tr[1])) {
                        state2.setFinal(true);
                    }
                }
                boolean isInAlphabet = false;
                for (String s : alphabet) {
                    if (s.equals(tr[2])) {
                        isInAlphabet = true;
                        break;
                    }
                }
                if (!isInAlphabet) {
                    throw new RuntimeException("Value not in alphabet");
                }
                Transition transition = new Transition(state, state2, tr[2]);
                transitions.add(transition);
            }
        }
        return new FA(transitions, states, initialStates, finalStates, alphabet);
    }
}