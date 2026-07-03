package zoo.animal;

public non-sealed interface Mammal extends Animal {}

// Die können hier ohne public einfach mitleben
interface Primate extends Mammal {}
interface Rodent extends Mammal {}

record Chimpanzee(String name) implements Primate {
    @Override public String getName() { return name; }
}
record Mouse(String name) implements Rodent {
    @Override public String getName() { return name; }
}