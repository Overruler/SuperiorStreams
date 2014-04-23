package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToDoubleFunction<T> extends ExToDoubleFunction<T, IOException> {}
