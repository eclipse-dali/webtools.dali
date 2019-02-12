/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import org.eclipse.jpt.common.utility.Pair;
import org.eclipse.jpt.common.utility.internal.AbstractPair;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class AbstractPairTests
	extends TestCase
{
	public AbstractPairTests(String name) {
		super(name);
	}

	public void testEquals1() {
		Pair<String, String> pair1 = new LocalPair("foo", "bar");
		Pair<String, String> pair2 = new LocalPair("foo", "bar");
		assertEquals(pair1, pair2);
	}

	public void testEquals1a() {
		Pair<String, String> pair1 = new LocalPair("foo", null);
		Pair<String, String> pair2 = new LocalPair("foo", null);
		assertEquals(pair1, pair2);
	}

	public void testEquals1b() {
		Pair<String, String> pair1 = new LocalPair(null, "bar");
		Pair<String, String> pair2 = new LocalPair(null, "bar");
		assertEquals(pair1, pair2);
	}

	public void testEquals2() {
		Pair<String, String> pair = new LocalPair("foo", "bar");
		assertFalse(pair.equals("foo"));
	}

	public void testEquals3() {
		Pair<String, String> pair1 = new LocalPair("foo", "bar");
		Pair<String, String> pair2 = new LocalPair("bar", "foo");
		assertFalse(pair1.equals(pair2));
	}

	public void testEquals4() {
		Pair<String, String> pair1 = new LocalPair("foo", "bar");
		Pair<String, String> pair2 = new LocalPair("foo", "foo");
		assertFalse(pair1.equals(pair2));
	}

	public void testEquals5() {
		Pair<String, String> pair1 = new LocalPair("foo", "bar");
		Pair<String, String> pair2 = new LocalPair(null, "bar");
		assertFalse(pair1.equals(pair2));
	}

	public void testEquals6() {
		Pair<String, String> pair1 = new LocalPair("foo", "bar");
		Pair<String, String> pair2 = new LocalPair("foo", null);
		assertFalse(pair1.equals(pair2));
	}

	public void testEquals7() {
		Pair<String, String> pair1 = new LocalPair(null, "bar");
		Pair<String, String> pair2 = new LocalPair("foo", "bar");
		assertFalse(pair1.equals(pair2));
	}

	public void testEquals8() {
		Pair<String, String> pair1 = new LocalPair("foo", null);
		Pair<String, String> pair2 = new LocalPair("foo", "bar");
		assertFalse(pair1.equals(pair2));
	}

	public void testToHashCode1() {
		Pair<String, String> pair = new LocalPair("foo", "bar");
		assertEquals("foo".hashCode() ^ "bar".hashCode(), pair.hashCode());
	}

	public void testToHashCode2() {
		Pair<String, String> pair = new LocalPair(null, "bar");
		assertEquals(0 ^ "bar".hashCode(), pair.hashCode());
	}

	public void testToHashCode3() {
		Pair<String, String> pair = new LocalPair("foo", null);
		assertEquals("foo".hashCode() ^ 0, pair.hashCode());
	}

	public void testToString() {
		Pair<String, String> pair = new LocalPair("foo", "bar");
		assertEquals("foo|bar", pair.toString());
	}

	public static class LocalPair
		extends AbstractPair<String, String>
	{
		private final String left;
		private final String right;
		public LocalPair(String left, String right) {
			super();
			this.left = left;
			this.right = right;
		}
		public String getLeft() {
			return this.left;
		}
		public String getRight() {
			return this.right;
		}
	}
}
