package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOBinaryOperator<T> extends ExBinaryOperator<T, IOException> {}
