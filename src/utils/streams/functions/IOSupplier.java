package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOSupplier<T> extends ExSupplier<T, IOException> {}
