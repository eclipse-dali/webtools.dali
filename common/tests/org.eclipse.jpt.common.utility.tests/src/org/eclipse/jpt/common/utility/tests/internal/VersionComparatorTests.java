/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.VersionComparator;
import org.eclipse.jpt.common.utility.internal.VersionComparator.SegmentParser;

@SuppressWarnings("nls")
public class VersionComparatorTests
	extends TestCase
{
	public VersionComparatorTests(String name) {
		super(name);
	}

	public void testVersionIsEqual_integer() {
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0", "2.0.0") == 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0", "2.0.0.0") == 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0.0.0.0.0000", "2.0") == 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.-1", "2.0.-1") == 0);
	}

	public void testVersionIsLess_integer() {
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0", "2.0.1") < 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.5.0", "2.14") < 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.5.0", "2.5.0.0.1.0") < 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.5.0.0.0.-1", "2.5") < 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.-1", "2.0.0") < 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.-1", "2") < 0);
	}

	public void testVersionIsGreater_integer() {
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.2", "2.0.1") > 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.2", "2.0.1") > 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.5.0.0.1.0", "2.5.0") > 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.5", "2.5.0.0.0.-1") > 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0", "2.0.-1") > 0);
		assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2", "2.0.-1") > 0);
	}

	public void testVersionIsEqual_integer_comma() {
		Comparator<String> versionComparator = new VersionComparator<BigDecimal>(",", DecimalSegmentParser.instance());
		assertTrue(versionComparator.compare("2,0,0", "2,0,0") == 0);
		assertTrue(versionComparator.compare("2,0.0,0", "2,0,0") == 0);
		assertTrue(versionComparator.compare("2,0.0,0", "2,0,0.0") == 0);
		assertTrue(versionComparator.compare("2.0,0.0,0", "2,0,0.0") == 0);
	}

	public void testVersionIsLess_integer_comma() {
		Comparator<String> versionComparator = new VersionComparator<BigDecimal>(",", DecimalSegmentParser.instance());
		assertTrue(versionComparator.compare("2,0,0", "2,0,1") < 0);
		assertTrue(versionComparator.compare("2,0.0,0", "2,0,1") < 0);
		assertTrue(versionComparator.compare("2,0,0", "2,0,1.0") < 0);
		assertTrue(versionComparator.compare("2.0,0,0", "2,0,1") < 0);
	}

	public void testVersionIsGreater_integer_comma() {
		Comparator<String> versionComparator = new VersionComparator<BigDecimal>(",", DecimalSegmentParser.instance());
		assertTrue(versionComparator.compare("2,0,2", "2,0,1") > 0);
		assertTrue(versionComparator.compare("2,0,2.1", "2,0,1") > 0);
		assertTrue(versionComparator.compare("2,0,2", "2,0,1.9") > 0);
		assertTrue(versionComparator.compare("2.000,0,2", "2,0,1") > 0);
	}

	public void testVersionIsEqual_subclass() {
		Comparator<String> versionComparator = new VersionComparator<Integer>() {
				@Override
				protected Integer parseSegment(int index, String s) {
					return Integer.valueOf(s);
				}
				@Override
				protected Integer getZero() {
					return Integer.valueOf(0);
				}
			};
		assertTrue(versionComparator.compare("2.0.0", "2.0.0") == 0);
		assertTrue(versionComparator.compare("2.0.0", "2.0.0.0") == 0);
		assertTrue(versionComparator.compare("2.0.0.0", "2.0") == 0);
		assertTrue(versionComparator.compare("2.0.-1", "2.0.-1") == 0);
	}

	public void testVersionIsLess_subclass() {
		Comparator<String> versionComparator = new VersionComparator<Integer>() {
				@Override
				protected Integer parseSegment(int index, String s) {
					return Integer.valueOf(s);
				}
				@Override
				protected Integer getZero() {
					return Integer.valueOf(0);
				}
			};
		assertTrue(versionComparator.compare("2.0.0", "2.0.1") < 0);
		assertTrue(versionComparator.compare("2.5.0", "2.14") < 0);
		assertTrue(versionComparator.compare("2.5.0", "2.5.0.0.1.0") < 0);
		assertTrue(versionComparator.compare("2.0.-1", "2.0.0") < 0);
		assertTrue(versionComparator.compare("2.0.-1", "2") < 0);
	}

	public void testVersionIsGreater_subclass() {
		Comparator<String> versionComparator = new VersionComparator<Integer>() {
				@Override
				protected Integer parseSegment(int index, String s) {
					return Integer.valueOf(s);
				}
				@Override
				protected Integer getZero() {
					return Integer.valueOf(0);
				}
			};
		assertTrue(versionComparator.compare("2.0.2", "2.0.1") > 0);
		assertTrue(versionComparator.compare("2.0.2", "2.0.1") > 0);
		assertTrue(versionComparator.compare("2.5.0.0.1.0", "2.5.0") > 0);
		assertTrue(versionComparator.compare("2.0.0", "2.0.-1") > 0);
		assertTrue(versionComparator.compare("2", "2.0.-1") > 0);
	}

	public void testBadString() {
		boolean exCaught = false;
		try {
			// note the letter 'O' instead of the numeral '0'
			assertTrue(VersionComparator.INTEGER_VERSION_COMPARATOR.compare("2.0.0", "2.O.O") == 0);
		} catch (NumberFormatException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testBogusSubclass1() {
		Comparator<String> versionComparator = new VersionComparator<Integer>() {
				// bogus - must override parseSegment(...)
				@Override
				protected Integer getZero() {
					return Integer.valueOf(0);
				}
			};
		boolean exCaught = false;
		try {
			assertTrue(versionComparator.compare("2.0.0", "2.0.0") == 0);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testBogusSubclass2() {
		Comparator<String> versionComparator = new VersionComparator<Integer>() {
				@Override
				protected Integer parseSegment(int index, String s) {
					return Integer.valueOf(s);
				}
				// bogus - must getZero()
			};
		boolean exCaught = false;
		try {
			assertTrue(versionComparator.compare("2.0.0", "2.0.0.0.0") == 0);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	static final class DecimalSegmentParser
		implements SegmentParser<BigDecimal>, Serializable
	{
		public static final SegmentParser<BigDecimal> INSTANCE = new DecimalSegmentParser();
		public static SegmentParser<BigDecimal> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private DecimalSegmentParser() {
			super();
		}
		// simply parse the segment as an integer
		public BigDecimal parse(int segmentIndex, String segment) {
			return new BigDecimal(segment);
		}
		public BigDecimal getZero() {
			return ZERO;
		}
		private static final BigDecimal ZERO = new BigDecimal(0);
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
