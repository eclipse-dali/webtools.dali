/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.List;

import org.eclipse.jpt.common.utility.internal.collection.NullElementList;

public class NullElementListTests
	extends AbstractRepeatingElementListTests
{
	public NullElementListTests(String name) {
		super(name);
	}

	@Override
	public List<String> buildList(int size) {
		return new NullElementList<String>(size);
	}

	@Override
	public String getElement() {
		return null;
	}
}
