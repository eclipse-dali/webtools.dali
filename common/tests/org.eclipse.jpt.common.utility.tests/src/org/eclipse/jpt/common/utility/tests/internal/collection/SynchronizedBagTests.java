/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.Collection;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;

@SuppressWarnings("nls")
public class SynchronizedBagTests
	extends BagTests
{
	public SynchronizedBagTests(String name) {
		super(name);
	}

	@Override
	protected Bag<String> buildBag_() {
		return CollectionTools.synchronizedBag();
	}

	@Override
	protected Bag<String> buildBag(Collection<String> c) {
		return CollectionTools.synchronizedBag(new HashBag<String>(c));
	}

	@Override
	protected Bag<String> buildBag(int initialCapacity, float loadFactor) {
		return CollectionTools.synchronizedBag(new HashBag<String>(initialCapacity, loadFactor));
	}

	@Override
	public void testClone() {
		// synchronized bag is not cloneable
	}

	public void testCtorBagObject_nullBag() {
		boolean exCaught = false;
		try {
			Bag<String> bag = CollectionTools.synchronizedBag(null, "foo");
			fail("bogus bag: " + bag);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtorBagObject_nullMutex() {
		Bag<String> wrapped = CollectionTools.hashBag();
		boolean exCaught = false;
		try {
			Bag<String> bag = CollectionTools.synchronizedBag(wrapped, null);
			fail("bogus bag: " + bag);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtorBag_nullBag() {
		boolean exCaught = false;
		try {
			Bag<String> bag = CollectionTools.synchronizedBag(null);
			fail("bogus bag: " + bag);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}
}
