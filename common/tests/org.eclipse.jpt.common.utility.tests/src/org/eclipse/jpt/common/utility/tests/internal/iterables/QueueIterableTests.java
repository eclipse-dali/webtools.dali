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

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.Queue;
import org.eclipse.jpt.common.utility.internal.SimpleQueue;
import org.eclipse.jpt.common.utility.internal.iterables.QueueIterable;

@SuppressWarnings("nls")
public class QueueIterableTests extends TestCase {

	public QueueIterableTests(String name) {
		super(name);
	}

	public void testIterator() {
		Iterable<String> iterable = this.buildIterable();
		for (String s : iterable) {
			assertNotNull(s);
		}
	}

	public void testToString() {
		assertNotNull(this.buildIterable().toString());
	}

	private Iterable<String> buildIterable() {
		return new QueueIterable<String>(this.buildQueue());
	}

	private Queue<String> buildQueue() {
		Queue<String> q = new SimpleQueue<String>();
		q.enqueue("foo");
		q.enqueue("bar");
		q.enqueue("baz");
		return q;
	}

}
