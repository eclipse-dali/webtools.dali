/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.NullCollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class NullCollectionValueModelTests extends TestCase {
	private CollectionValueModel collectionHolder;

	public NullCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = new NullCollectionValueModel();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSize() {
		assertEquals(0, this.collectionHolder.size());
	}

	public void testIterator() {
		assertFalse(this.collectionHolder.iterator().hasNext());
	}

}
