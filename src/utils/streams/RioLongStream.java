package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOLongBinaryOperator;
import utils.streams.functions.IOObjLongConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;

//*Q*
public final class RioLongStream<A extends AutoCloseable> extends AbstractLongStream<IOException,
AutoCloseableStrategy<A, LongStream>,
RioStream<A, Long>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjLongConsumer<A>,
IOBiPredicate<A, Long>,
Function<A, IOLongBinaryOperator>,
IOBiFunction<A, Long, ? extends IntStream>,
IOBiFunction<A, Long, ? extends LongStream>,
IOBiFunction<A, Long, ? extends DoubleStream>,
IOToIntBiFunction<A, Long>,
IOToLongBiFunction<A, Long>,
IOToDoubleBiFunction<A, Long>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, LongStream>> supplierAC;

	public RioLongStream(Supplier<A> allocator, Function<A, LongStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())));
	}
	<OLD> RioLongStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, LongStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioLongStream(Supplier<AutoCloseableStrategy<A, LongStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	private A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override LongStream castToStream(AutoCloseableStrategy<A, LongStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Long> asOS(Function<LongStream, Stream<Long>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A> asIS(Function<LongStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A> asSELF(Function<LongStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asDS(Function<LongStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Long, ? extends IntStream> castToIntStream(
	  IOBiFunction<A, Long, ? extends IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends LongStream> castToLongStream(
	  IOBiFunction<A, Long, ? extends LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends DoubleStream> castToDoubleStream(
	  IOBiFunction<A, Long, ? extends DoubleStream> mapper) {
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
	public <R> RioStream<A, R> map(IOBiFunction<A, Long, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Long, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Long, ? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
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
	private <R> Function<Long, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOBiFunction<A, Long, ? extends Stream<? extends R>> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> LongFunction<? extends R> castToMapFunctions(IOBiFunction<A, Long, ? extends R> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<LongStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}