package utils.streams.codegen;

interface Templates {
	String functionLike();
	String consumerLike();
	String biFunctionLike();
	String predicateLike();
	String unaryOperatorLike();
	String binaryOperatorLike();
	String supplierLike();

	static final String EX_RECHECK_UNCHECK = "	static <E extends Exception> ExIntBinaryOperator<E> recheck(\n"
	+ "		java.util.function.IntBinaryOperator unchecked,\n"
	+ "		Class<E> classOfE) {\n"
	+ "		Objects.requireNonNull(classOfE);\n"
	+ "		Objects.requireNonNull(unchecked);\n"
	+ "		return (int t, int u) -> {\n"
	+ "			try {\n"
	+ "				return unchecked.applyAsInt(t, u);\n"
	+ "			} catch(RuntimeException e) {\n"
	+ "				throw WrapperException.show(e, classOfE);\n"
	+ "			}\n"
	+ "		};\n"
	+ "	}\n"
	+ "	default IntBinaryOperator uncheck(Class<E> classOfE) {\n"
	+ "		Objects.requireNonNull(classOfE);\n"
	+ "		return (int t, int u) -> {\n"
	+ "			try {\n"
	+ "				return this.applyAsInt(t, u);\n"
	+ "			} catch(Exception e) {\n"
	+ "				throw WrapperException.hide(e, classOfE);\n"
	+ "			}\n"
	+ "		};\n"
	+ "	}\n"
	+ "}\n"
	+ "";

