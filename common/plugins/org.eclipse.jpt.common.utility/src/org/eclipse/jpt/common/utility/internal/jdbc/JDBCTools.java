/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.jdbc;

import java.sql.Types;
import java.util.HashMap;
import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.internal.SimpleJavaType;

/**
 * JDBC utility methods.
 */
public final class JDBCTools {
	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcTypeForClassNamed(String className) {
		JavaToJDBCTypeMapping mapping = getJavaToJDBCTypeMapping(className);
		return (mapping == null) ? DEFAULT_JDBC_TYPE : mapping.getJDBCType();
	}

	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcType(Class<?> javaClass) {
		return jdbcTypeForClassNamed(javaClass.getName());
	}

	/**
	 * Return the JDBC type corresponding to the specified class.
	 * @see java.sql.Types
	 */
	public static JDBCType jdbcType(JavaType javaType) {
		return jdbcTypeForClassNamed(javaType.getJavaClassName());
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaTypeForJDBCTypeNamed(String jdbcTypeName) {
		JDBCToJavaTypeMapping mapping = getJDBCToJavaTypeMapping(jdbcTypeName);
		return (mapping == null) ? DEFAULT_JAVA_TYPE : mapping.getJavaType();
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaType(JDBCType jdbcType) {
		return javaTypeForJDBCTypeNamed(jdbcType.name());
	}

	/**
	 * Return the Java type corresponding to the specified JDBC type.
	 * @see java.sql.Types
	 */
	public static JavaType javaTypeForJDBCTypeCode(int jdbcTypeCode) {
		return javaType(JDBCType.type(jdbcTypeCode));
	}


	// ********** JDBC => Java **********

	/**
	 * JDBC => Java type mappings, keyed by JDBC type name
	 * (e.g. <code>"VARCHAR"</code>)
	 */
	private static HashMap<String, JDBCToJavaTypeMapping> JDBC_TO_JAVA_TYPE_MAPPINGS;  // pseudo 'final' - lazy-initialized
	private static final JavaType DEFAULT_JAVA_TYPE = new SimpleJavaType(java.lang.Object.class);  // TODO Object is the default?


	private static JDBCToJavaTypeMapping getJDBCToJavaTypeMapping(String jdbcTypeName) {
		return getJDBCToJavaTypeMappings().get(jdbcTypeName);
	}

	private static synchronized HashMap<String, JDBCToJavaTypeMapping> getJDBCToJavaTypeMappings() {
		if (JDBC_TO_JAVA_TYPE_MAPPINGS == null) {
			JDBC_TO_JAVA_TYPE_MAPPINGS = buildJDBCToJavaTypeMappings();
		}
		return JDBC_TO_JAVA_TYPE_MAPPINGS;
	}

	private static HashMap<String, JDBCToJavaTypeMapping> buildJDBCToJavaTypeMappings() {
		HashMap<String, JDBCToJavaTypeMapping> mappings = new HashMap<String, JDBCToJavaTypeMapping>();
		addJDBCToJavaTypeMappings(mappings);
		return mappings;
	}

	/**
	 * Hard code the default mappings from the JDBC types to the
	 * appropriate Java types.
	 * See "JDBC 3.0 Specification" Appendix B.
	 * @see java.sql.Types
	 */
	private static void addJDBCToJavaTypeMappings(HashMap<String, JDBCToJavaTypeMapping> mappings) {
		addJDBCToJavaTypeMapping(Types.ARRAY, java.sql.Array.class, mappings);
		addJDBCToJavaTypeMapping(Types.BIGINT, long.class, mappings);
		addJDBCToJavaTypeMapping(Types.BINARY, byte[].class, mappings);
		addJDBCToJavaTypeMapping(Types.BIT, boolean.class, mappings);
		addJDBCToJavaTypeMapping(Types.BLOB, java.sql.Blob.class, mappings);
		addJDBCToJavaTypeMapping(Types.BOOLEAN, boolean.class, mappings);
		addJDBCToJavaTypeMapping(Types.CHAR, java.lang.String.class, mappings);
		addJDBCToJavaTypeMapping(Types.CLOB, java.sql.Clob.class, mappings);
		addJDBCToJavaTypeMapping(Types.DATALINK, java.net.URL.class, mappings);
		addJDBCToJavaTypeMapping(Types.DATE, java.sql.Date.class, mappings);
		addJDBCToJavaTypeMapping(Types.DECIMAL, java.math.BigDecimal.class, mappings);
		addJDBCToJavaTypeMapping(Types.DISTINCT, java.lang.Object.class, mappings);  // ???
		addJDBCToJavaTypeMapping(Types.DOUBLE, double.class, mappings);
		addJDBCToJavaTypeMapping(Types.FLOAT, double.class, mappings);
		addJDBCToJavaTypeMapping(Types.INTEGER, int.class, mappings);
		addJDBCToJavaTypeMapping(Types.JAVA_OBJECT, java.lang.Object.class, mappings);  // ???
		addJDBCToJavaTypeMapping(Types.LONGVARBINARY, byte[].class, mappings);
		addJDBCToJavaTypeMapping(Types.LONGVARCHAR, java.lang.String.class, mappings);
		// not sure why this is defined in java.sql.Types
//		addJDBCToJavaTypeMappingTo(Types.NULL, java.lang.Object.class, mappings);
		addJDBCToJavaTypeMapping(Types.NUMERIC, java.math.BigDecimal.class, mappings);
		addJDBCToJavaTypeMapping(Types.OTHER, java.lang.Object.class, mappings);	// ???
		addJDBCToJavaTypeMapping(Types.REAL, float.class, mappings);
		addJDBCToJavaTypeMapping(Types.REF, java.sql.Ref.class, mappings);
		addJDBCToJavaTypeMapping(Types.SMALLINT, short.class, mappings);
		addJDBCToJavaTypeMapping(Types.STRUCT, java.sql.Struct.class, mappings);
		addJDBCToJavaTypeMapping(Types.TIME, java.sql.Time.class, mappings);
		addJDBCToJavaTypeMapping(Types.TIMESTAMP, java.sql.Timestamp.class, mappings);
		addJDBCToJavaTypeMapping(Types.TINYINT, byte.class, mappings);
		addJDBCToJavaTypeMapping(Types.VARBINARY, byte[].class, mappings);
		addJDBCToJavaTypeMapping(Types.VARCHAR, java.lang.String.class, mappings);
	}

	private static void addJDBCToJavaTypeMapping(int jdbcTypeCode, Class<?> javaClass, HashMap<String, JDBCToJavaTypeMapping> mappings) {
		// check for duplicates
		JDBCType jdbcType = JDBCType.type(jdbcTypeCode);
		Object prev = mappings.put(jdbcType.name(), buildJDBCToJavaTypeMapping(jdbcType, javaClass));
		if (prev != null) {
			throw new IllegalArgumentException("duplicate JDBC type: " + jdbcType.name()); //$NON-NLS-1$
		}
	}

	private static JDBCToJavaTypeMapping buildJDBCToJavaTypeMapping(JDBCType jdbcType, Class<?> javaClass) {
		return new JDBCToJavaTypeMapping(jdbcType, new SimpleJavaType(javaClass));
	}


	// ********** Java => JDBC **********

	/**
	 * Java => JDBC type mappings, keyed by Java class name
	 * (e.g. <code>"java.lang.Object"</code>).
	 */
	private static HashMap<String, JavaToJDBCTypeMapping> JAVA_TO_JDBC_TYPE_MAPPINGS;  // pseudo 'final' - lazy-initialized
	private static final JDBCType DEFAULT_JDBC_TYPE = JDBCType.type(Types.VARCHAR);  // TODO VARCHAR is the default?


	private static JavaToJDBCTypeMapping getJavaToJDBCTypeMapping(String className) {
		return getJavaToJDBCTypeMappings().get(className);
	}

	private static synchronized HashMap<String, JavaToJDBCTypeMapping> getJavaToJDBCTypeMappings() {
		if (JAVA_TO_JDBC_TYPE_MAPPINGS == null) {
			JAVA_TO_JDBC_TYPE_MAPPINGS = buildJavaToJDBCTypeMappings();
		}
		return JAVA_TO_JDBC_TYPE_MAPPINGS;
	}

	private static HashMap<String, JavaToJDBCTypeMapping> buildJavaToJDBCTypeMappings() {
		HashMap<String, JavaToJDBCTypeMapping> mappings = new HashMap<String, JavaToJDBCTypeMapping>();
		addJavaToJDBCTypeMappings(mappings);
		return mappings;
	}

	/**
	 * Hard code the default mappings from the Java types to the
	 * appropriate JDBC types.
	 * See "JDBC 3.0 Specification" Appendix B.
	 * @see java.sql.Types
	 */
	private static void addJavaToJDBCTypeMappings(HashMap<String, JavaToJDBCTypeMapping> mappings) {
		// primitives
		addJavaToJDBCTypeMapping(boolean.class, Types.BIT, mappings);
		addJavaToJDBCTypeMapping(byte.class, Types.TINYINT, mappings);
		addJavaToJDBCTypeMapping(double.class, Types.DOUBLE, mappings);
		addJavaToJDBCTypeMapping(float.class, Types.REAL, mappings);
		addJavaToJDBCTypeMapping(int.class, Types.INTEGER, mappings);
		addJavaToJDBCTypeMapping(long.class, Types.BIGINT, mappings);
		addJavaToJDBCTypeMapping(short.class, Types.SMALLINT, mappings);

		// reference classes
		addJavaToJDBCTypeMapping(java.lang.Boolean.class, Types.BIT, mappings);
		addJavaToJDBCTypeMapping(java.lang.Byte.class, Types.TINYINT, mappings);
		addJavaToJDBCTypeMapping(java.lang.Double.class, Types.DOUBLE, mappings);
		addJavaToJDBCTypeMapping(java.lang.Float.class, Types.REAL, mappings);
		addJavaToJDBCTypeMapping(java.lang.Integer.class, Types.INTEGER, mappings);
		addJavaToJDBCTypeMapping(java.lang.Long.class, Types.BIGINT, mappings);
		addJavaToJDBCTypeMapping(java.lang.Short.class, Types.SMALLINT, mappings);
		addJavaToJDBCTypeMapping(java.lang.String.class, Types.VARCHAR, mappings);
		addJavaToJDBCTypeMapping(java.math.BigDecimal.class, Types.NUMERIC, mappings);
		addJavaToJDBCTypeMapping(java.net.URL.class, Types.DATALINK, mappings);
		addJavaToJDBCTypeMapping(java.sql.Array.class, Types.ARRAY, mappings);
		addJavaToJDBCTypeMapping(java.sql.Blob.class, Types.BLOB, mappings);
		addJavaToJDBCTypeMapping(java.sql.Clob.class, Types.CLOB, mappings);
		addJavaToJDBCTypeMapping(java.sql.Date.class, Types.DATE, mappings);
		addJavaToJDBCTypeMapping(java.sql.Ref.class, Types.REF, mappings);
		addJavaToJDBCTypeMapping(java.sql.Struct.class, Types.STRUCT, mappings);
		addJavaToJDBCTypeMapping(java.sql.Time.class, Types.TIME, mappings);
		addJavaToJDBCTypeMapping(java.sql.Timestamp.class, Types.TIMESTAMP, mappings);

		// arrays
		addJavaToJDBCTypeMapping(byte[].class, Types.VARBINARY, mappings);
		addJavaToJDBCTypeMapping(java.lang.Byte[].class, Types.VARBINARY, mappings);
	}

	private static void addJavaToJDBCTypeMapping(Class<?> javaClass, int jdbcTypeCode, HashMap<String, JavaToJDBCTypeMapping> mappings) {
		// check for duplicates
		Object prev = mappings.put(javaClass.getName(), buildJavaToJDBCTypeMapping(javaClass, jdbcTypeCode));
		if (prev != null) {
			throw new IllegalArgumentException("duplicate Java class: " + ((JavaToJDBCTypeMapping) prev).getJavaType().declaration()); //$NON-NLS-1$
		}
	}

	private static JavaToJDBCTypeMapping buildJavaToJDBCTypeMapping(Class<?> javaClass, int jdbcTypeCode) {
		return new JavaToJDBCTypeMapping(new SimpleJavaType(javaClass), JDBCType.type(jdbcTypeCode));
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private JDBCTools() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** mapping classes **********

	/**
	 * JDBC => Java
	 */
	static class JDBCToJavaTypeMapping {
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
			return this.jdbcType.code() == jdbcTypeCode;
		}

		public boolean maps(String jdbcTypeName) {
			return this.jdbcType.name().equals(jdbcTypeName);
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
			sb.append(" => "); //$NON-NLS-1$
			this.javaType.appendDeclarationTo(sb);
		}
	}

	/**
	 * Java => JDBC
	 */
	static class JavaToJDBCTypeMapping {
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
			sb.append(" => "); //$NON-NLS-1$
			this.jdbcType.appendTo(sb);
		}
	}
}
