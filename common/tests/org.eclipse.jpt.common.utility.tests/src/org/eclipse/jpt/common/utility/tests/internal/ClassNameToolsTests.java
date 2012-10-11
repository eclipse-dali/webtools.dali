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

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;

@SuppressWarnings("nls")
public class ClassNameToolsTests
	extends TestCase
{
	public ClassNameToolsTests(String name) {
		super(name);
	}

	public static void assertEquals(String expected, char[] actual) {
		assertEquals(expected, (actual == null) ? null : new String(actual));
	}

	public void testIsArray() {
		assertFalse(ClassNameTools.isArray(int.class.getName()));
		assertTrue(ClassNameTools.isArray(int[].class.getName()));
		assertTrue(ClassNameTools.isArray(int[][].class.getName()));

		assertFalse(ClassNameTools.isArray(java.lang.String.class.getName()));
		assertTrue(ClassNameTools.isArray(java.lang.String[].class.getName()));
		assertTrue(ClassNameTools.isArray(java.lang.String[][].class.getName()));
	}

	public void testIsArrayCharArray() {
		assertFalse(ClassNameTools.isArray(int.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isArray(int[].class.getName().toCharArray()));
		assertTrue(ClassNameTools.isArray(int[][].class.getName().toCharArray()));

		assertFalse(ClassNameTools.isArray(java.lang.String.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isArray(java.lang.String[].class.getName().toCharArray()));
		assertTrue(ClassNameTools.isArray(java.lang.String[][].class.getName().toCharArray()));
	}

	public void testArrayDepth() {
		assertEquals(0, ClassNameTools.arrayDepth(java.util.Vector.class.getName()));
		assertEquals(0, ClassNameTools.arrayDepth(int.class.getName()));
		assertEquals(0, ClassNameTools.arrayDepth(void.class.getName()));
		assertEquals(1, ClassNameTools.arrayDepth(java.util.Vector[].class.getName()));
		assertEquals(1, ClassNameTools.arrayDepth(int[].class.getName()));
		assertEquals(3, ClassNameTools.arrayDepth(java.util.Vector[][][].class.getName()));
		assertEquals(3, ClassNameTools.arrayDepth(int[][][].class.getName()));
	}

	public void testArrayDepthCharArray() {
		assertEquals(0, ClassNameTools.arrayDepth(java.util.Vector.class.getName().toCharArray()));
		assertEquals(0, ClassNameTools.arrayDepth(int.class.getName().toCharArray()));
		assertEquals(0, ClassNameTools.arrayDepth(void.class.getName().toCharArray()));
		assertEquals(1, ClassNameTools.arrayDepth(java.util.Vector[].class.getName().toCharArray()));
		assertEquals(1, ClassNameTools.arrayDepth(int[].class.getName().toCharArray()));
		assertEquals(3, ClassNameTools.arrayDepth(java.util.Vector[][][].class.getName().toCharArray()));
		assertEquals(3, ClassNameTools.arrayDepth(int[][][].class.getName().toCharArray()));
	}

	public void testElementTypeName() {
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector.class.getName()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int.class.getName()));
		assertEquals(void.class.getName(), ClassNameTools.elementTypeName(void.class.getName()));
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector[].class.getName()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int[].class.getName()));
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector[][][].class.getName()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int[][][].class.getName()));
	}

	public void testElementTypeNameCharArray() {
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector.class.getName().toCharArray()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int.class.getName().toCharArray()));
		assertEquals(void.class.getName(), ClassNameTools.elementTypeName(void.class.getName().toCharArray()));
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector[].class.getName().toCharArray()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int[].class.getName().toCharArray()));
		assertEquals(java.util.Vector.class.getName(), ClassNameTools.elementTypeName(java.util.Vector[][][].class.getName().toCharArray()));
		assertEquals(int.class.getName(), ClassNameTools.elementTypeName(int[][][].class.getName().toCharArray()));
	}

	public void testComponentTypeName() {
		assertEquals(null, ClassNameTools.componentTypeName(java.lang.Object.class.getName()));
		assertEquals(java.lang.Object.class.getName(), ClassNameTools.componentTypeName(java.lang.Object[].class.getName()));
		assertEquals(java.lang.Object[].class.getName(), ClassNameTools.componentTypeName(java.lang.Object[][].class.getName()));

		assertEquals(null, ClassNameTools.componentTypeName(int.class.getName()));
		assertEquals(int.class.getName(), ClassNameTools.componentTypeName(int[].class.getName()));
		assertEquals(int[].class.getName(), ClassNameTools.componentTypeName(int[][].class.getName()));
	}

	public void testComponentTypeNameCharArray() {
		assertNull(ClassNameTools.componentTypeName(java.lang.Object.class.getName().toCharArray()));
		assertEquals(java.lang.Object.class.getName(), ClassNameTools.componentTypeName(java.lang.Object[].class.getName().toCharArray()));
		assertEquals(java.lang.Object[].class.getName(), ClassNameTools.componentTypeName(java.lang.Object[][].class.getName().toCharArray()));

		assertNull(ClassNameTools.componentTypeName(int.class.getName().toCharArray()));
		assertEquals(int.class.getName(), ClassNameTools.componentTypeName(int[].class.getName().toCharArray()));
		assertEquals(int[].class.getName(), ClassNameTools.componentTypeName(int[][].class.getName().toCharArray()));
	}

	public void testTypeDeclaration() throws Exception {
		assertEquals("int".toCharArray(), ClassNameTools.typeDeclaration("int".toCharArray()));
		assertEquals("int[]".toCharArray(), ClassNameTools.typeDeclaration("[I".toCharArray()));
		assertEquals("int[][]".toCharArray(), ClassNameTools.typeDeclaration("[[I".toCharArray()));

		assertEquals("java.lang.Object".toCharArray(), ClassNameTools.typeDeclaration("java.lang.Object".toCharArray()));
		assertEquals("java.lang.Object[]".toCharArray(), ClassNameTools.typeDeclaration("[Ljava.lang.Object;".toCharArray()));
		assertEquals("java.lang.Object[][]".toCharArray(), ClassNameTools.typeDeclaration("[[Ljava.lang.Object;".toCharArray()));
	}

	public void testTypeDeclarationCharArray() throws Exception {
		assertEquals("int", ClassNameTools.typeDeclaration("int"));
		assertEquals("int[]", ClassNameTools.typeDeclaration("[I"));
		assertEquals("int[][]", ClassNameTools.typeDeclaration("[[I"));

		assertEquals("java.lang.Object", ClassNameTools.typeDeclaration("java.lang.Object"));
		assertEquals("java.lang.Object[]", ClassNameTools.typeDeclaration("[Ljava.lang.Object;"));
		assertEquals("java.lang.Object[][]", ClassNameTools.typeDeclaration("[[Ljava.lang.Object;"));
	}

	public void testSimpleName() throws Exception {
		assertEquals("Object", ClassNameTools.simpleName(java.lang.Object.class.getName()));
		assertEquals("Object[]", ClassNameTools.simpleName(java.lang.Object[].class.getName()));
		assertEquals("Object[][]", ClassNameTools.simpleName(java.lang.Object[][].class.getName()));

		assertEquals(java.util.Map.class.getSimpleName(), ClassNameTools.simpleName(java.util.Map.class.getName()));
		assertEquals(java.util.Map.Entry.class.getSimpleName(), ClassNameTools.simpleName(java.util.Map.Entry.class.getName()));

		assertEquals("int", ClassNameTools.simpleName(int.class.getName()));
		assertEquals("int[]", ClassNameTools.simpleName(int[].class.getName()));
		assertEquals("int[][]", ClassNameTools.simpleName(int[][].class.getName()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals("", ClassNameTools.simpleName(anonObject.getClass().getName()));

		class Local {
			// anonymous class
		}
		Local localObject = new Local();
		assertEquals("Local", ClassNameTools.simpleName(localObject.getClass().getName()));
	}

	public void testSimpleNameCharArray() throws Exception {
		assertEquals("Object", ClassNameTools.simpleName(java.lang.Object.class.getName().toCharArray()));
		assertEquals("Object[]", ClassNameTools.simpleName(java.lang.Object[].class.getName().toCharArray()));
		assertEquals("Object[][]", ClassNameTools.simpleName(java.lang.Object[][].class.getName().toCharArray()));

		assertEquals(java.util.Map.class.getSimpleName(), ClassNameTools.simpleName(java.util.Map.class.getName().toCharArray()));
		assertEquals(java.util.Map.Entry.class.getSimpleName(), ClassNameTools.simpleName(java.util.Map.Entry.class.getName().toCharArray()));

		assertEquals("int", ClassNameTools.simpleName(int.class.getName().toCharArray()));
		assertEquals("int[]", ClassNameTools.simpleName(int[].class.getName().toCharArray()));
		assertEquals("int[][]", ClassNameTools.simpleName(int[][].class.getName().toCharArray()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals("", ClassNameTools.simpleName(anonObject.getClass().getName().toCharArray()));

		class Local {
			// anonymous class
		}
		Local localObject = new Local();
		assertEquals("Local", ClassNameTools.simpleName(localObject.getClass().getName().toCharArray()));
	}

	public void testPackageName() throws Exception {
		assertEquals(java.lang.Object.class.getPackage().getName(), ClassNameTools.packageName(java.lang.Object.class.getName()));
		assertEquals("", ClassNameTools.packageName(java.lang.Object[].class.getName()));
		assertEquals("", ClassNameTools.packageName(java.lang.Object[][].class.getName()));

		assertEquals(java.util.Map.class.getPackage().getName(), ClassNameTools.packageName(java.util.Map.class.getName()));
		assertEquals(java.util.Map.Entry.class.getPackage().getName(), ClassNameTools.packageName(java.util.Map.Entry.class.getName()));

		assertEquals("", ClassNameTools.packageName(int.class.getName()));
		assertEquals("", ClassNameTools.packageName(int[].class.getName()));
		assertEquals("", ClassNameTools.packageName(int[][].class.getName()));

		assertEquals("", ClassNameTools.packageName(void.class.getName()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals(anonObject.getClass().getPackage().getName(), ClassNameTools.packageName(anonObject.getClass().getName()));
	}

	public void testPackageNameCharArray() throws Exception {
		assertEquals(java.lang.Object.class.getPackage().getName(), ClassNameTools.packageName(java.lang.Object.class.getName().toCharArray()));
		assertEquals("", ClassNameTools.packageName(java.lang.Object[].class.getName().toCharArray()));
		assertEquals("", ClassNameTools.packageName(java.lang.Object[][].class.getName().toCharArray()));

		assertEquals(java.util.Map.class.getPackage().getName(), ClassNameTools.packageName(java.util.Map.class.getName().toCharArray()));
		assertEquals(java.util.Map.Entry.class.getPackage().getName(), ClassNameTools.packageName(java.util.Map.Entry.class.getName().toCharArray()));

		assertEquals("", ClassNameTools.packageName(int.class.getName().toCharArray()));
		assertEquals("", ClassNameTools.packageName(int[].class.getName().toCharArray()));
		assertEquals("", ClassNameTools.packageName(int[][].class.getName().toCharArray()));

		assertEquals("", ClassNameTools.packageName(void.class.getName().toCharArray()));

		Object anonObject = new Object() {
			// anonymous class
		};
		assertEquals(anonObject.getClass().getPackage().getName(), ClassNameTools.packageName(anonObject.getClass().getName().toCharArray()));
	}

	public void testIsTopLevel() throws Exception {
		assertTrue(ClassNameTools.isTopLevel(java.util.Map.class.getName())); // top-level
		assertFalse(ClassNameTools.isTopLevel(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassNameTools.isTopLevel(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassNameTools.isTopLevel(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName()));
	}

	public void testIsTopLevelCharArray() throws Exception {
		assertTrue(ClassNameTools.isTopLevel(java.util.Map.class.getName().toCharArray())); // top-level
		assertFalse(ClassNameTools.isTopLevel(java.util.Map.Entry.class.getName().toCharArray())); // member
		assertFalse(ClassNameTools.isTopLevel(Class.forName(this.getClass().getName() + "$1LocalClass").getName().toCharArray())); // local
		assertFalse(ClassNameTools.isTopLevel(Class.forName("java.util.Vector$1").getName().toCharArray())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName().toCharArray()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName().toCharArray()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName().toCharArray()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isTopLevel(array.getClass().getName().toCharArray()));
	}

	public void testIsMember() throws Exception {
		assertFalse(ClassNameTools.isMember(java.util.Map.class.getName())); // top-level
		assertTrue(ClassNameTools.isMember(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassNameTools.isMember(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassNameTools.isMember(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isMember(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isMember(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isMember(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isMember(array.getClass().getName()));

		// test a few edge cases
		assertTrue(ClassNameTools.isMember("java.util.Map$a1"));
		assertTrue(ClassNameTools.isMember("java.util.Map$1aa$aaa"));  // member inside local
		assertTrue(ClassNameTools.isMember("java.util.Map$1$aaa"));  // member inside anonymous
		assertTrue(ClassNameTools.isMember("java.util.Map$a1$aaa$bbb"));
		assertTrue(ClassNameTools.isMember("java.util.Map$1a1$aaa"));  // member inside local
		assertFalse(ClassNameTools.isMember("java.util.Map$1a"));
		assertTrue(ClassNameTools.isMember("java.util.Map$a12345$b12345"));
		assertFalse(ClassNameTools.isMember("java.util.Map$12345a"));
		assertFalse(ClassNameTools.isMember("java.util.Map$333"));
		assertFalse(ClassNameTools.isMember("java.util.Map3$333"));
	}

	public void testIsMemberCharArray() throws Exception {
		assertFalse(ClassNameTools.isMember(java.util.Map.class.getName().toCharArray())); // top-level
		assertTrue(ClassNameTools.isMember(java.util.Map.Entry.class.getName().toCharArray())); // member
		assertFalse(ClassNameTools.isMember(Class.forName(this.getClass().getName() + "$1LocalClass").getName().toCharArray())); // local
		assertFalse(ClassNameTools.isMember(Class.forName("java.util.Vector$1").getName().toCharArray())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isMember(array.getClass().getName().toCharArray()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isMember(array.getClass().getName().toCharArray()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isMember(array.getClass().getName().toCharArray()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isMember(array.getClass().getName().toCharArray()));

		// test a few edge cases
		assertTrue(ClassNameTools.isMember("java.util.Map$a1".toCharArray()));
		assertTrue(ClassNameTools.isMember("java.util.Map$1aa$aaa".toCharArray()));  // member inside local
		assertTrue(ClassNameTools.isMember("java.util.Map$1$aaa".toCharArray()));  // member inside anonymous
		assertTrue(ClassNameTools.isMember("java.util.Map$a1$aaa$bbb".toCharArray()));
		assertTrue(ClassNameTools.isMember("java.util.Map$1a1$aaa".toCharArray()));  // member inside local
		assertFalse(ClassNameTools.isMember("java.util.Map$1a".toCharArray()));
		assertTrue(ClassNameTools.isMember("java.util.Map$a12345$b12345".toCharArray()));
		assertFalse(ClassNameTools.isMember("java.util.Map$12345a".toCharArray()));
		assertFalse(ClassNameTools.isMember("java.util.Map$333".toCharArray()));
		assertFalse(ClassNameTools.isMember("java.util.Map3$333".toCharArray()));
	}

	public void testIsLocal() throws Exception {
		class LocalClass {
			void foo() {
				System.getProperty("foo");
			}
		}
		new LocalClass().foo();
		assertFalse(ClassNameTools.isLocal(java.util.Map.class.getName())); // top-level
		assertFalse(ClassNameTools.isLocal(java.util.Map.Entry.class.getName())); // member
		assertTrue(ClassNameTools.isLocal(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertFalse(ClassNameTools.isLocal(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isLocal(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isLocal(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isLocal(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isLocal(array.getClass().getName()));

		// test a few edge cases
		assertFalse(ClassNameTools.isLocal("java.util.Map$a1"));
		assertFalse(ClassNameTools.isLocal("java.util.Map$a1$aaa$bbb"));
		assertFalse(ClassNameTools.isLocal("java.util.Map$11$aaa"));
		assertTrue(ClassNameTools.isLocal("java.util.Map$1a"));
		assertTrue(ClassNameTools.isLocal("java.util.Map$2abc"));
		assertTrue(ClassNameTools.isLocal("java.util.Map$2abc1"));
		assertFalse(ClassNameTools.isLocal("java.util.Map$a12345$b12345"));
		assertTrue(ClassNameTools.isLocal("java.util.Map$12345$1234a"));
		assertFalse(ClassNameTools.isLocal("java.util.Map$333"));
		assertFalse(ClassNameTools.isLocal("java.util.Map3$333"));
	}

	public void testIsLocalCharArray() throws Exception {
		class LocalClass {
			void foo() {
				System.getProperty("foo");
			}
		}
		new LocalClass().foo();
		assertFalse(ClassNameTools.isLocal(java.util.Map.class.getName().toCharArray())); // top-level
		assertFalse(ClassNameTools.isLocal(java.util.Map.Entry.class.getName().toCharArray())); // member
		assertTrue(ClassNameTools.isLocal(Class.forName(this.getClass().getName() + "$1LocalClass").getName().toCharArray())); // local
		assertFalse(ClassNameTools.isLocal(Class.forName("java.util.Vector$1").getName().toCharArray())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isLocal(array.getClass().getName().toCharArray()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isLocal(array.getClass().getName().toCharArray()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isLocal(array.getClass().getName().toCharArray()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isLocal(array.getClass().getName().toCharArray()));

		// test a few edge cases
		assertFalse(ClassNameTools.isLocal("java.util.Map$a1".toCharArray()));
		assertFalse(ClassNameTools.isLocal("java.util.Map$a1$aaa$bbb".toCharArray()));
		assertFalse(ClassNameTools.isLocal("java.util.Map$11$aaa".toCharArray()));
		assertTrue(ClassNameTools.isLocal("java.util.Map$1a".toCharArray()));
		assertTrue(ClassNameTools.isLocal("java.util.Map$2abc".toCharArray()));
		assertTrue(ClassNameTools.isLocal("java.util.Map$2abc1".toCharArray()));
		assertFalse(ClassNameTools.isLocal("java.util.Map$a12345$b12345".toCharArray()));
		assertTrue(ClassNameTools.isLocal("java.util.Map$12345$1234a".toCharArray()));
		assertFalse(ClassNameTools.isLocal("java.util.Map$333".toCharArray()));
		assertFalse(ClassNameTools.isLocal("java.util.Map3$333".toCharArray()));
	}

	public void testIsAnonymous() throws Exception {
		assertFalse(ClassNameTools.isAnonymous(java.util.Map.class.getName())); // top-level
		assertFalse(ClassNameTools.isAnonymous(java.util.Map.Entry.class.getName())); // member
		assertFalse(ClassNameTools.isAnonymous(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertTrue(ClassNameTools.isAnonymous(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName()));

		// test a few edge cases
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a1"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a1$aaa$bbb"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1a1$aaa"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1$a"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1a"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a12345$b12345"));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$12345$a1234"));
		assertTrue(ClassNameTools.isAnonymous("java.util.Map$333"));
		assertTrue(ClassNameTools.isAnonymous("java.util.Map3$333"));
	}

	public void testIsAnonymousCharArray() throws Exception {
		assertFalse(ClassNameTools.isAnonymous(java.util.Map.class.getName().toCharArray())); // top-level
		assertFalse(ClassNameTools.isAnonymous(java.util.Map.Entry.class.getName().toCharArray())); // member
		assertFalse(ClassNameTools.isAnonymous(Class.forName(this.getClass().getName() + "$1LocalClass").getName().toCharArray())); // local
		assertTrue(ClassNameTools.isAnonymous(Class.forName("java.util.Vector$1").getName().toCharArray())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName().toCharArray()));
		array = new java.util.Map.Entry[0]; // member
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName().toCharArray()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName().toCharArray()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertFalse(ClassNameTools.isAnonymous(array.getClass().getName().toCharArray()));

		// test a few edge cases
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a1".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a1$aaa$bbb".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1a1$aaa".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1$a".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$1a".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$a12345$b12345".toCharArray()));
		assertFalse(ClassNameTools.isAnonymous("java.util.Map$12345$a1234".toCharArray()));
		assertTrue(ClassNameTools.isAnonymous("java.util.Map$333".toCharArray()));
		assertTrue(ClassNameTools.isAnonymous("java.util.Map3$333".toCharArray()));
	}

	public void testIsReference() throws Exception {
		assertFalse(ClassNameTools.isReference(int.class.getName())); // top-level

		assertTrue(ClassNameTools.isReference(java.util.Map.class.getName())); // top-level
		assertTrue(ClassNameTools.isReference(java.util.Map.Entry.class.getName())); // member
		assertTrue(ClassNameTools.isReference(Class.forName(this.getClass().getName() + "$1LocalClass").getName())); // local
		assertTrue(ClassNameTools.isReference(Class.forName("java.util.Vector$1").getName())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertTrue(ClassNameTools.isReference(array.getClass().getName()));
		array = new java.util.Map.Entry[0]; // member
		assertTrue(ClassNameTools.isReference(array.getClass().getName()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertTrue(ClassNameTools.isReference(array.getClass().getName()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertTrue(ClassNameTools.isReference(array.getClass().getName()));
	}

	public void testIsReferenceCharArray() throws Exception {
		assertFalse(ClassNameTools.isReference(int.class.getName().toCharArray())); // top-level

		assertTrue(ClassNameTools.isReference(java.util.Map.class.getName().toCharArray())); // top-level
		assertTrue(ClassNameTools.isReference(java.util.Map.Entry.class.getName().toCharArray())); // member
		assertTrue(ClassNameTools.isReference(Class.forName(this.getClass().getName() + "$1LocalClass").getName().toCharArray())); // local
		assertTrue(ClassNameTools.isReference(Class.forName("java.util.Vector$1").getName().toCharArray())); // anonymous

		Object[] array = new java.util.Map[0]; // top-level
		assertTrue(ClassNameTools.isReference(array.getClass().getName().toCharArray()));
		array = new java.util.Map.Entry[0]; // member
		assertTrue(ClassNameTools.isReference(array.getClass().getName().toCharArray()));
		Class<?> localClass = Class.forName(this.getClass().getName() + "$1LocalClass"); // local
		array = (Object[]) Array.newInstance(localClass, 0);
		assertTrue(ClassNameTools.isReference(array.getClass().getName().toCharArray()));
		Class<?> anonClass = Class.forName("java.util.Vector$1"); // local
		array = (Object[]) Array.newInstance(anonClass, 0);
		assertTrue(ClassNameTools.isReference(array.getClass().getName().toCharArray()));
	}

	public void testIsPrimitive() {
		assertTrue(void.class.isPrimitive());

		assertTrue(ClassNameTools.isPrimitive(void.class.getName()));
		assertTrue(ClassNameTools.isPrimitive(int.class.getName()));
		assertTrue(ClassNameTools.isPrimitive(float.class.getName()));
		assertTrue(ClassNameTools.isPrimitive(boolean.class.getName()));

		assertFalse(ClassNameTools.isPrimitive(java.lang.Number.class.getName()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.String.class.getName()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.Boolean.class.getName()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.Integer.class.getName()));
	}

	public void testIsPrimitiveCharArray() {
		assertTrue(void.class.isPrimitive());

		assertTrue(ClassNameTools.isPrimitive(void.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isPrimitive(int.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isPrimitive(float.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isPrimitive(boolean.class.getName().toCharArray()));

		assertFalse(ClassNameTools.isPrimitive(java.lang.Number.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.String.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.Boolean.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitive(java.lang.Integer.class.getName().toCharArray()));
	}

	public void testIsPrimitiveWrapper() {
		assertFalse(ClassNameTools.isPrimitiveWrapper(void.class.getName()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(int.class.getName()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(float.class.getName()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(boolean.class.getName()));

		assertFalse(ClassNameTools.isPrimitiveWrapper(java.lang.reflect.Field.class.getName()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(java.lang.String.class.getName()));
		assertTrue(ClassNameTools.isPrimitiveWrapper(java.lang.Boolean.class.getName()));
		assertTrue(ClassNameTools.isPrimitiveWrapper(java.lang.Integer.class.getName()));
	}

	public void testIsPrimitiveWrapperCharArray() {
		assertFalse(ClassNameTools.isPrimitiveWrapper(void.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(int.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(float.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(boolean.class.getName().toCharArray()));

		assertFalse(ClassNameTools.isPrimitiveWrapper(java.lang.reflect.Field.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isPrimitiveWrapper(java.lang.String.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isPrimitiveWrapper(java.lang.Boolean.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isPrimitiveWrapper(java.lang.Integer.class.getName().toCharArray()));
	}

	public void testIsVariablePrimitive() {
		assertFalse(ClassNameTools.isVariablePrimitive(void.class.getName()));

		assertTrue(ClassNameTools.isVariablePrimitive(int.class.getName()));
		assertTrue(ClassNameTools.isVariablePrimitive(float.class.getName()));
		assertTrue(ClassNameTools.isVariablePrimitive(boolean.class.getName()));

		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.Number.class.getName()));
		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.String.class.getName()));
		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.Boolean.class.getName()));
	}

	public void testIsVariablePrimitiveCharArray() {
		assertFalse(ClassNameTools.isVariablePrimitive(void.class.getName().toCharArray()));

		assertTrue(ClassNameTools.isVariablePrimitive(int.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isVariablePrimitive(float.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isVariablePrimitive(boolean.class.getName().toCharArray()));

		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.Number.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.String.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isVariablePrimitive(java.lang.Boolean.class.getName().toCharArray()));
	}

	public void testIsVariablePrimitiveWrapper() {
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Void.class.getName()));

		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Integer.class.getName()));
		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Float.class.getName()));
		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Boolean.class.getName()));

		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Number.class.getName()));
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.String.class.getName()));
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Object.class.getName()));
	}

	public void testIsVariablePrimitiveWrapperCharArray() {
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Void.class.getName().toCharArray()));

		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Integer.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Float.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Boolean.class.getName().toCharArray()));

		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Number.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.String.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isVariablePrimitiveWrapper(java.lang.Object.class.getName().toCharArray()));
	}

	public void testWrapperClassName() {
		assertEquals(java.lang.Void.class.getName(), ClassNameTools.primitiveWrapperClassName(void.class.getName()));
		assertEquals(java.lang.Integer.class.getName(), ClassNameTools.primitiveWrapperClassName(int.class.getName()));
		assertEquals(java.lang.Float.class.getName(), ClassNameTools.primitiveWrapperClassName(float.class.getName()));
		assertEquals(java.lang.Boolean.class.getName(), ClassNameTools.primitiveWrapperClassName(boolean.class.getName()));

		assertNull(ClassNameTools.primitiveWrapperClassName(java.lang.String.class.getName()));
	}

	public void testWrapperClassNameCharArray() {
		assertEquals(java.lang.Void.class.getName(), ClassNameTools.primitiveWrapperClassName(void.class.getName().toCharArray()));
		assertEquals(java.lang.Integer.class.getName(), ClassNameTools.primitiveWrapperClassName(int.class.getName().toCharArray()));
		assertEquals(java.lang.Float.class.getName(), ClassNameTools.primitiveWrapperClassName(float.class.getName().toCharArray()));
		assertEquals(java.lang.Boolean.class.getName(), ClassNameTools.primitiveWrapperClassName(boolean.class.getName().toCharArray()));

		assertNull(ClassNameTools.primitiveWrapperClassName(java.lang.String.class.getName().toCharArray()));
	}

	public void testPrimitiveClassName() {
		assertEquals(void.class.getName(), ClassNameTools.primitiveClassName(java.lang.Void.class.getName()));
		assertEquals(int.class.getName(), ClassNameTools.primitiveClassName(java.lang.Integer.class.getName()));
		assertEquals(float.class.getName(), ClassNameTools.primitiveClassName(java.lang.Float.class.getName()));
		assertEquals(boolean.class.getName(), ClassNameTools.primitiveClassName(java.lang.Boolean.class.getName()));

		assertNull(ClassNameTools.primitiveClassName(java.lang.String.class.getName()));
	}
	
	public void testPrimitiveClassNameCharArray() {
		assertEquals(void.class.getName(), ClassNameTools.primitiveClassName(java.lang.Void.class.getName().toCharArray()));
		assertEquals(int.class.getName(), ClassNameTools.primitiveClassName(java.lang.Integer.class.getName().toCharArray()));
		assertEquals(float.class.getName(), ClassNameTools.primitiveClassName(java.lang.Float.class.getName().toCharArray()));
		assertEquals(boolean.class.getName(), ClassNameTools.primitiveClassName(java.lang.Boolean.class.getName().toCharArray()));

		assertNull(ClassNameTools.primitiveClassName(java.lang.String.class.getName().toCharArray()));
	}
	
	public void testIsAutoboxEquivalent() {
		assertTrue(ClassNameTools.isAutoboxEquivalent(Integer.class.getName(), Integer.class.getName()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(int.class.getName(), Integer.class.getName()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(Integer.class.getName(), int.class.getName()));
		assertFalse(ClassNameTools.isAutoboxEquivalent(int.class.getName(), Boolean.class.getName()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(String.class.getName(), String.class.getName()));
	}
	
	public void testIsAutoboxEquivalentCharArray() {
		assertTrue(ClassNameTools.isAutoboxEquivalent(Integer.class.getName().toCharArray(), Integer.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(int.class.getName().toCharArray(), Integer.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(Integer.class.getName().toCharArray(), int.class.getName().toCharArray()));
		assertFalse(ClassNameTools.isAutoboxEquivalent(int.class.getName().toCharArray(), Boolean.class.getName().toCharArray()));
		assertTrue(ClassNameTools.isAutoboxEquivalent(String.class.getName().toCharArray(), String.class.getName().toCharArray()));
	}
	
	public void testForCode() {
		assertEquals("byte", ClassNameTools.forCode('B'));
		assertEquals("char", ClassNameTools.forCode('C'));
		assertEquals("double", ClassNameTools.forCode('D'));
		assertEquals("float", ClassNameTools.forCode('F'));
		assertEquals("int", ClassNameTools.forCode('I'));
		assertEquals("long", ClassNameTools.forCode('J'));
		assertEquals("short", ClassNameTools.forCode('S'));
		assertEquals("boolean", ClassNameTools.forCode('Z'));
		assertEquals("void", ClassNameTools.forCode('V'));

		assertNull(ClassNameTools.forCode('X'));

		assertEquals("byte", ClassNameTools.forCode((int) 'B'));
		assertEquals("char", ClassNameTools.forCode((int) 'C'));
		assertEquals("double", ClassNameTools.forCode((int) 'D'));
		assertEquals("float", ClassNameTools.forCode((int) 'F'));
		assertEquals("int", ClassNameTools.forCode((int) 'I'));
		assertEquals("long", ClassNameTools.forCode((int) 'J'));
		assertEquals("short", ClassNameTools.forCode((int) 'S'));
		assertEquals("boolean", ClassNameTools.forCode((int) 'Z'));
		assertEquals("void", ClassNameTools.forCode((int) 'V'));

		assertNull(ClassNameTools.forCode((int) 'X'));
	}

	public void testForCodeCharArray() {
		assertEquals("byte", ClassNameTools.forCodeCharArray('B'));
		assertEquals("char", ClassNameTools.forCodeCharArray('C'));
		assertEquals("double", ClassNameTools.forCodeCharArray('D'));
		assertEquals("float", ClassNameTools.forCodeCharArray('F'));
		assertEquals("int", ClassNameTools.forCodeCharArray('I'));
		assertEquals("long", ClassNameTools.forCodeCharArray('J'));
		assertEquals("short", ClassNameTools.forCodeCharArray('S'));
		assertEquals("boolean", ClassNameTools.forCodeCharArray('Z'));
		assertEquals("void", ClassNameTools.forCodeCharArray('V'));

		assertNull(ClassNameTools.forCodeCharArray('X'));

		assertEquals("byte", ClassNameTools.forCodeCharArray((int) 'B'));
		assertEquals("char", ClassNameTools.forCodeCharArray((int) 'C'));
		assertEquals("double", ClassNameTools.forCodeCharArray((int) 'D'));
		assertEquals("float", ClassNameTools.forCodeCharArray((int) 'F'));
		assertEquals("int", ClassNameTools.forCodeCharArray((int) 'I'));
		assertEquals("long", ClassNameTools.forCodeCharArray((int) 'J'));
		assertEquals("short", ClassNameTools.forCodeCharArray((int) 'S'));
		assertEquals("boolean", ClassNameTools.forCodeCharArray((int) 'Z'));
		assertEquals("void", ClassNameTools.forCodeCharArray((int) 'V'));

		assertNull(ClassNameTools.forCodeCharArray((int) 'X'));
	}

	public void testPrimitiveClassCode() {
		assertEquals('I', ClassNameTools.primitiveClassCode(int.class.getName()));
		assertEquals('I', ClassNameTools.primitiveClassCode("int"));
		assertEquals('B', ClassNameTools.primitiveClassCode(byte.class.getName()));
		assertEquals('B', ClassNameTools.primitiveClassCode("byte"));

		assertEquals((char) 0, ClassNameTools.primitiveClassCode(java.lang.Object.class.getName()));
	}

	public void testPrimitiveClassCodeCharArray() {
		assertEquals('I', ClassNameTools.primitiveClassCode(int.class.getName().toCharArray()));
		assertEquals('I', ClassNameTools.primitiveClassCode("int".toCharArray()));
		assertEquals('B', ClassNameTools.primitiveClassCode(byte.class.getName().toCharArray()));
		assertEquals('B', ClassNameTools.primitiveClassCode("byte".toCharArray()));

		assertEquals((char) 0, ClassNameTools.primitiveClassCode(java.lang.Object.class.getName().toCharArray()));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ClassNameTools.class);
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

	public static void assertEquals(char[] a1, char[] a2) {
		assertTrue(Arrays.equals(a1, a2));
	}
}