	static Templates forException() {
		return new Templates() {
			public @Override String supplierLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class-ex */\n" +
				"@FunctionalInterface\n" +
				"public interface ExIntBinaryOperator<E extends Exception> {\n" +
				"	/** method-ex */\n" +
				"	int applyAsInt(int t, int u) throws E;\n" +
				EX_RECHECK_UNCHECK;
			}
			public @Override String predicateLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class-ex */\n"
				+ "@FunctionalInterface\n"
				+ "public interface ExBiPredicate<T, U, E extends Exception> {\n"
				+ "	/** method-ex */\n"
				+ "	boolean test(T t, U u) throws E;\n"
				+ "	default ExBiPredicate<T, U, E> and(ExBiPredicate<? super T, ? super U, E> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) && other.test(t, u);\n"
				+ "	}\n"
				+ "	default ExBiPredicate<T, U, E> negate() {\n"
				+ "		return (T t, U u) -> !test(t, u);\n"
				+ "	}\n"
				+ "	default ExBiPredicate<T, U, E> or(ExBiPredicate<? super T, ? super U, E> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) || other.test(t, u);\n"
				+ "	}\n")
				/**/.replace("ExBiPredicate<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("ExBiPredicate<? super T, ? super U, E>", "ExIntBinaryOperator<SUPER_T, SUPER_U, E>")
				/**/.replace("BiPredicate", "IntBinaryOperator")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("test(", "applyAsInt(") +
				EX_RECHECK_UNCHECK;
			}
			public @Override String functionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class-ex */\n"
				+ "@FunctionalInterface\n"
				+ "public interface ExFunction<T, R, E extends Exception> {\n"
				+ "	/** method-ex */\n"
				+ "	R apply(T t1) throws E;\n"
				+ "	default <V> ExFunction<V, R, E> compose(ExFunction<? super V, ? extends T, E> before) {\n"
				+ "		Objects.requireNonNull(before);\n"
				+ "		return (V v) -> apply(before.apply(v));\n"
				+ "	}\n"
				+ "	default <V> ExFunction<T, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t) -> after.apply(apply(t));\n"
				+ "	}\n"
				+ "	static <T, E extends Exception> ExFunction<T, T, E> identity() {\n"
				+ "		return t -> t;\n"
				+ "	}\n")
				/**/.replace("? super ", "SUPER_")
				/**/.replace("? extends ", "EXTENDS_")
				/**/.replace("ExFunction<T, R, ", "ExIntBinaryOperator<")
				/**/.replace("ExFunction", "ExIntBinaryOperator")
				/**/.replace(".Function", ".IntBinaryOperator")
				/**/.replace("R apply(T t1", "int apply(int t, int u")
				/**/.replace("apply", "applyAsInt")
				/**/.replace("(T t)", "(TAIL t)")
				/**/.replace("(V v)", "(HEAD v)") +
				EX_RECHECK_UNCHECK;
			}
			public @Override String consumerLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class-ex */\n"
				+ "@FunctionalInterface\n"
				+ "public interface ExBiConsumer<T, U, E extends Exception> {\n"
				+ "	/** method-ex */\n"
				+ "	void accept(T t1, U t2) throws E;\n"
				+ "	default ExBiConsumer<T, U, E> andThen(ExBiConsumer<? super T, ? super U, E> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t1, U t2) -> {\n"
				+ "			accept(t1, t2);\n"
				+ "			after.accept(t1, t2);\n"
				+ "		};\n"
				+ "	}\n")
				/**/.replace("ExBiConsumer<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("ExBiConsumer<? super T, ? super U,", "ExIntBinaryOperator<SUPER_T, SUPER_U,")
				/**/.replace("(T t1, U t2)", "(int t, int u)")
				/**/.replace("accept", "applyAsInt")
				/**/.replace("(t1, t2)", "(t, u)") +
				EX_RECHECK_UNCHECK;
			}
			public @Override String unaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class-ex */\n" +
				"@FunctionalInterface\n" +
				"public interface ExUnaryOperator<T, E extends Exception> extends ExFunction<T, T, E> {\n" +
				"	default ExUnaryOperator<T, E> compose(ExUnaryOperator<T, E> before) {\n" +
				"		Objects.requireNonNull(before);\n" +
				"		return (T t) -> apply(before.apply(t));\n" +
				"	}\n" +
				"	default ExUnaryOperator<T, E> andThen(ExUnaryOperator<T, E> after) {\n" +
				"		Objects.requireNonNull(after);\n" +
				"		return (T t) -> after.apply(apply(t));\n" +
				"	}\n" +
				"	static <T, E extends Exception> ExUnaryOperator<T, E> identity() {\n" +
				"		return t -> t;\n" +
				"	}\n" +
				EX_RECHECK_UNCHECK.replace("default ", "default @Override ");
			}
			public @Override String binaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Comparator;\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class-ex */\n" +
				"@FunctionalInterface\n" +
				"public interface ExBinaryOperator<T, E extends Exception> extends ExBiFunction<T, T, T, E> {\n" +
				"	static <T, E extends Exception> ExBinaryOperator<T, E> minBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;\n" +
				"	}\n" +
				"	static <T, E extends Exception> ExBinaryOperator<T, E> maxBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;\n" +
				"	}\n" +
				EX_RECHECK_UNCHECK.replace("default ", "default @Override ");
			}
			public @Override String biFunctionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class-ex */\n"
				+ "@FunctionalInterface\n"
				+ "public interface ExBiFunction<T, U, R, E extends Exception> {\n"
				+ "	/** method-ex */\n"
				+ "	R apply(T t1, U t2) throws E;\n"
				+ "	default <V> ExBiFunction<T, U, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t, U u) -> after.apply(apply(t, u));\n"
				+ "	}\n")
				/**/.replace("ExBiFunction<T, U, R, ", "ExIntBinaryOperator<")
				/**/.replace("ExBiFunction<T, U, V, ", "ExIntBinaryOperator<T, R, ")
				/**/.replace("BiFunction", "IntBinaryOperator")
				/**/.replace("(T t1, U t2)", "(int t, int u)")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("#apply", "#applyAsInt")
				/**/.replace("(apply(", "(applyAsInt(") +
				EX_RECHECK_UNCHECK;
			}
		};
	}

	static final String IO_RECHECK_UNCHECK =
		("	static IOIntBinaryOperator recheck(java.util.function.IntBinaryOperator unchecked) {\n"
		+ "		Objects.requireNonNull(unchecked);\n"
		+ "		return (int t, int u) -> {\n"
		+ "			try {\n"
		+ "				return unchecked.applyAsInt(t, u);\n"
		+ "			} catch(RuntimeException e) {\n"
		+ "				throw WrapperException.show(e, IOException.class);\n"
		+ "			}\n"
		+ "		};\n"
		+ "	}\n"
		+ "	default IntBinaryOperator uncheck() {\n"
		+ "		return (int t, int u) -> {\n"
		+ "			try {\n"
		+ "				return applyAsInt(t, u);\n"
		+ "			} catch(IOException e) {\n"
		+ "				throw WrapperException.hide(e, IOException.class);\n"
		+ "			}\n"
		+ "		};\n"
		+ "	}\n"
		+ "}\n"
		+ "").replace("static", "static <E extends IOException>").replace("return applyAsInt", "return this.applyAsInt");

	static Templates forIOException() {
		return new Templates() {
			public @Override String supplierLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.io.IOException;\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface IOIntBinaryOperator extends ExIntBinaryOperator<IOException> {\n" +
				IO_RECHECK_UNCHECK;
			}
			public @Override String predicateLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.io.IOException;\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface IOBiPredicate<T, U> extends ExBiPredicate<T, U, IOException> {\n"
				+ "	default IOBiPredicate<T, U> and(IOBiPredicate<? super T, ? super U> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) && other.test(t, u);\n"
				+ "	}\n"
				+ "	default @Override IOBiPredicate<T, U> negate() {\n"
				+ "		return (T t, U u) -> !test(t, u);\n"
				+ "	}\n"
				+ "	default IOBiPredicate<T, U> or(IOBiPredicate<? super T, ? super U> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) || other.test(t, u);\n"
				+ "	}\n")
				/**/.replace("IOBiPredicate<T, U>", "IOIntBinaryOperator")
				/**/.replace("ExBiPredicate<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("IOBiPredicate<? super T, ? super U>", "IOIntBinaryOperator<SUPER_T, SUPER_U>")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("test(", "applyAsInt(") +
				IO_RECHECK_UNCHECK;
			}
			public @Override String functionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.io.IOException;\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface IOFunction<T, R> extends ExFunction<T, R, IOException> {\n"
				+ "	default <V> IOFunction<V, R> compose(IOFunction<? super V, ? extends T> before) {\n"
				+ "		Objects.requireNonNull(before);\n"
				+ "		return (V v) -> apply(before.apply(v));\n"
				+ "	}\n"
				+ "	default <V> IOFunction<T, V> andThen(IOFunction<? super R, ? extends V> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t) -> after.apply(apply(t));\n"
				+ "	}\n"
				+ "	static <T> IOFunction<T, T> identity() {\n"
				+ "		return t -> t;\n"
				+ "	}\n")
				/**/.replace("? super ", "SUPER_")
				/**/.replace("? extends ", "EXTENDS_")
				/**/.replace("IOFunction<T, R>", "IOIntBinaryOperator")
				/**/.replace("ExFunction<T, R, ", "ExIntBinaryOperator<")
				/**/.replace("IOFunction", "IOIntBinaryOperator")
				/**/.replace("apply", "applyAsInt")
				/**/.replace("(T t)", "(TAIL t)")
				/**/.replace("(V v)", "(HEAD v)") +
				IO_RECHECK_UNCHECK;
			}
			public @Override String consumerLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.io.IOException;\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface IOBiConsumer<T, U> extends ExBiConsumer<T, U, IOException> {\n"
				+ "	default IOBiConsumer<T, U> andThen(IOBiConsumer<? super T, ? super U> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t1, U t2) -> {\n"
				+ "			accept(t1, t2);\n"
				+ "			after.accept(t1, t2);\n"
				+ "		};\n"
				+ "	}\n")
				/**/.replace("IOBiConsumer<T, U>", "IOIntBinaryOperator")
				/**/.replace("ExBiConsumer<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("IOBiConsumer<? super T, ? super U>", "IOIntBinaryOperator<SUPER_T, SUPER_U>")
				/**/.replace("(T t1, U t2)", "(int t, int u)")
				/**/.replace("accept", "applyAsInt")
				/**/.replace("(t1, t2)", "(t, u)") +
				IO_RECHECK_UNCHECK;
			}
			public @Override String unaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.io.IOException;\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface IOUnaryOperator<T> extends ExUnaryOperator<T, IOException>, IOFunction<T, T> {\n" +
				"	default IOUnaryOperator<T> compose(IOUnaryOperator<T> before) {\n" +
				"		Objects.requireNonNull(before);\n" +
				"		return (T t) -> apply(before.apply(t));\n" +
				"	}\n" +
				"	default IOUnaryOperator<T> andThen(IOUnaryOperator<T> after) {\n" +
				"		Objects.requireNonNull(after);\n" +
				"		return (T t) -> after.apply(apply(t));\n" +
				"	}\n" +
				"	static <T> IOUnaryOperator<T> identity() {\n" +
				"		return t -> t;\n" +
				"	}\n" +
				IO_RECHECK_UNCHECK.replace("default ", "default @Override ");
			}
			public @Override String binaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.io.IOException;\n" +
				"import java.util.Comparator;\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface IOBinaryOperator<T> extends ExBinaryOperator<T, IOException>, IOBiFunction<T, T, T> {\n" +
				"	static <T> IOBinaryOperator<T> minBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;\n" +
				"	}\n" +
				"	static <T> IOBinaryOperator<T> maxBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;\n" +
				"	}\n" +
				IO_RECHECK_UNCHECK.replace("default ", "default @Override ");
			}
			public @Override String biFunctionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.io.IOException;\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface IOBiFunction<T, U, R> extends ExBiFunction<T, U, R, IOException> {\n"
				+ "	default <V> IOBiFunction<T, U, V> andThen(IOFunction<? super R, ? extends V> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t, U u) -> after.apply(apply(t, u));\n"
				+ "	}\n")
				/**/.replace("IOBiFunction<T, U, R>", "IOIntBinaryOperator")
				/**/.replace("IOBiFunction<T, U, V>", "IOIntBinaryOperator<T, R>")
				/**/.replace("ExBiFunction<T, U, R, ", "ExIntBinaryOperator<")
				/**/.replace("(T t1, U t2)", "(int t, int u)")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("(apply(", "(applyAsInt(") +
				IO_RECHECK_UNCHECK;
			}
		};
	}

	static final String RECHECK =
		("	default <E extends Exception> ExIntBinaryOperator<E> recheck(Class<E> classOfE) {\n"
		+ "		Objects.requireNonNull(classOfE);\n"
		+ "		return (int t, int u) -> {\n"
		+ "			try {\n"
		+ "				return this.applyAsInt(t, u);\n"
		+ "			} catch(RuntimeException e) {\n"
		+ "				throw WrapperException.show(e, classOfE);\n"
		+ "			}\n"
		+ "		};\n"
		+ "	}\n"
		+ "}\n"
		+ "").replace("<E extends Exception> ", "<E extends RuntimeException> ");

	static Templates forRuntimeException() {
		return new Templates() {
			public @Override String supplierLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface IntBinaryOperator extends ExIntBinaryOperator<RuntimeException>, java.util.function.IntBinaryOperator {\n" +
				RECHECK;
			}
			public @Override String predicateLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface BiPredicate<T, U> extends ExBiPredicate<T, U, RuntimeException>, java.util.function.BiPredicate<T, U> {\n"
				+ "	default BiPredicate<T, U> and(BiPredicate<? super T, ? super U> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) && other.test(t, u);\n"
				+ "	}\n"
				+ "	default @Override BiPredicate<T, U> negate() {\n"
				+ "		return (T t, U u) -> !test(t, u);\n"
				+ "	}\n"
				+ "	default BiPredicate<T, U> or(BiPredicate<? super T, ? super U> other) {\n"
				+ "		Objects.requireNonNull(other);\n"
				+ "		return (T t, U u) -> test(t, u) || other.test(t, u);\n"
				+ "	}\n")
				/**/.replace("ExBiPredicate<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("BiPredicate<T, U>", "IntBinaryOperator")
				/**/.replace("BiPredicate<? super T, ? super U>", "IntBinaryOperator<SUPER_T, SUPER_U>")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("test(", "applyAsInt(") +
				RECHECK;
			}
			public @Override String functionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface Function<T, R> extends ExFunction<T, R, RuntimeException>, java.util.function.Function<T, R> {\n"
				+ "	default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {\n"
				+ "		Objects.requireNonNull(before);\n"
				+ "		return (V v) -> apply(before.apply(v));\n"
				+ "	}\n"
				+ "	default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t) -> after.apply(apply(t));\n"
				+ "	}\n"
				+ "	static <T> Function<T, T> identity() {\n"
				+ "		return t -> t;\n"
				+ "	}\n")
				/**/.replace("? super ", "SUPER_")
				/**/.replace("? extends ", "EXTENDS_")
				/**/.replace("ExFunction<T, R, ", "ExIntBinaryOperator<")
				/**/.replace("Function<T, R>", "IntBinaryOperator")
				/**/.replace("Function", "IntBinaryOperator")
				/**/.replace("@IntBinaryOperator", "@Function")
				/**/.replace("apply", "applyAsInt")
				/**/.replace("(T t)", "(TAIL t)")
				/**/.replace("(V v)", "(HEAD v)") +
				RECHECK;
			}
			public @Override String consumerLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface BiConsumer<T, U> extends ExBiConsumer<T, U, RuntimeException>, java.util.function.BiConsumer<T, U> {\n"
				+ "	default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t, U u) -> {\n"
				+ "			accept(t, u);\n"
				+ "			after.accept(t, u);\n"
				+ "		};\n"
				+ "	}\n")
				/**/.replace("ExBiConsumer<T, U, ", "ExIntBinaryOperator<")
				/**/.replace("BiConsumer<T, U>", "IntBinaryOperator")
				/**/.replace("BiConsumer<? super T, ? super U>", "IntBinaryOperator<SUPER_T, SUPER_U>")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("accept", "applyAsInt") +
				RECHECK;
			}
			public @Override String unaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface UnaryOperator<T> extends ExUnaryOperator<T, RuntimeException>, Function<T, T>,\n" +
				"	java.util.function.UnaryOperator<T> {\n" +
				"	default UnaryOperator<T> compose(UnaryOperator<T> before) {\n" +
				"		Objects.requireNonNull(before);\n" +
				"		return (T t) -> apply(before.apply(t));\n" +
				"	}\n" +
				"	default UnaryOperator<T> andThen(UnaryOperator<T> after) {\n" +
				"		Objects.requireNonNull(after);\n" +
				"		return (T t) -> after.apply(apply(t));\n" +
				"	}\n" +
				"	static <T> UnaryOperator<T> identity() {\n" +
				"		return t -> t;\n" +
				"	}\n" +
				RECHECK.replace("default ", "default @Override ");
			}
			public @Override String binaryOperatorLike() {
				return "package utils.streams.functions;\n" +
				"\n" +
				"import java.util.Comparator;\n" +
				"import java.util.Objects;\n" +
				"import utils.streams.WrapperException;\n" +
				"\n" +
				"/** class */\n" +
				"@FunctionalInterface\n" +
				"public interface BinaryOperator<T> extends ExBinaryOperator<T, RuntimeException>, BiFunction<T, T, T>,\n" +
				"	java.util.function.BinaryOperator<T> {\n" +
				"	static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;\n" +
				"	}\n" +
				"	static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {\n" +
				"		Objects.requireNonNull(comparator);\n" +
				"		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;\n" +
				"	}\n" +
				RECHECK.replace("default ", "default @Override ");
			}
			public @Override String biFunctionLike() {
				return ("package utils.streams.functions;\n"
				+ "\n"
				+ "import java.util.Objects;\n"
				+ "import utils.streams.WrapperException;\n"
				+ "\n"
				+ "/** class */\n"
				+ "@FunctionalInterface\n"
				+ "public interface BiFunction<T, U, R> extends ExBiFunction<T, U, R, RuntimeException>,\n"
				+ "	java.util.function.BiFunction<T, U, R> {\n"
				+ "	default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {\n"
				+ "		Objects.requireNonNull(after);\n"
				+ "		return (T t, U u) -> after.apply(apply(t, u));\n"
				+ "	}\n")
				/**/.replace("ExBiFunction<T, U, R, ", "ExIntBinaryOperator<")
				/**/.replace("BiFunction<T, U, R>", "IntBinaryOperator")
				/**/.replace("BiFunction<T, U, V>", "IntBinaryOperator<T, R>")
				/**/.replace("(T t1, U t2)", "(int t, int u)")
				/**/.replace("(T t, U u)", "(int t, int u)")
				/**/.replace("(apply(", "(applyAsInt(") +
				RECHECK;
			}
		};
	}
}
