package zoo.animal;

import zoo.enclosure.Enclosure;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Zoo {
    // 1. Das Log-Tagebuch
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Zoo.class.getName());

    // 2. DIE LISTE FÜR ALLE GEHEGE (Die hat gefehlt!)
    private final List<Enclosure<?>> enclosures = new ArrayList<>();

    // Gehege hinzufügen
    public void addEnclosure(Enclosure<?> enclosure) {
        logger.info("addEnclosure aufgerufen für: " + enclosure.getName());

        enclosures.add(enclosure); // Fügt das einzelne Gehege zur Liste hinzu

        logger.fine("Gehege erfolgreich hinzugefügt. Anzahl Gehege: " + enclosures.size());
    }

    // Alle Gehege holen
    public List<Enclosure<?>> getEnclosures() {
        return List.copyOf(enclosures);
    }

    // Gehege nach Name suchen
    public Optional<Enclosure<?>> findEnclosureByName(String name) {
        logger.info("findEnclosureByName aufgerufen für Name: " + name);

        Optional<Enclosure<?>> result = enclosures.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name))
                .findFirst();

        if (result.isEmpty()) {
            logger.warning("Gehege mit dem Namen '" + name + "' wurde nicht gefunden!");
        }

        return result;
    }

    // Ab hier deine perfekten Streams aus Aufgabe 1.4:
    public List<Animal> getAllAnimals() {
        return enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.toList());
    }

    public List<Mammal> getAllMammals() {
        return enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .filter(Mammal.class::isInstance)
                .map(Mammal.class::cast)
                .collect(Collectors.toList());
    }

    public List<Animal> getAnimalsByPredicate(Predicate<Animal> predicate) {
        return enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<Enclosure<?>> getOvercrowdedEnclosures(int maxAnimals) {
        return enclosures.stream()
                .filter(e -> e.getInhabitants().size() > maxAnimals)
                .collect(Collectors.toList());
    }

    public Map<Class<? extends Animal>, Long> countAnimalsByType() {
        return enclosures.stream()
                .flatMap(e -> e.getInhabitants().stream())
                .collect(Collectors.groupingBy(Animal::getClass, Collectors.counting()));
    }

    public String summary() {
        long totalAnimals = enclosures.stream().mapToLong(e -> e.getInhabitants().size()).sum();
        Map<Class<? extends Animal>, Long> counts = countAnimalsByType();
        String stats = counts.entrySet().stream()
                .map(entry -> entry.getKey().getSimpleName() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        return String.format("Zoo mit %d Gehegen und %d Tieren: %s", enclosures.size(), totalAnimals, stats);
    }
}