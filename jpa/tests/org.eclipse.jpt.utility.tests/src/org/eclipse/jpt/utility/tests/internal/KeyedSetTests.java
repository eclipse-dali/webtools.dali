/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.KeyedSet;

public class KeyedSetTests
		extends TestCase {
	
	private KeyedSet<String, String> nicknames;
	
	
	public KeyedSetTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nicknames = this.buildNicknames();
	}
	
	private KeyedSet<String, String> buildNicknames() {
		KeyedSet<String, String> ks = new KeyedSet<String, String>();
		ks.addItem("Jimmy", "James Sullivan");
		ks.addKey("Sully", "James Sullivan");
		ks.addItem("Bob", "Robert McKenna");
		ks.addKey("Mac", "Robert McKenna");
		return ks;
	}
	
	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}
	
	public void testAddItem() {
		// items added in setup
		assertTrue(this.nicknames.containsItem("James Sullivan"));
		assertTrue(this.nicknames.containsItem("Robert McKenna"));
		
		assertFalse(this.nicknames.containsItem("John Teasdale"));
		this.nicknames.addItem("Jack", "John Teasdale");
		assertTrue(this.nicknames.containsItem("John Teasdale"));
		
		this.nicknames.addItem("Teaser", "John Teasdale");
		assertTrue(this.nicknames.containsItem("John Teasdale"));
		assertTrue(this.nicknames.containsKey("Teaser"));
	}
	
	public void testAddKey() {
		// items added in setup
		assertTrue(this.nicknames.containsKey("Jimmy"));
		assertTrue(this.nicknames.containsKey("Sully"));
		assertTrue(this.nicknames.containsKey("Bob"));
		assertTrue(this.nicknames.containsKey("Mac"));
		
		assertFalse(this.nicknames.containsKey("Robbie"));
		this.nicknames.addKey("Robbie", "Robert McKenna");
		assertTrue(this.nicknames.containsKey("Robbie"));
		
		boolean exceptionCaught = false;
		try {
			this.nicknames.addKey("Teaser", "John Teasdale");
		}
		catch (IllegalArgumentException iae) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);	
	}
	
	public void testGetItem() {
		// items added in setup
		assertEquals(this.nicknames.getItem("Jimmy"), "James Sullivan");
		assertEquals(this.nicknames.getItem("Sully"), "James Sullivan");
		assertEquals(this.nicknames.getItem("Bob"), "Robert McKenna");
		assertEquals(this.nicknames.getItem("Mac"), "Robert McKenna");
		assertNull(this.nicknames.getItem("Jack"));
	}
	
	public void testRemoveItem() {
		// items added in setup
		assertTrue(this.nicknames.containsItem("James Sullivan"));
		assertTrue(this.nicknames.containsKey("Jimmy"));
		assertTrue(this.nicknames.containsKey("Sully"));
		
		assertTrue(this.nicknames.removeItem("James Sullivan"));
		assertFalse(this.nicknames.containsItem("James Sullivan"));
		assertFalse(this.nicknames.containsKey("Jimmy"));
		assertFalse(this.nicknames.containsKey("Sully"));
		
		assertFalse(this.nicknames.removeItem("William Goldberg"));
	}
	
	public void testRemoveKey() {
		// items added in setup
		assertTrue(this.nicknames.containsItem("James Sullivan"));
		assertTrue(this.nicknames.containsKey("Jimmy"));
		assertTrue(this.nicknames.containsKey("Sully"));
		
		assertTrue(this.nicknames.removeKey("Jimmy"));
		assertTrue(this.nicknames.containsItem("James Sullivan"));
		assertFalse(this.nicknames.containsKey("Jimmy"));
		assertTrue(this.nicknames.containsKey("Sully"));
		
		assertTrue(this.nicknames.removeKey("Sully"));
		assertFalse(this.nicknames.containsItem("James Sullivan"));
		assertFalse(this.nicknames.containsKey("Jimmy"));
		assertFalse(this.nicknames.containsKey("Sully"));
		
		assertFalse(this.nicknames.removeKey("Billy"));
	}
}
