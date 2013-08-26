/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal;

import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.MapEntryAssociation;

@SuppressWarnings("nls")
public class MapEntryAssociationTests
	extends TestCase
{
	private MapEntryAssociation<String, String> assoc;

	public MapEntryAssociationTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.assoc = this.<String, String>buildMapEntryAssociation("foo", "bar");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetKey() {
		assertEquals("foo", this.assoc.getKey());
	}

	public void testGetValue() {
		assertEquals("bar", this.assoc.getValue());
	}

	public void testSetValue() {
		assertEquals("bar", this.assoc.getValue());
		this.assoc.setValue("baz");
		assertEquals("baz", this.assoc.getValue());
	}

	public void testEquals() {
		assertFalse(this.assoc.equals("foo"));

		assertEquals(this.assoc, this.copy(this.assoc));

		MapEntryAssociation<String, String> assoc2 = this.<String, String>buildMapEntryAssociation("foo", "baz");
		assertFalse(this.assoc.equals(assoc2));

		assoc2 = this.<String, String>buildMapEntryAssociation("fop", "bar");
		assertFalse(this.assoc.equals(assoc2));

		MapEntryAssociation<String, String> assoc3 = this.<String, String>buildMapEntryAssociation(null, null);
		MapEntryAssociation<String, String> assoc4 = this.<String, String>buildMapEntryAssociation(null, null);
		assertEquals(assoc3, assoc4);
	}

	public void testHashCode() {
		assertEquals(this.assoc.hashCode(), this.copy(this.assoc).hashCode());

		MapEntryAssociation<String, String> assoc2 = this.<String, String>buildMapEntryAssociation(null, null);
		assertEquals(assoc2.hashCode(), this.copy(assoc2).hashCode());
	}

	public void testToString() {
		assertNotNull(this.assoc.toString());
	}

	public void testClone() {
		this.verifyClone(this.assoc, this.assoc.clone());
	}

	private void verifyClone(Association<String, String> expected, Association<String, String> actual) {
		assertEquals(expected, actual);
		assertNotSame(expected, actual);
		assertEquals(expected.getKey(), actual.getKey());
		assertSame(expected.getKey(), actual.getKey());
		assertEquals(expected.getValue(), actual.getValue());
		assertSame(expected.getValue(), actual.getValue());
	}

	private MapEntryAssociation<String, String> copy(MapEntryAssociation<String, String> mea) {
		return this.buildMapEntryAssociation(mea.getKey(), mea.getValue());
	}

	private <K, V> MapEntryAssociation<K, V> buildMapEntryAssociation(K key, V value) {
		HashMap<K, V> map = new HashMap<K, V>();
		map.put(key, value);
		Map.Entry<K, V> entry = map.entrySet().iterator().next();
		return new MapEntryAssociation<K, V>(entry);
	}
}
