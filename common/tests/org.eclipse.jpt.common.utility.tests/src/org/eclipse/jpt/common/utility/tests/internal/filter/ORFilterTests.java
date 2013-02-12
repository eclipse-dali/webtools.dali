/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.filter;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.filter.ORFilter;
import org.eclipse.jpt.common.utility.internal.filter.SimpleFilter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class ORFilterTests
	extends TestCase
{
	private ORFilter<Number> orFilter;


	public ORFilterTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.orFilter = new ORFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
	}

	private Predicate<Number> buildMinFilter(double min) {
		return new MinFilter(min);
	}

	static class MinFilter
		extends SimpleFilter<Number, Number>
	{
		private static final long serialVersionUID = 1L;
		MinFilter(double min) {
			super(new Double(min));
		}
		@Override
		public boolean evaluate(Number number) {
			return number.doubleValue() <= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildMaxFilter(double max) {
		return new MaxFilter(max);
	}

	static class MaxFilter
		extends SimpleFilter<Number, Number>
	{
		private static final long serialVersionUID = 1L;
		MaxFilter(double min) {
			super(new Double(min));
		}
		@Override
		public boolean evaluate(Number number) {
			return number.doubleValue() >= this.criterion.doubleValue();
		}
	}

	private Predicate<Number> buildEvenFilter() {
		return new EvenFilter();
	}

	static class EvenFilter
		extends FilterAdapter<Number>
	{
		EvenFilter() {
			super();
		}
		@Override
		public boolean evaluate(Number number) {
			return BitTools.isEven(number.intValue());
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testFiltering2() {
		assertFalse(this.orFilter.evaluate(new Integer(7)));
		assertFalse(this.orFilter.evaluate(new Integer(2)));
		assertFalse(this.orFilter.evaluate(new Double(6.666)));
		assertTrue(this.orFilter.evaluate(new Double(-99)));
		assertTrue(this.orFilter.evaluate(new Double(-1)));
		assertTrue(this.orFilter.evaluate(new Double(11)));
		assertTrue(this.orFilter.evaluate(new Double(111)));
	}

	public void testFiltering3() {
		@SuppressWarnings("unchecked")
		ORFilter<Number> orFilter2 = new ORFilter<Number>(this.orFilter, this.buildEvenFilter());
		assertFalse(orFilter2.evaluate(new Integer(7)));
		assertFalse(orFilter2.evaluate(new Integer(3)));
		assertFalse(orFilter2.evaluate(new Integer(9)));
		assertTrue(orFilter2.evaluate(new Integer(2)));
		assertTrue(orFilter2.evaluate(new Double(6.1)));
		assertTrue(orFilter2.evaluate(new Double(-99)));
		assertTrue(orFilter2.evaluate(new Double(-1)));
		assertTrue(orFilter2.evaluate(new Double(11)));
		assertTrue(orFilter2.evaluate(new Double(111)));
		assertTrue(orFilter2.evaluate(new Double(-98)));
		assertTrue(orFilter2.evaluate(new Double(0)));
		assertTrue(orFilter2.evaluate(new Double(-2)));
		assertTrue(orFilter2.evaluate(new Double(12)));
		assertTrue(orFilter2.evaluate(new Double(222)));
	}

	public void testFilteringComposite() {
		@SuppressWarnings("unchecked")
		Predicate<Number> orFilter2 = new ORFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10), this.buildEvenFilter());
		assertFalse(orFilter2.evaluate(new Integer(7)));
		assertFalse(orFilter2.evaluate(new Integer(3)));
		assertFalse(orFilter2.evaluate(new Integer(9)));
		assertTrue(orFilter2.evaluate(new Integer(2)));
		assertTrue(orFilter2.evaluate(new Double(6.1)));
		assertTrue(orFilter2.evaluate(new Double(-99)));
		assertTrue(orFilter2.evaluate(new Double(-1)));
		assertTrue(orFilter2.evaluate(new Double(11)));
		assertTrue(orFilter2.evaluate(new Double(111)));
		assertTrue(orFilter2.evaluate(new Double(-98)));
		assertTrue(orFilter2.evaluate(new Double(0)));
		assertTrue(orFilter2.evaluate(new Double(-2)));
		assertTrue(orFilter2.evaluate(new Double(12)));
		assertTrue(orFilter2.evaluate(new Double(222)));
	}

	public void testClone() {
		@SuppressWarnings("unchecked")
		ORFilter<Number> orFilter2 = (ORFilter<Number>) this.orFilter.clone();
		assertEquals(this.orFilter.getFilters()[0], orFilter2.getFilters()[0]);
		assertEquals(this.orFilter.getFilters()[1], orFilter2.getFilters()[1]);
		assertNotSame(this.orFilter, orFilter2);
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		ORFilter<Number> orFilter2 = new ORFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
		assertEquals(this.orFilter, orFilter2);
		assertEquals(this.orFilter.hashCode(), orFilter2.hashCode());
	}

	public void testSerialization() throws Exception {
		ORFilter<Number> orFilter2 = TestTools.serialize(this.orFilter);
		assertEquals(this.orFilter.getFilters()[0], orFilter2.getFilters()[0]);
		assertEquals(this.orFilter.getFilters()[1], orFilter2.getFilters()[1]);
		assertNotSame(this.orFilter, orFilter2);
	}
}
