/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
		assertEquals("foo", this.assoc.key());
	}

	public void testGetValue() {
		assertEquals("bar", this.assoc.value());
	}

	public void testSetValue() {
		assertEquals("bar", this.assoc.value());
		this.assoc.setValue("baz");
		assertEquals("baz", this.assoc.value());
	}

	public void testEquals() {
		assertEquals(this.assoc, this.copy(this.assoc));

		SimpleAssociation<String, String> assoc2 = new SimpleAssociation<String, String>("foo", "baz");
		assertFalse(this.assoc.equals(assoc2));

		assoc2 = new SimpleAssociation<String, String>("fop", "bar");
		assertFalse(this.assoc.equals(assoc2));
	}

	public void testHashCode() {
		assertEquals(this.assoc.hashCode(), this.copy(this.assoc).hashCode());
	}

	public void testClone() {
		this.verifyClone(this.assoc, this.assoc.clone());
	}

	private void verifyClone(Association<String, String> expected, Association<String, String> actual) {
		assertEquals(expected, actual);
		assertNotSame(expected, actual);
		assertEquals(expected.key(), actual.key());
		assertSame(expected.key(), actual.key());
		assertEquals(expected.value(), actual.value());
		assertSame(expected.value(), actual.value());
	}

	public void testSerialization() throws Exception {
		@SuppressWarnings("cast")
		Association<String, String> assoc2 = (Association<String, String>) TestTools.serialize(this.assoc);

		assertEquals(this.assoc, assoc2);
		assertNotSame(this.assoc, assoc2);
		assertEquals(this.assoc.key(), assoc2.key());
		assertNotSame(this.assoc.key(), assoc2.key());
		assertEquals(this.assoc.value(), assoc2.value());
		assertNotSame(this.assoc.value(), assoc2.value());
	}

	private SimpleAssociation<String, String> copy(SimpleAssociation<String, String> sa) {
		return new SimpleAssociation<String, String>(sa.key(), sa.value());
	}

}
