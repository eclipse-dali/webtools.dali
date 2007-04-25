/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal;

import java.sql.Types;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.JDBCType;

public class JDBCTypeTests extends TestCase {

	public JDBCTypeTests(String name) {
		super(name);
	}

	public void testTypesSize() {
		assertEquals(Types.class.getDeclaredFields().length, JDBCType.types().length);
	}

	public void testName() {
		JDBCType jdbcType;
		jdbcType = JDBCType.type(Types.VARCHAR);
		assertEquals("VARCHAR", jdbcType.getName());

		jdbcType = JDBCType.type(Types.INTEGER);
		assertEquals("INTEGER", jdbcType.getName());
	}

	public void testCode() {
		JDBCType jdbcType;
		jdbcType = JDBCType.type(Types.VARCHAR);
		assertEquals(Types.VARCHAR, jdbcType.getCode());

		jdbcType = JDBCType.type(Types.INTEGER);
		assertEquals(Types.INTEGER, jdbcType.getCode());
	}

	public void testInvalidTypeCode() throws Exception {
		boolean exCaught = false;
		try {
			JDBCType jdbcType = JDBCType.type(55);
			fail("invalid JDBCType: " + jdbcType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testInvalidTypeName() throws Exception {
		boolean exCaught = false;
		try {
			JDBCType jdbcType = JDBCType.type("VARCHAR2");
			fail("invalid JDBCType: " + jdbcType);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

}
