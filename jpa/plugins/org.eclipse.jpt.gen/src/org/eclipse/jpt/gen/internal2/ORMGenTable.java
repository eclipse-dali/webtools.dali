/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.EntityGenTools;
import org.eclipse.jpt.gen.internal2.util.DTPUtil;
import org.eclipse.jpt.gen.internal2.util.StringUtil;
import org.eclipse.jpt.utility.JavaType;

/**
 * Represents the ORM generation properties for a database table.
 * 
 * <p>
 * This is designed to be created/changed by the generation wizard, and
 * generated using Velocity templates. The modified properties (if any) are
 * persisted/retrieved using <code>ORMGenCustomizer</code>.
 * 
 */
public class ORMGenTable
{
	private ORMGenCustomizer mCustomizer;
	private List<ORMGenColumn> mColumns;
	private Table mDbTable;

	/**
	 * @param table
	 *            The database table or null if this table is used to get/set
	 *            the default table properties (properties that apply to all
	 *            tables unless overriden).
	 */
	public ORMGenTable(Table table, ORMGenCustomizer customizer) {
		super();
		mDbTable = table;
		mCustomizer = customizer;
	}

	public ORMGenCustomizer getCustomizer() {
		return mCustomizer;
	}

	/**
	 * Returns true if this table is is used to get/set the default table
	 * properties.
	 */
	public boolean isDefaultsTable() {
		return mDbTable == null;
	}

	protected String customized(String propName) {
		return getCustomizer().getProperty(propName, getName(), null);
	}

	protected boolean customizedBoolean(String propName) {
		return getCustomizer().getBooleanProperty(propName, getName(), null);
	}

	protected void setCustomized(String propName, String value) {
		if (value != null && value.length() == 0) {
			value = null;
		}
		getCustomizer().setProperty(propName, value, getName(), null);
	}

	protected void setCustomizedBoolean(String propName, boolean value, boolean defaultValue) {
		if (defaultValue == value) {
			setCustomized(propName, null); // remove the property
		}
		else {
			getCustomizer().setBooleanProperty(propName, value, getName(), null);
		}
	}

	public Table getDbTable() {
		return mDbTable;
	}

	/**
	 * Returns the table name.
	 */
	public String getName() {
		if (mDbTable == null)
			return ORMGenCustomizer.ANY_TABLE;
		return mDbTable.getName();
	}

	public String getJoinTableAnnotationName() {
		if (mDbTable == null)
			return ORMGenCustomizer.ANY_TABLE;
		String annotationName = this.mCustomizer.getDatabaseAnnotationNameBuilder().buildJoinTableAnnotationName(mDbTable);
		return annotationName != null ? annotationName : mDbTable.getName();
	}

	/**
	 * Returns the database schema containing the table.
	 */
	public String getSchema() {
		if (DTPUtil.isDefaultSchema(mDbTable))
			return ""; //$NON-NLS-1$
		String schemaName = mDbTable.getSchema().getName();
		return schemaName;
	}

	/**
	 * Sets the package for the generated class (empty string for the default
	 * package)
	 */
	public void setPackage(String srcFolder, String pkg) {
		setCustomized(SRC_FOLDER, srcFolder);
		getCustomizer().setProperty(PACKAGE, pkg, getName(), null); // not
																	// calling
																	// setCustomized
																	// so that
																	// empty
																	// strings
																	// do not
																	// get
																	// nulled
																	// out.
	}

	/**
	 * Returns the Java package (empty string for the default package).
	 */
	public String getPackage() {
		return customized(PACKAGE);
	}

	/**
	 * Returns the generated Java class name (not qualified).
	 */
	public String getClassName() {
		String name = customized(CLASS_NAME);
		if (name == null) {
			// name = StringUtil.tableNameToVarName(getName());
			// name = StringUtil.initUpper(name);
			name = EntityGenTools.convertToUniqueJavaStyleClassName(getName(), new ArrayList<String>());
			name = StringUtil.singularise(name);
		}
		return name;
	}

