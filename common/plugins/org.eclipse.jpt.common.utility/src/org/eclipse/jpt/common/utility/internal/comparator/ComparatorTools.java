/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.comparator.VersionComparator.SegmentParser;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Comparator} utility methods.
 */
public final class ComparatorTools {

	/**
	 * Of the two specified objects, return the one that is ordered first.
	 * @param <E> the type of elements to be compared
	 * @see Comparable#compareTo(Object)
	 */
	public static <E extends Comparable<E>> E min(E o1, E o2) {
		return (o1.compareTo(o2) < 0) ? o1 : o2;
	}

	/**
	 * Of the two specified objects, return the one that is ordered first,
	 * as determined by the specified comparator.
	 * @param <E> the type of elements to be compared
	 * @see Comparable#compareTo(Object)
	 */
	public static <E> E min(E o1, E o2, Comparator<? super E> comparator) {
		return (comparator.compare(o1, o2) < 0) ? o1 : o2;
	}

	/**
	 * Of the two specified objects, return the one that is ordered last.
	 * @param <E> the type of elements to be compared
	 * @see Comparable#compareTo(Object)
	 */
	public static <E extends Comparable<E>> E max(E o1, E o2) {
		return (o1.compareTo(o2) > 0) ? o1 : o2;
	}

	/**
	 * Of the two specified objects, return the one that is ordered last,
	 * as determined by the specified comparator.
	 * @param <E> the type of elements to be compared
	 * @see Comparable#compareTo(Object)
	 */
	public static <E> E max(E o1, E o2, Comparator<? super E> comparator) {
		return (comparator.compare(o1, o2) > 0) ? o1 : o2;
	}


	// ********** boolean **********

	/**
	 * Return a comparator will compare {@link Boolean}s, depending the
	 * specified "true first" flag.
	 */
	public static Comparator<Boolean> booleanComparator(boolean trueFirst) {
		return trueFirst ? truesFirstBooleanComparator() : falsesFirstBooleanComparator();
	}

	/**
	 * Return a comparator will compare {@link Boolean}s, sorting
	 * <code>false</code>s first.
	 */
	public static Comparator<Boolean> falsesFirstBooleanComparator() {
		return FalsesFirstBooleanComparator.instance();
	}

	/**
	 * Return a comparator will compare {@link Boolean}s, sorting
	 * <code>true</code>s first.
	 */
	public static Comparator<Boolean> truesFirstBooleanComparator() {
		return TruesFirstBooleanComparator.instance();
	}


	// ********** chain **********

	/**
	 * @see #chain(Iterable)
	 */
	@SafeVarargs
	public static <E> Comparator<E> chain(Comparator<? super E>... comparators) {
		return chain(ArrayTools.iterable(comparators));
	}

	/**
	 * Return a comparator that will use the specified list of comparators to
	 * compare two objects.
	 * If the first comparator returns a non-zero value, that will be the
	 * comparator's value; otherwise the next comparator will be called;
	 * and so on.
	 * @param <E> the type of elements to be compared
	 */
	public static <E> Comparator<E> chain(Iterable<Comparator<? super E>> comparators) {
		return new ComparatorChain<>(comparators);
	}


	// ********** comparable/natural **********

	/**
	 * Return a comparator will compare {@link Comparable}s.
	 * @param <E> the type of elements to be compared
	 */
	public static <E extends Comparable<E>> Comparator<E> comparableComparator() {
		return ComparableComparator.instance();
	}

	/**
	 * Return a comparator will compare {@link Comparable}s.
	 * @param <E> the type of elements to be compared
	 */
	public static <E extends Comparable<E>> Comparator<E> naturalComparator() {
		return comparableComparator();
	}


	// ********** nulls **********

	/**
	 * Return a comparator that will sort <code>null</code>s <em>before</em> any
	 * non-<code>null</code> elements. Non-<code>null</code> elements will be
	 * compared by the specified comparator.
	 * @param <E> the type of elements to be compared
	 */
	public static <E> Comparator<E> nullsFirst(Comparator<? super E> comparator) {
		return new NullsFirstComparator<>(comparator);
	}

	/**
	 * Return a comparator that will sort <code>null</code>s <em>after</em> any
	 * non-<code>null</code> elements. Non-<code>null</code> elements will be
	 * compared by the specified comparator.
	 * @param <E> the type of elements to be compared
	 */
	public static <E> Comparator<E> nullsLast(Comparator<? super E> comparator) {
		return new NullsLastComparator<>(comparator);
	}


	// ********** reverse **********

	/**
	 * Return a comparator that will reverse the order of
	 * {@link Comparable}s.
	 * @param <E> the type of elements to be compared
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Comparable<E>> Comparator<E> reverseComparator() {
		return reverse((Comparator<E>) comparableComparator());
	}

	/**
	 * Return a comparator that will reverse the order of the specified
	 * comparator.
	 * @param <E> the type of elements to be compared
	 */
	public static <E> Comparator<E> reverse(Comparator<? super E> comparator) {
		return new ReverseComparator<>(comparator);
	}


