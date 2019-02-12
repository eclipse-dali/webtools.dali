/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.AbstractModifiableIntReference;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;

public class AbstractModifiableIntReferenceTests
	extends ModifiableIntReferenceTests
{
	public AbstractModifiableIntReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableIntReference buildIntReference(int value) {
		return new LocalIntReference(value);
	}

	private class LocalIntReference
		extends AbstractModifiableIntReference
	{
		private int value;
		LocalIntReference(int value) {
			super();
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
		public int setValue(int value) {
			int old = this.value;
			this.value = value;
			return old;
		}
	}
}
