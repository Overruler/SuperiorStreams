package utils.lists2;

import utils.streams.functions.ExFunction;

interface CollectionSetAPI<T, SET extends CollectionSetAPI<T, SET>> extends RandomLookup<T, SET> {
	<U, E extends Exception> Object map(ExFunction<T, U, E> mapper) throws E;
	List<T> toList();
	ArrayList<T> toArrayList();
}
