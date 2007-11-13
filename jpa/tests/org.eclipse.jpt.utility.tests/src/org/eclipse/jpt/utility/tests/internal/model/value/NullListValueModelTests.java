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

	public void testAdd() {
		boolean exCaught = false;
		try {
			this.listHolder.add(0, "foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddAll() {
		boolean exCaught = false;
		List<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.listHolder.addAll(0, items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemove() {
		boolean exCaught = false;
		try {
			this.listHolder.remove(0);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveLength() {
		boolean exCaught = false;
		try {
			this.listHolder.remove(0, 3);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReplace() {
		boolean exCaught = false;
		try {
			this.listHolder.replace(0, "foo");
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testReplaceAll() {
		boolean exCaught = false;
		List<String> items = new ArrayList<String>();
		items.add("foo");
		items.add("bar");
		items.add("baz");
		try {
			this.listHolder.replaceAll(0, items);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testGet() {
		boolean exCaught = false;
		try {
			this.listHolder.get(0);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		assertEquals(0, this.listHolder.size());
	}

	public void testValues() {
		assertFalse(((ListIterator) this.listHolder.values()).hasNext());
	}

}
