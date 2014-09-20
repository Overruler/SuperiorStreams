package utils.lists;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import utils.streams2.DoubleStream;
import utils.streams2.IntStream;
import utils.streams2.LongStream;
import utils.streams2.Stream;

public class Arrays {//*Q*
	public static void sort(int[] a)    {java.util.Arrays.sort(a);}
	public static void sort(long[] a)   {java.util.Arrays.sort(a);}
	public static void sort(short[] a)  {java.util.Arrays.sort(a);}
	public static void sort(char[] a)   {java.util.Arrays.sort(a);}
	public static void sort(byte[] a)   {java.util.Arrays.sort(a);}
	public static void sort(float[] a)  {java.util.Arrays.sort(a);}
	public static void sort(double[] a) {java.util.Arrays.sort(a);}
	public static void sort(int[] a, int fromIndex, int toIndex)    {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(long[] a, int fromIndex, int toIndex)   {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(short[] a, int fromIndex, int toIndex)  {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(char[] a, int fromIndex, int toIndex)   {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(byte[] a, int fromIndex, int toIndex)   {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(float[] a, int fromIndex, int toIndex)  {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void sort(double[] a, int fromIndex, int toIndex) {java.util.Arrays.sort(a, fromIndex, toIndex);}
	public static void parallelSort(byte[] a)   {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(char[] a)   {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(short[] a)  {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(int[] a)    {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(long[] a)   {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(float[] a)  {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(double[] a) {java.util.Arrays.parallelSort(a);}
	public static void parallelSort(byte[] a, int fromIndex, int toIndex)   {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(char[] a, int fromIndex, int toIndex)   {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(short[] a, int fromIndex, int toIndex)  {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(int[] a, int fromIndex, int toIndex)    {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(long[] a, int fromIndex, int toIndex)   {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(float[] a, int fromIndex, int toIndex)  {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static void parallelSort(double[] a, int fromIndex, int toIndex) {java.util.Arrays.parallelSort(a, fromIndex, toIndex);}
	public static <T extends Comparable<? super T>> void parallelSort(T[] a)                             {java.util.Arrays.parallelSort(a                       );}
	public static <T extends Comparable<? super T>> void parallelSort(T[] a, int fromIndex, int toIndex) {java.util.Arrays.parallelSort(a, fromIndex, toIndex   );}
	public static <T> void parallelSort(T[] a, Comparator<? super T> c)                                  {java.util.Arrays.parallelSort(a,                     c);}
	public static <T> void parallelSort(T[] a, int fromIndex, int toIndex, Comparator<? super T> c)      {java.util.Arrays.parallelSort(a, fromIndex, toIndex, c);}
	public static void sort(Object[] a)                                                     {java.util.Arrays.sort(a                       );}
	public static void sort(Object[] a, int fromIndex, int toIndex)                         {java.util.Arrays.sort(a, fromIndex, toIndex   );}
	public static <T> void sort(T[] a, Comparator<? super T> c)                             {java.util.Arrays.sort(a,                     c);}
	public static <T> void sort(T[] a, int fromIndex, int toIndex, Comparator<? super T> c) {java.util.Arrays.sort(a, fromIndex, toIndex, c);}
	public static <T> void parallelPrefix(T[] array, BinaryOperator<T> op)     {java.util.Arrays.parallelPrefix(array, op);}
	public static void parallelPrefix(long[] array, LongBinaryOperator op)     {java.util.Arrays.parallelPrefix(array, op);}
	public static void parallelPrefix(double[] array, DoubleBinaryOperator op) {java.util.Arrays.parallelPrefix(array, op);}
	public static void parallelPrefix(int[] array, IntBinaryOperator op)       {java.util.Arrays.parallelPrefix(array, op);}
	public static <T> void parallelPrefix(T[] array, int fromIndex, int toIndex, BinaryOperator<T> op)     {java.util.Arrays.parallelPrefix(array, fromIndex, toIndex, op);}
	public static void parallelPrefix(long[] array, int fromIndex, int toIndex, LongBinaryOperator op)     {java.util.Arrays.parallelPrefix(array, fromIndex, toIndex, op);}
	public static void parallelPrefix(double[] array, int fromIndex, int toIndex, DoubleBinaryOperator op) {java.util.Arrays.parallelPrefix(array, fromIndex, toIndex, op);}
	public static void parallelPrefix(int[] array, int fromIndex, int toIndex, IntBinaryOperator op)       {java.util.Arrays.parallelPrefix(array, fromIndex, toIndex, op);}
	public static int binarySearch(long[] a, long key)     {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(int[] a, int key)       {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(short[] a, short key)   {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(char[] a, char key)     {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(byte[] a, byte key)     {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(double[] a, double key) {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(float[] a, float key)   {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(Object[] a, Object key) {return java.util.Arrays.binarySearch(a, key);}
	public static int binarySearch(long[] a, int fromIndex, int toIndex, long key)     {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(int[] a, int fromIndex, int toIndex, int key)       {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(short[] a, int fromIndex, int toIndex, short key)   {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(char[] a, int fromIndex, int toIndex, char key)     {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key)     {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(float[] a, int fromIndex, int toIndex, float key)   {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static int binarySearch(Object[] a, int fromIndex, int toIndex, Object key) {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key);}
	public static <T> int binarySearch(T[] a, T key, Comparator<? super T> c) {return java.util.Arrays.binarySearch(a, key, c);}
	public static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c) {return java.util.Arrays.binarySearch(a, fromIndex, toIndex, key, c);}
	public static boolean equals(long[] a, long[] a2)       {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(int[] a, int[] a2)         {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(short[] a, short a2[])     {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(char[] a, char[] a2)       {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(byte[] a, byte[] a2)       {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(boolean[] a, boolean[] a2) {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(double[] a, double[] a2)   {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(float[] a, float[] a2)     {return java.util.Arrays.equals(a, a2);}
	public static boolean equals(Object[] a, Object[] a2)   {return java.util.Arrays.equals(a, a2);}
	public static void fill(long[] a, long val)       {java.util.Arrays.fill(a, val);}
	public static void fill(int[] a, int val)         {java.util.Arrays.fill(a, val);}
	public static void fill(short[] a, short val)     {java.util.Arrays.fill(a, val);}
	public static void fill(char[] a, char val)       {java.util.Arrays.fill(a, val);}
	public static void fill(byte[] a, byte val)       {java.util.Arrays.fill(a, val);}
	public static void fill(boolean[] a, boolean val) {java.util.Arrays.fill(a, val);}
	public static void fill(double[] a, double val)   {java.util.Arrays.fill(a, val);}
	public static void fill(float[] a, float val)     {java.util.Arrays.fill(a, val);}
	public static void fill(Object[] a, Object val)   {java.util.Arrays.fill(a, val);}
	public static void fill(long[] a, int fromIndex, int toIndex, long val)       {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(int[] a, int fromIndex, int toIndex, int val)         {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(short[] a, int fromIndex, int toIndex, short val)     {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(char[] a, int fromIndex, int toIndex, char val)       {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(byte[] a, int fromIndex, int toIndex, byte val)       {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val) {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(double[] a, int fromIndex, int toIndex,double val)    {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(float[] a, int fromIndex, int toIndex, float val)     {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static void fill(Object[] a, int fromIndex, int toIndex, Object val)   {java.util.Arrays.fill(a, fromIndex, toIndex, val);}
	public static <T> T[] copyOf(T[] original, int newLength)         {return java.util.Arrays.copyOf(original, newLength);}
	public static byte[] copyOf(byte[] original, int newLength)       {return java.util.Arrays.copyOf(original, newLength);}
	public static short[] copyOf(short[] original, int newLength)     {return java.util.Arrays.copyOf(original, newLength);}
	public static int[] copyOf(int[] original, int newLength)         {return java.util.Arrays.copyOf(original, newLength);}
	public static long[] copyOf(long[] original, int newLength)       {return java.util.Arrays.copyOf(original, newLength);}
	public static char[] copyOf(char[] original, int newLength)       {return java.util.Arrays.copyOf(original, newLength);}
	public static float[] copyOf(float[] original, int newLength)     {return java.util.Arrays.copyOf(original, newLength);}
	public static double[] copyOf(double[] original, int newLength)   {return java.util.Arrays.copyOf(original, newLength);}
	public static boolean[] copyOf(boolean[] original, int newLength) {return java.util.Arrays.copyOf(original, newLength);}
	public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {return java.util.Arrays.copyOf(original, newLength, newType);}
	public static <T> T[] copyOfRange(T[] original, int from, int to)         {return java.util.Arrays.copyOfRange(original, from, to);}
	public static byte[] copyOfRange(byte[] original, int from, int to)       {return java.util.Arrays.copyOfRange(original, from, to);}
	public static short[] copyOfRange(short[] original, int from, int to)     {return java.util.Arrays.copyOfRange(original, from, to);}
	public static int[] copyOfRange(int[] original, int from, int to)         {return java.util.Arrays.copyOfRange(original, from, to);}
	public static long[] copyOfRange(long[] original, int from, int to)       {return java.util.Arrays.copyOfRange(original, from, to);}
	public static char[] copyOfRange(char[] original, int from, int to)       {return java.util.Arrays.copyOfRange(original, from, to);}
	public static float[] copyOfRange(float[] original, int from, int to)     {return java.util.Arrays.copyOfRange(original, from, to);}
	public static double[] copyOfRange(double[] original, int from, int to)   {return java.util.Arrays.copyOfRange(original, from, to);}
	public static boolean[] copyOfRange(boolean[] original, int from, int to) {return java.util.Arrays.copyOfRange(original, from, to);}
	public static <T,U> T[] copyOfRange(U[] original, int from, int to, Class<? extends T[]> newType) {return java.util.Arrays.copyOfRange(original, from, to, newType);}
	public @SafeVarargs static <T> List<T> asList(T... a) {return List.of(a);}
	public static int hashCode(long a[])    {return java.util.Arrays.hashCode(a);}
	public static int hashCode(int a[])     {return java.util.Arrays.hashCode(a);}
	public static int hashCode(short a[])   {return java.util.Arrays.hashCode(a);}
	public static int hashCode(char a[])    {return java.util.Arrays.hashCode(a);}
	public static int hashCode(byte a[])    {return java.util.Arrays.hashCode(a);}
	public static int hashCode(boolean a[]) {return java.util.Arrays.hashCode(a);}
	public static int hashCode(float a[])   {return java.util.Arrays.hashCode(a);}
	public static int hashCode(double a[])  {return java.util.Arrays.hashCode(a);}
	public static int hashCode(Object a[])  {return java.util.Arrays.hashCode(a);}
	public static int deepHashCode(Object a[]) {return java.util.Arrays.deepHashCode(a);}
	public static boolean deepEquals(Object[] a1, Object[] a2) {return java.util.Arrays.deepEquals(a1, a2);}
	public static String toString(long[] a)       {return java.util.Arrays.toString(a);}
	public static String toString(int[] a)        {return java.util.Arrays.toString(a);}
	public static String toString(short[] a)      {return java.util.Arrays.toString(a);}
	public static String toString(char[] a)       {return java.util.Arrays.toString(a);}
	public static String toString(byte[] a)       {return java.util.Arrays.toString(a);}
	public static String toString(boolean[] a)    {return java.util.Arrays.toString(a);}
	public static String toString(float[] a)      {return java.util.Arrays.toString(a);}
	public static String toString(double[] a)     {return java.util.Arrays.toString(a);}
	public static String toString(Object[] a)     {return java.util.Arrays.toString(a);}
	public static String deepToString(Object[] a) {return java.util.Arrays.deepToString(a);}
	public static <T> void setAll(T[] array, IntFunction<? extends T> generator) {java.util.Arrays.setAll(array, generator);}
	public static void setAll(int[] array, IntUnaryOperator generator)           {java.util.Arrays.setAll(array, generator);}
	public static void setAll(long[] array, IntToLongFunction generator)         {java.util.Arrays.setAll(array, generator);}
	public static void setAll(double[] array, IntToDoubleFunction generator)     {java.util.Arrays.setAll(array, generator);}
	public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> generator) {java.util.Arrays.parallelSetAll(array, generator);}
	public static void parallelSetAll(int[] array, IntUnaryOperator generator)           {java.util.Arrays.parallelSetAll(array, generator);}
	public static void parallelSetAll(long[] array, IntToLongFunction generator)         {java.util.Arrays.parallelSetAll(array, generator);}
	public static void parallelSetAll(double[] array, IntToDoubleFunction generator)     {java.util.Arrays.parallelSetAll(array, generator);}
	public static <T> Spliterator<T> spliterator(T[] array)        {return java.util.Arrays.spliterator(array);}
	public static Spliterator.OfInt spliterator(int[] array)       {return java.util.Arrays.spliterator(array);}
	public static Spliterator.OfLong spliterator(long[] array)     {return java.util.Arrays.spliterator(array);}
	public static Spliterator.OfDouble spliterator(double[] array) {return java.util.Arrays.spliterator(array);}
	public static <T> Spliterator<T> spliterator(T[] array, int startInclusive, int endExclusive)        {return java.util.Arrays.spliterator(array, startInclusive, endExclusive);}
	public static Spliterator.OfInt spliterator(int[] array, int startInclusive, int endExclusive)       {return java.util.Arrays.spliterator(array, startInclusive, endExclusive);}
	public static Spliterator.OfLong spliterator(long[] array, int startInclusive, int endExclusive)     {return java.util.Arrays.spliterator(array, startInclusive, endExclusive);}
	public static Spliterator.OfDouble spliterator(double[] array, int startInclusive, int endExclusive) {return java.util.Arrays.spliterator(array, startInclusive, endExclusive);}
	public static <T> Stream<T> stream(T[] array)     {return new     Stream<>(()->java.util.Arrays.stream(array));}
	public static IntStream stream(int[] array)       {return new    IntStream(()->java.util.Arrays.stream(array));}
	public static LongStream stream(long[] array)     {return new   LongStream(()->java.util.Arrays.stream(array));}
	public static DoubleStream stream(double[] array) {return new DoubleStream(()->java.util.Arrays.stream(array));}
	public static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive)     {return new     Stream<>(()->java.util.Arrays.stream(array, startInclusive, endExclusive));}
	public static IntStream stream(int[] array, int startInclusive, int endExclusive)       {return new    IntStream(()->java.util.Arrays.stream(array, startInclusive, endExclusive));}
	public static LongStream stream(long[] array, int startInclusive, int endExclusive)     {return new   LongStream(()->java.util.Arrays.stream(array, startInclusive, endExclusive));}
	public static DoubleStream stream(double[] array, int startInclusive, int endExclusive) {return new DoubleStream(()->java.util.Arrays.stream(array, startInclusive, endExclusive));}
}//*E*
