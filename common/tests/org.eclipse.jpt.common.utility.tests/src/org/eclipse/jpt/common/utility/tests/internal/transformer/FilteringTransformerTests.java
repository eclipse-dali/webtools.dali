/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.transformer;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.FilteringTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class FilteringTransformerTests
	extends TestCase
{
	public FilteringTransformerTests(String name) {
		super(name);
	}

	public void testTransform() {
		Predicate<String> nonNullFilter = new Filter(3);
		Predicate<String> filter = PredicateTools.nullCheck(nonNullFilter, false);
		Transformer<String, String> transformer = TransformerTools.filteringTransformer(filter);

		assertEquals("foo", transformer.transform("foo"));
		assertEquals("bar", transformer.transform("bar"));
		assertNull(transformer.transform("barbar"));
		assertNull(transformer.transform("b"));
		assertNull(transformer.transform(""));
		assertNull(transformer.transform(null));
	}

	public void testToString() {
		Predicate<String> nonNullFilter = new Filter(3);
		Predicate<String> filter = PredicateTools.nullCheck(nonNullFilter, false);
		Transformer<String, String> transformer = TransformerTools.filteringTransformer(filter);
		assertTrue(transformer.toString().indexOf("Filtering") != -1);
	}

	public void testGetters() {
		Predicate<String> nonNullFilter = new Filter(3);
		Predicate<String> filter = PredicateTools.nullCheck(nonNullFilter, false);
		FilteringTransformer<String> transformer = (FilteringTransformer<String>) TransformerTools.filteringTransformer(filter);
		assertSame(filter, transformer.getFilter());
		assertNull(transformer.getDefaultOutput());
	}

	public void testCtor_NPE() {
		boolean exCaught = false;
		try {
			Transformer<String, String> transformer = TransformerTools.filteringTransformer(null);
			fail("bogus: " + transformer);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public static class Filter
		extends PredicateAdapter<String>
	{
		public final int length;
		public Filter(int length) {
			super();
			this.length =length;
		}
		@Override
		public boolean evaluate(String variable) {
			return variable.length() == this.length;
		}
	}
}
