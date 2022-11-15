package scanner;

import data.*;
import data.Record;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
    public String inputFile;
    public String input;
    public ArrayList<String> reservedTokens;

    public MyHashTable<String> idSymbolTable;

    public MyHashTable<String> constSymbolTable;

    public FA idFA;

    public FA constFA;



    public Scanner(String inputFile, String tokensFile, String idFAFile, String constFAFile) throws FileNotFoundException {
        this.inputFile = inputFile;
        this.reservedTokens = FileScanner.readTokens(tokensFile);
        this.input = FileScanner.readInputFile(inputFile);
        this.idSymbolTable = new MyHashTable<>();
        this.constSymbolTable = new MyHashTable<>();
        this.idFA = FileScanner.readFA(idFAFile);
        this.constFA = FileScanner.readFA(constFAFile);
    }

    public ArrayList<Record<String, Record<Integer, Integer>>> run() throws IOException {
        ArrayList<String> tokens = tokenizeInput();
        ArrayList<Record<String, Record<Integer, Integer>>> result = new ArrayList<>();

        for (String t : tokens) {
            if (isReserved(t))
                result.add(new Record<>(t, null));
            else if (isReserved(t.toUpperCase())) {
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            } else if (isConstant(t)) {
                Record<Integer, Integer> position = this.constSymbolTable.lookup(t);
                if (position == null) {
                    position = this.constSymbolTable.insert(t);
                }
                result.add(new Record<>("const", position));
            } else if (isConstant(t.toUpperCase())) {
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            } else if (isIdentifier(t)) {
                Record<Integer, Integer> position = this.idSymbolTable.lookup(t);
                if (position == null) {
                    position = this.idSymbolTable.insert(t);
                }
                result.add(new Record<>("id", position));
            } else {
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            }
        }
        return result;
    }

    public boolean isIdentifier(String token) {
        return isAcceptedByFa(idFA,token);
    }

    public boolean isConstant(String token) {
        if ("TRUE".equals(token) || "FALSE".equals(token) || "0".equals(token)) {
            return true;
        }
        return isAcceptedByFa(constFA, token);
    }

    public boolean isReserved(String token) {
        for (String t : reservedTokens) {
            if (t.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> tokenizeInput() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        ArrayList<String> specialOperators = new ArrayList<>();
        specialOperators.add("<");
        specialOperators.add(">");
        specialOperators.add("=");
        specialOperators.add("!");

        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (this.reservedTokens.contains(String.valueOf(characters[i]))) {
                if (!word.isEmpty())
                    result.add(word.toString());
                if (!(characters[i] == ' ')) {
                    if (specialOperators.contains(String.valueOf(characters[i]))) {
                        if (specialOperators.contains(String.valueOf(characters[i + 1]))) {
                            result.add(String.valueOf(characters[i]) + characters[i + 1]);
                            i++;
                        } else {
                            result.add(String.valueOf(characters[i]));
                        }
                    } else {
                        result.add(String.valueOf(characters[i]));
                    }
                }
                word = new StringBuilder();
            } else {
                word.append(characters[i]);
            }
        }
        if (!word.isEmpty())
            result.add(word.toString());
        return result;
    }

    public FA getIdFA() {
        return idFA;
    }

    public FA getConstFA() {
        return constFA;
    }

    public boolean isAcceptedByFa(FA fa, String input) {
        boolean value = false;
        if (input == null || input.isEmpty()) {
            for(String fs : fa.getFinalStates()){
                for (String is : fa.getInitialStates()){
                    if(fs.equals(is)){
                        return true;
                    }
                }
            }
        } else {
            List<Transition> transitions = fa.getTransitions().stream().filter(v -> v.getState1().isInitial()).toList();

            for (Transition t : transitions) {
                if (t.getValue().equals(String.valueOf(input.charAt(0)))) {
                    value = value || isAcceptedByFaRecursive(fa, t.getState2(), input.substring(1));
                }
            }
        }
        return value;
    }

    private boolean isAcceptedByFaRecursive(FA fa, State state, String input) {
        boolean value = false;
        if (input == null || input.isEmpty()) {
            return state.isFinal();
        } else {
            List<Transition> transitions = fa.getTransitions().stream().filter(v -> v.getState1().equals(state)).toList();

            for (Transition t : transitions) {
                if (t.getValue().equals(String.valueOf(input.charAt(0)))) {
                    value = value || isAcceptedByFaRecursive(fa, t.getState2(), input.substring(1));
                }
            }
        }
        return value;
    }
}
