package com.example;

import java.util.ArrayList;

public class MyHashTable<A,B>{
    private ArrayList<ArrayList<Record<A,B>>> records;

    public MyHashTable() {
        this.records = new ArrayList<>(13);
        int i = 0;
        while( i <= 12){
            this.records.add(i,new ArrayList<>());
            i++;
        }
    }

    public int hash(A key) {
        int sum = 0;
        for(char c : key.toString().toCharArray()){
            sum += c;
        }
        return sum % 13;
    }

    public void insert(A key, B value){
        boolean flag = false;
        for(Record<A,B> r : this.records.get(hash(key))){
            if(r.getKey().equals(key)){
                r.setValue(value);
                flag = true;
            }
        }
        if(!flag){
            this.records.get(hash(key)).add(new Record<>(key, value));
        }
    }

    public Record<A,B> lookup(A key){
        for(Record<A,B> r : this.records.get(hash(key))){
            if(r.getKey().equals(key)){
                return r;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(ArrayList<Record<A,B>> r : this.records){
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
}