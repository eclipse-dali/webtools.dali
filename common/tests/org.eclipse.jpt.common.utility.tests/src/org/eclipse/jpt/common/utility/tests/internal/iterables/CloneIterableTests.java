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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;

@SuppressWarnings("nls")
public abstract class CloneIterableTests extends TestCase {
	Iterable<String> iterable;

	public CloneIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		List<String> c = new ArrayList<String>();
		c.add("0");
		c.add("1");
		c.add("2");
		c.add("3");
		assertEquals(4, c.size());
		this.iterable = this.buildIterable(c);
		int i = 0;
		for (String s : this.iterable) {
			assertEquals(String.valueOf(i++), s);
			c.remove("3");
		}
		assertEquals(4, i);
		assertEquals(3, c.size());
	}

	public void testRemove() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildRemovingIterable(collection);

		Object removed = "three";
		assertTrue(CollectionTools.contains(this.iterable, removed));
		for (Iterator<String> iterator = this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals(removed)) {
				iterator.remove();
			}
		}
		assertFalse(collection.contains(removed));
	}

	public void testRemover() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterableWithRemover(collection);

		Object removed = "three";
		assertTrue(CollectionTools.contains(this.iterable, removed));
		for (Iterator<String> iterator = this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals(removed)) {
				iterator.remove();
			}
		}
		assertFalse(collection.contains(removed));
	}

	public void testMissingRemover() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(this.iterable.toString());

		Object removed = "three";
		assertTrue(CollectionTools.contains(this.iterable, removed));
		boolean exCaught = false;
		for (Iterator<String> iterator = this.iterable.iterator(); iterator.hasNext(); ) {
			if (iterator.next().equals(removed)) {
				try {
					iterator.remove();
					fail();
				} catch (RuntimeException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	public void testToString() {
		final List<String> collection = this.buildCollection();
		this.iterable = this.buildIterable(collection);
		assertNotNull(iterable.toString());
	}

	abstract Iterable<String> buildIterable(List<String> c);

	abstract Iterable<String> buildRemovingIterable(List<String> c);

	abstract Iterable<String> buildIterableWithRemover(List<String> c);

	CloneIterator.Remover<String> buildRemover(final Collection<String> c) {
		return new CloneIterator.Remover<String>() {
			public void remove(String current) {
				c.remove(current);
			}
		};
	}

	CloneListIterator.Mutator<String> buildMutator(final List<String> list) {
		return new CloneListIterator.Mutator<String>() {
			public void add(int index, String string) {
				list.add(index, string);
			}
			public void set(int index, String string) {
				list.set(index, string);
			}
			public void remove(int index) {
				list.remove(index);
			}
		};
	}

	List<String> buildCollection() {
		List<String> c = new ArrayList<String>();
		c.add("one");
		c.add("two");
		c.add("three");
		c.add("four");
		c.add("five");
		c.add("six");
		c.add("seven");
		c.add("eight");
		return c;
	}

}
