package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOUnaryOperator<T> extends ExUnaryOperator<T, IOException> {}
