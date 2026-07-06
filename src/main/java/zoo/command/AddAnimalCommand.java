package zoo.command;

import zoo.animal.Animal;
import zoo.enclosure.Enclosure;

public class AddAnimalCommand<T extends Animal> implements Command<Enclosure<? super T>> {

    private final T animal;
    private boolean executed = false;

    public AddAnimalCommand(T animal) {
        this.animal = animal;
    }

    @Override
    public Result<ZooError, Boolean> execute(Enclosure<? super T> target) {
        // 1. Wir versuchen, das Tier ins Gehege zu setzen
        boolean success = target.add(animal);

        if (success) {
            this.executed = true;
            // Erfolg! Wir legen ein "Success" mit dem Wert true in den Umschlag
            return new Result.Success<>(true);
        } else {
            // Fehler! Das Tier war schon drin. Wir legen ein "Failure" in den Umschlag
            return new Result.Failure<>(ZooError.ANIMAL_ALREADY_EXISTS);
        }
    }

    @Override
    public Result<ZooError, Boolean> undo(Enclosure<? super T> target) {
        // 1. Sicherheitsprüfung: Wurde das Kommando überhaupt schon ausgeführt?
        if (!executed) {
            return new Result.Failure<>(ZooError.COMMAND_NOT_YET_EXECUTED);
        }

        // 2. Wir versuchen, das Tier wieder zu entfernen
        boolean success = target.remove(animal);

        if (success) {
            this.executed = false; // Zurückgesetzt
            return new Result.Success<>(true);
        } else {
            // Tier wurde nicht gefunden (vielleicht wurde es manuell gelöscht)
            return new Result.Failure<>(ZooError.ANIMAL_NOT_FOUND);
        }
    }

    @Override
    public String description() {
        return "Füge Tier hinzu: " + animal.getName();
    }
}