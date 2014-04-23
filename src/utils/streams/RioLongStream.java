package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
import utils.streams.functions.ExLongBinaryOperator;
import utils.streams.functions.ExObjLongConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RioLongStream<A extends AutoCloseable> extends AbstractLongStream<IOException,
AutoCloseableStrategy<A, LongStream>,
RioStream<A, Long>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
ExObjLongConsumer<A, IOException>,
ExBiPredicate<A, Long, IOException>,
Function<A, ExLongBinaryOperator<IOException>>,
ExBiFunction<A, Long, ? extends IntStream, IOException>,
ExBiFunction<A, Long, ? extends LongStream, IOException>,
ExBiFunction<A, Long, ? extends DoubleStream, IOException>,
ExToIntBiFunction<A, Long, IOException>,
ExToLongBiFunction<A, Long, IOException>,
ExToDoubleBiFunction<A, Long, IOException>> {//*E*

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
	public A getCached() {
		return supplierAC.get().resource;
	}
	public @Override LongStream castToStream(AutoCloseableStrategy<A, LongStream> strategy) {
		return strategy.user;
	}
	public @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	@Override
	public RioStream<A, Long> asOS(Function<LongStream, Stream<Long>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	@Override
	public RioIntStream<A> asIS(Function<LongStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	@Override
	public RioLongStream<A> asSELF(Function<LongStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	@Override
	public RioDoubleStream<A> asDS(Function<LongStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	@Override
	public Function<? super Long, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, Long, ? extends IntStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Long, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, Long, ? extends LongStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Long, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, Long, ? extends DoubleStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public LongToIntFunction castToInt(ExToIntBiFunction<A, Long, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	@Override
	public LongUnaryOperator castToLong(ExToLongBiFunction<A, Long, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	@Override
	public LongToDoubleFunction castToDouble(ExToDoubleBiFunction<A, Long, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	@Override
  public LongBinaryOperator castToBinaryOperators(Function<A, ExLongBinaryOperator<IOException>> combiner) {
  	return combiner.apply(getCached()).uncheck(classOfE());
  }
	@Override
	public LongConsumer castToConsumers(ExObjLongConsumer<A, IOException> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	@Override
	public LongPredicate castToPredicates(ExBiPredicate<A, Long, IOException> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(ExBiFunction<A, Long, ? extends R, IOException> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(ExBiFunction<A, Long, ? extends Stream<? extends R>, IOException> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Long, ? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<? extends K, long[]> toMap(ExBiFunction<A, Long, ? extends K, IOException> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, Long, ? extends K, IOException> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<long[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Long, ? extends K, IOException>, LongFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Long, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, Long, ? extends Stream<? extends R>, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> LongFunction<? extends R> castToMapFunctions(ExBiFunction<A, Long, ? extends R, IOException> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<LongStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}