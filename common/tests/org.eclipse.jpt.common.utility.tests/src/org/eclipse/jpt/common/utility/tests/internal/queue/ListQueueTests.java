/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.queue;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.queue.ListQueue;
import org.eclipse.jpt.common.utility.queue.Queue;

public class ListQueueTests
	extends QueueTests
{
	public ListQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return new ListQueue<String>(new ArrayList<String>());
	}

	@Override
	public void testClone() {
		// unsupported
	}
}
