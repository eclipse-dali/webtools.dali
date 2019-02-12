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

import java.util.Comparator;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.comparator.ComparatorTools;

public class BooleanComparatorTests
	extends TestCase
{
	public BooleanComparatorTests(String name) {
		super(name);
	}

	public void testTrueFirstEqual() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(true);
		assertEquals(0, comparator.compare(Boolean.TRUE, Boolean.TRUE));
	}

	public void testTrueFirstLess() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(true);
		assertTrue(comparator.compare(Boolean.TRUE, Boolean.FALSE) < 0);
	}

	public void testTrueFirstGreater() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(true);
		assertTrue(comparator.compare(Boolean.FALSE, Boolean.TRUE) > 0);
	}

	public void testFalseFirstEqual() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(false);
		assertEquals(0, comparator.compare(Boolean.FALSE, Boolean.FALSE));
	}

	public void testFalseFirstLess() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(false);
		assertTrue(comparator.compare(Boolean.FALSE, Boolean.TRUE) < 0);
	}

	public void testFalseFirstGreater() {
		Comparator<Boolean> comparator = ComparatorTools.booleanComparator(false);
		assertTrue(comparator.compare(Boolean.TRUE, Boolean.FALSE) > 0);
	}
}
