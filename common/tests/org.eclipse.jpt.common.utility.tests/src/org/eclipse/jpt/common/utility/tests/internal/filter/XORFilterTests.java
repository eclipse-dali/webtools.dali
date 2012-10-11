/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.filter;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.filter.SimpleFilter;
import org.eclipse.jpt.common.utility.internal.filter.XORFilter;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class XORFilterTests
	extends TestCase
{
	private XORFilter<Number> xorFilter;


	public XORFilterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.xorFilter = new XORFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
	}

	private Filter<Number> buildMinFilter(double min) {
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
		public boolean accept(Number number) {
			return number.doubleValue() <= this.criterion.doubleValue();
		}
	}

	private Filter<Number> buildMaxFilter(double max) {
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
		public boolean accept(Number number) {
			return number.doubleValue() >= this.criterion.doubleValue();
		}
	}

	private Filter<Number> buildEvenFilter() {
		return new EvenFilter();
	}

	static class EvenFilter
		extends FilterAdapter<Number>
	{
		EvenFilter() {
			super();
		}
		@Override
		public boolean accept(Number number) {
			return BitTools.isEven(number.intValue());
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testFiltering2() {
		assertFalse(this.xorFilter.accept(new Integer(7)));
		assertFalse(this.xorFilter.accept(new Integer(2)));
		assertFalse(this.xorFilter.accept(new Double(6.666)));
		assertTrue(this.xorFilter.accept(new Double(-99)));
		assertTrue(this.xorFilter.accept(new Double(-1)));
		assertTrue(this.xorFilter.accept(new Double(11)));
		assertTrue(this.xorFilter.accept(new Double(111)));
	}

	public void testFiltering3() {
		XORFilter<Number> xorFilter2 = new XORFilter<Number>(this.xorFilter, this.buildEvenFilter());
		assertFalse(xorFilter2.accept(new Integer(7)));
		assertFalse(xorFilter2.accept(new Integer(3)));
		assertFalse(xorFilter2.accept(new Integer(9)));
		assertTrue(xorFilter2.accept(new Integer(2)));
		assertTrue(xorFilter2.accept(new Double(6.1)));
		assertTrue(xorFilter2.accept(new Double(-99)));
		assertTrue(xorFilter2.accept(new Double(-1)));
		assertTrue(xorFilter2.accept(new Double(11)));
		assertTrue(xorFilter2.accept(new Double(111)));
		assertFalse(xorFilter2.accept(new Double(-98)));
		assertFalse(xorFilter2.accept(new Double(0)));
		assertFalse(xorFilter2.accept(new Double(-2)));
		assertFalse(xorFilter2.accept(new Double(12)));
		assertFalse(xorFilter2.accept(new Double(222)));
	}

	public void testClone() {
		@SuppressWarnings("unchecked")
		XORFilter<Number> xorFilter2 = (XORFilter<Number>) this.xorFilter.clone();
		assertEquals(this.xorFilter.getFilters()[0], xorFilter2.getFilters()[0]);
		assertEquals(this.xorFilter.getFilters()[1], xorFilter2.getFilters()[1]);
		assertNotSame(this.xorFilter, xorFilter2);
	}

	public void testEquals() {
		XORFilter<Number> xorFilter2 = new XORFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
		assertEquals(this.xorFilter, xorFilter2);
		assertEquals(this.xorFilter.hashCode(), xorFilter2.hashCode());
	}

	public void testSerialization() throws Exception {
		XORFilter<Number> xorFilter2 = TestTools.serialize(this.xorFilter);
		assertEquals(this.xorFilter.getFilters()[0], xorFilter2.getFilters()[0]);
		assertEquals(this.xorFilter.getFilters()[1], xorFilter2.getFilters()[1]);
		assertNotSame(this.xorFilter, xorFilter2);
	}
}
