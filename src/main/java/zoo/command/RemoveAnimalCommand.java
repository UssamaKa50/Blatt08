package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

public class RemoveAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed = false;

    public RemoveAnimalCommand(T animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, Boolean> execute(Enclosure<? super T> target) {
        // 1. Wir versuchen, das Tier zu entfernen
        boolean success = target.remove(animal);

        if (success) {
            this.executed = true;
            // Erfolg! Wir geben ein Success mit true zurück
            return new Result.Success<>(true);
        } else {
            // Fehler! Das Tier war gar nicht im Gehege
            return new Result.Failure<>(ZooError.ANIMAL_NOT_FOUND);
        }
    }

    @Override
    public Result<ZooError, Boolean> undo(Enclosure<? super T> target) {
        // 1. Prüfen, ob wir überhaupt schon ausgeführt wurden
        if (!executed) {
            return new Result.Failure<>(ZooError.COMMAND_NOT_YET_EXECUTED);
        }

        // 2. Beim Undo machen wir das Entfernen rückgängig -> also fügen wir das Tier wieder hinzu!
        boolean success = target.add(animal);

        if (success) {
            this.executed = false; // Zustand zurückgesetzt
            return new Result.Success<>(true);
        } else {
            // Fehler! Das Tier konnte nicht wieder hinzugefügt werden (z.B. Gehege blockiert)
            return new Result.Failure<>(ZooError.ANIMAL_ALREADY_EXISTS);
        }
    }

    @Override
    public String description() {
        return "Entferne Tier: " + animal.name();
    }
}