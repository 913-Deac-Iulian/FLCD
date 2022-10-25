package com.example;

import java.util.Random;

public class App
{
    public static void main( String[] args )
    {
        MyHashTable<String, Object> idHashTable = new MyHashTable<>();
        MyHashTable<Object, Integer> constHashTable = new MyHashTable<>();
        Random random = new Random();

        char c = 'a';
        int i = 0;
        while(i <= 100){
            if(i < 25) {
                System.out.println("add (" + c + ", -1)");
                idHashTable.insert(String.valueOf(c), -1);
                c++;
            }
            if(i%2==0) {
                var value = random.nextInt();
                constHashTable.insert(value, i);
                System.out.println("add constant (" + value + ", " + i + ")");
            }
            else{
                var value = random.nextFloat();
                constHashTable.insert(value, i);
                System.out.println("add constant (" + value + ", " + i + ")");
            }
            idHashTable.insert(String.valueOf(c), -1);
            i++;
        }
        System.out.println(idHashTable + "\n");
        System.out.println(constHashTable + "\n");

        System.out.println("add (a,2)");
        idHashTable.insert("a", 2);
        System.out.println(idHashTable + "\n");

        System.out.println("lookup (a)");
        System.out.println((idHashTable.lookup("a")) + "\n");

        System.out.println("add (a,3)");
        idHashTable.insert("a", 3);
        System.out.println(idHashTable + "\n");

        System.out.println("lookup (a)");
        System.out.println((idHashTable.lookup("a")) + "\n");

        System.out.println("lookup (A)");
        System.out.println((idHashTable.lookup("A")) + "\n");

        System.out.println("lookup 3");
        System.out.println((constHashTable.lookup("3")) + "\n");

        System.out.println("add const (3, 101)");
        constHashTable.insert(3, 101);

        System.out.println("lookup 3");
        System.out.println((constHashTable.lookup(3)) + "\n");

        System.out.println("add const (3, 102)");
        constHashTable.insert(3, 102);

        System.out.println(constHashTable);
    }
}
