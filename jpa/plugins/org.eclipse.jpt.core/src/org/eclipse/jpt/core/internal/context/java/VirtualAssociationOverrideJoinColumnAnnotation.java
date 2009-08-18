/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.internal.resource.java.NullBaseColumnAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Column
 */
public final class VirtualAssociationOverrideJoinColumnAnnotation extends NullBaseColumnAnnotation
	implements JoinColumnAnnotation
{
	private JoinColumn joinColumn;

	public VirtualAssociationOverrideJoinColumnAnnotation(Annotation parent, JoinColumn joinColumn) {
		super(parent);
		this.joinColumn = joinColumn;
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected JoinColumnAnnotation addAnnotation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getName() {
		return this.joinColumn.getSpecifiedName();
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException();
	}
	
	public String getReferencedColumnName() {
		return this.joinColumn.getSpecifiedReferencedColumnName();
	}
	
	public void setReferencedColumnName(String referencedColumnName) {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getReferencedColumnNameTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	@Override
	public String getColumnDefinition() {
		return this.joinColumn.getColumnDefinition();
	}

	@Override
	public void setColumnDefinition(String columnDefinition) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getTable() {
		return this.joinColumn.getSpecifiedTable();
	}

	@Override
	public void setTable(String table) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Boolean getInsertable() {
		return this.joinColumn.getSpecifiedInsertable();
	}

	@Override
	public void setInsertable(Boolean insertable) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Boolean getUpdatable() {
		return this.joinColumn.getSpecifiedUpdatable();
	}

	@Override
	public void setUpdatable(Boolean updatable) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Boolean getNullable() {
		return this.joinColumn.getSpecifiedNullable();
	}

	@Override
	public void setNullable(Boolean nullable) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Boolean getUnique() {
		return this.joinColumn.getSpecifiedUnique();
	}

	@Override
	public void setUnique(Boolean unique) {
		throw new UnsupportedOperationException();
	}

}
