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

import java.io.Serializable;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.filter.FilterAdapter;
import org.eclipse.jpt.common.utility.internal.filter.NOTFilter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NOTFilterTests
	extends TestCase
{
	private NOTFilter<Number> notFilter;


	public NOTFilterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.notFilter = new NOTFilter<Number>(this.buildPositiveFilter());
	}

	private Predicate<Number> buildPositiveFilter() {
		return new PositiveFilter();
	}

	static class PositiveFilter
		extends FilterAdapter<Number>
		implements Serializable
	{
		private static final long serialVersionUID = 1L;
		@Override
		public boolean evaluate(Number number) {
			return number.doubleValue() > 0;
		}
		@Override
		public boolean equals(Object obj) {
			return this.getClass() == obj.getClass();
		}
		@Override
		public int hashCode() {
			return 789;
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testFiltering() {
		assertTrue(this.notFilter.evaluate(new Integer(0)));
		assertTrue(this.notFilter.evaluate(new Integer(-1)));
		assertTrue(this.notFilter.evaluate(new Double(-0.001)));
		assertFalse(this.notFilter.evaluate(new Double(1)));
		assertFalse(this.notFilter.evaluate(new Double(11)));
		assertFalse(this.notFilter.evaluate(new Double(111)));
	}

	public void testClone() {
		@SuppressWarnings("unchecked")
		NOTFilter<Number> notFilter2 = (NOTFilter<Number>) this.notFilter.clone();
		assertEquals(this.notFilter.getFilter(), notFilter2.getFilter());
		assertNotSame(this.notFilter, notFilter2);
	}

	public void testEquals() {
		NOTFilter<Number> notFilter2 = new NOTFilter<Number>(this.buildPositiveFilter());
		assertEquals(this.notFilter, notFilter2);
		assertEquals(this.notFilter.hashCode(), notFilter2.hashCode());
	}

	public void testSerialization() throws Exception {
		NOTFilter<Number> notFilter2 = TestTools.serialize(this.notFilter);
		assertEquals(this.notFilter.getFilter(), notFilter2.getFilter());
		assertNotSame(this.notFilter, notFilter2);
	}
}
