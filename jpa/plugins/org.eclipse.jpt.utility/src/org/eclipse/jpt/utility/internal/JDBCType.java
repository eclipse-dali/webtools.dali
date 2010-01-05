/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Types;

/**
 * Associate the Java constant and the JDBC type name.
 * These are derived from java.sql.Types.
 * 
 * @see java.sql.Types
 */
public final class JDBCType
	implements Cloneable, Serializable
{

	/**
	 * the constant name (e.g. VARCHAR)
	 */
	private final String name;

	/**
	 * the JDBC code used by JDBC drivers
	 */
	private final int code;

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a JDBC type with the specified name and type code.
	 * This is private because all the possible JDBC types are built and
	 * stored in the static array TYPES.
	 * @see #types()
	 */
	private JDBCType(String name, int code) {
		super();
		this.name = name;
		this.code = code;
	}


	// ********** accessors **********

	/**
	 * Return the name of the type, as defined in java.sql.Types.
	 */
	public String name() {
		return this.name;
	}


	/**
	 * Return the type code, as defined in java.sql.Types.
	 */
	public int code() {
		return this.code;
	}


	// ********** printing and displaying **********

	public void appendTo(StringBuilder sb) {
		sb.append(this.name);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append('(');
		this.appendTo(sb);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public JDBCType clone() {
		try {
			return (JDBCType) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}


	// ********** static stuff **********

	/**
	 * all the JDBC type defined in java.sql.Types
	 */
	private static JDBCType[] TYPES;		// pseudo 'final' - lazy-initialized


	public synchronized static JDBCType[] types() {
		if (TYPES == null) {
			TYPES = buildTypes();
		}
		return TYPES;
	}

	/**
	 * Return the JDBC type for the specified type code (e.g. Types.VARCHAR).
	 * @see java.sql.Types
	 */
	public static JDBCType type(int code) {
		JDBCType[] types = types();
		for (int i = types.length; i-- > 0; ) {
			if (types[i].code() == code) {
				return types[i];
			}
		}
		throw new IllegalArgumentException("invalid JDBC type code: " + code); //$NON-NLS-1$
	}

	/**
	 * Return the JDBC type for the specified type name (e.g. "VARCHAR").
	 * @see java.sql.Types
	 */
	public static JDBCType type(String name) {
		JDBCType[] types = types();
		for (int i = types.length; i-- > 0; ) {
			if (types[i].name().equals(name)) {
				return types[i];
			}
		}
		throw new IllegalArgumentException("invalid JDBC type name: " + name); //$NON-NLS-1$
	}

	/**
	 * build up the JDBC types via reflection
	 * @see java.sql.Types
	 */
	private static JDBCType[] buildTypes() {
		Field[] fields = Types.class.getDeclaredFields();
		int len = fields.length;
		JDBCType[] types = new JDBCType[len];
		for (int i = len; i-- > 0; ) {
			String name = fields[i].getName();
			int code;
			try {
				code = ((Integer) fields[i].get(null)).intValue();
			} catch (IllegalAccessException ex) {
				throw new RuntimeException(ex);	// shouldn't happen...
			}
			types[i] = new JDBCType(name, code);
		}
		return types;
	}

}