	public void setClassName(String className) {
		/*
		 * if the class name is the same as the (possibly computed) class name
		 * then nothing to do
		 */
		if (!StringUtil.equalObjects(className, getClassName())) {
			setCustomized(CLASS_NAME, className);
		}
	}

	/**
	 * Returns a name suitable to be used as a variable or class name. This is
	 * computed based on the table name.
	 * 
	 * @param singular
	 *            Whether the name should be singular or plural.
	 */
	public String getVarName(boolean singular) {
		String name = StringUtil.tableNameToVarName(getName());
		if (singular) {
			name = StringUtil.singularise(name);
		}
		else {
			name = StringUtil.pluralise(name);
		}
		return name;
	}

	/**
	 * Returns the fully qualified generated Java class name.
	 */
	public String getQualifiedClassName() {
		return qualify(getClassName());
	}

	/**
	 * Returns the composite key Java class name (not qualified).
	 */
	public String getCompositeKeyClassName() {
		String name = customized(COMPOSITE_KEY_CLASS_NAME);
		if (name == null) {
			name = getClassName() + "PK"; //$NON-NLS-1$
		}
		return name;
	}

	/**
	 * Returns the fully qualified composite key Java class name.
	 */
	public String getQualifiedCompositeKeyClassName() {
		return qualify(getCompositeKeyClassName());
	}

	/**
	 * Returns the composite key property name.
	 */
	public String getCompositeKeyPropertyName() {
		return "id"; //$NON-NLS-1$
	}

	/**
	 * Returns the <code>ORMGenColumn</code> objects to be generated for this
	 * table.
	 */
	public List<ORMGenColumn> getColumns() {
		if (mColumns == null) {
			mColumns = new ArrayList<ORMGenColumn>();
			Iterator<Column> cols = mDbTable.columns();
			while (cols.hasNext()) {
				Column c = cols.next();
				ORMGenColumn genColumn = getCustomizer().createGenColumn(c);
				mColumns.add(genColumn);
			}
		}
		return mColumns;
	}

	public List<String> getColumnNames() {
		Iterator<Column> cols = mDbTable.columns();
		List<String> ret = new ArrayList<String>();
		while (cols.hasNext()) {
			Column c = cols.next();
			ret.add(c.getName());
		}
		return ret;
	}

	/**
	 * Returns the <code>ORMGenColumn</code> objects representing the table's
	 * primary key.
	 */
	public List<ORMGenColumn> getPrimaryKeyColumns() {
		List<Column> dbCols = DTPUtil.getPrimaryKeyColumns(mDbTable);
		List<ORMGenColumn> ret = new ArrayList<ORMGenColumn>();
		for (Column dbCol : dbCols) {
			ret.add(new ORMGenColumn(dbCol, this.mCustomizer));
		}
		return ret;
	}

	/**
	 * Returns the primary key column or null if there is no or many primary key
	 * columns.
	 */
	public ORMGenColumn getPrimaryKeyColumn() {
		ORMGenColumn pkCol = null;
		List<ORMGenColumn> pkColumns = getPrimaryKeyColumns();
		if (pkColumns.size() == 1) {
			// Column dbCol = (Column)pkColumns.get(0);
			pkCol = pkColumns.get(0); // (ORMGenColumn)
										// mCustomizer.createGenColumn(dbCol);
		}
		else {
			/*
			 * if no pk column then look for the first column with id mapping
			 * kind. This is so that the wizard can be used with tables not
			 * having primary keys.
			 */
			List<ORMGenColumn> columns = getColumns();
			for (int i = 0, n = columns.size(); i < n; ++i) {
				ORMGenColumn column = columns.get(i);
				if (column.getMappingKind().equals(mCustomizer.getIdMappingKind())) {
					pkCol = column;
					break;
				}
			}
		}
		return pkCol;
	}

	/**
	 * Returns true if there is more than 1 pk column.
	 */
	public boolean isCompositeKey() {
		return DTPUtil.getPrimaryKeyColumnNames(mDbTable).size() > 1;
	}

