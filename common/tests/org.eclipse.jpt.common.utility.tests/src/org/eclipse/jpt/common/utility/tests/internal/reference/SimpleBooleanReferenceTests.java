/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.reference.SimpleBooleanReference;
import org.eclipse.jpt.common.utility.reference.ModifiableBooleanReference;

public class SimpleBooleanReferenceTests
	extends ModifiableBooleanReferenceTests
{
	public SimpleBooleanReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableBooleanReference buildBooleanReference(boolean value) {
		return new SimpleBooleanReference(value);
	}

	public void testGetValueDefault() {
		SimpleBooleanReference br = new SimpleBooleanReference();
		assertFalse(br.getValue());
	}

	public void testClone() {
		SimpleBooleanReference br = new SimpleBooleanReference(true);
		SimpleBooleanReference clone = br.clone();
		assertTrue(clone.getValue());
	}
}
