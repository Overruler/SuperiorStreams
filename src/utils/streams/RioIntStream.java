package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOIntBinaryOperator;
import utils.streams.functions.IOObjIntConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;

//*Q*
public final class RioIntStream<A extends AutoCloseable> extends AbstractIntStream<IOException,
AutoCloseableStrategy<A, IntStream>,
RioStream<A, Integer>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjIntConsumer<A>,
IOBiPredicate<A, Integer>,
Function<A, IOIntBinaryOperator>,
IOBiFunction<A, Integer, ? extends IntStream>,
IOBiFunction<A, Integer, ? extends LongStream>,
IOBiFunction<A, Integer, ? extends DoubleStream>,
IOToIntBiFunction<A, Integer>,
IOToLongBiFunction<A, Integer>,
IOToDoubleBiFunction<A, Integer>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, IntStream>> supplierAC;

	public RioIntStream(Supplier<A> allocator, Function<A, IntStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())));
	}
	<OLD> RioIntStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, IntStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioIntStream(Supplier<AutoCloseableStrategy<A, IntStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override IntStream castToStream(AutoCloseableStrategy<A, IntStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Integer> asOS(Function<IntStream, Stream<Integer>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A> asSELF(Function<IntStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A> asLS(Function<IntStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asDS(Function<IntStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Integer, ? extends IntStream> castToIntStream(
	  IOBiFunction<A, Integer, ? extends IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends LongStream> castToLongStream(
	  IOBiFunction<A, Integer, ? extends LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
	  IOBiFunction<A, Integer, ? extends DoubleStream> mapper) {
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
	public <R> RioStream<A, R> map(IOBiFunction<A, Integer, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Integer, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Integer, ? extends Stream<? extends R>> mapper,
	  IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
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
	private <K> Function<IOBiFunction<A, Integer, ? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Integer, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOBiFunction<A, Integer, ? extends Stream<? extends R>> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> IntFunction<? extends R> castToMapFunctions(IOBiFunction<A, Integer, ? extends R> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<IntStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}