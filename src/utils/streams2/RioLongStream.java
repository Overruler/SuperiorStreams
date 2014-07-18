package utils.streams2;

import java.io.IOException;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOLongBinaryOperator;
import utils.streams.functions.IOObjLongConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;
import utils.streams.functions.LongBinaryOperator;
import utils.streams.functions.LongConsumer;
import utils.streams.functions.LongFunction;
import utils.streams.functions.LongPredicate;
import utils.streams.functions.LongToDoubleFunction;
import utils.streams.functions.LongToIntFunction;
import utils.streams.functions.LongUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class RioLongStream<A extends AutoCloseable> extends AbstractLongStream<IOException,
AutoCloseableStrategy<A, java.util.stream.LongStream>,
RioStream<A, Long>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjLongConsumer<A>,
IOBiPredicate<A, Long>,
Function<A, IOLongBinaryOperator>,
IOBiFunction<A, Long, ? extends java.util.stream.IntStream>,
IOBiFunction<A, Long, ? extends java.util.stream.LongStream>,
IOBiFunction<A, Long, ? extends java.util.stream.DoubleStream>,
IOToIntBiFunction<A, Long>,
IOToLongBiFunction<A, Long>,
IOToDoubleBiFunction<A, Long>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.LongStream>> supplierAC;

	public RioLongStream(Supplier<A> allocator, Function<A, java.util.stream.LongStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())));
	}
	<OLD> RioLongStream(Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, java.util.stream.LongStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioLongStream(Supplier<AutoCloseableStrategy<A, java.util.stream.LongStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	private A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.LongStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.LongStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Long> asOS(
		Function<java.util.stream.LongStream, java.util.stream.Stream<Long>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A> asIS(Function<java.util.stream.LongStream, java.util.stream.IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A> asSELF(
		Function<java.util.stream.LongStream, java.util.stream.LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asDS(
		Function<java.util.stream.LongStream, java.util.stream.DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.IntStream> castToIntStream(
		IOBiFunction<A, Long, ? extends java.util.stream.IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.LongStream> castToLongStream(
		IOBiFunction<A, Long, ? extends java.util.stream.LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOBiFunction<A, Long, ? extends java.util.stream.DoubleStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override LongToIntFunction castToInt(IOToIntBiFunction<A, Long> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	protected @Override LongUnaryOperator castToLong(IOToLongBiFunction<A, Long> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	protected @Override LongToDoubleFunction castToDouble(IOToDoubleBiFunction<A, Long> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	protected @Override LongBinaryOperator castToBinaryOperators(Function<A, IOLongBinaryOperator> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE());
	}
	protected @Override LongConsumer castToConsumers(IOObjLongConsumer<A> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	protected @Override LongPredicate castToPredicates(IOBiPredicate<A, Long> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(IOBiFunction<A, Long, ? extends R> mapper) {
		return mapInternal(castToMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R>
		map(BiFunction<A, Long, ? extends R> mapper, LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioLongStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Long, ? extends RioStream<A, ? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
		BiFunction<A, Long, ? extends RioStream<A, ? extends R>> mapper,
		LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioLongStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<? extends K, long[]> toMap(IOBiFunction<A, Long, ? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IOBiFunction<A, Long, ? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<long[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<IOBiFunction<A, Long, ? extends K>, LongFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> LongFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Long, ? extends RioStream<A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> LongFunction<? extends R> castToMapFunctions(BiFunction<A, Long, ? extends R> mapper2) {
		return t -> mapper2.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}