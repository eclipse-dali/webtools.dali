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

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.GenericIterableWrapper;

@SuppressWarnings("nls")
public class GenericIterableWrapperTests extends TestCase {

	public GenericIterableWrapperTests(String name) {
		super(name);
	}

	public void testIterator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("bar");
		list.add("baz");
		String concat = "";
		for (String s : list) {
			concat += s;
		}
		assertEquals("foobarbaz", concat);

		Iterable<Object> iterable = new GenericIterableWrapper<Object>(list);
		concat = "";
		for (Object s : iterable) {
			concat += s;
		}
		assertEquals("foobarbaz", concat);
	}

}
