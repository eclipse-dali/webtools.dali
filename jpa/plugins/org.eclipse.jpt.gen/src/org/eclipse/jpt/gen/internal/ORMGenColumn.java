/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal;

import java.util.Collections;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.util.EntityGenTools;
import org.eclipse.jpt.gen.internal.util.StringUtil;


/**
 * Represents the ORM generation properties for a database 
 * column.
 * 
 * <p>This is designed to be created/changed by the generation wizard,
 * and generated using Velocity templates.
 * The modified properties (if any) are persisted/retrieved using 
 * <code>ORMGenCustomizer</code>.
 * 
 */
public class ORMGenColumn
{
	private Table mTable;
	private Column mDbColumn;
	private ORMGenCustomizer mCustomizer;
	private ORMGenTable mGenTable;
	private static String JAVA_LANG_PACKAGE = "java.lang."; //$NON-NLS-1$
	
	public ORMGenColumn(Column dbColumn, ORMGenCustomizer customizer) {
		super();
		
		mDbColumn = dbColumn;
		mCustomizer = customizer;
		mTable = dbColumn.getTable();
	}
	
	public ORMGenCustomizer getCustomizer() {
		return mCustomizer;
	}
	
	public void setGenTable(ORMGenTable ormGenTable) {
		mGenTable = ormGenTable;
	}
	
	protected String customized(String propName) {
		return getCustomizer().getProperty(propName, mTable.getName(), getName());
	}
	
	protected boolean customizedBoolean(String propName) {
		return getCustomizer().getBooleanProperty(propName, mTable.getName(), getName());
	}
	
	protected void setCustomized(String propName, String value) {
		if (value != null && value.length() == 0) {
			value = null;
		}
		getCustomizer().setProperty(propName, value, mTable.getName(), getName());
	}
	
	protected void setCustomizedBoolean(String propName, boolean value, boolean defaultValue) {
		if (defaultValue == value) {
			setCustomized(propName, null); //remove the property
		} else {
			getCustomizer().setBooleanProperty(propName, value, mTable.getName(), getName());
		}
	}
	
	/**
	 * Returns the column name.
	 */
	public String getName() {
		String annotationName = this.mCustomizer.getDatabaseAnnotationNameBuilder().
			buildColumnAnnotationName(mDbColumn.getName(), mDbColumn);
		return annotationName != null ? annotationName : mDbColumn.getName();
	}

	public String getJoinColumnName(){
		String annotationName = this.mCustomizer.getDatabaseAnnotationNameBuilder().
			buildJoinColumnAnnotationName(mDbColumn);
		return annotationName != null ? annotationName : mDbColumn.getName();
	}
	
	public Column getDbColumn() {
		return this.mDbColumn;
	}
	
	/**
	 * Returns the generated bean property name for the given column.
	 * Does not return null.
	 */
	public String getPropertyName() {
		String name = customized(PROPERTY_NAME);
		if (name == null) {
			//name = StringUtil.columnNameToVarName(getName());
			name = EntityGenTools.convertToUniqueJavaStyleAttributeName(getName(), Collections.<String>emptySet());
		}
		return name;
	}
	
	public void setPropertyName(String name) {
		if (!StringUtil.equalObjects(name, getPropertyName())) {
			setCustomized(PROPERTY_NAME, name);
		}
	}
	
	/**
	 * Return true if the values of name element in the @Column is default
	 * so we can skip generating the annotation
	 * 
	 * @return true
	 */
	public boolean isDefault(){
		return isDefaultname() && isUpdateable() && isInsertable();
	}
	
	/**
	 * Return true if the values of name element in the @Column is default
	 * so we can skip generating the annotation
	 * 
	 * @return true
	 */
	public boolean isDefaultname(){
		String propName = getPropertyName();
//		String dbColumnName = getName();
//		return propName.equalsIgnoreCase( dbColumnName );
		String annotationName = this.mCustomizer.getDatabaseAnnotationNameBuilder().
			buildColumnAnnotationName(propName, this.mDbColumn );
		return annotationName==null;
	}

	
	/**
	 * Returns the column type.
	 * Does not return null.
	 */
	public String getPropertyType()  {
		String type = customized(PROPERTY_TYPE);
		if (type == null) {
			type = getCustomizer().getPropertyTypeFromColumn( this.mDbColumn );
		}
		if( type.startsWith(JAVA_LANG_PACKAGE) ) {
			type = type.substring( JAVA_LANG_PACKAGE.length() );
		}
		if( type.equals("java.sql.Date")){ //$NON-NLS-1$
			type = "java.util.Date"; //$NON-NLS-1$
		}
			
		return type;
	}
	