	/**
	 * Returns the <code>ORMGenColumn</code> objects for the the columns that
	 * are not part of any association.
	 * 
	 * @param genOnly
	 *            Whether to include only the columns marked for generation.
	 * 
	 * @param includePk
	 *            Whether to include the primary kley column(s).
	 * 
	 * @param includeInherited
	 *            Whether to include the columns associated with Java properties
	 *            that exist in the super class (if any).
	 */
	public List<ORMGenColumn> getSimpleColumns(boolean genOnly, boolean includePk, boolean includeInherited) {
		List<ORMGenColumn> result = new java.util.ArrayList<ORMGenColumn>();
		List<ORMGenColumn> columns = getColumns();
		List<AssociationRole> roles = getAssociationRoles();
		for (int i = 0, n = columns.size(); i < n; ++i) {
			ORMGenColumn column = columns.get(i);
			if (genOnly && !column.isGenerated()) {
				continue;
			}
			if (column.isPrimaryKey()) {
				if (!includePk || isCompositeKey()) {
					continue;
				}
			}
			else if (isColumnInAsscociation(column, roles)) {
				continue;
			}
			result.add(column);
		}
		return result;
	}

	public List<ORMGenColumn> getSimpleColumns() {
		return getSimpleColumns(true/* genOnly */, true/* includePk */, true/* includeInherited */);
	}

	/**
	 * Returns false if the given column should be generated with false
	 * updatable/insertable. This is the case when the column is mapped more
	 * than once, this usually happen with columns in composite keys and
	 * many-to-one associations.
	 * 
	 * <br>
	 * Note that for Hibernate the column param is null because the
	 * insert/update attributes are specified for the many-to-one tag itself
	 * instead of the nested column tags (bogus obviously).
	 */
	public boolean isColumnUpdateInsert(AssociationRole role, ORMGenColumn column) {
		if (column == null) {
			for (Iterator<ORMGenColumn> iter = role.getReferrerColumns().iterator(); iter.hasNext();) {
				ORMGenColumn c = iter.next();
				if (!isColumnUpdateInsert(role, c)) {
					return false;
				}
			}
			return true;
		}
		if (column.isPrimaryKey()) {
			return false;
		}
		/*
		 * should look if there are multiple associations using the same column
		 * and return false, but this is probably an unusual case.
		 */
		return true;
	}

	/**
	 * Returns the <code>ORMGenColumn</code> objects corresponding to the given
	 * column names.
	 */
	public List<ORMGenColumn> getColumnsByNames(List<String> names) {
		List<ORMGenColumn> result = new java.util.ArrayList<ORMGenColumn>();
		for (String name : names) {
			ORMGenColumn column = getColumnByName(name);
			assert (column != null);
			if (column != null) {
				result.add(column);
			}
		}
		return result;
	}

	/**
	 * Returns the columns having the given name, or null if none.
	 */
	public ORMGenColumn getColumnByName(String name) {
		List<ORMGenColumn> columns = getColumns();
		for (int i = 0, n = columns.size(); i < n; ++i) {
			ORMGenColumn column = columns.get(i);
			if (column.getName().equals(name)) {
				return column;
			}
		}
		return null;
	}

	/**
	 * Returns the <code>AssociationRole</code> objects for this table. Only the
	 * association marked for generation are returned.
	 */
	public List<AssociationRole> getAssociationRoles() {
		/*
		 * this is not cached intentionally because invalidating the cache with
		 * wizard changes is kinda tricky.
		 */
		List<AssociationRole> associationRoles = new ArrayList<AssociationRole>();
		String name = getName();
		List<Association> associations = mCustomizer.getAssociations();
		for (Iterator<Association> iter = associations.iterator(); iter.hasNext();) {
			Association association = iter.next();
			if (!association.isGenerated()) {
				continue;
			}
			/*
			 * check both referrerand referenced because an association could be
			 * from-to the same table (employee/manager)
			 */
			if (association.getReferrerTable().getName().equals(name)) {
				AssociationRole role = association.getReferrerRole();
				if (role != null) {
					associationRoles.add(role);
				}
			}
			if (association.getReferencedTable().getName().equals(name)) {
				AssociationRole role = association.getReferencedRole();
				if (role != null) {
					associationRoles.add(role);
				}
			}
		}
		return associationRoles;
	}

