package zoo.command;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class CommandManager {

    // Unser offizielles Log-Buch für den Zoo
    private static final Logger logger = Logger.getLogger(CommandManager.class.getName());

    private final List<Command<Object>> undoStack = new LinkedList<>();
    private final List<Command<Object>> redoStack = new LinkedList<>();

    @SuppressWarnings("unchecked")
    public <T> void executeCommand(Command<T> command, T target) {
        logger.info("Führe Befehl aus: " + command.description());

        // 1. Befehl ausführen und den Umschlag erhalten
        Result<ZooError, Boolean> result = command.execute(target);

        // 2. Pattern Matching: Was steht im Umschlag?
        switch (result) {
            case Result.Success<ZooError, Boolean> success -> {
                logger.info("ERFOLG: " + command.description() + " war erfolgreich.");
                undoStack.add(0, (Command<Object>) command);
                redoStack.clear();
            }
            case Result.Failure<ZooError, Boolean> failure -> {
                logger.warning("FEHLER beim Ausführen von '" + command.description() + "'. Grund: " + failure.error());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void undo(Object target) {
        if (undoStack.isEmpty()) {
            logger.info("Undo nicht möglich: Keine Befehle auf dem Stapel.");
            return;
        }

        Command<Object> command = undoStack.get(0); // Erst mal nur anschauen
        logger.info("Rufe Undo auf für: " + command.description());

        Result<ZooError, Boolean> result = command.undo(target);

        switch (result) {
            case Result.Success<ZooError, Boolean> success -> {
                logger.info("ERFOLG: Undo für '" + command.description() + "' durchgeführt.");
                undoStack.remove(0); // Jetzt erst vom Stapel runternehmen
                redoStack.add(0, command); // Auf den Wiederholen-Stapel legen
            }
            case Result.Failure<ZooError, Boolean> failure -> {
                logger.severe("CRITICAL: Undo fehlgeschlagen für '" + command.description() + "'. Grund: " + failure.error());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void redo(Object target) {
        if (redoStack.isEmpty()) {
            logger.info("Redo nicht möglich: Keine Befehle zum Wiederholen.");
            return;
        }

        Command<Object> command = redoStack.get(0);
        logger.info("Rufe Redo auf für: " + command.description());

        Result<ZooError, Boolean> result = command.execute(target);

        switch (result) {
            case Result.Success<ZooError, Boolean> success -> {
                logger.info("ERFOLG: Redo für '" + command.description() + "' durchgeführt.");
                redoStack.remove(0);
                undoStack.add(0, command);
            }
            case Result.Failure<ZooError, Boolean> failure -> {
                logger.severe("CRITICAL: Redo fehlgeschlagen für '" + command.description() + "'. Grund: " + failure.error());
            }
        }
    }
}