	// ********** string **********

	/**
	 * Return a collator that wraps the default Java text collator and
	 * implements a {@link String} {@link Comparator} (instead of an
	 * {@link Object} {@link Comparator}, which is what {@link Collator} does,
	 * possibly for backward-compatibility reasons(?)).
	 * @see Collator#getInstance()
	 */
	public static Comparator<String> stringCollator() {
		return stringCollator(Collator.getInstance());
	}

	/**
	 * Return a collator that wraps the Java text collator for the specified
	 * locale and implements a
	 * {@link String} {@link Comparator} (instead of an {@link Object}
	 * {@link Comparator}, which is what {@link Collator} does, possibly for
	 * backward-compatibility reasons(?)).
	 * @see Collator#getInstance(Locale)
	 */
	public static Comparator<String> stringCollator(Locale locale) {
		return stringCollator(Collator.getInstance(locale));
	}

	/**
	 * Return a collator that wraps the specified Java text collator and
	 * implements a {@link String} {@link Comparator} (instead of an
	 * {@link Object} {@link Comparator}, which is what {@link Collator}
	 * does, possibly for backward-compatibility reasons(?)).
	 * @see Collator
	 */
	public static Comparator<String> stringCollator(Collator collator) {
		return new StringCollator(collator);
	}


	// ********** transformation **********

	/**
	 * Return a comparator will transform the elements to be compared and
	 * compare the resulting outputs (i.e. assume the outputs
	 * implement the {@link Comparable} interface).
	 * @param <E> the type of elements to be compared
	 * @param <O> the type of the result of transforming the elements and the type
	 *   of the elements to be compared by the wrapped comaparator, if present
	 */
	@SuppressWarnings("unchecked")
	public static <E, O> Comparator<E> transformationComparator(Transformer<? super E, ? extends O> transformer) {
		return transformationComparator(transformer, (Comparator<O>) comparableComparator());
	}

	/**
	 * Return a comparator will transform the elements to be compared and pass the
	 * resulting outputs to a specified comparator.
	 * If the specified comparator is <code>null</code>,
	 * the natural ordering of the outputs will be used (i.e. assume the outputs
	 * implement the {@link Comparable} interface).
	 * @param <E> the type of elements to be compared
	 * @param <O> the type of the result of transforming the elements and the type
	 *     of the elements to be compared by the wrapped comaparator, if present
	 */
	public static <E, O> Comparator<E> transformationComparator(Transformer<? super E, ? extends O> transformer, Comparator<O> comparator) {
		return new TransformationComparator<>(transformer, comparator);
	}


	// ********** version **********

	/**
	 * Return a version comparator that converts
	 * each version into a series of integers and compares them.
	 * <p>
	 * <strong>NB:</strong> With this comparator
	 * <code>"2.<strong>14</strong>" > "2.<strong>2</strong>"</code>
	 * is <code>true</code>.
	 */
	public static <T extends Comparable<T>> Comparator<String> integerVersionComparator() {
		return INTEGER_VERSION_COMPARATOR;
	}

	/**
	 * @see #integerVersionComparator()
	 */
	public static final Comparator<String> INTEGER_VERSION_COMPARATOR = versionComparator(VersionComparator.SegmentParser.IntegerSegmentParser.instance());

	/**
	 * The default delimiter is <code>'.'</code>.
	 * @see #versionComparator(String, SegmentParser)
	 */
	public static <T extends Comparable<T>> Comparator<String> versionComparator(SegmentParser<T> segmentParser) {
		return versionComparator(".", segmentParser); //$NON-NLS-1$
	}

	/**
	 * @see #versionComparator(String, SegmentParser)
	 */
	public static <T extends Comparable<T>> Comparator<String> versionComparator(char delimiter, SegmentParser<T> segmentParser) {
		return versionComparator(new char[] {delimiter}, segmentParser);
	}

	/**
	 * @see #versionComparator(String, SegmentParser)
	 */
	public static <T extends Comparable<T>> Comparator<String> versionComparator(char[] delimiters, SegmentParser<T> segmentParser) {
		return versionComparator(new String(delimiters), segmentParser);
	}

	/**
	 * Return a comparator tha can be used to compare version strings
	 * (e.g. <code>"2.2.2"</code> vs. <code>"2.14.3"</code>).
	 * Clients can specify the delimiter(s) that separates a version's
	 * <em>segments</em> as well as a parser to be used for parsing each
	 * <em>segment</em>.
	 * @param <T> the type of comparable returned by the comparator's segment parser
	 */
	public static <T extends Comparable<T>> Comparator<String> versionComparator(String delimiters, SegmentParser<T> segmentParser) {
		return new VersionComparator<>(delimiters, segmentParser);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ComparatorTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
