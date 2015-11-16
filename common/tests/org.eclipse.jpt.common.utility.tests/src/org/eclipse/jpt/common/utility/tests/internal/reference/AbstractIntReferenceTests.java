/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.AbstractIntReference;
import org.eclipse.jpt.common.utility.reference.IntReference;

public class AbstractIntReferenceTests
	extends IntReferenceTests
{
	public AbstractIntReferenceTests(String name) {
		super(name);
	}

	@Override
	protected IntReference buildIntReference(int value) {
		return new LocalIntReference(value);
	}

	private class LocalIntReference
		extends AbstractIntReference
	{
		private final int value;
		LocalIntReference(int value) {
			super();
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
	}
}
