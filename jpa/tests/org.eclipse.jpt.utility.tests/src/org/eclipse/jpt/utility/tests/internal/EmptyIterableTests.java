/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import org.eclipse.jpt.utility.internal.EmptyIterable;

import junit.framework.TestCase;

public class EmptyIterableTests extends TestCase {

	public EmptyIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		int i = 0;
		for (String string : EmptyIterable.<String>instance()) {
			fail("bogus string: " + string);
			i++;
		}
		assertEquals(0, i);
	}

}
