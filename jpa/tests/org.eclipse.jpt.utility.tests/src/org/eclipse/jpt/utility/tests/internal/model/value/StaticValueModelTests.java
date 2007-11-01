/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import org.eclipse.jpt.utility.internal.model.value.AbstractReadOnlyPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class StaticValueModelTests extends TestCase {
	private ValueModel objectHolder;

	
	public StaticValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = this.buildObjectHolder();
	}

	private ValueModel buildObjectHolder() {
		return new AbstractReadOnlyPropertyValueModel() {
			public Object getValue() {
				return "foo";
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue() {
		assertEquals("foo", this.objectHolder.getValue());
	}

	public void testToString() {
		assertTrue(this.objectHolder.toString().indexOf("foo") >= 0);
	}

}
