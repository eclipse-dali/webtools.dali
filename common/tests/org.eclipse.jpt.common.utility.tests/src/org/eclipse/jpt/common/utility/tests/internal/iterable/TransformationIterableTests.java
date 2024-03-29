/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.DisabledTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TransformationIterableTests
	extends TestCase
{
	public TransformationIterableTests(String name) {
		super(name);
	}

	public void testTransform() {
		int i = 1;
		for (Integer integer : this.buildIterable()) {
			assertEquals(i++, integer.intValue());
		}
	}

	private Iterable<Integer> buildIterable() {
		return this.buildTransformationIterable(this.buildNestedIterable());
	}

	private Iterable<Integer> buildTransformationIterable(Iterable<String> nestedIterable) {
		// transform each string into an integer with a value of the string's length
		return new TransformationIterable<String, Integer>(nestedIterable, STRING_LENGTH_TRANSFORMER);
	}

	private Iterable<String> buildNestedIterable() {
		Collection<String> c = new ArrayList<String>();
		c.add("1");
		c.add("22");
		c.add("333");
		c.add("4444");
		c.add("55555");
		c.add("666666");
		c.add("7777777");
		c.add("88888888");
		return c;
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	public void testMissingTransformer() {
		Iterable<Integer> iterable = new TransformationIterable<String, Integer>(this.buildNestedIterable(), DisabledTransformer.<String, Integer>instance());
		boolean exCaught = false;
		try {
			int i = 1;
			for (Integer integer : iterable) {
				assertEquals(i++, integer.intValue());
			}
		} catch (RuntimeException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	static final Transformer<String, Integer> STRING_LENGTH_TRANSFORMER = new StringLengthTransformer();
	/**
	 * transform each string into an integer with a value of the string's length
	 */
	static class StringLengthTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String s) {
			return new Integer(s.length());
		}
	}
}
