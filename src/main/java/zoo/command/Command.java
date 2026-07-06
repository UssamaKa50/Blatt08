package zoo.command;

public interface Command<T> {
    Result<ZooError, Boolean> execute(T target);
    Result<ZooError, Boolean> undo(T target);
    String description();
}