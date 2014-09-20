package utils.lists2;

import java.util.Iterator;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

public interface Collection<T, C extends Collection<T, C>> extends Iterable<T> {
	default C addAll(@SuppressWarnings("unchecked") T... items) {
		C self = identity();
		for(T item : items) {
			self = self.add(item);
		}
		return self;
	}
	default C addAll(Iterable<T> items) {
		C self = identity();
		for(T item : items) {
			self = self.add(item);
		}
		return self;
	}
	default C clear() {
		C self = identity();
		while(self.notEmpty()) {
			self = self.remove(self.iterator().next());
		}
		return self;
	}
	default C retainAll(Iterable<T> items) {
		C self = identity();
		for(T item : items) {
			if(self.contains(item) == false) {
				self = self.remove(item);
			}
		}
		return self;
	}
	default C removeAll(Iterable<T> items) {
		C self = identity();
		for(T item : items) {
			if(self.contains(item)) {
				self = self.remove(item);
			}
		}
		return self;
	}
	default <E extends Exception> C filter(ExPredicate<T, E> filter) throws E {
		for(Iterator<T> iter = iterator(); iter.hasNext();) {
			T item = iter.next();
			if(filter.test(item) == false) {
				iter.remove();
			}
		}
		return identity();
	}
	default <E extends Exception> C removeIf(ExPredicate<T, E> filter) throws E {
		for(Iterator<T> iter = iterator(); iter.hasNext();) {
			T item = iter.next();
			if(filter.test(item)) {
				iter.remove();
			}
		}
		return identity();
	}
	default <E extends Exception> C each(ExConsumer<T, E> action) throws E {
		for(T item : this) {
			action.accept(item);
		}
		return identity();
	}
	C add(T item);
	C remove(T item);
	<E extends Exception> C replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	C identity();
}
