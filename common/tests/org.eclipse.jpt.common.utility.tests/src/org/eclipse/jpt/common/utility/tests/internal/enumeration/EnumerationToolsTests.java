/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.enumeration;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.enumeration.EnumerationTools;

import junit.framework.TestCase;

@SuppressWarnings("nls")
public class EnumerationToolsTests
	extends TestCase
{
	public EnumerationToolsTests(String name) {
		super(name);
	}

	public void testContainsEnumerationObject_String() {
		Vector<String> v = this.buildStringVector1();
		assertTrue(EnumerationTools.contains(v.elements(), "one"));
		assertFalse(EnumerationTools.contains(v.elements(), null));
		v.add(null);
		assertTrue(EnumerationTools.contains(v.elements(), null));
	}

	public void testContainsEnumerationObject_Object() {
		Vector<Object> c = new Vector<Object>();
		c.add("zero");
		c.add("one");
		c.add("two");
		c.add("three");
		String one = "one";
		assertTrue(EnumerationTools.contains(c.elements(), one));
		assertFalse(EnumerationTools.contains(c.elements(), null));
		c.add(null);
		assertTrue(EnumerationTools.contains(c.elements(), null));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(EnumerationTools.class);
			fail("bogus: " + at); //$NON-NLS-1$
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}

	private Vector<String> buildStringVector1() {
		Vector<String> v = new Vector<String>();
		this.addToCollection1(v);
		return v;
	}

	private void addToCollection1(Collection<? super String> c) {
		c.add("zero");
		c.add("one");
		c.add("two");
	}
}
