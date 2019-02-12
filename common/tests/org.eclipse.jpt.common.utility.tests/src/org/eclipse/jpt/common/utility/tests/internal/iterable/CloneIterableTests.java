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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.CloneListIterator;

@SuppressWarnings("nls")
public abstract class CloneIterableTests
	extends TestCase
{
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
		this.iterable = this.buildIterableWithRemover(collection);

		Object removed = "three";
		assertTrue(IterableTools.contains(this.iterable, removed));
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
		assertTrue(IterableTools.contains(this.iterable, removed));
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
		assertNotNull(this.iterable.toString());
	}

	abstract Iterable<String> buildIterable(List<String> c);

	abstract Iterable<String> buildIterableWithRemover(List<String> c);

	Closure<String> buildRemoveCommand(final Collection<String> c) {
		return new Closure<String>() {
			public void execute(String current) {
				c.remove(current);
			}
		};
	}

	CloneListIterator.Adapter<String> buildMutator(final List<String> list) {
		return new CloneListIterator.Adapter<String>() {
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
