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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationListIterator;

public class TransformationListIteratorTests extends TestCase {
	public TransformationListIteratorTests(String name) {
		super(name);
	}

	public void testHasNextAndHasPrevious() {
		int i = 0;
		ListIterator<Integer> stream = this.buildIterator();

		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(8, i);

		while (stream.hasPrevious()) {
			stream.previous();
			i--;
		}
		assertEquals(0, i);
	}

	public void testHasNextAndHasPreviousUpcast() {
		int i = 0;
		ListIterator<Object> stream = this.buildIteratorUpcast();

		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(8, i);

		while (stream.hasPrevious()) {
			stream.previous();
			i--;
		}
		assertEquals(0, i);
	}

	public void testInnerHasNextAndHasPrevious() {
		int i = 0;
		ListIterator<Integer> stream = this.buildInnerIterator();

		while (stream.hasNext()) {
			stream.next();
			i++;
		}
		assertEquals(8, i);

		while (stream.hasPrevious()) {
			stream.previous();
			i--;
		}
		assertEquals(0, i);
	}

	public void testNextAndPrevious() {
		int i = 0;
		ListIterator<Integer> stream = this.buildIterator();

		while (stream.hasNext()) {
			assertEquals(++i, stream.next().intValue());
		}

		++i;

		while (stream.hasPrevious()) {
			assertEquals(--i, stream.previous().intValue());
		}
	}

	public void testInnerNextAndPrevious() {
		int i = 0;
		ListIterator<Integer> stream = this.buildInnerIterator();

		while (stream.hasNext()) {
			assertEquals(++i, stream.next().intValue());
		}

		++i;

		while (stream.hasPrevious()) {
			assertEquals(--i, stream.previous().intValue());
		}
	}

	public void testNextIndexAndPreviousIndex() {
		int i = -1;
		ListIterator<Integer> stream = this.buildIterator();

		while (stream.hasNext()) {
			assertEquals(++i, stream.nextIndex());
			stream.next();
		}

		++i;

		while (stream.hasPrevious()) {
			assertEquals(--i, stream.previousIndex());
			stream.previous();
		}
	}

	public void testInnerNextIndexAndPreviousIndex() {
		int i = -1;
		ListIterator<Integer> stream = this.buildInnerIterator();

		while (stream.hasNext()) {
			assertEquals(++i, stream.nextIndex());
			stream.next();
		}

		++i;

		while (stream.hasPrevious()) {
			assertEquals(--i, stream.previousIndex());
			stream.previous();
		}
	}

	public void testRemove() {
		List<String> l = this.buildList();
		for (ListIterator<Integer> stream = this.buildInnerTransformationListIterator(l.listIterator()); stream.hasNext();) {
			if (stream.next().intValue() == 3) {
				stream.remove();
			}
		}
		assertEquals("nothing removed", this.buildList().size() - 1, l.size());
		assertFalse("element still in list", l.contains("333"));
		assertTrue("wrong element removed", l.contains("22"));
	}

	public void testInnerRemove() {
		List<String> l = this.buildList();
		for (ListIterator<Integer> stream = this.buildTransformationListIterator(l.listIterator(), this.buildTransformer()); stream.hasNext();) {
			if (stream.next().intValue() == 3) {
				stream.remove();
			}
		}
		assertEquals("nothing removed", this.buildList().size() - 1, l.size());
		assertFalse("element still in list", l.contains("333"));
		assertTrue("wrong element removed", l.contains("22"));
	}

	public void testUnsupportedOperationExceptionOnAdd() {
		ListIterator<Integer> stream = this.buildIterator();
		boolean exCaught = false;
		try {
			stream.add(new Integer(0));
			fail("exception not thrown");
		} catch (UnsupportedOperationException e) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testUnsupportedOperationExceptionOnSet() {
		ListIterator<Integer> stream = this.buildIterator();
		boolean exCaught = false;
		try {
			stream.set(new Integer(0));
			fail("exception not thrown");
		} catch (UnsupportedOperationException e) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testNoSuchElementException() {
		boolean exCaught = false;
		ListIterator<Integer> stream = this.buildIterator();
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

	private ListIterator<Integer> buildIterator() {
		return this.buildTransformationListIterator(this.buildNestedIterator(), this.buildTransformer());
	}

	private ListIterator<Object> buildIteratorUpcast() {
		return this.buildTransformationListIteratorUpcast(this.buildNestedIterator(), this.buildTransformerUpcast());
	}

	private ListIterator<Integer> buildInnerIterator() {
		return this.buildInnerTransformationListIterator(this.buildNestedIterator());
	}

	private ListIterator<Integer> buildUnmodifiableIterator() {
		return this.buildTransformationListIterator(this.buildUnmodifiableNestedIterator(), this.buildTransformer());
	}

	private ListIterator<Integer> buildTransformationListIterator(ListIterator<String> nestedIterator, Transformer<String, Integer> transformer) {
		return new TransformationListIterator<String, Integer>(nestedIterator, transformer);
	}

	private ListIterator<Object> buildTransformationListIteratorUpcast(ListIterator<String> nestedIterator, Transformer<Object, Integer> transformer) {
		return new TransformationListIterator<Object, Object>(nestedIterator, transformer);
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

	private ListIterator<Integer> buildInnerTransformationListIterator(ListIterator<String> nestedIterator) {
		// transform each string into an integer with a value of the string's length
		return new TransformationListIterator<String, Integer>(nestedIterator) {
			@Override
			protected Integer transform(String next) {
				return new Integer(next.length());
			}
		};
	}

	private ListIterator<String> buildNestedIterator() {
		return this.buildList().listIterator();
	}

	private ListIterator<String> buildUnmodifiableNestedIterator() {
		return this.buildUnmodifiableList().listIterator();
	}

	private List<String> buildList() {
		List<String> l = new ArrayList<String>();
		l.add("1");
		l.add("22");
		l.add("333");
		l.add("4444");
		l.add("55555");
		l.add("666666");
		l.add("7777777");
		l.add("88888888");
		return l;
	}

	private List<String> buildUnmodifiableList() {
		return Collections.unmodifiableList(this.buildList());
	}
}
