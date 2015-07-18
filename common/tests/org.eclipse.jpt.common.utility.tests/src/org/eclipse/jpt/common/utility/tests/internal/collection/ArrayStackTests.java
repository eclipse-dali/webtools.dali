/*******************************************************************************
 * Copyright (c) 2012, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.internal.collection.ArrayStack;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ArrayStackTests
	extends StackTests
{
	public ArrayStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new ArrayStack<String>();
	}

	public void testSerialization_fullArray() throws Exception {
		Stack<String> stack = new ArrayStack<String>(3);
		stack.push("first");
		stack.push("second");
		stack.push("third");

		this.verifyClone(stack, TestTools.serialize(stack));
	}
}
