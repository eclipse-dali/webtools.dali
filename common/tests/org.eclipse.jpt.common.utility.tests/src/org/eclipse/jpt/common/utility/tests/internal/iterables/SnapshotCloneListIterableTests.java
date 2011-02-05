/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterables;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneListIterable;

@SuppressWarnings("nls")
public class SnapshotCloneListIterableTests extends SnapshotCloneIterableTests {

	public SnapshotCloneListIterableTests(String name) {
		super(name);
	}

	public void testAdd() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildRemovingIterable(collection);

		String added = "xxxx";
		assertFalse(CollectionTools.contains(this.iterable, added));
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("two")) {
				iterator.add(added);
			}
		}
		assertTrue(collection.contains(added));
		// "snapshot" clone iterable not will contain the element added to the
		// original collection
		assertFalse(CollectionTools.contains(this.iterable, added));
	}

	public void testMissingMutatorAdd() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(this.iterable.toString());

		String added = "xxxx";
		assertFalse(CollectionTools.contains(this.iterable, added));
		boolean exCaught = false;
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("three")) {
				try {
					iterator.add(added);
					fail();
				} catch (RuntimeException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testSet() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildRemovingIterable(collection);

		String added = "xxxx";
		assertFalse(CollectionTools.contains(this.iterable, added));
		assertTrue(CollectionTools.contains(this.iterable, "two"));
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("two")) {
				iterator.set(added);
			}
		}
		assertTrue(collection.contains(added));
		assertFalse(collection.contains("two"));
		// "snapshot" clone iterable will not be changed
		assertFalse(CollectionTools.contains(this.iterable, added));
		assertTrue(CollectionTools.contains(this.iterable, "two"));
	}

	public void testMissingMutatorSet() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(this.iterable.toString());

		String added = "xxxx";
		assertFalse(CollectionTools.contains(this.iterable, added));
		boolean exCaught = false;
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("three")) {
				try {
					iterator.set(added);
					fail();
				} catch (RuntimeException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	@Override
	Iterable<String> buildIterable(List<String> c) {
		return new SnapshotCloneListIterable<String>(c);
	}

	@Override
	Iterable<String> buildRemovingIterable(final List<String> c) {
		return new SnapshotCloneListIterable<String>(c) {
				@Override
				protected void add(int index, String element) {
					c.add(index, element);
				}
				@Override
				protected void remove(int index) {
					c.remove(index);
				}
				@Override
				protected void set(int index, String element) {
					c.set(index, element);
				}
			};
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return new SnapshotCloneListIterable<String>(c, this.buildMutator(c));
	}

}
