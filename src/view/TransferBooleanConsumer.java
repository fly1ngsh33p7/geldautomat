package view;

@FunctionalInterface
public interface TransferBooleanConsumer<T, U, V> {
    boolean accept(T t, U u, V v);
}