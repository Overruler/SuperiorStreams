package utils.streams2;

import java.io.IOException;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOIntBinaryOperator;
import utils.streams.functions.IOObjIntConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class RioIntStream<A extends AutoCloseable> extends AbstractIntStream<IOException,
AutoCloseableStrategy<A, java.util.stream.IntStream>,
RioStream<A, Integer>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjIntConsumer<A>,
IOBiPredicate<A, Integer>,
Function<A, IOIntBinaryOperator>,
IOBiFunction<A, Integer, ? extends java.util.stream.IntStream>,
IOBiFunction<A, Integer, ? extends java.util.stream.LongStream>,
IOBiFunction<A, Integer, ? extends java.util.stream.DoubleStream>,
IOToIntBiFunction<A, Integer>,
IOToLongBiFunction<A, Integer>,
IOToDoubleBiFunction<A, Integer>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.IntStream>> supplierAC;

	public RioIntStream(Supplier<A> allocator, Function<A, java.util.stream.IntStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())));
	}
	<OLD> RioIntStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, java.util.stream.IntStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioIntStream(Supplier<AutoCloseableStrategy<A, java.util.stream.IntStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.IntStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.IntStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Integer> asOS(
		Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A>
		asSELF(Function<java.util.stream.IntStream, java.util.stream.IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A>
		asLS(Function<java.util.stream.IntStream, java.util.stream.LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asDS(
		Function<java.util.stream.IntStream, java.util.stream.DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(
		IOBiFunction<A, Integer, ? extends java.util.stream.IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(
		IOBiFunction<A, Integer, ? extends java.util.stream.LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOBiFunction<A, Integer, ? extends java.util.stream.DoubleStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override IntUnaryOperator castToInt(IOToIntBiFunction<A, Integer> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	protected @Override IntToLongFunction castToLong(IOToLongBiFunction<A, Integer> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	protected @Override IntToDoubleFunction castToDouble(IOToDoubleBiFunction<A, Integer> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	protected @Override IntBinaryOperator castToBinaryOperators(Function<A, IOIntBinaryOperator> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE());
	}
	protected @Override IntConsumer castToConsumers(IOObjIntConsumer<A> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	protected @Override IntPredicate castToPredicates(IOBiPredicate<A, Integer> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(IOBiFunction<A, Integer, ? extends R> mapper) {
		return mapInternal(castToMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(
		BiFunction<A, Integer, ? extends R> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioIntStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Integer, ? extends RioStream<A, ? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
		BiFunction<A, Integer, ? extends RioStream<A, ? extends R>> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioIntStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, int[]> toMap(IOBiFunction<A, Integer, ? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IOBiFunction<A, Integer, ? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	public IOIntStream toIO() {
		return new IOIntStream(() -> supplierAC.get().user);
	}
	private <K> Function<IOBiFunction<A, Integer, ? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> IntFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Integer, ? extends RioStream<A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> IntFunction<? extends R> castToMapFunctions(BiFunction<A, Integer, ? extends R> mapping) {
		return t -> mapping.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}