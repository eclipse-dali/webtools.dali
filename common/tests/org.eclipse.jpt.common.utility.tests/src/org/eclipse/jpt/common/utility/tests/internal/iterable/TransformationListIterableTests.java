/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.DisabledTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TransformationListIterableTests
	extends TestCase
{
	public TransformationListIterableTests(String name) {
		super(name);
	}

	public void testTransform() {
		int i = 1;
		for (Integer integer : this.buildIterable()) {
			assertEquals(i++, integer.intValue());
		}
	}

	private Iterable<Integer> buildIterable() {
		return this.buildTransformationListIterable(this.buildNestedList());
	}

	private Iterable<Integer> buildTransformationListIterable(List<String> nestedList) {
		// transform each string into an integer with a value of the string's length
		return IterableTools.transform(nestedList, this.buildTransformer());
	}

	private Transformer<String, Integer> buildTransformer() {
		// transform each string into an integer with a value of the string's length
		return new Transformer<String, Integer>() {
			public Integer transform(String next) {
				return new Integer(next.length());
			}
		};
	}

	private List<String> buildNestedList() {
		List<String> c = new ArrayList<String>();
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
		Iterable<Integer> iterable = IterableTools.transform(this.buildNestedList(), DisabledTransformer.<String, Integer>instance());
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

}
