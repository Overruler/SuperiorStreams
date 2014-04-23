package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOBiFunction<T, U, R> extends ExBiFunction<T, U, R, IOException> {}
