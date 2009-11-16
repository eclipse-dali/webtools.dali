/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;

import org.eclipse.datatools.modelbase.dbdefinition.PredefinedDataTypeDefinition;
import org.eclipse.datatools.modelbase.sql.datatypes.DataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PrimitiveType;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.utility.JavaType;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.NameTools;
import org.eclipse.jpt.utility.internal.SimpleJavaType;

/**
 *  Wrap a DTP Column
 */
final class DTPColumnWrapper
	extends DTPWrapper
	implements Column
{
	// backpointer to parent
	private final DTPTableWrapper table;

	// the wrapped DTP column
	private final org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn;


	// ***** some constants used when converting the column to a Java field
	// TODO Object is the default?
	private static final JavaType DEFAULT_JAVA_TYPE = new SimpleJavaType(java.lang.Object.class);

	private static final JavaType BLOB_JAVA_TYPE = new SimpleJavaType(java.sql.Blob.class);
	private static final JavaType BYTE_ARRAY_JAVA_TYPE = new SimpleJavaType(byte[].class);

	private static final JavaType CLOB_JAVA_TYPE = new SimpleJavaType(java.sql.Clob.class);
	private static final JavaType STRING_JAVA_TYPE = new SimpleJavaType(java.lang.String.class);

	private static final JavaType UTIL_DATE_JAVA_TYPE = new SimpleJavaType(java.util.Date.class);
	private static final JavaType SQL_DATE_JAVA_TYPE = new SimpleJavaType(java.sql.Date.class);
	private static final JavaType SQL_TIME_JAVA_TYPE = new SimpleJavaType(java.sql.Time.class);
	private static final JavaType SQL_TIMESTAMP_JAVA_TYPE = new SimpleJavaType(java.sql.Timestamp.class);

	private static final JavaType BIG_DECIMAL_JAVA_TYPE = new SimpleJavaType(java.math.BigDecimal.class);
	private static final JavaType LONG_JAVA_TYPE = new SimpleJavaType(long.class);


	// ********** constructor **********

	DTPColumnWrapper(DTPTableWrapper table, org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		super(table, dtpColumn);
		this.table = table;
		this.dtpColumn = dtpColumn;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		this.getConnectionProfile().columnChanged(this, eventType);
	}


	// ********** Column implementation **********

	@Override
	public String getName() {
		return this.dtpColumn.getName();
	}

	public String getDataTypeName() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType == null) ? null : dataType.getName();
	}

	public String getJavaFieldName() {
		String jName = this.getName();
		if ( ! this.isCaseSensitive()) {
			jName = jName.toLowerCase();
		}
		return NameTools.convertToJavaIdentifier(jName);
	}

	public boolean matchesJavaFieldName(String javaFieldName) {
		return this.isCaseSensitive() ?
			this.getName().equals(javaFieldName)
		:
			this.getName().equalsIgnoreCase(javaFieldName);
	}

	public String getPrimaryKeyJavaTypeDeclaration() {
		return this.getPrimaryKeyJavaType().declaration();
	}

	public JavaType getPrimaryKeyJavaType() {
		return this.jpaSpecCompliantPrimaryKeyJavaType(this.getJavaType());
	}

	/**
	 * The JPA spec [2.1.4] says only the following types are allowed in
	 * primary key fields:
	 *     [variable] primitives
	 *     [variable] primitive wrappers
	 *     java.lang.String
	 *     java.util.Date
	 *     java.sql.Date
	 */
	private JavaType jpaSpecCompliantPrimaryKeyJavaType(JavaType javaType) {
		if (javaType.isVariablePrimitive()
				|| javaType.isVariablePrimitiveWrapper()
				|| javaType.equals(STRING_JAVA_TYPE)
				|| javaType.equals(UTIL_DATE_JAVA_TYPE)
				|| javaType.equals(SQL_DATE_JAVA_TYPE)) {
			return javaType;
		}
		if (javaType.equals(BIG_DECIMAL_JAVA_TYPE)) {
			return LONG_JAVA_TYPE;  // ??
		}
		if (javaType.equals(SQL_TIME_JAVA_TYPE)) {
			return UTIL_DATE_JAVA_TYPE;  // ???
		}
		if (javaType.equals(SQL_TIMESTAMP_JAVA_TYPE)) {
			return UTIL_DATE_JAVA_TYPE;  // ???
		}
		// all the other typical types are pretty much un-mappable - return String(?)
		return STRING_JAVA_TYPE;
	}

	public String getJavaTypeDeclaration() {
		return this.getJavaType().declaration();
	}

	public JavaType getJavaType() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof PredefinedDataType) ?
			this.jpaSpecCompliantJavaType(this.javaType((PredefinedDataType) dataType))
		:
			DEFAULT_JAVA_TYPE;
	}

	private JavaType javaType(PredefinedDataType dataType) {
		// this is just a bit hacky: moving from a type declaration to a class name to a type declaration...
		String dtpJavaClassName = this.predefinedDataTypeDefinition(dataType).getJavaClassName();
		return new SimpleJavaType(ClassTools.classNameForTypeDeclaration(dtpJavaClassName));
	}

	private PredefinedDataTypeDefinition predefinedDataTypeDefinition(PredefinedDataType dataType) {
		return this.database().getDtpDefinition().getPredefinedDataTypeDefinition(dataType.getName());
	}

	/**
	 * The JDBC spec says JDBC drivers should be able to map BLOBs and CLOBs
	 * directly, but the JPA spec does not allow them.
	 */
	private JavaType jpaSpecCompliantJavaType(JavaType javaType) {
		if (javaType.equals(BLOB_JAVA_TYPE)) {
			return BYTE_ARRAY_JAVA_TYPE;
		}
		if (javaType.equals(CLOB_JAVA_TYPE)) {
			return STRING_JAVA_TYPE;
		}
		return javaType;
	}

	public boolean dataTypeIsLOB() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof PredefinedDataType) ?
			this.primitiveTypeIsLob(((PredefinedDataType) dataType).getPrimitiveType())
		:
			false;
	}

	private boolean primitiveTypeIsLob(PrimitiveType primitiveType) {
		return (primitiveType == PrimitiveType.BINARY_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.CHARACTER_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT_LITERAL);
	}


	// ********** Comparable implementation **********

	public int compareTo(Column column) {
		return Collator.getInstance().compare(this.getName(), column.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Column column) {
		return this.dtpColumn == column;
	}

	boolean isCaseSensitive() {
		return this.table.isCaseSensitive();
	}

	DTPDatabaseWrapper database() {
		return this.table.database();
	}

}
