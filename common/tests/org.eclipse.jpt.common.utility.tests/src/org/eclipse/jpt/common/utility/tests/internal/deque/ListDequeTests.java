/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.deque;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.deque.Deque;
import org.eclipse.jpt.common.utility.internal.deque.DequeTools;

public class ListDequeTests
	extends DequeTests
{
	public ListDequeTests(String name) {
		super(name);
	}

	@Override
	Deque<String> buildDeque() {
		return DequeTools.adapt(new ArrayList<String>());
	}

	@Override
	public void testClone() {
		// unsupported
	}
}