	public String getSimplePropertyType()  {
		return mGenTable.getSimplifiedColType( getPropertyType() );
	}	
	
	public void setPropertyType(String type)  {
		if (!StringUtil.equalObjects(type, getPropertyType())) {
			setCustomized(PROPERTY_TYPE, type);
		}
	}
	/**
	 * Returns true if the column type is numeric.
	 */
	public boolean isNumeric() {
		boolean ret = this.mDbColumn.isNumeric();
		return ret;
	}
	
	/**
	 * Returns the mapping kind, one of {@link #PROPERTY_MAPPING_KIND}|{@link #ID_MAPPING_KIND}
	 * |{@link #VERSION_MAPPING_KIND}|{@link #TIMESTAMP_MAPPING_KIND}.
	 * 
	 * This method does not return null (defaults to basic property type).
	 */
	public String getMappingKind() {
		String kind = customized(MAPPING_KIND);
		if (kind == null) {
			kind = getCustomizer().getBasicMappingKind();
			
			if ( this.mDbColumn.isPartOfPrimaryKey() 
				 && this.mDbColumn.getTable().getPrimaryKeyColumnsSize() == 1) {
				kind = getCustomizer().getIdMappingKind();
			}
		}
		return kind;
	}
	
	public void setMappingKind(String mappingKind)  {
		if (!StringUtil.equalObjects(mappingKind, getMappingKind())) {
			setCustomized(MAPPING_KIND, mappingKind);
		}
	}
	
	public boolean isNullable() {
		return this.mDbColumn.isNullable();
	}

	public int getSize() {
		if ( this.mDbColumn.isNumeric()){
			return mDbColumn.getPrecision();
		}
		return mDbColumn.getLength();
	}

	public int getDecimalDigits() {
		if ( this.mDbColumn.isNumeric() ){
			return mDbColumn.getScale();
		}
		return -1;
	}
	
	public boolean isPrimaryKey() {
		return this.mDbColumn.isPartOfPrimaryKey();
	}
	
	public boolean isPartOfCompositePrimaryKey() {
		return this.mDbColumn.isPartOfPrimaryKey() &&
				this.mTable.getPrimaryKeyColumnsSize() > 1;
	}
	
	public boolean isForeignKey() {
		return this.mDbColumn.isPartOfForeignKey();
	}
	
	public boolean isUnique() {
		return this.mDbColumn.isPartOfUniqueConstraint();
	}
	
	public String getPropertyDescription() {
		return customized(PROPERTY_DESC);
	}
	
	public boolean isDataTypeLOB() {
		return this.mDbColumn.isLOB();
	}	

	public boolean isNeedMapTemporalType() {
		String propertyType = this.getPropertyType();
		return ( propertyType.equals("java.util.Date") || propertyType.equals("java.util.Calendar") );  //$NON-NLS-1$ //$NON-NLS-2$
	}	
	
	public String getTemporalType() {
		String defaultType = getCustomizer().getPropertyTypeFromColumn( this.mDbColumn );
		if( defaultType.equals("java.sql.Date")){ //$NON-NLS-1$
			return "DATE"; //$NON-NLS-1$
		}else if( defaultType.equals("java.sql.Time")){ //$NON-NLS-1$
			return "TIME"; //$NON-NLS-1$
		}else {
			return "TIMESTAMP"; //$NON-NLS-1$
		}
	}	
	
	/**
	 * Returns the generated property getter scope, one of {@link #PUBLIC_SCOPE}|{@link #PROTECTED_SCOPE}
	 * |{@link #PRIVATE_SCOPE}.
	 * This method never returns null (defaults to public).
	 */
	public String getPropertyGetScope() {
		String scope = customized(PROPERTY_GET_SCOPE);
		if (scope == null) {
			scope = PUBLIC_SCOPE;
		}
		return scope;
	}
	
	public void setPropertyGetScope(String scope) {
		if (!StringUtil.equalObjects(scope, getPropertyGetScope())) {
			setCustomized(PROPERTY_GET_SCOPE, scope);
		}
	}
	
