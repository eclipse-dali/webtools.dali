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

import org.eclipse.jpt.common.utility.internal.reference.AbstractObjectReference;
import org.eclipse.jpt.common.utility.reference.ObjectReference;

public class AbstractObjectReferenceTests
	extends ObjectReferenceTests
{
	public AbstractObjectReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ObjectReference<String> buildObjectReference(String value) {
		return new LocalObjectReference(value);
	}

	protected static class LocalObjectReference
		extends AbstractObjectReference<String>
	{
		protected String value;
		protected LocalObjectReference(String value) {
			super();
			this.value = value;
		}
		public String getValue() {
			return this.value;
		}
	}
}
