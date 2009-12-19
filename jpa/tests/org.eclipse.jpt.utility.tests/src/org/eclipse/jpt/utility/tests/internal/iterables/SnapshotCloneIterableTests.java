/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterables;

import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.SnapshotCloneIterable;

@SuppressWarnings("nls")
public class SnapshotCloneIterableTests extends CloneIterableTests {

	public SnapshotCloneIterableTests(String name) {
		super(name);
	}

	@Override
	public void testIterator() {
		super.testIterator();
		// "snapshot" iterable should still return 4 strings (since the original collection was cloned)
		int i = 0;
		for (String s : this.iterable) {
			assertEquals(String.valueOf(i++), s);
		}
		assertEquals(4, i);
	}

	@Override
	public void testRemove() {
		super.testRemove();
		// "snapshot" clone iterable will still contain the element removed from the
		// original collection
		assertTrue(CollectionTools.contains(this.iterable, "three"));
	}

	@Override
	public void testRemover() {
		super.testRemover();
		// "snapshot" clone iterable will still contain the element removed from the
		// original collection
		assertTrue(CollectionTools.contains(this.iterable, "three"));
	}

	@Override
	Iterable<String> buildIterable(List<String> c) {
		return new SnapshotCloneIterable<String>(c);
	}

	@Override
	Iterable<String> buildRemovingIterable(final List<String> c) {
		return new SnapshotCloneIterable<String>(c) {
				@Override
				protected void remove(String current) {
					c.remove(current);
				}
			};
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return new SnapshotCloneIterable<String>(c, this.buildRemover(c));
	}

}