	/**
	 * Returns the generated property setter scope, one of {@link #PUBLIC_SCOPE}|{@link #PROTECTED_SCOPE}
	 * |{@link #PRIVATE_SCOPE}.
	 * This method never returns null (defaults to public).
	 */
	public String getPropertySetScope() {
		String scope = customized(PROPERTY_SET_SCOPE);
		if (scope == null) {
			scope = PUBLIC_SCOPE;
		}
		return scope;
	}
	
	public void setPropertySetScope(String scope) {
		if (!StringUtil.equalObjects(scope, getPropertySetScope())) {
			setCustomized(PROPERTY_SET_SCOPE, scope);
		}
	}
	
	/**
	 * Returns the generated field member scope, one of {@link #PUBLIC_SCOPE}|{@link #PROTECTED_SCOPE}
	 * |{@link #PRIVATE_SCOPE}.
	 * This method never returns null (defaults to private).
	 */
	public String getFieldScope() {
		String scope = customized(FIELD_SCOPE);
		if (scope == null) {
			scope = PRIVATE_SCOPE;
		}
		return scope;
	}
	
	/**
	 * Returns true if this column should be used in the 
	 * <code>equals</code> method implementation.
	 */
	public boolean isUseInEquals()  {
		return customizedBoolean(USE_IN_EQUALS) || isPrimaryKey();
	}
	
	public void setUseInEquals(boolean value) {
		setCustomizedBoolean(USE_IN_EQUALS, value, false);
	}
	
	/**
	 * Returns true if this column should be used in the 
	 * <code>toString</code> method implementation.
	 */
	public boolean isUseInToString()  {
		return customizedBoolean(USE_IN_TO_STRING) || isPrimaryKey();
	}
	
	public void setUseInToString(boolean value) {
		setCustomizedBoolean(USE_IN_TO_STRING, value, false);
	}
	
	public boolean isUpdateable() {
		return !"false".equals(customized(UPDATEABLE)); //defaults to true //$NON-NLS-1$
	}
	
	public void setUpdateable(boolean value) {
		setCustomizedBoolean(UPDATEABLE, value, true);
	}
	
	public boolean isInsertable() {
		return !"false".equals(customized(INSERTABLE)); //defaults to true //$NON-NLS-1$
	}
	
	public void setInsertable(boolean value) {
		setCustomizedBoolean(INSERTABLE, value, true);
	}
	
	public boolean isGenerated() {
		return !"false".equals(customized(GENERATED)); //defaults to true //$NON-NLS-1$
	}
	
	public void setGenerated(boolean value) {
		setCustomizedBoolean(GENERATED, value, true);
	}
	
	@Override
	public String toString() {
		return "name=" + getName() + "; type=" + getPropertyType() ; //$NON-NLS-1$ //$NON-NLS-2$ 
	}

	/*get/set and field scopes*/
	public static final String PUBLIC_SCOPE = "public"; //$NON-NLS-1$
	public static final String PROTECTED_SCOPE = "protected"; //$NON-NLS-1$
	public static final String PRIVATE_SCOPE = "private"; //$NON-NLS-1$

	/*customization properties*/
	private static final String PROPERTY_NAME = "propertyName"; //$NON-NLS-1$
	protected static final String PROPERTY_TYPE = "propertyType"; //$NON-NLS-1$
	protected static final String MAPPING_KIND = "mappingKind"; //$NON-NLS-1$
	private static final String PROPERTY_DESC = "propertyDesc"; //$NON-NLS-1$
	private static final String PROPERTY_GET_SCOPE = "propertyGetScope"; //$NON-NLS-1$
	private static final String PROPERTY_SET_SCOPE = "propertySetScope"; //$NON-NLS-1$
	private static final String FIELD_SCOPE = "fieldScope"; //$NON-NLS-1$
	private static final String USE_IN_EQUALS = "useInEquals"; //$NON-NLS-1$
	private static final String USE_IN_TO_STRING = "useInToString"; //$NON-NLS-1$
	private static final String UPDATEABLE = "updateable"; //$NON-NLS-1$
	private static final String INSERTABLE = "insertable"; //$NON-NLS-1$
	private static final String GENERATED = "genProperty"; //$NON-NLS-1$
}
