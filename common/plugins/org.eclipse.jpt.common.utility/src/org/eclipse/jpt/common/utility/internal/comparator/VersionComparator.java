/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This comparator can be used to compare version strings
 * (e.g. <code>"2.2.2"</code> vs. <code>"2.14.3"</code>).
 * Clients can specify the delimiter(s) that separates a version's
 * <em>segments</em> as well as a parser to be used for parsing each
 * <em>segment</em>.
 * 
 * @param <T> the type of comparable returned by the comparator's segment parser
 */
public class VersionComparator<T extends Comparable<T>>
	implements Comparator<String>
{
	private final String delimiters;
	private final SegmentParser<T> segmentParser;


	public VersionComparator(String delimiters, SegmentParser<T> segmentParser) {
		super();
		if ((delimiters == null) || (segmentParser == null)) {
			throw new NullPointerException();
		}
		this.delimiters = delimiters;
		this.segmentParser = segmentParser;
	}

	/**
	 * <strong>NB:</strong> Callers must handle any runtime exceptions thrown by the
	 * segment parser supplied to the comparator. In particular, the pre-built
	 * integer segment parser {@link ComparatorTools#INTEGER_VERSION_COMPARATOR} can throw a
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
		ArrayList<T> segments = new ArrayList<>();
		int i = 0;
		for (StringTokenizer stream = new StringTokenizer(s, this.delimiters); stream.hasMoreTokens(); ) {
			segments.add(this.segmentParser.parse(i++, stream.nextToken()));
		}
		return segments;
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
				return ObjectTools.singletonToString(this);
			}
			private static final long serialVersionUID = 1L;
			private Object readResolve() {
				// replace this object with the singleton
				return INSTANCE;
			}
		}
	}
}
