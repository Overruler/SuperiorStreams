package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOObjIntConsumer<T> extends ExObjIntConsumer<T, IOException> {}
