/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.modelbase.dbdefinition.PredefinedDataTypeDefinition;
import org.eclipse.datatools.modelbase.sql.datatypes.CharacterStringDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.DataType;
import org.eclipse.datatools.modelbase.sql.datatypes.ExactNumericDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.NumericalDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PredefinedDataType;
import org.eclipse.datatools.modelbase.sql.datatypes.PrimitiveType;
import org.eclipse.jpt.common.utility.JavaType;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.SimpleJavaType;
import org.eclipse.jpt.jpa.db.Column;

/**
 *  Wrap a DTP Column
 */
final class DTPColumnWrapper
	extends DTPDatabaseObjectWrapper<DTPTableWrapper>
	implements Column
{
	/** the wrapped DTP column */
	private final org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn;


	// ********** constructor **********

	DTPColumnWrapper(DTPTableWrapper table, org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		super(table);
		this.dtpColumn = dtpColumn;
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	ICatalogObject getCatalogObject() {
		return (ICatalogObject) this.dtpColumn;
	}

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().columnChanged(this);
	}


	// ********** Column implementation **********

	public String getName() {
		return this.dtpColumn.getName();
	}

	public DTPTableWrapper getTable() {
		return this.parent;
	}

	public boolean isPartOfPrimaryKey() {
		return this.getTable().primaryKeyColumnsContains(this);
	}

	public boolean isPartOfForeignKey() {
		return this.getTable().foreignKeyBaseColumnsContains(this);
	}

	public boolean isPartOfUniqueConstraint() {
		return this.dtpColumn.isPartOfUniqueConstraint();
	}

	public boolean isNullable() {
		return this.dtpColumn.isNullable();
	}

	public String getDataTypeName() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType == null) ? null : dataType.getName();
	}

	public boolean isNumeric() {
		return this.dtpColumn.getDataType() instanceof NumericalDataType;
	}	

	public int getPrecision() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof NumericalDataType) ?
						((NumericalDataType) dataType).getPrecision() :
						-1;
	}

	public int getScale(){
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof ExactNumericDataType) ?
						((ExactNumericDataType) dataType).getScale() :
						-1;
	}

	public int getLength() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof CharacterStringDataType) ?
						((CharacterStringDataType) dataType).getLength() :
						-1;
	}

	public boolean isLOB() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof PredefinedDataType) ?
						primitiveTypeIsLob(((PredefinedDataType) dataType).getPrimitiveType()) :
						false;
	}

	public String getJavaTypeDeclaration() {
		return this.getJavaType().declaration();
	}

	public JavaType getJavaType() {
		DataType dataType = this.dtpColumn.getDataType();
		return (dataType instanceof PredefinedDataType) ?
			convertToJPAJavaType(this.getJavaType((PredefinedDataType) dataType)) :
			DEFAULT_JAVA_TYPE;
	}

	public String getPrimaryKeyJavaTypeDeclaration() {
		return this.getPrimaryKeyJavaType().declaration();
	}

	public JavaType getPrimaryKeyJavaType() {
		return convertToJPAPrimaryKeyJavaType(this.getJavaType());
	}

	private JavaType getJavaType(PredefinedDataType dataType) {
		// this is just a bit hacky: moving from a type declaration to a class name to a type declaration...
		String dtpJavaClassName = this.resolveDefinition(dataType).getJavaClassName();
		return new SimpleJavaType(ReflectionTools.getClassNameForTypeDeclaration(dtpJavaClassName));
	}

	private PredefinedDataTypeDefinition resolveDefinition(PredefinedDataType dataType) {
		return this.getDatabase().getDTPDefinition().getPredefinedDataTypeDefinition(dataType.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.tables.Column column) {
		return this.dtpColumn == column;
	}

	@Override
	void clear() {
		// no state to clear
	}


	// ********** static methods **********

	/**
	 * The JDBC spec says JDBC drivers should be able to map BLOBs and CLOBs
	 * directly, but the JPA spec does not allow them.
	 */
	private static JavaType convertToJPAJavaType(JavaType javaType) {
		if (javaType.equals(BLOB_JAVA_TYPE)) {
			return BYTE_ARRAY_JAVA_TYPE;
		}
		if (javaType.equals(CLOB_JAVA_TYPE)) {
			return STRING_JAVA_TYPE;
		}
		return javaType;
	}

	/**
	 * The JPA spec [2.1.4] says only the following types are allowed in
	 * primary key fields:<ul>
	 *     <li>[variable] primitives
	 *     <li>[variable] primitive wrappers
	 *     <li>{@link java.lang.String}
	 *     <li>{@link java.util.Date}
	 *     <li>{@link java.sql.Date}
	 * </ul>
	 */
	private static JavaType convertToJPAPrimaryKeyJavaType(JavaType javaType) {
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

	private static boolean primitiveTypeIsLob(PrimitiveType primitiveType) {
		return (primitiveType == PrimitiveType.BINARY_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.CHARACTER_LARGE_OBJECT_LITERAL)
				|| (primitiveType == PrimitiveType.NATIONAL_CHARACTER_LARGE_OBJECT_LITERAL);
	}


	// ***** some constants used when converting the column to a Java attribute
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

}
