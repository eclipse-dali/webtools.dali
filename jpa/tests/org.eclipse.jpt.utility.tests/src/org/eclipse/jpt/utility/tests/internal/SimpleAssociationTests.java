/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.eclipse.jpt.utility.internal.Association;
import org.eclipse.jpt.utility.internal.SimpleAssociation;

@SuppressWarnings("nls")
public class SimpleAssociationTests extends TestCase {
	private SimpleAssociation<String, String> assoc;

	public static Test suite() {
		return new TestSuite(SimpleAssociationTests.class);
	}
	
	public SimpleAssociationTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.assoc = new SimpleAssociation<String, String>("foo", "bar");
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

		SimpleAssociation<String, String> assoc2 = new SimpleAssociation<String, String>("foo", "baz");
		assertFalse(this.assoc.equals(assoc2));

		assoc2 = new SimpleAssociation<String, String>("fop", "bar");
		assertFalse(this.assoc.equals(assoc2));

		SimpleAssociation<String, String> assoc3 = new SimpleAssociation<String, String>(null, null);
		SimpleAssociation<String, String> assoc4 = new SimpleAssociation<String, String>(null, null);
		assertEquals(assoc3, assoc4);
	}

	public void testHashCode() {
		assertEquals(this.assoc.hashCode(), this.copy(this.assoc).hashCode());

		SimpleAssociation<String, String> assoc2 = new SimpleAssociation<String, String>(null, null);
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

	public void testSerialization() throws Exception {
		@SuppressWarnings("cast")
		Association<String, String> assoc2 = (Association<String, String>) TestTools.serialize(this.assoc);

		assertEquals(this.assoc, assoc2);
		assertNotSame(this.assoc, assoc2);
		assertEquals(this.assoc.getKey(), assoc2.getKey());
		assertNotSame(this.assoc.getKey(), assoc2.getKey());
		assertEquals(this.assoc.getValue(), assoc2.getValue());
		assertNotSame(this.assoc.getValue(), assoc2.getValue());
	}

	private SimpleAssociation<String, String> copy(SimpleAssociation<String, String> sa) {
		return new SimpleAssociation<String, String>(sa.getKey(), sa.getValue());
	}

}
