package org.example;

public sealed interface Animal permits Mammal, Fish, Reptile, Bird {
    String getName();
}