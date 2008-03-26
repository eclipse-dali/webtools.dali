/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.BaseColumnAnnotation;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;


public abstract class NullBaseColumn extends NullNamedColumn implements BaseColumnAnnotation, Annotation
{	
	protected NullBaseColumn(JavaResourceNode parent) {
		super(parent);
	}

	@Override
	protected abstract BaseColumnAnnotation createColumnResource();

	public String getTable() {
		return null;
	}
	
	public void setTable(String table) {
		if (table != null) {
			createColumnResource().setTable(table);
		}
	}
	
	public Boolean getUnique() {
		return null;
	}
	
	public void setUnique(Boolean unique) {
		if (unique != null) {
			createColumnResource().setUnique(unique);
		}
	}
	
	public Boolean getUpdatable() {
		return null;
	}
	
	public void setUpdatable(Boolean updatable) {
		if (updatable != null) {
			createColumnResource().setUpdatable(updatable);
		}
	}
	
	public Boolean getInsertable() {
		return null;
	}
	
	public void setInsertable(Boolean insertable) {
		if (insertable != null) {
			createColumnResource().setInsertable(insertable);
		}
	}
	
	public Boolean getNullable() {
		return null;
	}
	
	public void setNullable(Boolean nullable) {
		if (nullable != null) {
			createColumnResource().setNullable(nullable);
		}
	}
	
	public TextRange tableTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange uniqueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange updatableTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange insertableTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange nullableTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
}
