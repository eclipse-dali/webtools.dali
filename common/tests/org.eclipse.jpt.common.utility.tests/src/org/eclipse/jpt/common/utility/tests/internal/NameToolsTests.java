/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.NameTools;

@SuppressWarnings("nls")
public class NameToolsTests
	extends TestCase
{
	public NameToolsTests(String name) {
		super(name);
	}

	public void testStringAbsentIgnoreCase() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueNameIgnoreCase("Taupe", colorCollection);
		assertEquals("Taupe", returned);
	}

	public void testStringPresentCaseDiffers() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueName("green", colorCollection);
		assertEquals("green", returned);
	}

	public void testStringPresentIgnoreCase() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueNameIgnoreCase("green", colorCollection);
		assertEquals("green2", returned);
	}

	public void testStringPresentWithAppendices() {
		List<String> colorCollection = this.buildColorCollection();
		colorCollection.add("Red1");
		colorCollection.add("red2");
		String returned = NameTools.uniqueNameIgnoreCase("red", colorCollection);
		colorCollection.remove("Red1");
		colorCollection.remove("red2");
		assertEquals("red3", returned);
	}

	private List<String> buildColorCollection() {
		List<String> colorCollection = new ArrayList<String>();
		colorCollection.add("Red");
		colorCollection.add("Orange");
		colorCollection.add("Yellow");
		colorCollection.add("Green");
		colorCollection.add("Blue");
		colorCollection.add("Indigo");
		colorCollection.add("Violet");
		return colorCollection;
	}

	public void testUniqueNameForCollection1() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle Corporation");
		strings.add("Oracle2");
		strings.add("oracle1");
		strings.add("Oracl");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", strings));
		assertEquals("Test", NameTools.uniqueName("Test", strings));

		assertEquals("Oracle3", NameTools.uniqueNameIgnoreCase("Oracle", strings));
		assertEquals("oracle3", NameTools.uniqueNameIgnoreCase("oracle", strings));
		assertEquals("Test", NameTools.uniqueNameIgnoreCase("Test", strings));
	}

	public void testUniqueNameForCollection2() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", strings));
		assertEquals("Test", NameTools.uniqueName("Test", strings));

		strings.add("Oracle Corporation");
		assertEquals("Oracle3", NameTools.uniqueNameIgnoreCase("Oracle", strings));
		assertEquals("oracle3", NameTools.uniqueNameIgnoreCase("oracle", strings));
		assertEquals("Test", NameTools.uniqueNameIgnoreCase("Test", strings));
	}

	public void testUniqueNameForCollection3() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", strings));
	}

	public void testUniqueNameForIterable1() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle Corporation");
		strings.add("Oracle2");
		strings.add("oracle1");
		strings.add("Oracl");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", (Iterable<String>) strings));
		assertEquals("Test", NameTools.uniqueName("Test", (Iterable<String>) strings));

		assertEquals("Oracle3", NameTools.uniqueNameIgnoreCase("Oracle", (Iterable<String>) strings));
		assertEquals("oracle3", NameTools.uniqueNameIgnoreCase("oracle", (Iterable<String>) strings));
		assertEquals("Test", NameTools.uniqueNameIgnoreCase("Test", (Iterable<String>) strings));
	}

	public void testUniqueNameForIterable2() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", (Iterable<String>) strings));
		assertEquals("Test", NameTools.uniqueName("Test", (Iterable<String>) strings));

		strings.add("Oracle Corporation");
		assertEquals("Oracle3", NameTools.uniqueNameIgnoreCase("Oracle", (Iterable<String>) strings));
		assertEquals("oracle3", NameTools.uniqueNameIgnoreCase("oracle", (Iterable<String>) strings));
		assertEquals("Test", NameTools.uniqueNameIgnoreCase("Test", (Iterable<String>) strings));
	}

	public void testUniqueNameForIterable3() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueName("Oracle", (Iterable<String>) strings));
	}

	public void testBuildQualifiedDatabaseObjectName() {
		assertEquals("catalog.schema.name", NameTools.buildQualifiedName("catalog", "schema", "name"));
		assertEquals("catalog..name", NameTools.buildQualifiedName("catalog", null, "name"));
		assertEquals("schema.name", NameTools.buildQualifiedName(null, "schema", "name"));
		assertEquals("name", NameTools.buildQualifiedName(null, null, "name"));
	}

	public void testJavaReservedWords() {
		assertTrue(NameTools.JAVA_RESERVED_WORDS.contains("class"));
		assertFalse(NameTools.JAVA_RESERVED_WORDS.contains("Class"));
		assertTrue(NameTools.JAVA_RESERVED_WORDS.contains("private"));
	}

	public void testconvertToJavaIdentifierString() {
		assertEquals("foo", NameTools.convertToJavaIdentifier("foo"));
		assertEquals("foo1", NameTools.convertToJavaIdentifier("foo1"));
		assertEquals("private_", NameTools.convertToJavaIdentifier("private"));
		assertEquals("throw_", NameTools.convertToJavaIdentifier("throw"));
		assertEquals("_foo", NameTools.convertToJavaIdentifier("1foo"));
		assertEquals("foo_", NameTools.convertToJavaIdentifier("foo%"));
		assertEquals("foo__bar__", NameTools.convertToJavaIdentifier("foo  bar  "));
	}

	public void testconvertToJavaIdentifierStringChar() {
		assertEquals("foo", NameTools.convertToJavaIdentifier("foo", '$'));
		assertEquals("foo1", NameTools.convertToJavaIdentifier("foo1", '$'));
		assertEquals("private$", NameTools.convertToJavaIdentifier("private", '$'));
		assertEquals("throwss", NameTools.convertToJavaIdentifier("throw", 's'));
		assertEquals("$foo", NameTools.convertToJavaIdentifier("1foo", '$'));
		assertEquals("foo$", NameTools.convertToJavaIdentifier("foo%", '$'));
		assertEquals("foo$$bar$$", NameTools.convertToJavaIdentifier("foo  bar  ", '$'));

		boolean exCaught = false;
		try {
			String s = NameTools.convertToJavaIdentifier("1foo", '7');
			fail("invalid string: \"" + s + "\"");
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf('7') != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			String s = NameTools.convertToJavaIdentifier("foo%", '^');
			fail("invalid string: \"" + s + "\"");
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf('^') != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			String s = NameTools.convertToJavaIdentifier("private", '^');
			fail("invalid string: \"" + s + "\"");
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf('^') != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);

	}

	public void testStringIsLegalJavaIdentifier() {
		assertFalse(NameTools.isLegalJavaIdentifier("class"));
		assertTrue(NameTools.isLegalJavaIdentifier("clasS"));

		assertFalse(NameTools.isLegalJavaIdentifier("7foo"));
		assertFalse(NameTools.isLegalJavaIdentifier("foo@bar"));
		assertTrue(NameTools.isLegalJavaIdentifier("_foo"));
	}

	public void testConvertGetterOrSetterMethodNameToPropertyName() {
		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("getFoo"));
		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("setFoo"));
		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("isFoo"));

		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("getfoo"));
		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("setfoo"));
		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("isfoo"));

		assertEquals("foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("foo"));
		assertEquals("Foo", NameTools.convertGetterOrSetterMethodNameToPropertyName("Foo"));

		assertEquals("get", NameTools.convertGetterOrSetterMethodNameToPropertyName("get"));
		assertEquals("set", NameTools.convertGetterOrSetterMethodNameToPropertyName("set"));
		assertEquals("is", NameTools.convertGetterOrSetterMethodNameToPropertyName("is"));
	}
}
