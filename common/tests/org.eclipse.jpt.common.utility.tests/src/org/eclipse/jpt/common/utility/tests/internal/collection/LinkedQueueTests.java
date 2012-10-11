/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import org.eclipse.jpt.common.utility.collection.Queue;
import org.eclipse.jpt.common.utility.internal.collection.LinkedQueue;

public class LinkedQueueTests
	extends QueueTests
{
	public LinkedQueueTests(String name) {
		super(name);
	}

	@Override
	Queue<String> buildQueue() {
		return new LinkedQueue<String>();
	}
}
