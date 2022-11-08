package scanner;

import data.MyHashTable;
import data.Record;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    public String inputFile;
    public String input;
    public ArrayList<String> reservedTokens;

    public MyHashTable<String> idSymbolTable;

    public MyHashTable<String> constSymbolTable;

    public Scanner(String inputFile, String tokensFile) throws FileNotFoundException {
        this.inputFile = inputFile;
        this.reservedTokens = FileScanner.readTokens(tokensFile);
        this.input = FileScanner.readInputFile(inputFile);
        this.idSymbolTable = new MyHashTable<>();
        this.constSymbolTable = new MyHashTable<>();
    }

    public ArrayList<Record<String,Record<Integer,Integer>>> run() throws IOException {
        ArrayList<String> tokens = tokenizeInput();
        ArrayList<Record<String, Record<Integer,Integer>>> result = new ArrayList<>();
        
        for(String t : tokens){
            if(isReserved(t))
                result.add(new Record<>(t, null));
            else if (isReserved(t.toUpperCase())) {
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            } else if (isConstant(t)) {
                Record<Integer,Integer> position = this.constSymbolTable.lookup(t);
                if(position == null) {
                    position = this.constSymbolTable.insert(t);
                }
                result.add(new Record<>("const", position));
            }
            else if (isConstant(t.toUpperCase())) {
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            }
            else if (isIdentifier(t)) {
                Record<Integer,Integer> position = this.idSymbolTable.lookup(t);
                if(position == null) {
                    position = this.idSymbolTable.insert(t);
                }
                result.add(new Record<>("id", position));
            }
            else{
                throw new RuntimeException("Lexical error " + t + " on line " + FileScanner.getErrorLine(inputFile, t));
            }
        }
        return result;
    }

    public boolean isIdentifier(String token) {
        String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digit = "0123456789";
        Pattern identifierPattern = Pattern.compile("^[" + letter + "][" + letter + digit + "]*");
        Matcher matcher = identifierPattern.matcher(token);
        return matcher.find();
    }

    public boolean isConstant(String token) {
        if ("TRUE".equals(token) || "FALSE".equals(token) || "0".equals(token)) {
            return true;
        }
        String nzDigit = "123456789";
        String digit = "0" + nzDigit;
        Pattern identifierPattern = Pattern.compile("^[+-]?[" + nzDigit + "][" + digit + "]*");
        Matcher matcher = identifierPattern.matcher(token);
        return matcher.find();
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
        for (int i = 0; i < characters.length; i++){
            if (this.reservedTokens.contains(String.valueOf(characters[i]))) {
                if(!word.isEmpty())
                    result.add(word.toString());
                if (!(characters[i] == ' ')) {
                    if(specialOperators.contains(String.valueOf(characters[i]))) {
                        if(specialOperators.contains(String.valueOf(characters[i + 1]))) {
                            result.add(String.valueOf(characters[i]) + characters[i + 1]);
                            i++;
                        }
                        else{
                            result.add(String.valueOf(characters[i]));
                        }
                    }
                    else{
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
}
