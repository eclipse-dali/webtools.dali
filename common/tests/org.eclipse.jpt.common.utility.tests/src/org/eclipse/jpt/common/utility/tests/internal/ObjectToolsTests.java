/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ObjectToolsTests
	extends TestCase
{
	public ObjectToolsTests(String name) {
		super(name);
	}

	public void testEquals1() {
		assertTrue(ObjectTools.equals(null, null));
	}

	public void testEquals2() {
		assertFalse(ObjectTools.equals(null, "foo"));
	}

	public void testEquals3() {
		assertFalse(ObjectTools.equals("foo", null));
	}

	public void testEquals4() {
		assertTrue(ObjectTools.equals("foo", "foo"));
	}

	public void testEquals5() {
		assertFalse(ObjectTools.equals("foo", "bar"));
	}

	public void testNotEquals1() {
		assertFalse(ObjectTools.notEquals(null, null));
	}

	public void testNotEquals2() {
		assertTrue(ObjectTools.notEquals(null, "foo"));
	}

	public void testNotEquals3() {
		assertTrue(ObjectTools.notEquals("foo", null));
	}

	public void testNotEquals4() {
		assertFalse(ObjectTools.notEquals("foo", "foo"));
	}

	public void testNotEquals5() {
		assertTrue(ObjectTools.notEquals("foo", "bar"));
	}

	public void testToStringName_anonymous() {
		Object o = new Object(){/*anonymous subclass of Object*/};
		assertEquals("Object", ClassTools.toStringName(o.getClass()));
	}

	public void testToStringName_member() {
		assertEquals("Map.Entry", ClassTools.toStringName(java.util.Map.Entry.class));
	}

	public void testToStringName_local() {
		class Foo {
			Bar bar = new Bar();
			class Bar {
				Bar() {
					super();
				}
			}
			Foo() {
				super();
			}
		}
		Foo foo = new Foo();
		assertEquals("ObjectToolsTests.Foo", ClassTools.toStringName(foo.getClass()));
		assertEquals("ObjectToolsTests.Foo.Bar", ClassTools.toStringName(foo.bar.getClass()));
	}

	public void testGet() {
		int initialCapacity = 200;
		Vector<?> v = new Vector<Object>(initialCapacity);
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);

		// test inherited field
		Integer modCountInteger = (Integer) ObjectTools.get(v, "modCount");
		int modCount = modCountInteger.intValue();
		assertEquals(0, modCount);

		boolean exCaught = false;
		Object bogusFieldValue = null;
		try {
			bogusFieldValue = ObjectTools.get(v, "bogusField");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchFieldException not thrown: " + bogusFieldValue, exCaught);
	}

	public void testExecuteObjectString() {
		Vector<String> v = new Vector<String>();
		int size = ((Integer) ObjectTools.execute(v, "size")).intValue();
		assertEquals(0, size);

		v.addElement("foo");
		size = ((Integer) ObjectTools.execute(v, "size")).intValue();
		assertEquals(1, size);
	}

	public void testExecuteObjectStringClassObject() {
		Vector<String> v = new Vector<String>();
		boolean booleanResult = ((Boolean) ObjectTools.execute(v, "add", Object.class, "foo")).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));
		Object voidResult = ObjectTools.execute(v, "addElement", Object.class, "bar");
		assertNull(voidResult);
	}

	public void testExecuteObjectStringClassArrayObjectArray() {
		Vector<String> v = new Vector<String>();
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = java.lang.Object.class;
		Object[] args = new Object[1];
		args[0] = "foo";
		boolean booleanResult = ((Boolean) ObjectTools.execute(v, "add", parmTypes, args)).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));

		boolean exCaught = false;
		Object bogusMethodReturnValue = null;
		try {
			bogusMethodReturnValue = ObjectTools.execute(v, "bogusMethod", parmTypes, args);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchMethodException not thrown: " + bogusMethodReturnValue, exCaught);
	}

	public void testSet() {
		Vector<String> v = new Vector<String>();
		Object[] newElementData = new Object[5];
		newElementData[0] = "foo";
		ObjectTools.set(v, "elementData", newElementData);
		ObjectTools.set(v, "elementCount", new Integer(1));
		// test inherited field
		ObjectTools.set(v, "modCount", new Integer(1));
		assertTrue(v.contains("foo"));

		boolean exCaught = false;
		try {
			ObjectTools.set(v, "bogusField", "foo");
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue("NoSuchFieldException not thrown", exCaught);
	}
}
