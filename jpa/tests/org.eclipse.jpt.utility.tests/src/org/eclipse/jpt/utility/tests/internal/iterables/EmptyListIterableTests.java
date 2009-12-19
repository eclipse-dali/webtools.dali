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

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.iterables.EmptyListIterable;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class EmptyListIterableTests extends TestCase {

	public EmptyListIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		for (String s : EmptyListIterable.<String>instance()) {
			fail("bogus element: " + s);
		}
	}

	public void testToString() {
		assertNotNull(EmptyListIterable.instance().toString());
	}

	public void testSerialization() throws Exception {
		Iterable<String> iterable = EmptyListIterable.instance();
		assertSame(iterable, TestTools.serialize(iterable));
	}

}
