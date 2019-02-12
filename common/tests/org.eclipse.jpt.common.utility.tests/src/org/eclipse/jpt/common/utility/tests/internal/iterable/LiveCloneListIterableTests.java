/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public class LiveCloneListIterableTests
	extends LiveCloneIterableTests
{
	public LiveCloneListIterableTests(String name) {
		super(name);
	}

	public void testAdd() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterableWithRemover(collection);

		String added = "xxxx";
		assertFalse(IterableTools.contains(this.iterable, added));
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("two")) {
				iterator.add(added);
			}
		}
		assertTrue(collection.contains(added));
		// "live" clone iterable will contain the element added to the
		// original collection
		assertTrue(IterableTools.contains(this.iterable, added));
	}

	public void testMissingMutatorAdd() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(this.iterable.toString());

		String added = "xxxx";
		assertFalse(IterableTools.contains(this.iterable, added));
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
		this.iterable = this.buildIterableWithRemover(collection);

		String added = "xxxx";
		assertFalse(IterableTools.contains(this.iterable, added));
		for (ListIterator<String> iterator = (ListIterator<String>) this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals("two")) {
				iterator.set(added);
			}
		}
		assertTrue(collection.contains(added));
		assertFalse(collection.contains("two"));
		// "live" clone iterable will contain the element added to the
		// original collection
		assertTrue(IterableTools.contains(this.iterable, added));
		assertFalse(IterableTools.contains(this.iterable, "two"));
	}

	public void testMissingMutatorSet() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(this.iterable.toString());

		String added = "xxxx";
		assertFalse(IterableTools.contains(this.iterable, added));
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
		return IterableTools.cloneLive(c);
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return IterableTools.cloneLive(c, this.buildMutator(c));
	}
}
