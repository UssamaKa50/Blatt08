package zoo.enclosure;

import zoo.animal.Animal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


    public class Enclosure<T extends Animal>{

        private final String name;
        //Durch die Liste Set, werden keine doppelten Inhalte
        //zugelassen
        private final Set<T> inhabitants = new LinkedHashSet<>();

        public Enclosure(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    public boolean add(T animal){
        return inhabitants.add(animal);
    }

    public boolean remove(T animal){
        return inhabitants.remove(animal);
    }

    public List<T> getInhabitants(){
        return new ArrayList<>(inhabitants);
    }

        public Optional<T> findAnimalByName(String animalName) {
            return inhabitants.stream()
                    .filter(animal -> animal.name().equalsIgnoreCase(animalName))
                    .findFirst();
        }

}