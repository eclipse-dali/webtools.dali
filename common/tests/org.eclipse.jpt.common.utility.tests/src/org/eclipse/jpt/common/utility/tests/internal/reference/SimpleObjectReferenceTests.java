/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.reference;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.reference.SimpleObjectReference;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;
import org.eclipse.jpt.common.utility.reference.ObjectReference;

@SuppressWarnings("nls")
public class SimpleObjectReferenceTests
	extends ModifiableObjectReferenceTests
{
	public SimpleObjectReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableObjectReference<String> buildObjectReference() {
		return new SimpleObjectReference<>();
	}

	@Override
	protected ModifiableObjectReference<String> buildObjectReference(String value) {
		return new SimpleObjectReference<>(value);
	}

	public void testClone() {
		ObjectReference<String> or = this.buildObjectReference("foo");
		@SuppressWarnings("unchecked")
		ObjectReference<String> clone = (ObjectReference<String>) ObjectTools.execute(or, "clone");
		assertEquals("foo", clone.getValue());
		assertNotSame(or, clone);
	}
}
