/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.sql.Types;
import java.util.HashMap;
import org.eclipse.datatools.modelbase.sql.datatypes.PrimitiveType;
import org.eclipse.jpt.utility.internal.JDBCTools;
import org.eclipse.jpt.utility.internal.JDBCType;
import org.eclipse.jpt.utility.internal.JavaType;

/**
 * Helper methods for dealing with Eclipse DTP.
 */
public final class DTPTools {

	/**
	 * Return the JDBC type corresponding to the specified Primitive type.
	 */
	public static JDBCType jdbcTypeForPrimitiveTypeNamed(String primitiveTypeName) {
		PrimitiveToJDBCTypeMapping mapping = primitiveToJDBCTypeMapping(primitiveTypeName);
		return (mapping == null) ? DEFAULT_JDBC_TYPE : mapping.getJDBCType();
	}

	/**
	 * Return the JDBC type corresponding to the specified Primitive type.
	 */
	public static JDBCType jdbcTypeFor(PrimitiveType primitiveType) {
		return jdbcTypeForPrimitiveTypeNamed(primitiveType.getName());
	}

	/**
	 * Return the JDBC type corresponding to the specified Primitive type.
	 */
	public static JDBCType jdbcTypeForPrimitiveTypeCode(int primitiveTypeCode) {
		return jdbcTypeFor(PrimitiveType.get(primitiveTypeCode));
	}

	/**
	 * Return the Java type corresponding to the specified Primitive type.
	 */
	public static JavaType javaTypeForPrimitiveTypeNamed(String primitiveTypeName) {
		return JDBCTools.javaTypeFor(jdbcTypeForPrimitiveTypeNamed(primitiveTypeName));
	}

	/**
	 * Return the Java type corresponding to the specified Primitive type.
	 */
	public static JavaType javaTypeFor(PrimitiveType primitiveType) {
		return JDBCTools.javaTypeFor(jdbcTypeFor(primitiveType));
	}

	/**
	 * Return the Java type corresponding to the specified Primitive type.
	 */
	public static JavaType javaTypeForPrimitiveTypeCode(int primitiveTypeCode) {
		return JDBCTools.javaTypeFor(jdbcTypeForPrimitiveTypeCode(primitiveTypeCode));
	}

	/**
	 * Return whether the specified Primitive type is a LOB
	 * (i.e. a BLOB, CLOB, or NCLOB).
	 */
	public static boolean dataTypeIsLob(PrimitiveType primitiveType) {
		return (primitiveType == PrimitiveType.BINARY_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.CHARACTER_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT_LITERAL);
	}


	// ********** internal stuff **********


	// ********** DTP Primitive => JDBC **********

	/**
	 * Primitive => JDBC type mappings, keyed by Primitive type name (e.g. "CHARACTER_VARYING")
	 */
	private static HashMap<String, PrimitiveToJDBCTypeMapping> PRIMITIVE_TO_JDBC_TYPE_MAPPINGS;  // pseudo 'final' - lazy-initialized
	private static final JDBCType DEFAULT_JDBC_TYPE = JDBCType.type(Types.VARCHAR);  // TODO VARCHAR is the default?


	private static PrimitiveToJDBCTypeMapping primitiveToJDBCTypeMapping(String primitiveTypeName) {
		return primitiveToJDBCTypeMappings().get(primitiveTypeName);
	}

	private static synchronized HashMap<String, PrimitiveToJDBCTypeMapping> primitiveToJDBCTypeMappings() {
		if (PRIMITIVE_TO_JDBC_TYPE_MAPPINGS == null) {
			PRIMITIVE_TO_JDBC_TYPE_MAPPINGS = buildPrimitiveToJDBCTypeMappings();
		}
		return PRIMITIVE_TO_JDBC_TYPE_MAPPINGS;
	}

	private static HashMap<String, PrimitiveToJDBCTypeMapping> buildPrimitiveToJDBCTypeMappings() {
		HashMap<String, PrimitiveToJDBCTypeMapping> mappings = new HashMap<String, PrimitiveToJDBCTypeMapping>();
		addPrimitiveToJDBCTypeMappingsTo(mappings);
		return mappings;
	}

