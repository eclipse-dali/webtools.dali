/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.collection;

import java.util.List;

import org.eclipse.jpt.common.utility.internal.collection.RepeatingElementList;

@SuppressWarnings("nls")
public class RepeatingElementListTests
	extends AbstractRepeatingElementListTests
{
	public RepeatingElementListTests(String name) {
		super(name);
	}

	@Override
	public List<String> buildList(int size) {
		return new RepeatingElementList<String>(this.getElement(), size);
	}

	@Override
	public String getElement() {
		return "repeating element";
	}
}
