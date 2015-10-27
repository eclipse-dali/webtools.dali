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

import org.eclipse.jpt.common.utility.internal.reference.AbstractModifiableObjectReference;
import org.eclipse.jpt.common.utility.reference.ModifiableObjectReference;

public class AbstractModifiableObjectReferenceTests
	extends ModifiableObjectReferenceTests
{
	public AbstractModifiableObjectReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ModifiableObjectReference<String> buildObjectReference(String value) {
		return new LocalObjectReference(value);
	}

	private static class LocalObjectReference
		extends AbstractModifiableObjectReference<String>
	{
		protected String value;
		protected LocalObjectReference(String value) {
			super();
			this.value = value;
		}
		public String getValue() {
			return this.value;
		}
		public String setValue(String value) {
			String old = this.value;
			this.value = value;
			return old;
		}
	}
}
