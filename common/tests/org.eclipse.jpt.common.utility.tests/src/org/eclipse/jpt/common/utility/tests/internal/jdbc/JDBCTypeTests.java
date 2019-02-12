/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.jdbc;

import java.sql.Types;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.jdbc.JDBCType;

@SuppressWarnings("nls")
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
		assertEquals("VARCHAR", jdbcType.name());

		jdbcType = JDBCType.type(Types.INTEGER);
		assertEquals("INTEGER", jdbcType.name());
	}

	public void testCode() {
		JDBCType jdbcType;
		jdbcType = JDBCType.type(Types.VARCHAR);
		assertEquals(Types.VARCHAR, jdbcType.code());

		jdbcType = JDBCType.type(Types.INTEGER);
		assertEquals(Types.INTEGER, jdbcType.code());
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
