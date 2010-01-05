/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.Collection;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class StaticCollectionValueModelTests extends TestCase {

	private static final Collection<String> COLLECTION = buildCollection();
	private static Collection<String> buildCollection() {
		Collection<String> result = new HashBag<String>();
		result.add("foo");
		result.add("bar");
		return result;
	}

	private CollectionValueModel<String> collectionHolder;


	public StaticCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = this.buildCollectionHolder();
	}

	private CollectionValueModel<String> buildCollectionHolder() {
		return new StaticCollectionValueModel<String>(COLLECTION);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		assertEquals(buildCollection(), CollectionTools.bag(this.collectionHolder.iterator()));
	}

	public void testSize() {
		assertEquals(buildCollection().size(), this.collectionHolder.size());
	}

}
