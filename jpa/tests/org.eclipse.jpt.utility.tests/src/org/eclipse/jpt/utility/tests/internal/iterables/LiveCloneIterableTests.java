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
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;

@SuppressWarnings("nls")
public class LiveCloneIterableTests extends CloneIterableTests {

	public LiveCloneIterableTests(String name) {
		super(name);
	}

	@Override
	public void testIterator() {
		super.testIterator();
		// iterable should now return only 3 strings (since it's "live")
		int i = 0;
		for (String s : this.iterable) {
			assertEquals(String.valueOf(i++), s);
		}
		assertEquals(3, i);
	}

	@Override
	public void testRemove() {
		super.testRemove();
		// "live" clone iterable will no longer contain the element removed from the
		// original collection
		assertFalse(CollectionTools.contains(this.iterable, "three"));
	}

	@Override
	public void testRemover() {
		super.testRemover();
		// "live" clone iterable will no longer contain the element removed from the
		// original collection
		assertFalse(CollectionTools.contains(this.iterable, "three"));
	}

	@Override
	Iterable<String> buildIterable(List<String> c) {
		return new LiveCloneIterable<String>(c);
	}

	@Override
	Iterable<String> buildRemovingIterable(final List<String> c) {
		return new LiveCloneIterable<String>(c) {
				@Override
				protected void remove(String current) {
					c.remove(current);
				}
			};
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return new LiveCloneIterable<String>(c, this.buildRemover(c));
	}

}
