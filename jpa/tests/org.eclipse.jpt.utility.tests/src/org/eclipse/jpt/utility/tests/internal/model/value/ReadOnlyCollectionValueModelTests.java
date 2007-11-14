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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.value.AbstractReadOnlyCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ReadOnlyCollectionValueModelTests extends TestCase {
	private CollectionValueModel collectionHolder;
	private static Collection<String> collection;


	public ReadOnlyCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = this.buildCollectionHolder();
	}

	private CollectionValueModel buildCollectionHolder() {
		return new AbstractReadOnlyCollectionValueModel() {
			public Iterator iterator() {
				return ReadOnlyCollectionValueModelTests.collection();
			}
		};
	}

	static Iterator<String> collection() {
		return getCollection().iterator();
	}

	private static Collection<String> getCollection() {
		if (collection == null) {
			collection = buildCollection();
		}
		return collection;
	}

	private static Collection<String> buildCollection() {
		Collection<String> result = new HashBag<String>();
		result.add("foo");
		result.add("bar");
		return result;
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
