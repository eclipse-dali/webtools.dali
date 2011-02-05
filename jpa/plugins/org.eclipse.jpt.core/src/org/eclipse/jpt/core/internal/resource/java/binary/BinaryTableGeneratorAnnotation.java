/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;

/**
 * javax.persistence.TableGenerator
 */
public final class BinaryTableGeneratorAnnotation
	extends BinaryGeneratorAnnotation
	implements TableGeneratorAnnotation
{
	private String table;
	private String schema;
	private String catalog;
	private String pkColumnName;
	private String valueColumnName;
	private String pkColumnValue;
	private final Vector<UniqueConstraintAnnotation> uniqueConstraints;


	public BinaryTableGeneratorAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.table = this.buildTable();
		this.schema = this.buildSchema();
		this.catalog = this.buildCatalog();
		this.pkColumnName = this.buildPkColumnName();
		this.valueColumnName = this.buildValueColumnName();
		this.pkColumnValue = this.buildPkColumnValue();
		this.uniqueConstraints = this.buildUniqueConstraints();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setTable_(this.buildTable());
		this.setSchema_(this.buildSchema());
		this.setCatalog_(this.buildCatalog());
		this.setPkColumnName_(this.buildPkColumnName());
		this.setValueColumnName_(this.buildValueColumnName());
		this.setPkColumnValue_(this.buildPkColumnValue());
		this.updateUniqueConstraints();
	}


	// ********** AbstractGeneratorAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.TABLE_GENERATOR__NAME;
	}

	@Override
	String getInitialValueElementName() {
		return JPA.TABLE_GENERATOR__INITIAL_VALUE;
	}

	@Override
	String getAllocationSizeElementName() {
		return JPA.TABLE_GENERATOR__ALLOCATION_SIZE;
	}


	// ********** TableGeneratorAnnotation implementation **********

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		throw new UnsupportedOperationException();
	}

	private void setTable_(String table) {
		String old = this.table;
		this.table = table;
		this.firePropertyChanged(TABLE_PROPERTY, old, table);
	}

	private String buildTable() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__TABLE);
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** schema
	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		throw new UnsupportedOperationException();
	}

	private void setSchema_(String schema) {
		String old = this.schema;
		this.schema = schema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, schema);
	}

	private String buildSchema() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__SCHEMA);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** catalog
	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		throw new UnsupportedOperationException();
	}

	private void setCatalog_(String catalog) {
		String old = this.catalog;
		this.catalog = catalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, catalog);
	}

	private String buildCatalog() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__CATALOG);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** pk column name
	public String getPkColumnName() {
		return this.pkColumnName;
	}

	public void setPkColumnName(String pkColumnName) {
		throw new UnsupportedOperationException();
	}

	private void setPkColumnName_(String pkColumnName) {
		String old = this.pkColumnName;
		this.pkColumnName = pkColumnName;
		this.firePropertyChanged(PK_COLUMN_NAME_PROPERTY, old, pkColumnName);
	}

	private String buildPkColumnName() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__PK_COLUMN_NAME);
	}

	public TextRange getPkColumnNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean pkColumnNameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** value column name
	public String getValueColumnName() {
		return this.valueColumnName;
	}

	public void setValueColumnName(String valueColumnName) {
		throw new UnsupportedOperationException();
	}

	private void setValueColumnName_(String valueColumnName) {
		String old = this.valueColumnName;
		this.valueColumnName = valueColumnName;
		this.firePropertyChanged(VALUE_COLUMN_NAME_PROPERTY, old, valueColumnName);
	}

	private String buildValueColumnName() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__VALUE_COLUMN_NAME);
	}

	public TextRange getValueColumnNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean valueColumnNameTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** pk column value
	public String getPkColumnValue() {
		return this.pkColumnValue;
	}

	public void setPkColumnValue(String pkColumnValue) {
		throw new UnsupportedOperationException();
	}

	private void setPkColumnValue_(String pkColumnValue) {
		String old = this.pkColumnValue;
		this.pkColumnValue = pkColumnValue;
		this.firePropertyChanged(PK_COLUMN_VALUE_PROPERTY, old, pkColumnValue);
	}

	private String buildPkColumnValue() {
		return (String) this.getJdtMemberValue(JPA.TABLE_GENERATOR__PK_COLUMN_VALUE);
	}

	public TextRange getPkColumnValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public boolean pkColumnValueTouches(int pos, CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** unique constraints
	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraintAnnotation>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}

	public int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint) {
		return this.uniqueConstraints.indexOf(uniqueConstraint);
	}

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	private Vector<UniqueConstraintAnnotation> buildUniqueConstraints() {
		Object[] jdtUniqueConstraints = this.getJdtMemberValues(JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS);
		Vector<UniqueConstraintAnnotation> result = new Vector<UniqueConstraintAnnotation>(jdtUniqueConstraints.length);
		for (Object jdtUniqueConstraint : jdtUniqueConstraints) {
			result.add(new BinaryUniqueConstraintAnnotation(this, (IAnnotation) jdtUniqueConstraint));
		}
		return result;
	}

	// TODO
	private void updateUniqueConstraints() {
		throw new UnsupportedOperationException();
	}

}
