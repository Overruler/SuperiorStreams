package utils.streams2;

import utils.streams.functions.BiFunction;
import utils.streams.functions.Function;
import utils.streams.functions.Supplier;

final class AutoCloseableStrategy<RESOURCE, USER> {
	public <OLD_USER> AutoCloseableStrategy(Supplier<AutoCloseableStrategy<RESOURCE, OLD_USER>> older,
		Function<OLD_USER, USER> update) {
		AutoCloseableStrategy<RESOURCE, OLD_USER> old = older.get();
		this.resource = old.resource;
		this.user = update.apply(old.user);
	}
	public <HOLDER> AutoCloseableStrategy(Supplier<RESOURCE> resourceSupplier,
		Function<RESOURCE, HOLDER> resourceHolderSupplier, BiFunction<RESOURCE, HOLDER, HOLDER> resourceAttacher,
		Function<HOLDER, USER> resourceUserSupplier) {
		this.resource = resourceSupplier.get();
		this.user =
			resourceUserSupplier.apply(resourceAttacher.apply(resource, resourceHolderSupplier.apply(resource)));
	}

	public final RESOURCE resource;
	public final USER user;
}