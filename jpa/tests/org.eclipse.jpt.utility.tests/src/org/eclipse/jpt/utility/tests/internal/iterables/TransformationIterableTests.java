/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

@SuppressWarnings("nls")
public class TransformationIterableTests extends TestCase {

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
		return new TransformationIterable<String, Integer>(nestedIterable) {
			@Override
			protected Integer transform(String next) {
				return new Integer(next.length());
			}
		};
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

}
