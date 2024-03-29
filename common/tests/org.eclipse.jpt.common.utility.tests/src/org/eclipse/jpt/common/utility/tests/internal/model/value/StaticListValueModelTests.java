/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class StaticListValueModelTests extends TestCase {

	private static final List<String> LIST = buildList();
	private static List<String> buildList() {
		List<String> result = new ArrayList<String>();
		result.add("foo");
		result.add("bar");
		return result;
	}

	private ListValueModel<String> listHolder;


	public StaticListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new StaticListValueModel<String>(LIST);
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
		assertEquals(buildList(), ListTools.arrayList(this.listHolder.listIterator()));
	}

	public void testSize() {
		assertEquals(buildList().size(), this.listHolder.size());
	}

}
