package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOBiPredicate<T, U> extends ExBiPredicate<T, U, IOException> {}