	public String getClassDescription() {
		return customized(CLASS_DESC);
	}

	/**
	 * Returns the generated class scope, one of {@link #PUBLIC_SCOPE}|
	 * {@link #PROTECTED_SCOPE} |{@link #PRIVATE_SCOPE}. This method never
	 * returns null (defaults to public).
	 */
	public String getClassScope() {
		String scope = customized(CLASS_SCOPE);
		if (scope == null) {
			scope = PUBLIC_SCOPE;
		}
		return scope;
	}

	public String getExtends() {
		return customized(EXTENDS);
	}

	public void setExtends(String baseClass) {
		setCustomized(EXTENDS, baseClass);
	}

	public List<String> getImplements() {
		String str = customized(IMPLEMENTS);
		List<String> result = StringUtil.strToList(str, ',', true/* trim */);
		if (result == null) {
			result = Collections.emptyList();
		}
		return result;
	}

	public void setImplements(List<String> interfaces) {
		setCustomized(IMPLEMENTS, StringUtil.listToStr(interfaces, ','));
	}

	/**
	 * Returns the string that should be generated in the Java class for extends
	 * and implements.
	 */
	public String generateExtendsImplements() {
		StringBuffer buffer = new StringBuffer();
		String extendsClass = getExtends();
		if (extendsClass != null && !extendsClass.equals("java.lang.Object") && !extendsClass.equals("Object")) {
			buffer.append("extends " + simplifyClassName(extendsClass));
		}
		buffer.append(" implements Serializable"); // assuming that the Java
													// file template imports the
													// java.io.Serializable
		for (Iterator<String> iter = getImplements().iterator(); iter.hasNext();) {
			buffer.append(", " + simplifyClassName(iter.next()));
		}
		return buffer.toString();
	}

	private String simplifyClassName(String fullClassName) {
		final String JAVA_LANG = "java.lang.";
		if (fullClassName.startsWith(JAVA_LANG)) {
			return fullClassName.substring(JAVA_LANG.length());
		}
		String pkg = StringUtil.getPackageName(fullClassName);
		if (pkg != null && StringUtil.equalObjects(pkg, getPackage())) {
			return StringUtil.getClassName(fullClassName);
		}
		return fullClassName;
	}

	/**
	 * Returns the id generator scheme (assigned, sequence, etc). Does not
	 * return null, defaults to "assigned" or "identity" depending on whether
	 * the table has an identity column.
	 */
	public String getIdGenerator() {
		String generator = customized(ID_GENERATOR);
		String noneGenerator = getCustomizer().getNoIdGenerator();
		if (!isDefaultsTable()) {
			/*
			 * This is done mainly because there might be cases where some
			 * tables have autoinctement pk and others are assigned. In this
			 * case this makes it so that it is possible to have a "none"
			 * default value that is interpreted depending on the case.
			 */
			if (generator == null || generator.equals(noneGenerator)) {
				ORMGenColumn pkColumn = getPrimaryKeyColumn();
				if (pkColumn != null && DTPUtil.isAutoIncrement(pkColumn.getDbColumn())) {
					generator = getCustomizer().getIdentityIdGenerator();
				}
			}
		}
		if (generator == null) {
			generator = noneGenerator;
		}
		return generator;
	}

	/**
	 * Changes the id generator scheme (assigned, sequence, etc).
	 */
	public void setIdGenerator(String scheme) {
		setCustomized(ID_GENERATOR, scheme);
	}

	/**
	 * Returns the sequence name for the given table, or null if none (makes
	 * sense only when the scheme is native, sequence, ..).
	 */
	public String getSequence() {
		return customized(SEQUENCE);
	}

	public void setSequence(String name) {
		setCustomized(SEQUENCE, name);
	}

