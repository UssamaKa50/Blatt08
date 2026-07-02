package org.example;

public non-sealed interface Reptile extends Animal {}

record Snake(String name) implements Reptile {
    @Override
    public String getName() {
        return name;
    }
}