/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * This comparator can be used to compare version strings (e.g. "2.2.2" vs.
 * "2.14.3"). Clients can specify the delimiter(s) that separates a version's
 * <em>segments</em> as well as a parser to be used for parsing each
 * <em>segment</em>.
 * 
 * @see #INTEGER_VERSION_COMPARATOR
 */
public class VersionComparator<T extends Comparable<T>>
	implements Comparator<String>
{
	private final String delimiters;
	private final SegmentParser<T> segmentParser;


	/**
	 * Static implementation of the version comparator interface that converts
	 * each version into a series of integers and compares them.
	 * <p>
	 * <strong>NB:</strong> With this comparator <code>"2.14" > "2.2"</code>
	 */
	public static final Comparator<String> INTEGER_VERSION_COMPARATOR = new VersionComparator<Integer>(SegmentParser.IntegerSegmentParser.instance());


	/**
	 * The default delimiter is <code>'.'</code>.
	 * The default segment parser is disabled.
	 * <p>
	 * <strong>NB:</strong> Subclass must override:<ul>
	 *     <li>{@link #parseSegment(int, String)}
	 *     <li>{@link #getZero()}
	 * </ul>
	 */
	protected VersionComparator() {
		this("."); //$NON-NLS-1$
	}

	/**
	 * The default segment parser is disabled.
	 * <p>
	 * <strong>NB:</strong> Subclass must override:<ul>
	 *     <li>{@link #parseSegment(int, String)}
	 *     <li>{@link #getZero()}
	 * </ul>
	 */
	protected VersionComparator(String delimiters) {
		this(delimiters, SegmentParser.Disabled.<T>instance());
	}

	/**
	 * Use the specified segment parser.
	 * The default delimiter is <code>'.'</code>.
	 */
	public VersionComparator(SegmentParser<T> segmentParser) {
		this(".", segmentParser); //$NON-NLS-1$
	}

	/**
	 * Use the specified delimiters and segment parser.
	 */
	public VersionComparator(String delimiters, SegmentParser<T> segmentParser) {
		super();
		this.delimiters = delimiters;
		this.segmentParser = segmentParser;
	}


	/**
	 * <strong>NB:</strong> Callers must handle any exceptions thrown by the
	 * segment parser supplied to the comparator. In particular, the pre-built
	 * integer segment parser {@link #INTEGER_VERSION_COMPARATOR} can throw a
	 * {@link NumberFormatException} if any segement string contains non-numeric
	 * characters.
	 */
	public int compare(String version1, String version2) {
		ArrayList<T> segments1 = this.parseVersion(version1);
		ArrayList<T> segments2 = this.parseVersion(version2);
		int size1 = segments1.size();
		int size2 = segments2.size();
		int min = Math.min(size1, size2);
		for (int i = 0; i < min; i++) {
			int segmentCompare = segments1.get(i).compareTo(segments2.get(i));
			if (segmentCompare != 0) {
				return segmentCompare;
			}
		}

		if (size1 == size2) {
			return 0;
		}

		int max = Math.max(size1, size2);
		T zero = this.getZero();
		if (size1 < size2) {
			for (int i = min; i < max; i++) {
				int segmentCompare = zero.compareTo(segments2.get(i));
				if (segmentCompare != 0) {
					return segmentCompare;
				}
			}
		} else {
			for (int i = min; i < max; i++) {
				int segmentCompare = segments1.get(i).compareTo(zero);
				if (segmentCompare != 0) {
					return segmentCompare;
				}
			}
		}
		return 0;
	}

	/**
	 * Parse the specified version into a list of segments that can be
	 * compared individually.
	 */
	protected ArrayList<T> parseVersion(String s) {
		ArrayList<T> segments = new ArrayList<T>();
		int i = 0;
		for (StringTokenizer stream = new StringTokenizer(s, this.delimiters); stream.hasMoreTokens(); ) {
			segments.add(this.parseSegment(i++, stream.nextToken()));
		}
		return segments;
	}

	/**
	 * Parse the specified segment into the appropriate comparable.
	 * Subclasses must override this method if a segment parser is not passed
	 * to the version comparator's constructor.
	 */
	protected T parseSegment(int index, String s) {
		return this.segmentParser.parse(index, s);
	}

	protected T getZero() {
		return this.segmentParser.getZero();
	}


	/**
	 * A segment parser is used by a version comparator to convert each
	 * <em>segment</em> of a version into something that can be compared to the
	 * corresponding <em>segment</em> in another version.
	 */
	public interface SegmentParser<T extends Comparable<T>> {
		/**
		 * Convert the specified version <em>segment</em> into something that
		 * can be compared to the corresponding <em>segment</em> in another
		 * version.
		 */
		T parse(int segmentIndex, String segment);

		/**
		 * Return a "zero" <em>segment</em> value that can be compared to
		 * trailing segments when two version have differing numbers of
		 * <em>segments</em>.
		 */
		T getZero();

		/**
		 * Singleton implementation of the segment parser interface that converts
		 * each segment into an integer, irrespective of position.
		 * <p>
		 * <strong>NB:</strong> With this parser <code>"2.14" > "2.2"</code>
		 */
		final class IntegerSegmentParser
			implements SegmentParser<Integer>, Serializable
		{
			public static final SegmentParser<Integer> INSTANCE = new IntegerSegmentParser();
			public static SegmentParser<Integer> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private IntegerSegmentParser() {
				super();
			}
			// simply parse the segment as an integer
			public Integer parse(int segmentIndex, String segment) {
				return Integer.valueOf(segment);
			}
			public Integer getZero() {
				return ZERO;
			}
			private static final Integer ZERO = Integer.valueOf(0);
			@Override
			public String toString() {
				return StringTools.buildSingletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}

		/**
		 * Singleton implementation of the segment parser interface that throws
		 * an exception if called.
		 */
		final class Disabled<S extends Comparable<S>>
			implements SegmentParser<S>, Serializable
		{
			@SuppressWarnings("rawtypes")
			public static final SegmentParser INSTANCE = new Disabled();
			@SuppressWarnings("unchecked")
			public static <R extends Comparable<R>> SegmentParser<R> instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Disabled() {
				super();
			}
			// throw an exception
			public S parse(int segmentIndex, String segment) {
				throw new UnsupportedOperationException();
			}
			public S getZero() {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return StringTools.buildSingletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}
