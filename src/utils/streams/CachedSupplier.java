package utils.streams;

import java.util.function.Supplier;
import org.eclipse.jdt.annotation.Nullable;

class CachedSupplierBuilder {

	@Nullable
	static @SafeVarargs <A> Supplier<@Nullable A> create(Supplier<@Nullable A> allocator, A... allocated) {
		return () -> allocated[0] != null ? allocated[0] : (allocated[0] = allocator.get());
	}
}
interface CachedSupplier<@Nullable A> extends Supplier<@Nullable A> {

	static <A> Supplier<@Nullable A> create(Supplier<@Nullable A> allocator) {
		@Nullable
		A a = null;
		return CachedSupplierBuilder.create(allocator, a);
	}
}
