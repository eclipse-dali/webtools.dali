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
import org.eclipse.jpt.common.utility.internal.filter.ANDFilter;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.filter.SimpleFilter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class ANDFilterTests
	extends TestCase
{
	private ANDFilter<Number> andFilter;


	public ANDFilterTests(String name) {
		super(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		this.andFilter = new ANDFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
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
			return number.doubleValue() >= this.criterion.doubleValue();
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
			return number.doubleValue() <= this.criterion.doubleValue();
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
		assertTrue(this.andFilter.evaluate(new Integer(7)));
		assertTrue(this.andFilter.evaluate(new Integer(2)));
		assertTrue(this.andFilter.evaluate(new Double(6.666)));
		assertFalse(this.andFilter.evaluate(new Double(-99)));
		assertFalse(this.andFilter.evaluate(new Double(-1)));
		assertFalse(this.andFilter.evaluate(new Double(11)));
		assertFalse(this.andFilter.evaluate(new Double(111)));
	}

	public void testFiltering3() {
		@SuppressWarnings("unchecked")
		ANDFilter<Number> andFilter2 = new ANDFilter<Number>(this.andFilter, this.buildEvenFilter());
		assertFalse(andFilter2.evaluate(new Integer(7)));
		assertTrue(andFilter2.evaluate(new Integer(2)));
		assertTrue(andFilter2.evaluate(new Double(6.1)));
		assertFalse(andFilter2.evaluate(new Double(-99)));
		assertFalse(andFilter2.evaluate(new Double(-1)));
		assertFalse(andFilter2.evaluate(new Double(11)));
		assertFalse(andFilter2.evaluate(new Double(111)));
	}

	public void testFilteringComposite() {
		@SuppressWarnings("unchecked")
		Predicate<Number> andFilter2 = new ANDFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10), this.buildEvenFilter());
		assertFalse(andFilter2.evaluate(new Integer(7)));
		assertTrue(andFilter2.evaluate(new Integer(2)));
		assertTrue(andFilter2.evaluate(new Double(6.1)));
		assertFalse(andFilter2.evaluate(new Double(-99)));
		assertFalse(andFilter2.evaluate(new Double(-1)));
		assertFalse(andFilter2.evaluate(new Double(11)));
		assertFalse(andFilter2.evaluate(new Double(111)));
	}

	public void testClone() {
		@SuppressWarnings("unchecked")
		ANDFilter<Number> andFilter2 = (ANDFilter<Number>) this.andFilter.clone();
		assertEquals(this.andFilter.getFilters()[0], andFilter2.getFilters()[0]);
		assertEquals(this.andFilter.getFilters()[1], andFilter2.getFilters()[1]);
		assertNotSame(this.andFilter, andFilter2);
	}

	public void testEquals() {
		@SuppressWarnings("unchecked")
		ANDFilter<Number> andFilter2 = new ANDFilter<Number>(this.buildMinFilter(1), this.buildMaxFilter(10));
		assertEquals(this.andFilter, andFilter2);
		assertEquals(this.andFilter.hashCode(), andFilter2.hashCode());
	}

	public void testSerialization() throws Exception {
		@SuppressWarnings("cast")
		ANDFilter<Number> andFilter2 = (ANDFilter<Number>) TestTools.serialize(this.andFilter);
		assertEquals(this.andFilter.getFilters()[0], andFilter2.getFilters()[0]);
		assertEquals(this.andFilter.getFilters()[1], andFilter2.getFilters()[1]);
		assertNotSame(this.andFilter, andFilter2);
	}
}
