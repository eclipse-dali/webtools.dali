/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.exception.CollectingExceptionHandler;
import org.eclipse.jpt.common.utility.internal.factory.FactoryTools;
import org.eclipse.jpt.common.utility.internal.factory.FactoryWrapper;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

@SuppressWarnings("nls")
public class FactoryTests
	extends TestCase
{
	public FactoryTests(String name) {
		super(name);
	}

	public void testIteratorFactory() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("zero");
		list.add("one");
		list.add("two");
		list.add("three");
		Factory<String> factory = FactoryTools.adapt(list);

		for (int i = 0; i < list.size(); i++) {
			assertEquals(factory.create(), list.get(i));
		}
		assertNotNull(factory.toString());
	}

	public void testFactoryWrapper() {
		Factory<String> factory1 = FactoryTools.staticFactory("foo");
		Factory<String> factory2 = FactoryTools.staticFactory("bar");
		FactoryWrapper<String> wrapper = FactoryTools.wrap(factory1);

		assertEquals(wrapper.create(), "foo");
		wrapper.setFactory(factory2);
		assertEquals(wrapper.create(), "bar");
		wrapper.setFactory(factory1);
		assertEquals(wrapper.create(), "foo");

		assertNotNull(wrapper.toString());
	}

	public void testCastingFactory() {
		ArrayList<String> list = new ArrayList<String>();
		Factory<List<String>> factory1 = FactoryTools.<List<String>>staticFactory(list);
		Factory<RandomAccess> factory2 = FactoryTools.cast(factory1);

		RandomAccess ra = factory2.create();
		assertEquals(list, ra);

		assertNotNull(factory2.toString());
	}

	public void testDowncastingFactory() {
		ArrayList<String> list = new ArrayList<String>();
		Factory<List<String>> factory1 = FactoryTools.<List<String>>staticFactory(list);
		Factory<ArrayList<String>> factory2 = FactoryTools.downcast(factory1);

		ArrayList<String> actual = factory2.create();
		assertEquals(list, actual);

		assertNotNull(factory2.toString());
	}

	public void testUpcastingFactory() {
		ArrayList<String> list = new ArrayList<String>();
		Factory<List<String>> factory1 = FactoryTools.<List<String>>staticFactory(list);
		Factory<Object> factory2 = FactoryTools.upcast(factory1);

		Object actual = factory2.create();
		assertEquals(list, actual);

		assertNotNull(factory2.toString());
	}

	public void testSafeFactory() {
		CollectingExceptionHandler exHandler = new CollectingExceptionHandler();
		Factory<String> factory1 = FactoryTools.disabledFactory();
		String foo = "foo";
		Factory<String> factory2 = FactoryTools.safe(factory1, exHandler, foo);

		String actual = factory2.create();
		assertEquals(foo, actual);
		assertEquals(1, IterableTools.size(exHandler.getExceptions()));
		assertTrue(IterableTools.first(exHandler.getExceptions()) instanceof UnsupportedOperationException);

		assertNotNull(factory2.toString());
	}

	public void testCloneFactory() {
		StringRef ref = new StringRef("foo");
		Factory<StringRef> factory = FactoryTools.cloneFactory(ref);

		StringRef clone = factory.create();
		assertEquals(ref, clone);
		assertNotSame(ref, clone);

		assertNotNull(factory.toString());
	}

	public void testInstantiationFactory() {
		StringRef ref1 = new StringRef();
		Factory<StringRef> factory = FactoryTools.instantiationFactory(StringRef.class);

		StringRef ref2 = factory.create();
		assertEquals(ref1, ref2);
		assertNotSame(ref1, ref2);

		assertNotNull(factory.toString());
	}

	public void testInstantiationFactory_Args() {
		StringRef ref1 = new StringRef("foo");
		Factory<StringRef> factory = FactoryTools.instantiationFactory(StringRef.class, String.class, "foo");

		StringRef ref2 = factory.create();
		assertEquals(ref1, ref2);
		assertNotSame(ref1, ref2);

		assertNotNull(factory.toString());
	}

	public void testStaticFieldFactory() {
		Factory<String> factory = FactoryTools.get(StringRef.class, "DEFAULT");

		String s = factory.create();
		assertEquals(StringTools.EMPTY_STRING, s);

		assertNotNull(factory.toString());
	}

	public void testStaticMethodFactory() {
		Factory<String> factory = FactoryTools.execute(StringRef.class, "defaultString");

		String s = factory.create();
		assertEquals(StringTools.EMPTY_STRING, s);

		assertNotNull(factory.toString());
	}

	public void testDisabledFactory() {
		Factory<String> factory = FactoryTools.disabledFactory();

		boolean exCaught = false;
		try {
			String s = factory.create();
			fail("bogus value: " + s);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		assertNotNull(factory.toString());
	}

	public void testNullFactory() {
		Factory<String> factory = FactoryTools.nullFactory();

		String s = factory.create();
		assertNull(s);

		assertNotNull(factory.toString());
	}

	public void testStaticFactory() {
		Factory<String> factory = FactoryTools.staticFactory("foo");

		String s = factory.create();
		assertEquals("foo", s);

		s = factory.create();
		assertEquals("foo", s);

		assertNotNull(factory.toString());
	}


	// ********** string ref **********

	public static class StringRef
		implements Cloneable
	{
		private final String string;
		private static final String DEFAULT = StringTools.EMPTY_STRING;
		public static String defaultString() {
			return DEFAULT;
		}
		public StringRef() {
			this(DEFAULT);
		}
		public StringRef(String string) {
			super();
			if (string == null) {
				throw new NullPointerException();
			}
			this.string = string;
		}
		public String getString() {
			return this.string;
		}
		@Override
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException ex) {
				throw new RuntimeException(ex);
			}
		}
		@Override
		public boolean equals(Object obj) {
			if ( ! (obj instanceof StringRef)) {
				return false;
			}
			return this.string.equals(((StringRef) obj).string);
		}
		@Override
		public int hashCode() {
			return this.string.hashCode();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.string);
		}
	}
}
