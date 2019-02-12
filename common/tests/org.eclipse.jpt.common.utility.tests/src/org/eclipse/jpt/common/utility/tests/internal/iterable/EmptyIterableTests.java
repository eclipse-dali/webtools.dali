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

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class EmptyIterableTests extends TestCase {

	public EmptyIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		for (String s : EmptyIterable.<String>instance()) {
			fail("bogus element: " + s);
		}
	}

	public void testToString() {
		assertNotNull(EmptyIterable.instance().toString());
	}

	public void testSerialization() throws Exception {
		Iterable<String> iterable = EmptyIterable.instance();
		assertSame(iterable, TestTools.serialize(iterable));
	}

}
