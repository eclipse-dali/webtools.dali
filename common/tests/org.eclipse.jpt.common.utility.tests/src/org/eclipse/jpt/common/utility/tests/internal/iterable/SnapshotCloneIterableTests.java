/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterable;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public class SnapshotCloneIterableTests
	extends CloneIterableTests
{
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
		assertTrue(IterableTools.contains(this.iterable, "three"));
	}

	@Override
	Iterable<String> buildIterable(List<String> c) {
		return IterableTools.cloneSnapshot(c);
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return IterableTools.cloneSnapshot(c, this.buildRemoveCommand(c));
	}
}
