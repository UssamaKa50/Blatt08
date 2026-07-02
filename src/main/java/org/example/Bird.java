package org.example;

public non-sealed interface Bird extends Animal {}

record Eagle(String name) implements Bird {
    @Override
    public String getName() {
        return name;
    }
}