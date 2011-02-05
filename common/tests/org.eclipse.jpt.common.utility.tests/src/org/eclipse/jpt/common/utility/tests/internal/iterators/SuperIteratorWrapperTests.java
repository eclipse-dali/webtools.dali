/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterators.SuperIteratorWrapper;

@SuppressWarnings("nls")
public class SuperIteratorWrapperTests extends TestCase {

	public SuperIteratorWrapperTests(String name) {
		super(name);
	}

	public void testIterator() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("bar");
		list.add("baz");
		String concat = "";
		for (Iterator<String> stream = list.iterator(); stream.hasNext(); ) {
			concat += stream.next();
		}
		assertEquals("foobarbaz", concat);

		Iterator<Object> iterator = new SuperIteratorWrapper<Object>(list);
		concat = "";
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next.equals("bar")) {
				iterator.remove();
			} else {
				concat += next;
			}
		}
		assertEquals("foobaz", concat);
		assertEquals(2, list.size());
		assertFalse(list.contains("bar"));
	}

}
