package zoo.command;

public sealed interface Result<E, R> {
    record Success<E, R>(R value) implements Result<E, R> {}
    record Failure<E, R>(E error) implements Result<E, R> {}
}