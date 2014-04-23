package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToDoubleBiFunction<T, U> extends ExToDoubleBiFunction<T, U, IOException> {}
