package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExDoubleBinaryOperator;
import utils.streams.functions.ExObjDoubleConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RioDoubleStream<A extends AutoCloseable> extends AbstractDoubleStream<IOException,
AutoCloseableStrategy<A, DoubleStream>,
RioStream<A, Double>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
ExObjDoubleConsumer<A, IOException>,
ExBiPredicate<A, Double, IOException>,
Function<A, ExDoubleBinaryOperator<IOException>>,
ExBiFunction<A, Double, ? extends IntStream, IOException>,
ExBiFunction<A, Double, ? extends LongStream, IOException>,
ExBiFunction<A, Double, ? extends DoubleStream, IOException>,
ExToIntBiFunction<A, Double, IOException>,
ExToLongBiFunction<A, Double, IOException>,
ExToDoubleBiFunction<A, Double, IOException>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC;
	public RioDoubleStream(Supplier<A> allocator, Function<A, DoubleStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())));
	}
	<OLD> RioDoubleStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, DoubleStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioDoubleStream(Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	public @Override DoubleStream castToStream(AutoCloseableStrategy<A, DoubleStream> strategy) {
		return strategy.user;
	}
	public @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	@Override
	public RioStream<A, Double> asOS(Function<DoubleStream, Stream<Double>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	@Override
	public RioIntStream<A> asIS(Function<DoubleStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	@Override
	public RioLongStream<A> asLS(Function<DoubleStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	@Override
	public RioDoubleStream<A> asSELF(Function<DoubleStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	@Override
	public Function<? super Double, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, Double, ? extends IntStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Double, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, Double, ? extends LongStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public Function<? super Double, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, Double, ? extends DoubleStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	@Override
	public DoubleToIntFunction castToInt(ExToIntBiFunction<A, Double, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	@Override
	public DoubleToLongFunction castToLong(ExToLongBiFunction<A, Double, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	@Override
	public DoubleUnaryOperator castToDouble(ExToDoubleBiFunction<A, Double, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	@Override
  public DoubleBinaryOperator castToBinaryOperators(Function<A, ExDoubleBinaryOperator<IOException>> combiner) {
  	return combiner.apply(getCached()).uncheck(classOfE());
  }
	@Override
	public DoubleConsumer castToConsumers(ExObjDoubleConsumer<A, IOException> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	@Override
	public DoublePredicate castToPredicates(ExBiPredicate<A, Double, IOException> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(ExBiFunction<A, Double, ? extends R, IOException> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(ExBiFunction<A, Double, ? extends Stream<? extends R>, IOException> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Double, ? extends Stream<? extends R>> mapper,
	  DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<? extends K, double[]> toMap(ExBiFunction<A, Double, ? extends K, IOException> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, Double, ? extends K, IOException> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Double, ? extends K, IOException>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Double, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, Double, ? extends Stream<? extends R>, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(ExBiFunction<A, Double, ? extends R, IOException> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}