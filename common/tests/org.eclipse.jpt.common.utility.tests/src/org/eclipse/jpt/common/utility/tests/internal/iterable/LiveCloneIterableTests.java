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
public class LiveCloneIterableTests
	extends CloneIterableTests
{
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
		assertFalse(IterableTools.contains(this.iterable, "three"));
	}

	@Override
	Iterable<String> buildIterable(List<String> c) {
		return IterableTools.cloneLive(c);
	}

	@Override
	Iterable<String> buildIterableWithRemover(List<String> c) {
		return IterableTools.cloneLive(c, this.buildRemoveCommand(c));
	}
}
