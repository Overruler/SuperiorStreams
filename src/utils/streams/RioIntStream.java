package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExIntBinaryOperator;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RioIntStream<A extends AutoCloseable> extends AbstractIntStream<IOException,
AutoCloseableStrategy<A, IntStream>,
RioStream<A, Integer>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
ExObjIntConsumer<A, IOException>,
ExBiPredicate<A, Integer, IOException>,
Function<A, ExIntBinaryOperator<IOException>>,
ExBiFunction<A, Integer, ? extends IntStream, IOException>,
ExBiFunction<A, Integer, ? extends LongStream, IOException>,
ExBiFunction<A, Integer, ? extends DoubleStream, IOException>,
ExToIntBiFunction<A, Integer, IOException>,
ExToLongBiFunction<A, Integer, IOException>,
ExToDoubleBiFunction<A, Integer, IOException>> {//*E*

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
	public @Override IntStream castToStream(AutoCloseableStrategy<A, IntStream> strategy) {
		return strategy.user;
	}
	public @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	@Override
	public RioStream<A, Integer> asOS(Function<IntStream, Stream<Integer>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	@Override
	public RioIntStream<A> asSELF(Function<IntStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	@Override
	public RioLongStream<A> asLS(Function<IntStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	@Override
	public RioDoubleStream<A> asDS(Function<IntStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	@Override
	public Function<? super Integer, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, Integer, ? extends IntStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Integer, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, Integer, ? extends LongStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, Integer, ? extends DoubleStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public IntUnaryOperator castToInt(ExToIntBiFunction<A, Integer, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	@Override
	public IntToLongFunction castToLong(ExToLongBiFunction<A, Integer, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	@Override
	public IntToDoubleFunction castToDouble(ExToDoubleBiFunction<A, Integer, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	@Override
  public IntBinaryOperator castToBinaryOperators(Function<A, ExIntBinaryOperator<IOException>> combiner) {
  	return combiner.apply(getCached()).uncheck(classOfE());
  }
	@Override
	public IntConsumer castToConsumers(ExObjIntConsumer<A, IOException> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	@Override
	public IntPredicate castToPredicates(ExBiPredicate<A, Integer, IOException> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(ExBiFunction<A, Integer, ? extends R, IOException> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(ExBiFunction<A, Integer, ? extends Stream<? extends R>, IOException> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Integer, ? extends Stream<? extends R>> mapper,
	  IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<K, int[]> toMap(ExBiFunction<A, Integer, ? extends K, IOException> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, Integer, ? extends K, IOException> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<int[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Integer, ? extends K, IOException>, IntFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Integer, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, Integer, ? extends Stream<? extends R>, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> IntFunction<? extends R> castToMapFunctions(ExBiFunction<A, Integer, ? extends R, IOException> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<IntStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}