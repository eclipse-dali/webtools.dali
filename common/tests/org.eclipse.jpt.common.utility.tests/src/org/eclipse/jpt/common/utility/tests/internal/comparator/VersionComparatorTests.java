/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.comparator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;
import org.eclipse.jpt.common.utility.internal.comparator.VersionComparator;
import org.eclipse.jpt.common.utility.internal.comparator.VersionComparator.SegmentParser;

@SuppressWarnings("nls")
public class VersionComparatorTests
	extends TestCase
{
	public VersionComparatorTests(String name) {
		super(name);
	}

	public void testVersionIsEqual_integer() {
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0", "2.0.0") == 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0", "2.0.0.0") == 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0.0.0.0.0000", "2.0") == 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.-1", "2.0.-1") == 0);
	}

	public void testVersionIsLess_integer() {
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0", "2.0.1") < 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.5.0", "2.14") < 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.5.0", "2.5.0.0.1.0") < 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.5.0.0.0.-1", "2.5") < 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.-1", "2.0.0") < 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.-1", "2") < 0);
	}

	public void testVersionIsGreater_integer() {
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.2", "2.0.1") > 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.2", "2.0.1") > 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.5.0.0.1.0", "2.5.0") > 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.5", "2.5.0.0.0.-1") > 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0", "2.0.-1") > 0);
		assertTrue(ComparatorTools.integerVersionComparator().compare("2", "2.0.-1") > 0);
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
		Comparator<String> versionComparator = ComparatorTools.versionComparator(',', DecimalSegmentParser.instance());
		assertTrue(versionComparator.compare("2,0,2", "2,0,1") > 0);
		assertTrue(versionComparator.compare("2,0,2.1", "2,0,1") > 0);
		assertTrue(versionComparator.compare("2,0,2", "2,0,1.9") > 0);
		assertTrue(versionComparator.compare("2.000,0,2", "2,0,1") > 0);
	}

	public void testVersionIsEqual_subclass() {
		Comparator<String> versionComparator = ComparatorTools.integerVersionComparator();
		assertTrue(versionComparator.compare("2.0.0", "2.0.0") == 0);
		assertTrue(versionComparator.compare("2.0.0", "2.0.0.0") == 0);
		assertTrue(versionComparator.compare("2.0.0.0", "2.0") == 0);
		assertTrue(versionComparator.compare("2.0.-1", "2.0.-1") == 0);
	}

	public void testVersionIsLess_subclass() {
		Comparator<String> versionComparator = ComparatorTools.integerVersionComparator();
		assertTrue(versionComparator.compare("2.0.0", "2.0.1") < 0);
		assertTrue(versionComparator.compare("2.5.0", "2.14") < 0);
		assertTrue(versionComparator.compare("2.5.0", "2.5.0.0.1.0") < 0);
		assertTrue(versionComparator.compare("2.0.-1", "2.0.0") < 0);
		assertTrue(versionComparator.compare("2.0.-1", "2") < 0);
	}

	public void testVersionIsGreater_subclass() {
		Comparator<String> versionComparator = ComparatorTools.integerVersionComparator();
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
			assertTrue(ComparatorTools.integerVersionComparator().compare("2.0.0", "2.O.O") == 0);
		} catch (NumberFormatException ex) {
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
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