	/**
	 * hard code the default mappings from the DTP primitive types to the
	 * appropriate JDBC types;
	 * pretty much a straight one-to-one mapping based on similar names;
	 * TODO some JDBC types are missing: INTERVAL, XML_TYPE
	 */
	private static void addPrimitiveToJDBCTypeMappingsTo(HashMap<String, PrimitiveToJDBCTypeMapping> mappings) {
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.BIGINT_LITERAL, Types.BIGINT, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.BINARY_LARGE_OBJECT_LITERAL, Types.BLOB, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.BINARY_LITERAL, Types.BINARY, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.BINARY_VARYING_LITERAL, Types.VARBINARY, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.BOOLEAN_LITERAL, Types.BOOLEAN, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.CHARACTER_LARGE_OBJECT_LITERAL, Types.CLOB, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.CHARACTER_LITERAL, Types.CHAR, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.CHARACTER_VARYING_LITERAL, Types.VARCHAR, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.DATALINK_LITERAL, Types.DATALINK, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.DATE_LITERAL, Types.DATE, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.DECIMAL_LITERAL, Types.DECIMAL, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.DOUBLE_PRECISION_LITERAL, Types.DOUBLE, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.FLOAT_LITERAL, Types.FLOAT, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.INTEGER_LITERAL, Types.INTEGER, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.INTERVAL_LITERAL, Types.OTHER, mappings);  // ???
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT_LITERAL, Types.CLOB, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.NATIONAL_CHARACTER_LITERAL, Types.CHAR, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.NATIONAL_CHARACTER_VARYING_LITERAL, Types.VARCHAR, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.NUMERIC_LITERAL, Types.NUMERIC, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.REAL_LITERAL, Types.REAL, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.SMALLINT_LITERAL, Types.SMALLINT, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.TIME_LITERAL, Types.TIME, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.TIMESTAMP_LITERAL, Types.TIMESTAMP, mappings);
		addPrimitiveToJDBCTypeMappingTo(PrimitiveType.XML_TYPE_LITERAL, Types.OTHER, mappings);  // ???
	}

	private static void addPrimitiveToJDBCTypeMappingTo(PrimitiveType primitiveType, int jdbcTypeCode, HashMap<String, PrimitiveToJDBCTypeMapping> mappings) {
		// check for duplicates
		Object prev = mappings.put(primitiveType.getName(), buildPrimitiveToJDBCTypeMapping(primitiveType, jdbcTypeCode));
		if (prev != null) {
			throw new IllegalArgumentException("duplicate Java class: " + ((PrimitiveToJDBCTypeMapping) prev).getPrimitiveType().getName());
		}
	}

	private static PrimitiveToJDBCTypeMapping buildPrimitiveToJDBCTypeMapping(PrimitiveType primitiveType, int jdbcTypeCode) {
		return new PrimitiveToJDBCTypeMapping(primitiveType, JDBCType.type(jdbcTypeCode));
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private DTPTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** member classes **********

	/**
	 * Primitive => JDBC
	 */
	private static class PrimitiveToJDBCTypeMapping {
		private final PrimitiveType primitiveType;
		private final JDBCType jdbcType;

		PrimitiveToJDBCTypeMapping(PrimitiveType primitiveType, JDBCType jdbcType) {
			super();
			this.primitiveType = primitiveType;
			this.jdbcType = jdbcType;
		}

		public PrimitiveType getPrimitiveType() {
			return this.primitiveType;
		}

		public JDBCType getJDBCType() {
			return this.jdbcType;
		}

		public boolean maps(PrimitiveType pt) {
			return this.primitiveType.equals(pt);
		}

		public boolean maps(String primitiveTypeName) {
			return this.primitiveType.getName().equals(primitiveTypeName);
		}

		public boolean maps(int primitiveTypeCode) {
			return this.primitiveType.getValue() == primitiveTypeCode;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			this.appendTo(sb);
			return sb.toString();
		}

		public void appendTo(StringBuilder sb) {
			sb.append(this.primitiveType.getName());
			sb.append(" => ");
			this.jdbcType.appendTo(sb);
		}
	}
}
