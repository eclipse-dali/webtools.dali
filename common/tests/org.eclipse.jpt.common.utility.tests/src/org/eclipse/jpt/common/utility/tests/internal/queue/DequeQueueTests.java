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

import org.eclipse.jpt.common.utility.internal.deque.DequeTools;
import org.eclipse.jpt.common.utility.internal.queue.QueueTools;
import org.eclipse.jpt.common.utility.queue.Queue;

public class DequeQueueTests
	extends QueueTests
{
	public DequeQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return QueueTools.adapt(DequeTools.<String>arrayDeque());
	}

	@Override
	public void testClone() {
		// unsupported
	}
}
