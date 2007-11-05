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
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.NullListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class NullListValueModelTests extends TestCase {
	private ListValueModel listHolder;

	public NullListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = NullListValueModel.instance();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testAddItem() {
		boolean exCaught = false;
		try {
			this.listHolder.addItem(0, "foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddItems() {
		boolean exCaught = false;
		List<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.listHolder.addItems(0, items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveItem() {
		boolean exCaught = false;
		try {
			this.listHolder.removeItem(0);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveItems() {
		boolean exCaught = false;
		try {
			this.listHolder.removeItems(0, 3);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReplaceItem() {
		boolean exCaught = false;
		try {
			this.listHolder.replaceItem(0, "foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReplaceItems() {
		boolean exCaught = false;
		List<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.listHolder.replaceItems(0, items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testGetItem() {
		boolean exCaught = false;
		try {
			this.listHolder.getItem(0);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		assertEquals(0, this.listHolder.size());
	}

	public void testValue() {
		assertFalse(((ListIterator) this.listHolder.value()).hasNext());
	}

}
