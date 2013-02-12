/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.db.Table;

/**
 * 
 */
public class NullJavaMapKeyColumn2_0
	extends AbstractJavaJpaContextNode
	implements JavaColumn
{
	public NullJavaMapKeyColumn2_0(JavaRelationshipMapping parent) {
		super(parent);
	}


	// ********** column annotation **********

	public ColumnAnnotation getColumnAnnotation() {
		return (ColumnAnnotation) this.getParent().getPersistentAttribute().getResourceAttribute().getNonNullAnnotation(ColumnAnnotation.ANNOTATION_NAME);
	}

	public boolean isVirtual() {
		throw new UnsupportedOperationException();
	}

	// ********** name **********

	public String getName() {
		return null;
	}

	public String getSpecifiedName() {
		return null;
	}

	public void setSpecifiedName(String name) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultName() {
		return null;
	}


	// ********** column definition **********

	public String getColumnDefinition() {
		return null;
	}

	public void setColumnDefinition(String columnDefinition) {
		throw new UnsupportedOperationException();
	}


	// ********** table name **********

	public String getTableName() {
		return null;
	}

	public String getSpecifiedTableName() {
		return null;
	}

	public void setSpecifiedTableName(String table) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultTableName() {
		return null;
	}


	// ********** unique **********

	public boolean isUnique() {
		return false;
	}

	public Boolean getSpecifiedUnique() {
		return null;
	}

	public void setSpecifiedUnique(Boolean unique) {
		throw new UnsupportedOperationException();
	}

	public boolean isDefaultUnique() {
		return false;
	}


	// ********** nullable **********

	public boolean isNullable() {
		return false;
	}

	public Boolean getSpecifiedNullable() {
		return null;
	}

	public void setSpecifiedNullable(Boolean nullable) {
		throw new UnsupportedOperationException();
	}

	public boolean isDefaultNullable() {
		return false;
	}


	// ********** insertable **********

	public boolean isInsertable() {
		return false;
	}

	public Boolean getSpecifiedInsertable() {
		return null;
	}

	public void setSpecifiedInsertable(Boolean insertable) {
		throw new UnsupportedOperationException();
	}

	public boolean isDefaultInsertable() {
		return false;
	}


	// ********** updatable **********

	public boolean isUpdatable() {
		return false;
	}

	public Boolean getSpecifiedUpdatable() {
		return null;
	}

	public void setSpecifiedUpdatable(Boolean updatable) {
		throw new UnsupportedOperationException();
	}

	public boolean isDefaultUpdatable() {
		return false;
	}


	// ********** length **********

	public int getLength() {
		return 0;
	}

	public Integer getSpecifiedLength() {
		return null;
	}

	public void setSpecifiedLength(Integer length) {
		throw new UnsupportedOperationException();
	}

	public int getDefaultLength() {
		return 0;
	}


	// ********** precision **********

	public int getPrecision() {
		return 0;
	}

	public Integer getSpecifiedPrecision() {
		return null;
	}

	public void setSpecifiedPrecision(Integer precision) {
		throw new UnsupportedOperationException();
	}

	public int getDefaultPrecision() {
		return 0;
	}


	// ********** scale **********

	public int getScale() {
		return 0;
	}

	public Integer getSpecifiedScale() {
		return null;
	}

	public void setSpecifiedScale(Integer scale) {
		throw new UnsupportedOperationException();
	}

	public int getDefaultScale() {
		return 0;
	}


	// ********** misc **********

	@Override
	public JavaRelationshipMapping getParent() {
		return (JavaRelationshipMapping) super.getParent();
	}

	public void initializeFrom(ReadOnlyColumn oldColumn) {
		// NOP
	}

	public void initializeFromVirtual(ReadOnlyColumn virtualColumn) {
		// NOP
	}

	public Iterable<String> getCandidateTableNames() {
		return EmptyIterable.instance();
	}


	// ********** database stuff **********

	public org.eclipse.jpt.jpa.db.Column getDbColumn() {
		return null;
	}

	public Table getDbTable() {
		return null;
	}

	public boolean isResolved() {
		return false;
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getParent().getValidationTextRange();
	}

	public TextRange getNameValidationTextRange() {
		return this.getValidationTextRange();
	}

	public TextRange getTableNameValidationTextRange() {
		return this.getValidationTextRange();
	}

	public boolean tableNameIsInvalid() {
		return false;
	}
}
