/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

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