	/**
	 * Returns the sequence name after replacing the ${table} and ${pk} by their
	 * values, or null if none.
	 */
	public String getFormattedSequence() {
		String sequence = getSequence();
		if (sequence != null) {
			/* resolve the ${table} and ${pk} patterns */
			sequence = StringUtil.strReplace(sequence, TABLE_SEQ_PATTERN, getName());
			if (sequence.indexOf(PK_SEQ_PATTERN) >= 0) {
				List<String> pkNames = DTPUtil.getPrimaryKeyColumnNames(getDbTable());
				String pkName = null;
				if (pkNames.size() > 0) {
					pkName = pkNames.get(0);
				}
				sequence = StringUtil.strReplace(sequence, PK_SEQ_PATTERN, pkName);
			}
		}
		return sequence != null ? sequence.toUpperCase() : "";
	}

	public boolean isImplementEquals() {
		return !"true".equals(customized(IMPLEMENT_EQUALS)); // defaults to
																// false
	}

	public void setImplementEquals(boolean value) {
		setCustomizedBoolean(IMPLEMENT_EQUALS, value, true);
	}

	/**
	 * Returns true if there is any column participating in equals/hashcode.
	 */
	public boolean hasColumnsInEquals() {
		List<ORMGenColumn> columns = getSimpleColumns();
		for (int i = 0, n = columns.size(); i < n; ++i) {
			ORMGenColumn column = columns.get(i);
			if (column.isUseInEquals()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns {@link #GENERATE_DDL_ANNOTATION} indicating whether the optional
	 * DDL parameters like length, nullable, unqiue, etc should be generated in @Column
	 * annotation. defaults to false.
	 */
	public boolean isGenerateDDLAnnotations() {
		return "true".equals(customized(ORMGenCustomizer.GENERATE_DDL_ANNOTATION)); // defaults
																					// to
																					// false
	}

	public void setGenerateDDLAnnotations(boolean generate) {
		setCustomizedBoolean(ORMGenCustomizer.GENERATE_DDL_ANNOTATION, generate, false);
	}

	/**
	 * Returns one of {@link #PROPERTY_ACCESS}|{@link #FIELD_ACCESS} indicating
	 * how the entity properties are mapped. Does not return null (defaults to
	 * {@link #FIELD_ACCESS}).
	 */
	public String getAccess() {
		String name = customized(ACCESS);
		if (name == null) {
			name = FIELD_ACCESS;
		}
		return name;
	}

	public void setAccess(String access) {
		assert (access == null || access.equals(PROPERTY_ACCESS) || access.equals(FIELD_ACCESS));
		if (!StringUtil.equalObjects(access, getAccess())) {
			setCustomized(ACCESS, access);
		}
	}

	/**
	 * Returns one of {@link #LAZY_FETCH}|{@link #EAGER_FETCH} indicating how
	 * the table associations are feched. Returns null if the provider defaults
	 * should be used.
	 */
	public String getDefaultFetch() {
		return customized(DEFAULT_FETCH);
	}

	public void setDefaultFetch(String fetch) {
		assert (fetch == null || fetch.equals(LAZY_FETCH) || fetch.equals(EAGER_FETCH));
		setCustomized(DEFAULT_FETCH, fetch);
	}

	public String[] getSupportedCollectionTypes() {
		return new String[] {
			SET_COLLECTION_TYPE, LIST_COLLECTION_TYPE
		};
	}

	/**
	 * Returns one of {@link #LIST_COLLECTION_TYPE}|{@link #SET_COLLECTION_TYPE}
	 * indicating the Java type (full class name) used for properties of
	 * collection types. This does not return null (defaults to list).
	 */
	public String getDefaultCollectionType() {
		String cType = customized(DEFAULT_COLLECTION_TYPE);
		if (cType == null) {
			cType = SET_COLLECTION_TYPE;
		}
		return cType;
	}

	public void setDefaultCollectionType(String cType) {
		assert (cType.equals(LIST_COLLECTION_TYPE) || cType.equals(SET_COLLECTION_TYPE));
		setCustomized(DEFAULT_COLLECTION_TYPE, cType);
	}

	/**
	 * Returns true if the primary key is compound and any of its columns should
	 * be included in the <code>equals</code> method implementation.
	 */
	public boolean isCompoundKeyUseInEquals() {
		if (isCompositeKey()) {
			for (Iterator<ORMGenColumn> iter = getPrimaryKeyColumns().iterator(); iter.hasNext();) {
				ORMGenColumn column = iter.next();
				if (column.isUseInEquals()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isRoleUseInEquals(AssociationRole role) {
		for (Iterator<ORMGenColumn> iter = role.getReferrerColumns().iterator(); iter.hasNext();) {
			ORMGenColumn column = iter.next();
			if (column.isUseInEquals()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return true if the values of name element in the @Table is default so we
	 * can skip generating the annotation
	 * 
	 * @return true
	 */
	public boolean isDefaultname() {
		String entityName = getClassName();
		String annotationName = this.mCustomizer.getDatabaseAnnotationNameBuilder().buildTableAnnotationName(entityName, mDbTable);
		return annotationName == null;
	}

	/**
	 * Qualifies a class name if there is a package.
	 */
	private String qualify(String className) {
		String pkg = getPackage();
		if (pkg != null && pkg.length() != 0) {
			className = pkg + '.' + className;
		}
		return className;
	}

	/**
	 * Returns true if the given column is part of any association.
	 */
	private boolean isColumnInAsscociation(ORMGenColumn column, List<AssociationRole> roles) {
		for (int i = 0, n = roles.size(); i < n; ++i) {
			AssociationRole role = roles.get(i);
			List<ORMGenColumn> cols = role.getReferrerColumns();
			for (ORMGenColumn col : cols) {
				if (col.getName().equals(column.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Print the clause to be used in the generated equals() method
	 * 
	 * @return String
	 */
	public String getPrimaryKeyEqualsClause() {
		StringBuilder buf = new StringBuilder();
		Iterator<ORMGenColumn> columns = this.getPrimaryKeyColumns().iterator();
		while (columns.hasNext()) {
			this.printPrimaryKeyEqualsClauseOn(columns.next(), buf);
			if (columns.hasNext()) {
				buf.append("\n");
				buf.append("\t\t\t");
				buf.append("&& ");
			}
		}
		buf.append(";");
		return buf.toString();
	}

	private void printPrimaryKeyEqualsClauseOn(ORMGenColumn column, StringBuilder buf) {
		String fieldName = column.getPropertyName();
		JavaType javaType = column.getDbColumn().getPrimaryKeyJavaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveEqualsClauseOn(fieldName, buf);
		}
		else {
			this.printReferenceEqualsClauseOn(fieldName, buf);
		}
	}

	private void printPrimitiveEqualsClauseOn(String fieldName, StringBuilder buf) {
		buf.append("(this.");
		buf.append(fieldName);
		buf.append(" == castOther.");
		buf.append(fieldName);
		buf.append(')');
	}

	private void printReferenceEqualsClauseOn(String fieldName, StringBuilder buf) {
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".equals(castOther.");
		buf.append(fieldName);
		buf.append(')');
	}

	/**
	 * Print the clause to be used in the generated hasCode() method
	 * 
	 * @return String
	 */
	public String getPrimaryKeyHashCodeClause() {
		StringBuilder buf = new StringBuilder();
		Iterator<ORMGenColumn> columns = this.getPrimaryKeyColumns().iterator();
		while (columns.hasNext()) {
			buf.append("hash = hash * prime + ");
			this.printPrimaryKeyHashCodeClauseOn(columns.next(), buf);
			buf.append(';');
			buf.append('\n');
			buf.append("\t\t");
		}
		return buf.toString();
	}

	private void printPrimaryKeyHashCodeClauseOn(ORMGenColumn column, StringBuilder buf) {
		String fieldName = column.getPropertyName();
		JavaType javaType = column.getDbColumn().getPrimaryKeyJavaType();
		if (javaType.isPrimitive()) {
			this.printPrimitiveHashCodeClauseOn(javaType.getElementTypeName(), fieldName, buf);
		}
		else {
			this.printReferenceHashCodeClauseOn(fieldName, buf);
		}
	}

	private void printPrimitiveHashCodeClauseOn(String primitiveName, String fieldName, StringBuilder buf) {
		if (primitiveName.equals("int")) {
			// this.value
			buf.append("this.");
			buf.append(fieldName);
		}
		else if (primitiveName.equals("short") || primitiveName.equals("byte") || primitiveName.equals("char")) { // explicit
																													// cast
			// ((int) this.value)
			buf.append("((int) this.");
			buf.append(fieldName);
			buf.append(')');
		}
		else if (primitiveName.equals("long")) { // cribbed from Long#hashCode()
			// ((int) (this.value ^ (this.value >>> 32)))
			buf.append("((int) (this.");
			buf.append(fieldName);
			buf.append(" ^ (this.");
			buf.append(fieldName);
			buf.append(" >>> 32)))");
		}
		else if (primitiveName.equals("float")) { // cribbed from
													// Float#hashCode()
			// java.lang.Float.floatToIntBits(this.value)
			buf.append("java.lang.Float");
			buf.append(".floatToIntBits(this.");
			buf.append(fieldName);
			buf.append(')');
		}
		else if (primitiveName.equals("double")) { // cribbed from
													// Double#hashCode()
			// ((int) (java.lang.Double.doubleToLongBits(this.value) ^
			// (java.lang.Double.doubleToLongBits(this.value) >>> 32)))
			buf.append("((int) (");
			buf.append("java.lang.Double");
			buf.append(".doubleToLongBits(this.");
			buf.append(fieldName);
			buf.append(") ^ (");
			buf.append("java.lang.Double");
			buf.append(".doubleToLongBits(this.");
			buf.append(fieldName);
			buf.append(") >>> 32)))");
		}
		else if (primitiveName.equals("boolean")) {
			// (this.value ? 1 : 0)
			buf.append("(this.");
			buf.append(fieldName);
			buf.append(" ? 1 : 0)");
		}
		else {
			throw new IllegalArgumentException(primitiveName);
		}
	}

	private void printReferenceHashCodeClauseOn(String fieldName, StringBuilder buf) {
		buf.append("this.");
		buf.append(fieldName);
		buf.append(".hashCode()");
	}

	@Override
	public String toString() {
		return "name=" + this.getName() + "; columns=" + Arrays.toString(this.getColumnNames().toArray());
	}

	/* class scopes */
	public static final String PUBLIC_SCOPE = "public";

	public static final String PROTECTED_SCOPE = "protected";

	public static final String PRIVATE_SCOPE = "private";

	/* access constants. Note that these strings are used in the ui */
	public static final String PROPERTY_ACCESS = "property";

	public static final String FIELD_ACCESS = "field";

	/*
	 * default fech constants. Note that these strings are used in the gen
	 * velocity templates.
	 */
	public static final String DEFAULT_FETCH = "defaultFetch";

	public static final String LAZY_FETCH = "lazy";

	public static final String EAGER_FETCH = "eager";

	/*
	 * default collection type constants. Note that these strings are used in
	 * the gen velocity templates.
	 */
	public static final String LIST_COLLECTION_TYPE = "java.util.List";

	public static final String SET_COLLECTION_TYPE = "java.util.Set";

	/**
	 * The pattern replaced by the table name in the id generator sequence name
	 * param.
	 */
	public static final String TABLE_SEQ_PATTERN = "$table";

	/**
	 * The pattern replaced by the primary key in the id generator sequence name
	 * param.
	 */
	public static final String PK_SEQ_PATTERN = "$pk";

	/* customization properties */
	private static final String PACKAGE = "package";

	private static final String SRC_FOLDER = "srcFolder";

	private static final String CLASS_NAME = "className";

	private static final String CLASS_DESC = "classDesc";

	private static final String CLASS_SCOPE = "classScope";

	private static final String EXTENDS = "extends";

	private static final String IMPLEMENTS = "implements";

	private static final String ID_GENERATOR = "idGenerator";

	private static final String SEQUENCE = "sequence";

	private static final String COMPOSITE_KEY_CLASS_NAME = "compositeKeyClassName";

	private static final String IMPLEMENT_EQUALS = "implementEquals";

	private static final String ACCESS = "access";

	private static final String DEFAULT_COLLECTION_TYPE = "defaultCollectionType";
}
