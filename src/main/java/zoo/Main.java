package zoo;

import zoo.animal.Mammal;
import zoo.command.AddAnimalCommand;
import zoo.command.Command;
import zoo.command.CommandManager;
import zoo.enclosure.Enclosure;


class TestAffe implements Mammal {

    @Override
    public String getName() {
        return "Kiki";
    }

    @Override
    public String name() {
        return "";
    }
}

public class Main {
    public static void main(String[] args) {
        CommandManager manager = new CommandManager();
        Enclosure<Mammal> monkeyHouse = new Enclosure<>("Affenhaus");

        // Wir erstellen Kiki ganz sauber über unsere Testklasse
        Mammal chimpanzee = new TestAffe();

        System.out.println("=== DEIN ZOO-COMMAND-SYSTEM SPEZIALTEST ===");

        // 2. Ersten Befehl erstellen: Kiki hinzufügen
        Command<Enclosure<? super Mammal>> addCommand1 = new AddAnimalCommand<>(chimpanzee);

        System.out.println("\n[TEST 1] Kiki das erste Mal hinzufügen:");
        manager.executeCommand(addCommand1, monkeyHouse);

        System.out.println("\n[TEST 2] Fehler provozieren (Kiki ein zweites Mal hinzufügen):");
        Command<Enclosure<? super Mammal>> addCommand2 = new AddAnimalCommand<>(chimpanzee);
        manager.executeCommand(addCommand2, monkeyHouse);

        System.out.println("\n[TEST 3] Undo aufrufen:");
        manager.undo(monkeyHouse);

        System.out.println("\n[TEST 4] Redo aufrufen:");
        manager.redo(monkeyHouse);

        System.out.println("\n=== TEST BEENDET ===");
    }
}