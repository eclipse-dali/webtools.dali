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

import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.factory.FactoryAdapter;
import org.eclipse.jpt.common.utility.internal.reference.LazyObjectReference;
import org.eclipse.jpt.common.utility.reference.ObjectReference;

@SuppressWarnings("nls")
public class LazyObjectReferenceTests
	extends ObjectReferenceTests
{
	public LazyObjectReferenceTests(String name) {
		super(name);
	}

	@Override
	protected ObjectReference<String> buildObjectReference(String value) {
		return new LazyObjectReference<>(this.buildFactory(value));
	}

	private Factory<String> buildFactory(String value) {
		return new LocalFactory(value);
	}

	@Override
	public void testGetValue() {
		super.testGetValue();
		ObjectReference<String> ref = this.buildObjectReference();
		assertNull(ref.getValue());
		ref = this.buildObjectReference("foo");
		assertEquals("foo", ref.getValue());
		assertEquals("foo", ref.getValue());
	}

	public void testClone() {
		ObjectReference<String> or = this.buildObjectReference("foo");
		@SuppressWarnings("unchecked")
		ObjectReference<String> clone = (ObjectReference<String>) ObjectTools.execute(or, "clone");
		assertEquals("foo", clone.getValue());
		assertNotSame(or, clone);
	}

	private static class LocalFactory
		extends FactoryAdapter<String>
	{
		protected String value;
		protected LocalFactory(String value) {
			super();
			this.value = value;
		}
		@Override
		public String create() {
			return this.value;
		}
	}
}
