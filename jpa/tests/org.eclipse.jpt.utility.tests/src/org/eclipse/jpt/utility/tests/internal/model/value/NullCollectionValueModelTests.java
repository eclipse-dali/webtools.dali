/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.NullCollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class NullCollectionValueModelTests extends TestCase {
	private CollectionValueModel collectionHolder;

	public NullCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = NullCollectionValueModel.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAdd() {
		boolean exCaught = false;
		try {
			this.collectionHolder.add("foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddAll() {
		boolean exCaught = false;
		Collection<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.collectionHolder.addAll(items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove() {
		boolean exCaught = false;
		try {
			this.collectionHolder.remove("foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveAll() {
		boolean exCaught = false;
		Collection<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.collectionHolder.removeAll(items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		assertEquals(0, this.collectionHolder.size());
	}

	public void testValues() {
		assertFalse(((Iterator) this.collectionHolder.values()).hasNext());
	}

}
