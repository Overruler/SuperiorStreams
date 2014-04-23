package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToLongFunction<T> extends ExToLongFunction<T, IOException> {}
