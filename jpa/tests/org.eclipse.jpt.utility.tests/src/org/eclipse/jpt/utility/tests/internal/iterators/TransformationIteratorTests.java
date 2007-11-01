/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class TransformationIteratorTests extends TestCase {

	public TransformationIteratorTests(String name) {
		super(name);
	}

	public void testHasNext() {
		int i = 0;
		for (Iterator<Integer> stream = this.buildIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(8, i);
	}

	public void testHasNextUpcast() {
		int i = 0;
		for (Iterator<Object> stream = this.buildIteratorUpcast(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(8, i);
	}

	public void testInnerHasNext() {
		int i = 0;
		for (Iterator<Integer> stream = this.buildInnerIterator(); stream.hasNext();) {
			stream.next();
			i++;
		}
		assertEquals(8, i);
	}

	public void testNext() {
		int i = 0;
		for (Iterator<Integer> stream = this.buildIterator(); stream.hasNext();) {
			assertEquals("bogus transformation", ++i, stream.next().intValue());
		}
	}

	public void testNextUpcast() {
		int i = 0;
		for (Iterator<Object> stream = this.buildIteratorUpcast(); stream.hasNext();) {
			assertEquals("bogus transformation", ++i, ((Integer) stream.next()).intValue());
		}
	}

	public void testInnerNext() {
		int i = 0;
		for (Iterator<Integer> stream = this.buildInnerIterator(); stream.hasNext();) {
			assertEquals("bogus transformation", ++i, stream.next().intValue());
		}
	}

	public void testRemove() {
		Collection<String> c = this.buildCollection();
		for (Iterator<Integer> stream = this.buildInnerTransformationIterator(c.iterator()); stream.hasNext();) {
			if (stream.next().intValue() == 3) {
				stream.remove();
			}
		}
		assertEquals("nothing removed", this.buildCollection().size() - 1, c.size());
		assertFalse("element still in collection", c.contains("333"));
		assertTrue("wrong element removed", c.contains("22"));
	}

	public void testInnerRemove() {
		Collection<String> c = this.buildCollection();
		for (Iterator<Integer> stream = this.buildTransformationIterator(c.iterator(), this.buildTransformer()); stream.hasNext();) {
			if (stream.next().intValue() == 3) {
				stream.remove();
			}
		}
		assertEquals("nothing removed", this.buildCollection().size() - 1, c.size());
		assertFalse("element still in collection", c.contains("333"));
		assertTrue("wrong element removed", c.contains("22"));
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		Iterator<Integer> stream = this.buildIterator();
		Integer integer = null;
		while (stream.hasNext()) {
			integer = stream.next();
		}
		try {
			integer = stream.next();
		} catch (NoSuchElementException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown: " + integer, exCaught);
	}

	public void testUnsupportedOperationException() {
		boolean exCaught = false;
		for (Iterator<Integer> stream = this.buildUnmodifiableIterator(); stream.hasNext();) {
			int i = stream.next().intValue();
			if (i == 3) {
				try {
					stream.remove();
				} catch (UnsupportedOperationException ex) {
					exCaught = true;
				}
			}
		}
		assertTrue("UnsupportedOperationException not thrown", exCaught);
	}

	public void testIllegalStateException() {
		boolean exCaught = false;
		try {
			this.buildIterator().remove();
		} catch (IllegalStateException ex) {
			exCaught = true;
		}
		assertTrue("IllegalStateException not thrown", exCaught);
	}

	private Iterator<Integer> buildIterator() {
		return this.buildTransformationIterator(this.buildNestedIterator(), this.buildTransformer());
	}

	private Iterator<Object> buildIteratorUpcast() {
		return this.buildTransformationIteratorUpcast(this.buildNestedIterator(), this.buildTransformerUpcast());
	}

	private Iterator<Integer> buildInnerIterator() {
		return this.buildInnerTransformationIterator(this.buildNestedIterator());
	}

	private Iterator<Integer> buildUnmodifiableIterator() {
		return this.buildTransformationIterator(this.buildUnmodifiableNestedIterator(), this.buildTransformer());
	}

	private Iterator<Integer> buildTransformationIterator(Iterator<String> nestedIterator, Transformer<String, Integer> transformer) {
		return new TransformationIterator<String, Integer>(nestedIterator, transformer);
	}

	private Iterator<Object> buildTransformationIteratorUpcast(Iterator<String> nestedIterator, Transformer<Object, Integer> transformer) {
		return new TransformationIterator<Object, Object>(nestedIterator, transformer);
	}

	private Transformer<String, Integer> buildTransformer() {
		// transform each string into an integer with a value of the string's length
		return new Transformer<String, Integer>() {
			public Integer transform(String next) {
				return new Integer(next.length());
			}
		};
	}

	private Transformer<Object, Integer> buildTransformerUpcast() {
		// transform each string into an integer with a value of the string's length
		return new Transformer<Object, Integer>() {
			public Integer transform(Object next) {
				return new Integer(((String) next).length());
			}
		};
	}

	private Iterator<Integer> buildInnerTransformationIterator(Iterator<String> nestedIterator) {
		// transform each string into an integer with a value of the string's length
		return new TransformationIterator<String, Integer>(nestedIterator) {
			@Override
			protected Integer transform(String next) {
				return new Integer(next.length());
			}
		};
	}

	private Iterator<String> buildNestedIterator() {
		return this.buildCollection().iterator();
	}

	private Iterator<String> buildUnmodifiableNestedIterator() {
		return this.buildUnmodifiableCollection().iterator();
	}

	private Collection<String> buildCollection() {
		Collection<String> c = new ArrayList<String>();
		c.add("1");
		c.add("22");
		c.add("333");
		c.add("4444");
		c.add("55555");
		c.add("666666");
		c.add("7777777");
		c.add("88888888");
		return c;
	}

	private Collection<String> buildUnmodifiableCollection() {
		return Collections.unmodifiableCollection(this.buildCollection());
	}

	public void testInvalidTransformationIterator() {
		// missing method override
		Iterator<Integer> iterator = new TransformationIterator<String, Integer>(this.buildCollection().iterator());
		boolean exCaught = false;
		try {
			Integer integer = iterator.next();
			fail("invalid integer: " + integer);
		} catch (UnsupportedOperationException ex) {
			exCaught = true;
		}
		assertTrue("NoSuchElementException not thrown", exCaught);
	}

}
