package utils.streams.functions;

import java.util.function.*;
import java.util.Objects;
import java.util.function.IntSupplier;
import utils.streams.WrapperException;

public interface Conversions {

	public static <T, E extends Exception> Predicate<T> unchecked(Class<E> classOfE, ExPredicate<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.test(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExPredicate<T, E> rechecked(Class<E> classOfE, Predicate<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.test(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> BiConsumer<T, U> unchecked(Class<E> classOfE, ExBiConsumer<T, U, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				it.accept(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ExBiConsumer<T, U, E> rechecked(Class<E> classOfE, BiConsumer<T, U> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				it.accept(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, R, E extends Exception> BiFunction<T, U, R> unchecked(Class<E> classOfE, ExBiFunction<T, U, R, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.apply(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, R, E extends Exception> ExBiFunction<T, U, R, E> rechecked(Class<E> classOfE, BiFunction<T, U, R> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.apply(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> BinaryOperator<T> unchecked(Class<E> classOfE, ExBinaryOperator<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, T t2) -> {
			try {
				return it.apply(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExBinaryOperator<T, E> rechecked(Class<E> classOfE, BinaryOperator<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, T t2) -> {
			try {
				return it.apply(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> BiPredicate<T, U> unchecked(Class<E> classOfE, ExBiPredicate<T, U, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.test(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ExBiPredicate<T, U, E> rechecked(Class<E> classOfE, BiPredicate<T, U> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.test(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> Consumer<T> unchecked(Class<E> classOfE, ExConsumer<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				it.accept(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExConsumer<T, E> rechecked(Class<E> classOfE, Consumer<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				it.accept(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, R, E extends Exception> Function<T, R> unchecked(Class<E> classOfE, ExFunction<T, R, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.apply(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, R, E extends Exception> ExFunction<T, R, E> rechecked(Class<E> classOfE, Function<T, R> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.apply(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> Supplier<T> unchecked(Class<E> classOfE, ExSupplier<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.get();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExSupplier<T, E> rechecked(Class<E> classOfE, Supplier<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.get();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> UnaryOperator<T> unchecked(Class<E> classOfE, ExUnaryOperator<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.apply(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExUnaryOperator<T, E> rechecked(Class<E> classOfE, UnaryOperator<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.apply(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleBinaryOperator unchecked(Class<E> classOfE, ExDoubleBinaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1, double t2) -> {
			try {
				return it.applyAsDouble(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleBinaryOperator<E> rechecked(Class<E> classOfE, DoubleBinaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1, double t2) -> {
			try {
				return it.applyAsDouble(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleConsumer unchecked(Class<E> classOfE, ExDoubleConsumer<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				it.accept(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleConsumer<E> rechecked(Class<E> classOfE, DoubleConsumer it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				it.accept(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> DoubleFunction<R> unchecked(Class<E> classOfE, ExDoubleFunction<R, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.apply(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> ExDoubleFunction<R, E> rechecked(Class<E> classOfE, DoubleFunction<R> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.apply(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoublePredicate unchecked(Class<E> classOfE, ExDoublePredicate<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.test(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoublePredicate<E> rechecked(Class<E> classOfE, DoublePredicate it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.test(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleSupplier unchecked(Class<E> classOfE, ExDoubleSupplier<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsDouble();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleSupplier<E> rechecked(Class<E> classOfE, DoubleSupplier it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsDouble();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleToIntFunction unchecked(Class<E> classOfE, ExDoubleToIntFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleToIntFunction<E> rechecked(Class<E> classOfE, DoubleToIntFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleToLongFunction unchecked(Class<E> classOfE, ExDoubleToLongFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleToLongFunction<E> rechecked(Class<E> classOfE, DoubleToLongFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> DoubleUnaryOperator unchecked(Class<E> classOfE, ExDoubleUnaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExDoubleUnaryOperator<E> rechecked(Class<E> classOfE, DoubleUnaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (double t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ObjDoubleConsumer<T> unchecked(Class<E> classOfE, ExObjDoubleConsumer<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, double t2) -> {
			try {
				it.accept(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExObjDoubleConsumer<T, E> rechecked(Class<E> classOfE, ObjDoubleConsumer<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, double t2) -> {
			try {
				it.accept(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ToDoubleBiFunction<T, U> unchecked(Class<E> classOfE, ExToDoubleBiFunction<T, U, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsDouble(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ExToDoubleBiFunction<T, U, E> rechecked(Class<E> classOfE, ToDoubleBiFunction<T, U> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsDouble(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ToDoubleFunction<T> unchecked(Class<E> classOfE, ExToDoubleFunction<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExToDoubleFunction<T, E> rechecked(Class<E> classOfE, ToDoubleFunction<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongBinaryOperator unchecked(Class<E> classOfE, ExLongBinaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1, long t2) -> {
			try {
				return it.applyAsLong(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongBinaryOperator<E> rechecked(Class<E> classOfE, LongBinaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1, long t2) -> {
			try {
				return it.applyAsLong(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongConsumer unchecked(Class<E> classOfE, ExLongConsumer<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				it.accept(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongConsumer<E> rechecked(Class<E> classOfE, LongConsumer it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				it.accept(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> LongFunction<R> unchecked(Class<E> classOfE, ExLongFunction<R, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.apply(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> ExLongFunction<R, E> rechecked(Class<E> classOfE, LongFunction<R> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.apply(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongPredicate unchecked(Class<E> classOfE, ExLongPredicate<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.test(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongPredicate<E> rechecked(Class<E> classOfE, LongPredicate it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.test(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongSupplier unchecked(Class<E> classOfE, ExLongSupplier<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsLong();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongSupplier<E> rechecked(Class<E> classOfE, LongSupplier it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsLong();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongToDoubleFunction unchecked(Class<E> classOfE, ExLongToDoubleFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongToDoubleFunction<E> rechecked(Class<E> classOfE, LongToDoubleFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongToIntFunction unchecked(Class<E> classOfE, ExLongToIntFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongToIntFunction<E> rechecked(Class<E> classOfE, LongToIntFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> LongUnaryOperator unchecked(Class<E> classOfE, ExLongUnaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExLongUnaryOperator<E> rechecked(Class<E> classOfE, LongUnaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (long t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ObjLongConsumer<T> unchecked(Class<E> classOfE, ExObjLongConsumer<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, long t2) -> {
			try {
				it.accept(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExObjLongConsumer<T, E> rechecked(Class<E> classOfE, ObjLongConsumer<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, long t2) -> {
			try {
				it.accept(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ToLongBiFunction<T, U> unchecked(Class<E> classOfE, ExToLongBiFunction<T, U, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsLong(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ExToLongBiFunction<T, U, E> rechecked(Class<E> classOfE, ToLongBiFunction<T, U> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsLong(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ToLongFunction<T> unchecked(Class<E> classOfE, ExToLongFunction<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExToLongFunction<T, E> rechecked(Class<E> classOfE, ToLongFunction<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntBinaryOperator unchecked(Class<E> classOfE, ExIntBinaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1, int t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntBinaryOperator<E> rechecked(Class<E> classOfE, IntBinaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1, int t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntConsumer unchecked(Class<E> classOfE, ExIntConsumer<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				it.accept(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntConsumer<E> rechecked(Class<E> classOfE, IntConsumer it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				it.accept(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> IntFunction<R> unchecked(Class<E> classOfE, ExIntFunction<R, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.apply(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <R, E extends Exception> ExIntFunction<R, E> rechecked(Class<E> classOfE, IntFunction<R> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.apply(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntPredicate unchecked(Class<E> classOfE, ExIntPredicate<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.test(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntPredicate<E> rechecked(Class<E> classOfE, IntPredicate it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.test(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntSupplier unchecked(Class<E> classOfE, ExIntSupplier<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsInt();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntSupplier<E> rechecked(Class<E> classOfE, IntSupplier it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return () -> {
			try {
				return it.getAsInt();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntToDoubleFunction unchecked(Class<E> classOfE, ExIntToDoubleFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntToDoubleFunction<E> rechecked(Class<E> classOfE, IntToDoubleFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsDouble(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntToLongFunction unchecked(Class<E> classOfE, ExIntToLongFunction<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntToLongFunction<E> rechecked(Class<E> classOfE, IntToLongFunction it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsLong(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <E extends Exception> IntUnaryOperator unchecked(Class<E> classOfE, ExIntUnaryOperator<E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <E extends Exception> ExIntUnaryOperator<E> rechecked(Class<E> classOfE, IntUnaryOperator it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (int t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ObjIntConsumer<T> unchecked(Class<E> classOfE, ExObjIntConsumer<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, int t2) -> {
			try {
				it.accept(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ExObjIntConsumer<T, E> rechecked(Class<E> classOfE, ObjIntConsumer<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, int t2) -> {
			try {
				it.accept(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ToIntBiFunction<T, U> unchecked(Class<E> classOfE, ExToIntBiFunction<T, U, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, U, E extends Exception> ExToIntBiFunction<T, U, E> rechecked(Class<E> classOfE, ToIntBiFunction<T, U> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1, U t2) -> {
			try {
				return it.applyAsInt(t1, t2);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> ToIntFunction<T> unchecked(Class<E> classOfE, EXToIntFunction<T, E> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}

	public static <T, E extends Exception> EXToIntFunction<T, E> rechecked(Class<E> classOfE, ToIntFunction<T> it) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(it);
		return (T t1) -> {
			try {
				return it.applyAsInt(t1);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}


} //Conversions
