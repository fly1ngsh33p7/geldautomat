package view;

@FunctionalInterface
public interface BooleanConsumer<T> {
    boolean accept(T parameter);
}