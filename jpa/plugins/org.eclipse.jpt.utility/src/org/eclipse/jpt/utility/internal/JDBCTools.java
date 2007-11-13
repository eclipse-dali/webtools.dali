/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.sql.Types;
import java.util.HashMap;

/**
 * Helper methods for dealing with the JDBC API.
 */
public final class JDBCTools {


	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcTypeForClassNamed(String className) {
		JavaToJDBCTypeMapping mapping = javaToJDBCTypeMapping(className);
		return (mapping == null) ? DEFAULT_JDBC_TYPE : mapping.getJDBCType();
	}

	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcTypeFor(Class<?> javaClass) {
		return jdbcTypeForClassNamed(javaClass.getName());
	}

	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcTypeFor(JavaType javaType) {
		return jdbcTypeForClassNamed(javaType.javaClassName());
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaTypeForJDBCTypeNamed(String jdbcTypeName) {
		JDBCToJavaTypeMapping mapping = jdbcToJavaTypeMapping(jdbcTypeName);
		return (mapping == null) ? DEFAULT_JAVA_TYPE : mapping.getJavaType();
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaTypeFor(JDBCType jdbcType) {
		return javaTypeForJDBCTypeNamed(jdbcType.getName());
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaTypeForJDBCTypeCode(int jdbcTypeCode) {
		return javaTypeFor(JDBCType.type(jdbcTypeCode));
	}


	// ********** internal stuff **********


	// ********** JDBC => Java **********

	/**
	 * JDBC => Java type mappings, keyed by JDBC type name (e.g. "VARCHAR")
	 */
	private static HashMap<String, JDBCToJavaTypeMapping> JDBC_TO_JAVA_TYPE_MAPPINGS;  // pseudo 'final' - lazy-initialized
	private static final JavaType DEFAULT_JAVA_TYPE = new JavaType(java.lang.Object.class);  // TODO Object is the default?


	private static JDBCToJavaTypeMapping jdbcToJavaTypeMapping(String jdbcTypeName) {
		return jdbcToJavaTypeMappings().get(jdbcTypeName);
	}

	private static synchronized HashMap<String, JDBCToJavaTypeMapping> jdbcToJavaTypeMappings() {
		if (JDBC_TO_JAVA_TYPE_MAPPINGS == null) {
			JDBC_TO_JAVA_TYPE_MAPPINGS = buildJDBCToJavaTypeMappings();
		}
		return JDBC_TO_JAVA_TYPE_MAPPINGS;
	}

	private static HashMap<String, JDBCToJavaTypeMapping> buildJDBCToJavaTypeMappings() {
		HashMap<String, JDBCToJavaTypeMapping> mappings = new HashMap<String, JDBCToJavaTypeMapping>();
		addJDBCToJavaTypeMappingsTo(mappings);
		return mappings;
	}

	/**
	 * hard code the default mappings from the JDBC types to the
	 * appropriate Java types
	 * @see java.sql.Types
	 * see "JDBC 3.0 Specification" Appendix B
	 */
	private static void addJDBCToJavaTypeMappingsTo(HashMap<String, JDBCToJavaTypeMapping> mappings) {
		addJDBCToJavaTypeMappingTo(Types.ARRAY, java.sql.Array.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.BIGINT, long.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.BINARY, byte[].class, mappings);
		addJDBCToJavaTypeMappingTo(Types.BIT, boolean.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.BLOB, java.sql.Blob.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.BOOLEAN, boolean.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.CHAR, java.lang.String.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.CLOB, java.sql.Clob.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.DATALINK, java.net.URL.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.DATE, java.sql.Date.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.DECIMAL, java.math.BigDecimal.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.DISTINCT, java.lang.Object.class, mappings);  // ???
		addJDBCToJavaTypeMappingTo(Types.DOUBLE, double.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.FLOAT, double.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.INTEGER, int.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.JAVA_OBJECT, java.lang.Object.class, mappings);  // ???
		addJDBCToJavaTypeMappingTo(Types.LONGVARBINARY, byte[].class, mappings);
		addJDBCToJavaTypeMappingTo(Types.LONGVARCHAR, java.lang.String.class, mappings);
		// not sure why this is defined in java.sql.Types
//		addJDBCToJavaTypeMappingTo(Types.NULL, java.lang.Object.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.NUMERIC, java.math.BigDecimal.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.OTHER, java.lang.Object.class, mappings);	// ???
		addJDBCToJavaTypeMappingTo(Types.REAL, float.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.REF, java.sql.Ref.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.SMALLINT, short.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.STRUCT, java.sql.Struct.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.TIME, java.sql.Time.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.TIMESTAMP, java.sql.Timestamp.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.TINYINT, byte.class, mappings);
		addJDBCToJavaTypeMappingTo(Types.VARBINARY, byte[].class, mappings);
		addJDBCToJavaTypeMappingTo(Types.VARCHAR, java.lang.String.class, mappings);
	}

	private static void addJDBCToJavaTypeMappingTo(int jdbcTypeCode, Class<?> javaClass, HashMap<String, JDBCToJavaTypeMapping> mappings) {
		// check for duplicates
		JDBCType jdbcType = JDBCType.type(jdbcTypeCode);
		Object prev = mappings.put(jdbcType.getName(), buildJDBCToJavaTypeMapping(jdbcType, javaClass));
		if (prev != null) {
			throw new IllegalArgumentException("duplicate JDBC type: " + jdbcType.getName());
		}
	}

	private static JDBCToJavaTypeMapping buildJDBCToJavaTypeMapping(JDBCType jdbcType, Class<?> javaClass) {
		return new JDBCToJavaTypeMapping(jdbcType, new JavaType(javaClass));
	}


	// ********** Java => JDBC **********

	/**
	 * Java => JDBC type mappings, keyed by Java class name (e.g. "java.lang.Object")
	 */
	private static HashMap<String, JavaToJDBCTypeMapping> JAVA_TO_JDBC_TYPE_MAPPINGS;  // pseudo 'final' - lazy-initialized
	private static final JDBCType DEFAULT_JDBC_TYPE = JDBCType.type(Types.VARCHAR);  // TODO VARCHAR is the default?


	private static JavaToJDBCTypeMapping javaToJDBCTypeMapping(String className) {
		return javaToJDBCTypeMappings().get(className);
	}

	private static synchronized HashMap<String, JavaToJDBCTypeMapping> javaToJDBCTypeMappings() {
		if (JAVA_TO_JDBC_TYPE_MAPPINGS == null) {
			JAVA_TO_JDBC_TYPE_MAPPINGS = buildJavaToJDBCTypeMappings();
		}
		return JAVA_TO_JDBC_TYPE_MAPPINGS;
	}

	private static HashMap<String, JavaToJDBCTypeMapping> buildJavaToJDBCTypeMappings() {
		HashMap<String, JavaToJDBCTypeMapping> mappings = new HashMap<String, JavaToJDBCTypeMapping>();
		addJavaToJDBCTypeMappingsTo(mappings);
		return mappings;
	}

	/**
	 * hard code the default mappings from the Java types to the
	 * appropriate JDBC types
	 * @see java.sql.Types
	 * see "JDBC 3.0 Specification" Appendix B
	 */
	private static void addJavaToJDBCTypeMappingsTo(HashMap<String, JavaToJDBCTypeMapping> mappings) {
		// primitives
		addJavaToJDBCTypeMappingTo(boolean.class, Types.BIT, mappings);
		addJavaToJDBCTypeMappingTo(byte.class, Types.TINYINT, mappings);
		addJavaToJDBCTypeMappingTo(double.class, Types.DOUBLE, mappings);
		addJavaToJDBCTypeMappingTo(float.class, Types.REAL, mappings);
		addJavaToJDBCTypeMappingTo(int.class, Types.INTEGER, mappings);
		addJavaToJDBCTypeMappingTo(long.class, Types.BIGINT, mappings);
		addJavaToJDBCTypeMappingTo(short.class, Types.SMALLINT, mappings);

		// reference classes
		addJavaToJDBCTypeMappingTo(java.lang.Boolean.class, Types.BIT, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Byte.class, Types.TINYINT, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Double.class, Types.DOUBLE, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Float.class, Types.REAL, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Integer.class, Types.INTEGER, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Long.class, Types.BIGINT, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Short.class, Types.SMALLINT, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.String.class, Types.VARCHAR, mappings);
		addJavaToJDBCTypeMappingTo(java.math.BigDecimal.class, Types.NUMERIC, mappings);
		addJavaToJDBCTypeMappingTo(java.net.URL.class, Types.DATALINK, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Array.class, Types.ARRAY, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Blob.class, Types.BLOB, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Clob.class, Types.CLOB, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Date.class, Types.DATE, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Ref.class, Types.REF, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Struct.class, Types.STRUCT, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Time.class, Types.TIME, mappings);
		addJavaToJDBCTypeMappingTo(java.sql.Timestamp.class, Types.TIMESTAMP, mappings);

		// arrays
		addJavaToJDBCTypeMappingTo(byte[].class, Types.VARBINARY, mappings);
		addJavaToJDBCTypeMappingTo(java.lang.Byte[].class, Types.VARBINARY, mappings);
	}

	private static void addJavaToJDBCTypeMappingTo(Class<?> javaClass, int jdbcTypeCode, HashMap<String, JavaToJDBCTypeMapping> mappings) {
		// check for duplicates
		Object prev = mappings.put(javaClass.getName(), buildJavaToJDBCTypeMapping(javaClass, jdbcTypeCode));
		if (prev != null) {
			throw new IllegalArgumentException("duplicate Java class: " + ((JavaToJDBCTypeMapping) prev).getJavaType().declaration());
		}
	}

	private static JavaToJDBCTypeMapping buildJavaToJDBCTypeMapping(Class<?> javaClass, int jdbcTypeCode) {
		return new JavaToJDBCTypeMapping(new JavaType(javaClass), JDBCType.type(jdbcTypeCode));
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private JDBCTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** member classes **********

	/**
	 * JDBC => Java
	 */
	private static class JDBCToJavaTypeMapping {
		private final JDBCType jdbcType;
		private final JavaType javaType;

		JDBCToJavaTypeMapping(JDBCType jdbcType, JavaType javaType) {
			super();
			this.jdbcType = jdbcType;
			this.javaType = javaType;
		}

		public JDBCType getJDBCType() {
			return this.jdbcType;
		}

		public JavaType getJavaType() {
			return this.javaType;
		}

		public boolean maps(int jdbcTypeCode) {
			return this.jdbcType.getCode() == jdbcTypeCode;
		}

		public boolean maps(String jdbcTypeName) {
			return this.jdbcType.getName().equals(jdbcTypeName);
		}

		public boolean maps(JDBCType type) {
			return this.jdbcType == type;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			this.appendTo(sb);
			return sb.toString();
		}

		public void appendTo(StringBuilder sb) {
			this.jdbcType.appendTo(sb);
			sb.append(" => ");
			this.javaType.appendDeclarationTo(sb);
		}

	}

	/**
	 * Java => JDBC
	 */
	private static class JavaToJDBCTypeMapping {
		private final JavaType javaType;
		private final JDBCType jdbcType;

		JavaToJDBCTypeMapping(JavaType javaType, JDBCType jdbcType) {
			super();
			this.javaType = javaType;
			this.jdbcType = jdbcType;
		}

		public JavaType getJavaType() {
			return this.javaType;
		}

		public JDBCType getJDBCType() {
			return this.jdbcType;
		}

		public boolean maps(JavaType jt) {
			return this.javaType.equals(jt);
		}

		public boolean maps(String elementTypeName, int arrayDepth) {
			return this.javaType.equals(elementTypeName, arrayDepth);
		}

		public boolean maps(String javaClassName) {
			return this.javaType.describes(javaClassName);
		}

		public boolean maps(Class<?> javaClass) {
			return this.javaType.describes(javaClass);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			this.appendTo(sb);
			return sb.toString();
		}

		public void appendTo(StringBuilder sb) {
			this.javaType.appendDeclarationTo(sb);
			sb.append(" => ");
			this.jdbcType.appendTo(sb);
		}

	}

}
