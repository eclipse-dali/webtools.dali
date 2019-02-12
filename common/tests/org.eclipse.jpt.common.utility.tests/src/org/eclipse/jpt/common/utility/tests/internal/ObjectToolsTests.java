/*******************************************************************************
 * Copyright (c) 2010, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
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

	public void testHashCode() {
		Object o = null;
		assertEquals(0, ObjectTools.hashCode(o));
		o = new Object();
		assertEquals(o.hashCode(), ObjectTools.hashCode(o));
	}

	public void testHashCodeNull() {
		Object o = null;
		assertEquals(-1, ObjectTools.hashCode(o, -1));
	}

	public void testChain() {
		Iterable<Class<?>> classes = ObjectTools.chain(Vector.class, SUPERCLASS_TRANSFORMER);
		assertEquals(4, IterableTools.size(classes));
	}
	public static final Transformer<Class<?>, Class<?>> SUPERCLASS_TRANSFORMER = new SuperClassTransformer();
	static class SuperClassTransformer
		extends TransformerAdapter<Class<?>, Class<?>>
	{
		@Override
		public Class<?> transform(Class<?> input) {
			return input.getSuperclass();
		}
	}

	public void testRepeat() {
		String s = "foo";
		List<String> strings = ObjectTools.repeat(s, 3);
		assertEquals(3, strings.size());
		assertEquals(s, strings.get(0));
		assertEquals(s, strings.get(1));
		assertEquals(s, strings.get(2));
	}

	public void testToStringObjectObject() {
		String s = ObjectTools.toString(this, Arrays.asList(new Object[] {"foo", "bar"}));
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("([foo, bar])"));
	}

	public void testToStringObjectObjectArray() {
		String s = ObjectTools.toString(this, new Object[] {"foo", "bar"});
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("([foo, bar])"));
	}

	public void testToStringObjectBoolean() {
		String s = ObjectTools.toString(this, false);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(false)"));
	}

	public void testToStringObjectChar() {
		String s = ObjectTools.toString(this, 'x');
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(x)"));
	}

	public void testToStringObjectCharArray() {
		String s = ObjectTools.toString(this, "foo".toCharArray());
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(foo)"));
	}

	public void testToStringObjectCharSequence() {
		String s = ObjectTools.toString(this, "foo");
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(foo)"));
	}

	public void testToStringObjectDouble() {
		String s = ObjectTools.toString(this, 77.77);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(77.77)"));
	}

	public void testToStringObjectFloat() {
		String s = ObjectTools.toString(this, 77.77f);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(77.77)"));
	}

	public void testToStringObjectInt() {
		String s = ObjectTools.toString(this, 77);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(77)"));
	}

	public void testToStringObjectLong() {
		String s = ObjectTools.toString(this, 77l);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("(77)"));
	}

	public void testToStringObject() {
		String s = ObjectTools.toString(this);
		assertTrue(s.startsWith(this.getClass().getSimpleName()));
		assertTrue(s.endsWith("]"));
	}

	public void testIdentityToStringObject() {
		Object o = new Object();
		assertEquals(o.toString(), ObjectTools.identityToString(o));
	}

	public void testSingletonToStringObject() {
		assertEquals(this.getClass().getSimpleName(), ObjectTools.singletonToString(this));
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
		Vector<?> v = new Vector<>(initialCapacity);
		Object[] elementData = (Object[]) ObjectTools.get(v, "elementData");
		assertEquals(initialCapacity, elementData.length);

		// test inherited field
		Integer modCountInteger = (Integer) ObjectTools.get(v, "modCount");
		int modCount = modCountInteger.intValue();
		assertEquals(0, modCount);
	}

	public void testGet_exception() {
		boolean exCaught = false;
		try {
			Object value = ObjectTools.get(new Vector<String>(), "bogusField");
			fail("bogus: " + value);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testSet() {
		Vector<String> v = new Vector<>();
		Object[] newElementData = new Object[5];
		newElementData[0] = "foo";
		ObjectTools.set(v, "elementData", newElementData);
		ObjectTools.set(v, "elementCount", new Integer(1));
		// test inherited field
		ObjectTools.set(v, "modCount", new Integer(1));
		assertTrue(v.contains("foo"));
	}

	public void testSet_exception() {
		Vector<String> v = new Vector<>();
		Object[] newElementData = new Object[5];
		newElementData[0] = "foo";

		boolean exCaught = false;
		try {
			ObjectTools.set(v, "bogusField", "foo");
			fail();
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testField() {
		assertNotNull(ObjectTools.field(this, "baz"));
	}
	public String baz;

	public void testField_exception() {
		boolean exCaught = false;
		try {
			Field field = ObjectTools.field(this, "BOGUS");
			fail("bogus: " + field);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchFieldException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testExecuteObjectString() {
		Vector<String> v = new Vector<>();
		int size = ((Integer) ObjectTools.execute(v, "size")).intValue();
		assertEquals(0, size);

		v.addElement("foo");
		size = ((Integer) ObjectTools.execute(v, "size")).intValue();
		assertEquals(1, size);
	}

	public void testExecuteObjectString_() throws Exception {
		Vector<String> v = new Vector<>();
		int size = ((Integer) ObjectTools.execute_(v, "size")).intValue();
		assertEquals(0, size);

		v.addElement("foo");
		size = ((Integer) ObjectTools.execute(v, "size")).intValue();
		assertEquals(1, size);
	}

	public void testExecuteObjectStringClassObject() {
		Vector<String> v = new Vector<>();
		boolean booleanResult = ((Boolean) ObjectTools.execute(v, "add", Object.class, "foo")).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));
		Object voidResult = ObjectTools.execute(v, "addElement", Object.class, "bar");
		assertNull(voidResult);
	}

	public void testExecuteObjectStringClassObject_() throws Exception {
		Vector<String> v = new Vector<>();
		boolean booleanResult = ((Boolean) ObjectTools.execute_(v, "add", Object.class, "foo")).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));
		Object voidResult = ObjectTools.execute(v, "addElement", Object.class, "bar");
		assertNull(voidResult);
	}

	public void testExecuteObjectStringClassArrayObjectArray() {
		Vector<String> v = new Vector<>();
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = java.lang.Object.class;
		Object[] args = new Object[1];
		args[0] = "foo";
		boolean booleanResult = ((Boolean) ObjectTools.execute(v, "add", parmTypes, args)).booleanValue();
		assertTrue(booleanResult);
		assertTrue(v.contains("foo"));
	}

	public void testExecuteObjectStringClassArrayObjectArray_exception() {
		Vector<String> v = new Vector<>();
		Class<?>[] parmTypes = new Class[1];
		parmTypes[0] = java.lang.Object.class;
		Object[] args = new Object[1];
		args[0] = "foo";
		boolean exCaught = false;
		try {
			Object value = ObjectTools.execute(v, "bogusMethod", parmTypes, args);
			fail("bogus: " + value);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof NoSuchMethodException) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testMethod() {
		assertNotNull(ObjectTools.method(this, "testMethod"));
	}

	public void testMethod_() throws Exception {
		assertNotNull(ObjectTools.method_(this, "testMethod"));
	}

	public void testMethodWithParm() {
		assertNotNull(ObjectTools.method(this, "methodWithParm", String.class));
	}

	public void testMethodWithParm_() throws Exception {
		assertNotNull(ObjectTools.method_(this, "methodWithParm", String.class));
	}
	public void methodWithParm(String string) {
		assertNotNull(string);
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object at = ClassTools.newInstance(ObjectTools.class);
			fail("bogus: " + at);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}
}
