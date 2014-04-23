package utils.streams;

import java.util.function.Supplier;

class CachedSupplierBuilder {

	static @SafeVarargs <A> CachedSupplier<A> create(Supplier<A> allocator, A... allocated) {
		return () -> allocated[0] != null ? allocated[0] : (allocated[0] = allocator.get());
	}
}
interface CachedSupplier<A> extends Supplier<A> {

	static <A> CachedSupplier<A> create(Supplier<A> allocator) {
		return CachedSupplierBuilder.create(allocator, (A) null);
	}
}
