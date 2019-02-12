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

import org.eclipse.jpt.common.utility.internal.reference.AbstractModifiableBooleanReference;
import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;

public class AbstractModifiableBooleanReferenceTests
	extends ModifiableBooleanReferenceTests
{
	public AbstractModifiableBooleanReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableBooleanReference buildBooleanReference(boolean value) {
		return new LocalBooleanReference(value);
	}

	private class LocalBooleanReference
		extends AbstractModifiableBooleanReference
	{
		private boolean value;
		LocalBooleanReference(boolean value) {
			super();
			this.value = value;
		}
		public boolean getValue() {
			return this.value;
		}

		public boolean setValue(boolean value) {
			boolean old = this.value;
			this.value = value;
			return old;
		}
	}
}
