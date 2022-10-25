package com.example;

public class Record<A, B> {
    private A key;
    private B value;

    public Record(A key, B value) {
        this.key = key;
        this.value = value;
    }

    public A getKey() {
        return this.key;
    }

    public void setKey(A key) {
        this.key = key;
    }

    public B getValue() {
        return this.value;
    }

    public void setValue(B value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + "," + value + ")";
    }
}
