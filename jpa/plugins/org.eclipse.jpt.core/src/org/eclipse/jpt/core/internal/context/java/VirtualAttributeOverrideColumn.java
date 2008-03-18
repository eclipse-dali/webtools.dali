/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.internal.resource.java.NullAbstractColumn;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;


public class VirtualAttributeOverrideColumn extends NullAbstractColumn implements ColumnAnnotation, Annotation
{	
	private Column column;
	
	public VirtualAttributeOverrideColumn(AttributeOverrideAnnotation parent, Column column) {
		super(parent);
		this.column = column;
	}

	@Override
	public AttributeOverrideAnnotation parent() {
		return (AttributeOverrideAnnotation) super.parent();
	}
	
	public String getAnnotationName() {
		return ColumnAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected ColumnAnnotation createColumnResource() {
		return parent().addColumn();
	}

	@Override
	public String getName() {
		if (this.column.getSpecifiedName() != null) {
			return this.column.getSpecifiedName();
		}
		return null;
	}
	
	@Override
	public String getTable() {
		if (this.column.getSpecifiedTable() != null) {
			return this.column.getSpecifiedTable();
		}
		return null;
	}
	
	@Override
	public Boolean getInsertable() {
		if (this.column.getSpecifiedInsertable() != null) {
			return this.column.getSpecifiedInsertable();
		}
		return null;
	}
	
	@Override
	public Boolean getUpdatable() {
		if (this.column.getSpecifiedUpdatable() != null) {
			return this.column.getSpecifiedUpdatable();
		}
		return null;
	}
	@Override
	public Boolean getNullable() {
		if (this.column.getSpecifiedNullable() != null) {
			return this.column.getSpecifiedNullable();
		}
		return null;
	}
	
	@Override
	public Boolean getUnique() {
		if (this.column.getSpecifiedUnique() != null) {
			return this.column.getSpecifiedUnique();
		}
		return null;
	}
	
	@Override
	public String getColumnDefinition() {
		return this.column.getColumnDefinition();
	}
	
	public Integer getLength() {
		if (this.column.getSpecifiedLength() != null) {
			return this.column.getSpecifiedLength();
		}
		return null;
	}
	
	public void setLength(Integer length) {
		if (length != null) {
			createColumnResource().setLength(length);
		}
	}
	
	public Integer getScale() {
		if (this.column.getSpecifiedScale() != null) {
			return this.column.getSpecifiedScale();
		}
		return null;
	}
	
	public void setScale(Integer scale) {
		if (scale != null) {
			createColumnResource().setScale(scale);
		}
	}
	
	public Integer getPrecision() {
		if (this.column.getSpecifiedPrecision() != null) {
			return this.column.getSpecifiedPrecision();
		}
		return null;
	}
	
	public void setPrecision(Integer precision) {
		if (precision != null) {
			createColumnResource().setPrecision(precision);
		}
	}
	
	public TextRange scaleTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange lengthTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange precisionTextRange(CompilationUnit astRoot) {
		return null;
	}
}
