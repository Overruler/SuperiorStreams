package utils.streams.functions;

import java.io.IOException;

@FunctionalInterface
public interface IOObjLongConsumer<T> extends ExObjLongConsumer<T, IOException> {}
