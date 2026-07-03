package zoo.animal;

public non-sealed interface Fish extends Animal {}

record Cod(String name) implements Fish {
    @Override
    public String getName() {
        return name;
    }
}