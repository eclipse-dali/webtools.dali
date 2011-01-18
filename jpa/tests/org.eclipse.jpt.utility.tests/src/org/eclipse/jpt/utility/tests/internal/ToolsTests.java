/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import org.eclipse.jpt.utility.internal.Tools;

import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ToolsTests extends TestCase {

	public ToolsTests(String name) {
		super(name);
	}

	public void testValuesAreEqual1() {
		assertTrue(Tools.valuesAreEqual(null, null));
	}

	public void testValuesAreEqual2() {
		assertFalse(Tools.valuesAreEqual(null, "foo"));
	}

	public void testValuesAreEqual3() {
		assertFalse(Tools.valuesAreEqual("foo", null));
	}

	public void testValuesAreEqual4() {
		assertTrue(Tools.valuesAreEqual("foo", "foo"));
	}

	public void testValuesAreEqual5() {
		assertFalse(Tools.valuesAreEqual("foo", "bar"));
	}

	public void testValuesAreDifferent1() {
		assertFalse(Tools.valuesAreDifferent(null, null));
	}

	public void testValuesAreDifferent2() {
		assertTrue(Tools.valuesAreDifferent(null, "foo"));
	}

	public void testValuesAreDifferent3() {
		assertTrue(Tools.valuesAreDifferent("foo", null));
	}

	public void testValuesAreDifferent4() {
		assertFalse(Tools.valuesAreDifferent("foo", "foo"));
	}

	public void testValuesAreDifferent5() {
		assertTrue(Tools.valuesAreDifferent("foo", "bar"));
	}

}
