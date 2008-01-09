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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class StaticListValueModelTests extends TestCase {

	private static final List<String> LIST = buildList();
	private static List<String> buildList() {
		List<String> result = new ArrayList<String>();
		result.add("foo");
		result.add("bar");
		return result;
	}

	private ListValueModel listHolder;


	public StaticListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new StaticListValueModel(LIST);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGet() {
		List<String> expected = buildList();
		for (int i = 0; i < this.listHolder.size(); i++) {
			assertEquals(expected.get(i), this.listHolder.get(i));
		}
	}

	public void testIterator() {
		assertEquals(buildList(), CollectionTools.list(this.listHolder.listIterator()));
	}

	public void testSize() {
		assertEquals(buildList().size(), this.listHolder.size());
	}

}
