/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.tests.internal;

import java.util.HashSet;
import org.eclipse.jpt.gen.internal.util.EntityGenTools;

import junit.framework.TestCase;

/**
 * 
 */
@SuppressWarnings("nls")
public class EntityGenToolsTests extends TestCase {

	public EntityGenToolsTests(String name) {
		super(name);
	}

	public void testConvertToUniqueJavaStyleClassName1() {
		HashSet<String> names = new HashSet<String>();
		assertEquals("Foo", EntityGenTools.convertToUniqueJavaStyleClassName("Foo", names));
		assertEquals("Foo", EntityGenTools.convertToUniqueJavaStyleClassName("foo", names));
		assertEquals("Foo", EntityGenTools.convertToUniqueJavaStyleClassName("FOO", names));
		assertEquals("Foo", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_", names));
		assertEquals("Foo", EntityGenTools.convertToUniqueJavaStyleClassName("_FOO", names));
		assertEquals("Foo_", EntityGenTools.convertToUniqueJavaStyleClassName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleClassName("5oo", names));
	}

	public void testConvertToUniqueJavaStyleClassName2() {
		HashSet<String> names = new HashSet<String>();
		names.add("Foo");
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("Foo", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("foo", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("_FOO", names));
		assertEquals("Foo_", EntityGenTools.convertToUniqueJavaStyleClassName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleClassName("5OO", names));
	}

	public void testConvertToUniqueJavaStyleClassName3() {
		HashSet<String> names = new HashSet<String>();
		names.add("foo");
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("Foo", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("foo", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_", names));
		assertEquals("Foo2", EntityGenTools.convertToUniqueJavaStyleClassName("_FOO", names));
		assertEquals("Foo_", EntityGenTools.convertToUniqueJavaStyleClassName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleClassName("5OO", names));
	}

	public void testConvertToUniqueJavaStyleClassName4() {
		HashSet<String> names = new HashSet<String>();
		assertEquals("FooBar", EntityGenTools.convertToUniqueJavaStyleClassName("FooBar", names));
		assertEquals("Foo_bar", EntityGenTools.convertToUniqueJavaStyleClassName("foo_bar", names));
		assertEquals("FooBar", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR", names));
		assertEquals("FooBar", EntityGenTools.convertToUniqueJavaStyleClassName("_FOO_BAR", names));
		assertEquals("FooBar", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR_", names));
		assertEquals("FooBar_", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR_*", names));
		assertEquals("_fooBar", EntityGenTools.convertToUniqueJavaStyleClassName("4FOO_BAR", names));
	}

	public void testConvertToUniqueJavaStyleClassName5() {
		HashSet<String> names = new HashSet<String>();
		names.add("FooBar");
		assertEquals("FooBar2", EntityGenTools.convertToUniqueJavaStyleClassName("FooBar", names));
		assertEquals("Foo_bar", EntityGenTools.convertToUniqueJavaStyleClassName("foo_bar", names));
		assertEquals("FooBar2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR", names));
		assertEquals("FooBar2", EntityGenTools.convertToUniqueJavaStyleClassName("_FOO_BAR", names));
		assertEquals("FooBar2", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR_", names));
		assertEquals("FooBar_", EntityGenTools.convertToUniqueJavaStyleClassName("FOO_BAR_*", names));
		assertEquals("_fooBar", EntityGenTools.convertToUniqueJavaStyleClassName("4FOO_BAR", names));
	}

	public void testConvertToUniqueJavaStyleAttributeName1() {
		HashSet<String> names = new HashSet<String>();
		assertEquals("foo", EntityGenTools.convertToUniqueJavaStyleAttributeName("Foo", names));
		assertEquals("foo", EntityGenTools.convertToUniqueJavaStyleAttributeName("foo", names));
		assertEquals("foo", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO", names));
		assertEquals("foo", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_", names));
		assertEquals("foo", EntityGenTools.convertToUniqueJavaStyleAttributeName("_FOO", names));
		assertEquals("foo_", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleAttributeName("5oo", names));
	}

	public void testConvertToUniqueJavaStyleAttributeName2() {
		HashSet<String> names = new HashSet<String>();
		names.add("Foo");
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("Foo", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("foo", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("_FOO", names));
		assertEquals("foo_", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleAttributeName("5OO", names));
	}

	public void testConvertToUniqueJavaStyleAttributeName3() {
		HashSet<String> names = new HashSet<String>();
		names.add("foo");
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("Foo", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("foo", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_", names));
		assertEquals("foo2", EntityGenTools.convertToUniqueJavaStyleAttributeName("_FOO", names));
		assertEquals("foo_", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO*", names));
		assertEquals("_oo", EntityGenTools.convertToUniqueJavaStyleAttributeName("5OO", names));
	}

	public void testConvertToUniqueJavaStyleAttributeName4() {
		HashSet<String> names = new HashSet<String>();
		assertEquals("fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("FooBar", names));
		assertEquals("foo_bar", EntityGenTools.convertToUniqueJavaStyleAttributeName("foo_bar", names));
		assertEquals("fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR", names));
		assertEquals("fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("_FOO_BAR", names));
		assertEquals("fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR_", names));
		assertEquals("fooBar_", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR_*", names));
		assertEquals("_fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("4FOO_BAR", names));
	}

	public void testConvertToUniqueJavaStyleAttributeName5() {
		HashSet<String> names = new HashSet<String>();
		names.add("FooBar");
		assertEquals("fooBar2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FooBar", names));
		assertEquals("foo_bar", EntityGenTools.convertToUniqueJavaStyleAttributeName("foo_bar", names));
		assertEquals("fooBar2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR", names));
		assertEquals("fooBar2", EntityGenTools.convertToUniqueJavaStyleAttributeName("_FOO_BAR", names));
		assertEquals("fooBar2", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR_", names));
		assertEquals("fooBar_", EntityGenTools.convertToUniqueJavaStyleAttributeName("FOO_BAR_*", names));
		assertEquals("_fooBar", EntityGenTools.convertToUniqueJavaStyleAttributeName("4FOO_BAR", names));
	}

}
