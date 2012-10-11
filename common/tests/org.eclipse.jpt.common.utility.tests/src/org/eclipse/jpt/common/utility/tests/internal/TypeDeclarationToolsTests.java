/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;

@SuppressWarnings("nls")
public class TypeDeclarationToolsTests
	extends TestCase
{
	public TypeDeclarationToolsTests(String name) {
		super(name);
	}

	public void testIsArrayString() throws Exception {
		assertFalse(TypeDeclarationTools.isArray("java.lang.Object"));
		assertTrue(TypeDeclarationTools.isArray("java.lang.Object[]"));
		assertTrue(TypeDeclarationTools.isArray("java.lang.Object[][][]"));

		assertFalse(TypeDeclarationTools.isArray("int"));
		assertTrue(TypeDeclarationTools.isArray("int[]"));
		assertTrue(TypeDeclarationTools.isArray("int[][][]"));

		assertFalse(TypeDeclarationTools.isArray("float"));
		assertTrue(TypeDeclarationTools.isArray("float [ ]"));
		assertTrue(TypeDeclarationTools.isArray("float[] [] []"));
	}

	public void testIsArrayCharArray() throws Exception {
		assertFalse(TypeDeclarationTools.isArray("java.lang.Object".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("java.lang.Object[]".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("java.lang.Object[][][]".toCharArray()));

		assertFalse(TypeDeclarationTools.isArray("int".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("int[]".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("int[][][]".toCharArray()));

		assertFalse(TypeDeclarationTools.isArray("float".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("float [ ]".toCharArray()));
		assertTrue(TypeDeclarationTools.isArray("float[] [] []".toCharArray()));
	}

	public void testArrayDepthString() throws Exception {
		assertEquals(0, TypeDeclarationTools.arrayDepth("java.lang.Object"));
		assertEquals(1, TypeDeclarationTools.arrayDepth("java.lang.Object[]"));
		assertEquals(3, TypeDeclarationTools.arrayDepth("java.lang.Object[][][]"));

		assertEquals(0, TypeDeclarationTools.arrayDepth("int"));
		assertEquals(1, TypeDeclarationTools.arrayDepth("int[]"));
		assertEquals(3, TypeDeclarationTools.arrayDepth("int[][][]"));

		assertEquals(0, TypeDeclarationTools.arrayDepth("float"));
		assertEquals(1, TypeDeclarationTools.arrayDepth("float [ ]"));
		assertEquals(3, TypeDeclarationTools.arrayDepth("float[] [] []"));
	}

	public void testArrayDepthCharArray() throws Exception {
		assertEquals(0, TypeDeclarationTools.arrayDepth("java.lang.Object".toCharArray()));
		assertEquals(1, TypeDeclarationTools.arrayDepth("java.lang.Object[]".toCharArray()));
		assertEquals(3, TypeDeclarationTools.arrayDepth("java.lang.Object[][][]".toCharArray()));

		assertEquals(0, TypeDeclarationTools.arrayDepth("int".toCharArray()));
		assertEquals(1, TypeDeclarationTools.arrayDepth("int[]".toCharArray()));
		assertEquals(3, TypeDeclarationTools.arrayDepth("int[][][]".toCharArray()));

		assertEquals(0, TypeDeclarationTools.arrayDepth("float".toCharArray()));
		assertEquals(1, TypeDeclarationTools.arrayDepth("float [ ]".toCharArray()));
		assertEquals(3, TypeDeclarationTools.arrayDepth("float[] [] []".toCharArray()));
	}

	public void testElementTypeNameString() throws Exception {
		assertEquals("java.lang.Object", TypeDeclarationTools.elementTypeName("java.lang.Object"));
		assertEquals("java.lang.Object", TypeDeclarationTools.elementTypeName("java.lang.Object[]"));
		assertEquals("java.lang.Object", TypeDeclarationTools.elementTypeName("java.lang.Object[][][]"));

		assertEquals("int", TypeDeclarationTools.elementTypeName("int"));
		assertEquals("int", TypeDeclarationTools.elementTypeName("int[]"));
		assertEquals("int", TypeDeclarationTools.elementTypeName("int[][][]"));

		assertEquals("float", TypeDeclarationTools.elementTypeName("float"));
		assertEquals("float", TypeDeclarationTools.elementTypeName("float [ ]"));
		assertEquals("float", TypeDeclarationTools.elementTypeName("float[] [] []"));
	}

	public void testElementTypeNameCharArray() throws Exception {
		assertEquals("java.lang.Object".toCharArray(), TypeDeclarationTools.elementTypeName("java.lang.Object".toCharArray()));
		assertEquals("java.lang.Object".toCharArray(), TypeDeclarationTools.elementTypeName("java.lang.Object[]".toCharArray()));
		assertEquals("java.lang.Object".toCharArray(), TypeDeclarationTools.elementTypeName("java.lang.Object[][][]".toCharArray()));

		assertEquals("int".toCharArray(), TypeDeclarationTools.elementTypeName("int".toCharArray()));
		assertEquals("int".toCharArray(), TypeDeclarationTools.elementTypeName("int[]".toCharArray()));
		assertEquals("int".toCharArray(), TypeDeclarationTools.elementTypeName("int[][][]".toCharArray()));

		assertEquals("float".toCharArray(), TypeDeclarationTools.elementTypeName("float".toCharArray()));
		assertEquals("float".toCharArray(), TypeDeclarationTools.elementTypeName("float [ ]".toCharArray()));
		assertEquals("float".toCharArray(), TypeDeclarationTools.elementTypeName("float[] [] []".toCharArray()));
	}

	public void testComponentTypeDeclarationString() throws Exception {
		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("java.lang.Object"));
		assertEquals("java.lang.Object", TypeDeclarationTools.componentTypeDeclaration("java.lang.Object[]"));
		assertEquals("java.lang.Object[][]", TypeDeclarationTools.componentTypeDeclaration("java.lang.Object[][][]"));

		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("int"));
		assertEquals("int", TypeDeclarationTools.componentTypeDeclaration("int[]"));
		assertEquals("int[][]", TypeDeclarationTools.componentTypeDeclaration("int[][][]"));

		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("float"));
		assertEquals("float", TypeDeclarationTools.componentTypeDeclaration("float [ ]"));
		assertEquals("float[][]", TypeDeclarationTools.componentTypeDeclaration("float[] [] []"));
	}

	public void testComponentTypeDeclarationCharArray() throws Exception {
		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("java.lang.Object".toCharArray()));
		assertEquals("java.lang.Object".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("java.lang.Object[]".toCharArray()));
		assertEquals("java.lang.Object[][]".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("java.lang.Object[][][]".toCharArray()));

		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("int".toCharArray()));
		assertEquals("int".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("int[]".toCharArray()));
		assertEquals("int[][]".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("int[][][]".toCharArray()));

		assertEquals(null, TypeDeclarationTools.componentTypeDeclaration("float".toCharArray()));
		assertEquals("float".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("float [ ]".toCharArray()));
		assertEquals("float[][]".toCharArray(), TypeDeclarationTools.componentTypeDeclaration("float[] [] []".toCharArray()));
	}

	public void testClassNameString() throws Exception {
		assertEquals("int", TypeDeclarationTools.className("int"));
		assertEquals("[I", TypeDeclarationTools.className("int[]"));
		assertEquals("[[I", TypeDeclarationTools.className("int [ ] [ ]"));

		assertEquals("java.lang.Object", TypeDeclarationTools.className("java.lang.Object"));
		assertEquals("[Ljava.lang.Object;", TypeDeclarationTools.className("java.lang.Object\t[]"));
		assertEquals("[[Ljava.lang.Object;", TypeDeclarationTools.className("java.lang.Object\t[]\t[]"));
	}

	public void testClassNameCharArray() throws Exception {
		assertEquals("int".toCharArray(), TypeDeclarationTools.className("int".toCharArray()));
		assertEquals("[I".toCharArray(), TypeDeclarationTools.className("int[]".toCharArray()));
		assertEquals("[[I".toCharArray(), TypeDeclarationTools.className("int [ ] [ ]".toCharArray()));

		assertEquals("java.lang.Object".toCharArray(), TypeDeclarationTools.className("java.lang.Object".toCharArray()));
		assertEquals("[Ljava.lang.Object;".toCharArray(), TypeDeclarationTools.className("java.lang.Object\t[]".toCharArray()));
		assertEquals("[[Ljava.lang.Object;".toCharArray(), TypeDeclarationTools.className("java.lang.Object\t[]\t[]".toCharArray()));
	}

	public void testClassNameStringInt() throws Exception {
		assertEquals(int.class.getName(), TypeDeclarationTools.className("int", 0));
		assertEquals(int[].class.getName(), TypeDeclarationTools.className("int", 1));
		assertEquals(int[][][].class.getName(), TypeDeclarationTools.className("int", 3));

		assertEquals(Object.class.getName(), TypeDeclarationTools.className("java.lang.Object", 0));
		assertEquals(Object[][][].class.getName(), TypeDeclarationTools.className("java.lang.Object", 3));

		assertEquals(void.class.getName(), TypeDeclarationTools.className("void", 0));
		try {
			TypeDeclarationTools.className(void.class.getName(), 1);
			fail("should not get here...");
		} catch (IllegalArgumentException ex) {
			// expected
		}
	}

	public void testClassNameCharArrayInt() throws Exception {
		assertEquals(int.class.getName().toCharArray(), TypeDeclarationTools.className("int".toCharArray(), 0));
		assertEquals(int[].class.getName().toCharArray(), TypeDeclarationTools.className("int".toCharArray(), 1));
		assertEquals(int[][][].class.getName().toCharArray(), TypeDeclarationTools.className("int".toCharArray(), 3));

		assertEquals(Object.class.getName().toCharArray(), TypeDeclarationTools.className("java.lang.Object".toCharArray(), 0));
		assertEquals(Object[][][].class.getName().toCharArray(), TypeDeclarationTools.className("java.lang.Object".toCharArray(), 3));

		assertEquals(void.class.getName().toCharArray(), TypeDeclarationTools.className("void".toCharArray(), 0));
		try {
			TypeDeclarationTools.className(void.class.getName().toCharArray(), 1);
			fail("should not get here...");
		} catch (IllegalArgumentException ex) {
			// expected
		}
	}

	public void testSimpleNameString() throws Exception {
		assertEquals("int", TypeDeclarationTools.simpleName("int"));
		assertEquals("int[]", TypeDeclarationTools.simpleName("int[]"));
		assertEquals("int[][]", TypeDeclarationTools.simpleName("int [ ] [ ]"));

		assertEquals("Object", TypeDeclarationTools.simpleName("java.lang.Object"));
		assertEquals("Object[]", TypeDeclarationTools.simpleName("java.lang.Object\t[]"));
		assertEquals("Object[][]", TypeDeclarationTools.simpleName("java.lang.Object\t[]\t[]"));

		assertEquals("Default", TypeDeclarationTools.simpleName("Default"));
		assertEquals("Default[]", TypeDeclarationTools.simpleName("Default\t[]"));
		assertEquals("Default[][]", TypeDeclarationTools.simpleName("Default\t[]\t[]"));
	}

	public void testSimpleNameCharArray() throws Exception {
		assertEquals("int".toCharArray(), TypeDeclarationTools.simpleName("int".toCharArray()));
		assertEquals("int[]".toCharArray(), TypeDeclarationTools.simpleName("int[]".toCharArray()));
		assertEquals("int[][]".toCharArray(), TypeDeclarationTools.simpleName("int [ ] [ ]".toCharArray()));

		assertEquals("Object".toCharArray(), TypeDeclarationTools.simpleName("java.lang.Object".toCharArray()));
		assertEquals("Object[]".toCharArray(), TypeDeclarationTools.simpleName("java.lang.Object\t[]".toCharArray()));
		assertEquals("Object[][]".toCharArray(), TypeDeclarationTools.simpleName("java.lang.Object\t[]\t[]".toCharArray()));

		assertEquals("Default".toCharArray(), TypeDeclarationTools.simpleName("Default".toCharArray()));
		assertEquals("Default[]".toCharArray(), TypeDeclarationTools.simpleName("Default\t[]".toCharArray()));
		assertEquals("Default[][]".toCharArray(), TypeDeclarationTools.simpleName("Default\t[]\t[]".toCharArray()));
	}

	public void testPackageNameString() throws Exception {
		assertEquals("", TypeDeclarationTools.packageName("int"));
		assertEquals("", TypeDeclarationTools.packageName("int[]"));
		assertEquals("", TypeDeclarationTools.packageName("int [ ] [ ]"));

		assertEquals("java.lang", TypeDeclarationTools.packageName("java.lang.Object"));
		assertEquals("", TypeDeclarationTools.packageName("java.lang.Object\t[]"));
		assertEquals("", TypeDeclarationTools.packageName("java.lang.Object\t[]\t[]"));

		assertEquals("", TypeDeclarationTools.packageName("Default"));
		assertEquals("", TypeDeclarationTools.packageName("Default\t[]"));
		assertEquals("", TypeDeclarationTools.packageName("Default\t[]\t[]"));
	}

	public void testPackageNameCharArray() throws Exception {
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("int".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("int[]".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("int [ ] [ ]".toCharArray()));

		assertEquals("java.lang".toCharArray(), TypeDeclarationTools.packageName("java.lang.Object".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("java.lang.Object\t[]".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("java.lang.Object\t[]\t[]".toCharArray()));

		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("Default".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("Default\t[]".toCharArray()));
		assertEquals("".toCharArray(), TypeDeclarationTools.packageName("Default\t[]\t[]".toCharArray()));
	}

	public void testLoadClassString() throws Exception {
		assertEquals(int.class, TypeDeclarationTools.loadClass("int"));
		assertEquals(int[].class, TypeDeclarationTools.loadClass("int[]"));
		assertEquals(int[][].class, TypeDeclarationTools.loadClass("int [ ] [ ]"));

		assertEquals(java.lang.Object.class, TypeDeclarationTools.loadClass("java.lang.Object"));
		assertEquals(java.lang.Object[].class, TypeDeclarationTools.loadClass("java.lang.Object\t[]"));
		assertEquals(java.lang.Object[][].class, TypeDeclarationTools.loadClass("java.lang.Object\t[]\t[]"));
	}

	public void testLoadClassCharArray() throws Exception {
		assertEquals(int.class, TypeDeclarationTools.loadClass("int".toCharArray()));
		assertEquals(int[].class, TypeDeclarationTools.loadClass("int[]".toCharArray()));
		assertEquals(int[][].class, TypeDeclarationTools.loadClass("int [ ] [ ]".toCharArray()));

		assertEquals(java.lang.Object.class, TypeDeclarationTools.loadClass("java.lang.Object".toCharArray()));
		assertEquals(java.lang.Object[].class, TypeDeclarationTools.loadClass("java.lang.Object\t[]".toCharArray()));
		assertEquals(java.lang.Object[][].class, TypeDeclarationTools.loadClass("java.lang.Object\t[]\t[]".toCharArray()));
	}

	public void testIsJavaLangClassString() {
		assertTrue(TypeDeclarationTools.isJavaLangClass("Object"));
		assertTrue(TypeDeclarationTools.isJavaLangClass("String"));
		assertTrue(TypeDeclarationTools.isJavaLangClass("AutoCloseable"));
		assertFalse(TypeDeclarationTools.isJavaLangClass(""));
		assertFalse(TypeDeclarationTools.isJavaLangClass("Collection"));
	}

	public void testIsJavaLangClassCharArray() {
		assertTrue(TypeDeclarationTools.isJavaLangClass("Object".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass("String".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass("AutoCloseable".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass("".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass("Collection".toCharArray()));
	}

	public void testIsJavaLangClass5String() {
		assertTrue(TypeDeclarationTools.isJavaLangClass5("Object"));
		assertTrue(TypeDeclarationTools.isJavaLangClass5("String"));
		assertFalse(TypeDeclarationTools.isJavaLangClass5("AutoCloseable"));
		assertFalse(TypeDeclarationTools.isJavaLangClass5(""));
		assertFalse(TypeDeclarationTools.isJavaLangClass5("Collection"));
	}

	public void testIsJavaLangClass5CharArray() {
		assertTrue(TypeDeclarationTools.isJavaLangClass5("Object".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass5("String".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass5("AutoCloseable".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass5("".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass5("Collection".toCharArray()));
	}

	public void testIsJavaLangClass6String() {
		assertTrue(TypeDeclarationTools.isJavaLangClass6("Object"));
		assertTrue(TypeDeclarationTools.isJavaLangClass6("String"));
		assertFalse(TypeDeclarationTools.isJavaLangClass6("AutoCloseable"));
		assertFalse(TypeDeclarationTools.isJavaLangClass6(""));
		assertFalse(TypeDeclarationTools.isJavaLangClass6("Collection"));
	}

	public void testIsJavaLangClass6CharArray() {
		assertTrue(TypeDeclarationTools.isJavaLangClass6("Object".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass6("String".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass6("AutoCloseable".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass6("".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass6("Collection".toCharArray()));
	}

	public void testIsJavaLangClass7String() {
		assertTrue(TypeDeclarationTools.isJavaLangClass7("Object"));
		assertTrue(TypeDeclarationTools.isJavaLangClass7("String"));
		assertTrue(TypeDeclarationTools.isJavaLangClass7("AutoCloseable"));
		assertFalse(TypeDeclarationTools.isJavaLangClass7(""));
		assertFalse(TypeDeclarationTools.isJavaLangClass7("Collection"));
	}

	public void testIsJavaLangClass7CharArray() {
		assertTrue(TypeDeclarationTools.isJavaLangClass7("Object".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass7("String".toCharArray()));
		assertTrue(TypeDeclarationTools.isJavaLangClass7("AutoCloseable".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass7("".toCharArray()));
		assertFalse(TypeDeclarationTools.isJavaLangClass7("Collection".toCharArray()));
	}

	public static void assertEquals(char[] a1, char[] a2) {
		assertTrue(Arrays.equals(a1, a2));
	}
}
