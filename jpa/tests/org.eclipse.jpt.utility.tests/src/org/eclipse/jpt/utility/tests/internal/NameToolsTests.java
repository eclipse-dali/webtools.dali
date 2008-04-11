/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NameTools;

public class NameToolsTests extends TestCase {

	public NameToolsTests(String name) {
		super(name);
	}

	public void testStringAbsentIgnoreCase() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueNameForIgnoreCase("Taupe", colorCollection);
		assertEquals("Taupe", returned);
	}

	public void testStringPresentCaseDiffers() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueNameFor("green", colorCollection);
		assertEquals("green", returned);
	}

	public void testStringPresentIgnoreCase() {
		List<String> colorCollection = this.buildColorCollection();
		String returned = NameTools.uniqueNameForIgnoreCase("green", colorCollection);
		assertEquals("green2", returned);
	}

	public void testStringPresentWithAppendices() {
		List<String> colorCollection = this.buildColorCollection();
		colorCollection.add("Red1");
		colorCollection.add("red2");
		String returned = NameTools.uniqueNameForIgnoreCase("red", colorCollection);
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

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings));
		assertEquals("Test", NameTools.uniqueNameFor("Test", strings));

		assertEquals("Oracle3", NameTools.uniqueNameForIgnoreCase("Oracle", strings));
		assertEquals("oracle3", NameTools.uniqueNameForIgnoreCase("oracle", strings));
		assertEquals("Test", NameTools.uniqueNameForIgnoreCase("Test", strings));
	}

	public void testUniqueNameForCollection2() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings));
		assertEquals("Test", NameTools.uniqueNameFor("Test", strings));

		strings.add("Oracle Corporation");
		assertEquals("Oracle3", NameTools.uniqueNameForIgnoreCase("Oracle", strings));
		assertEquals("oracle3", NameTools.uniqueNameForIgnoreCase("oracle", strings));
		assertEquals("Test", NameTools.uniqueNameForIgnoreCase("Test", strings));
	}

	public void testUniqueNameForCollection3() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings));
	}

	public void testUniqueNameForIterator1() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle Corporation");
		strings.add("Oracle2");
		strings.add("oracle1");
		strings.add("Oracl");

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings.iterator()));
		assertEquals("Test", NameTools.uniqueNameFor("Test", strings.iterator()));

		assertEquals("Oracle3", NameTools.uniqueNameForIgnoreCase("Oracle", strings.iterator()));
		assertEquals("oracle3", NameTools.uniqueNameForIgnoreCase("oracle", strings.iterator()));
		assertEquals("Test", NameTools.uniqueNameForIgnoreCase("Test", strings.iterator()));
	}

	public void testUniqueNameForIterator2() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings.iterator()));
		assertEquals("Test", NameTools.uniqueNameFor("Test", strings.iterator()));

		strings.add("Oracle Corporation");
		assertEquals("Oracle3", NameTools.uniqueNameForIgnoreCase("Oracle", strings.iterator()));
		assertEquals("oracle3", NameTools.uniqueNameForIgnoreCase("oracle", strings.iterator()));
		assertEquals("Test", NameTools.uniqueNameForIgnoreCase("Test", strings.iterator()));
	}

	public void testUniqueNameForIterator3() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("Oracle3", NameTools.uniqueNameFor("Oracle", strings.iterator()));
	}

	public void testUniqueJavaNameForCollection() {
		Collection<String> strings = new ArrayList<String>();
		strings.add("Oracle");
		strings.add("Oracle");
		strings.add("Oracle2");
		strings.add("Oracle1");

		assertEquals("private2", NameTools.uniqueJavaNameFor("private", strings.iterator()));
		assertEquals("class2", NameTools.uniqueJavaNameFor("class", strings.iterator()));
	}

	public void testBuildQualifiedDatabaseObjectName() {
		assertEquals("catalog.schema.name", NameTools.buildQualifiedDatabaseObjectName("catalog", "schema", "name"));
		assertEquals("catalog..name", NameTools.buildQualifiedDatabaseObjectName("catalog", null, "name"));
		assertEquals("schema.name", NameTools.buildQualifiedDatabaseObjectName(null, "schema", "name"));
		assertEquals("name", NameTools.buildQualifiedDatabaseObjectName(null, null, "name"));
	}

	public void testJavaReservedWords() {
		assertTrue(CollectionTools.contains(NameTools.javaReservedWords(), "class"));
		assertFalse(CollectionTools.contains(NameTools.javaReservedWords(), "Class"));
		assertTrue(CollectionTools.contains(NameTools.javaReservedWords(), "private"));
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

}
