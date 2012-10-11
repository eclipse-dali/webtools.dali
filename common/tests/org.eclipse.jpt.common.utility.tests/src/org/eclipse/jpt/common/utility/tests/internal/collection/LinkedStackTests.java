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

import org.eclipse.jpt.common.utility.collection.Stack;
import org.eclipse.jpt.common.utility.internal.collection.LinkedStack;

public class LinkedStackTests
	extends StackTests
{
	public LinkedStackTests(String name) {
		super(name);
	}

	@Override
	Stack<String> buildStack() {
		return new LinkedStack<String>();
	}
}
