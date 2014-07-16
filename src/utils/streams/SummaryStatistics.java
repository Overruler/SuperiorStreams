package utils.streams;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;

public class SummaryStatistics<T> implements Consumer<T> {
	private long count;
	private long sum;
	private long min = Long.MAX_VALUE;
	private long max = Long.MIN_VALUE;
	private final ToLongFunction<? super T> toValue;
	private T minElement;
	private T maxElement;

	public SummaryStatistics(ToLongFunction<? super T> toValue) {
		this.toValue = toValue;
	}
	public static <T> Supplier<SummaryStatistics<T>> supplier(ToLongFunction<? super T> toValue) {
		return () -> new SummaryStatistics<>(toValue);
	}
	public static <T> BiConsumer<SummaryStatistics<T>, ? super T> accumulator() {
		return SummaryStatistics::accept;
	}
	public static <T> BiConsumer<SummaryStatistics<T>, SummaryStatistics<T>> combiner() {
		return SummaryStatistics::combine;
	}
	public static <T> Collector<? super T, SummaryStatistics<T>, SummaryStatistics<T>> collector(
		ToLongFunction<? super T> toValue) {
		return Collector.of(supplier(toValue), SummaryStatistics::accept, SummaryStatistics::combine);
	}
	public @Override void accept(T t) {
		++count;
		long value = toValue.applyAsLong(t);
		sum += value;
		updateMin(value, t);
		updateMax(value, t);
	}
	public SummaryStatistics<T> combine(SummaryStatistics<T> other) {
		count += other.count;
		sum += other.sum;
		updateMin(other.min, other.minElement);
		updateMax(other.max, other.maxElement);
		return this;
	}
	private void updateMin(long min2, T minElement2) {
		if(min2 < min) {
			min = min2;
			minElement = minElement2;
		}
	}
	private void updateMax(long max2, T maxElement2) {
		if(max2 > max) {
			max = max2;
			maxElement = maxElement2;
		}
	}
	public final long getCount() {
		return count;
	}
	public final long getSum() {
		return sum;
	}
	public final long getMin() {
		return min;
	}
	public final long getMax() {
		return max;
	}
	public final T getMinElement() {
		return minElement;
	}
	public final T getMaxElement() {
		return maxElement;
	}
	public final double getAverage() {
		return getCount() > 0 ? (double) getSum() / getCount() : 0.0d;
	}
	public @Override String toString() {
		return String.format(
			"%s{count=%d, sum=%d, min=%d, average=%f, max=%d}",
			this.getClass().getSimpleName(),
			getCount(),
			getSum(),
			getMin(),
			getAverage(),
			getMax());
	}
}
