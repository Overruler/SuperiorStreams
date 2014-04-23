package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOToIntFunction<T> extends EXToIntFunction<T, IOException> {}
