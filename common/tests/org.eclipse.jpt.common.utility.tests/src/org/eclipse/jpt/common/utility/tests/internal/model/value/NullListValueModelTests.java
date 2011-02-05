/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.model.value.NullListValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

public class NullListValueModelTests extends TestCase {
	private ListValueModel<Object> listHolder;

	public NullListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new NullListValueModel<Object>();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGet() {
		boolean exCaught = false;
		try {
			this.listHolder.get(0);
		} catch (IndexOutOfBoundsException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testSize() {
		assertEquals(0, this.listHolder.size());
	}

	public void testIterator() {
		assertFalse(this.listHolder.iterator().hasNext());
	}

}
