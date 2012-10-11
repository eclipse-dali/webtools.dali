/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.iterator.SimultaneousListIterator;

public class SimultaneousListIteratorTests
	extends SimultaneousIteratorTests
{
	public SimultaneousListIteratorTests(String name) {
		super(name);
	}

	@Override
	protected Iterator<List<String>> buildIterator(ListIterator<String>... iterators) {
		return new SimultaneousListIterator<String>(iterators);
	}

	@Override
	protected Iterator<List<String>> buildIterator(Iterable<ListIterator<String>> iterators) {
		return new SimultaneousListIterator<String>(iterators);
	}
}
