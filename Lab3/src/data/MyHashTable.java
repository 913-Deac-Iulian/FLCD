package data;

import java.util.ArrayList;

public class MyHashTable<T>{
    private final ArrayList<ArrayList<T>> values;

    public MyHashTable() {
        this.values = new ArrayList<>(13);
        int i = 0;
        while( i <= 12){
            this.values.add(i,new ArrayList<>());
            i++;
        }
    }

    public int hash(T value) {
        int sum = 0;
        for(char c : value.toString().toCharArray()){
            sum += c;
        }
        return sum % 13;
    }

    public Record<Integer, Integer> insert(T value){
        Record<Integer, Integer> index = lookup(value);
        if(index == null) {
            this.values.get(hash(value)).add(value);
        }
        return lookup(value);
    }

    public Record<Integer, Integer> lookup(T value){
        Integer index = 0;
        for(T v : this.values.get(hash(value))){
            if(v.equals(value)){
                return new Record<>(hash(value),index);
            }
            index++;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ArrayList<T> v : this.values){
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }
}