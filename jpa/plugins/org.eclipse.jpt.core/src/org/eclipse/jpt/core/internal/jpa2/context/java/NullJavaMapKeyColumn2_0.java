/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class NullJavaMapKeyColumn2_0
	extends AbstractJavaJpaContextNode
	implements JavaColumn
{

	public NullJavaMapKeyColumn2_0(JavaJpaContextNode parent) {
		super(parent);
	}

	public void initialize(JavaResourcePersistentAttribute resource) {
		//no-op
	}

	public void update(JavaResourcePersistentAttribute resource) {
		//no-op
	}

	public void initialize(ColumnAnnotation resourceColumn) {
		//no-op
	}

	public void update(ColumnAnnotation resourceColumn) {
		//no-op
	}

	public String getName() {
		return null;
	}

	public String getSpecifiedName() {
		return null;
	}

	public void setSpecifiedName(String newSpecifiedName) {
		throw new UnsupportedOperationException();
	}

	public String getDefaultName() {
		return null;
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean isNullable() {
		return false;
	}

	public boolean isDefaultNullable() {
		return false;
	}

	public Boolean getSpecifiedNullable() {
		return null;
	}

	public void setSpecifiedNullable(Boolean newSpecifiedNullable) {
		throw new UnsupportedOperationException();
	}

	public boolean isInsertable() {
		return false;
	}

	public boolean isDefaultInsertable() {
		return false;
	}

	public Boolean getSpecifiedInsertable() {
		return null;
	}

	public void setSpecifiedInsertable(Boolean newSpecifiedInsertable) {
		throw new UnsupportedOperationException();
	}

	public boolean isUpdatable() {
		return false;
	}

	public boolean isDefaultUpdatable() {
		return false;
	}

	public Boolean getSpecifiedUpdatable() {
		return null;
	}

	public void setSpecifiedUpdatable(Boolean newSpecifiedUpdatable) {
		throw new UnsupportedOperationException();
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return null;
	}

	public int getDefaultLength() {
		return 0;
	}

	public int getDefaultPrecision() {
		return 0;
	}

	public int getDefaultScale() {
		return 0;
	}

	public int getLength() {
		return 0;
	}

	public int getPrecision() {
		return 0;
	}

	public int getScale() {
		return 0;
	}

	public Integer getSpecifiedLength() {
		return null;
	}

	public Integer getSpecifiedPrecision() {
		return null;
	}

	public Integer getSpecifiedScale() {
		return null;
	}

	public void setSpecifiedLength(Integer newSpecifiedLength) {
		throw new UnsupportedOperationException();
	}

	public void setSpecifiedPrecision(Integer newSpecifiedPrecision) {
		throw new UnsupportedOperationException();
	}

	public void setSpecifiedScale(Integer newSpecifiedScale) {
		throw new UnsupportedOperationException();
	}

	public Iterator<String> candidateTableNames() {
		return EmptyIterator.instance();
	}

	public String getDefaultTable() {
		return null;
	}

	public String getSpecifiedTable() {
		return null;
	}

	public String getTable() {
		return null;
	}

	public void setSpecifiedTable(String value) {
		throw new UnsupportedOperationException();
	}

	public Boolean getSpecifiedUnique() {
		return null;
	}

	public void setSpecifiedUnique(Boolean newSpecifiedUnique) {
		throw new UnsupportedOperationException();
	}

	public boolean isDefaultUnique() {
		return false;
	}

	public boolean isUnique() {
		return false;
	}

	public String getColumnDefinition() {
		return null;
	}

	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException();
	}

	public boolean tableNameIsInvalid() {
		return false;
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return null;
	}

	public Column getDbColumn() {
		return null;
	}

	public Table getDbTable() {
		return null;
	}

	public boolean isResolved() {
		return false;
	}

}
