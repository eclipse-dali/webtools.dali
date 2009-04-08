/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * javax.persistence.Column
 * javax.persistence.JoinColumn
 */
public abstract class NullBaseColumnAnnotation
	extends NullNamedColumnAnnotation
	implements BaseColumnAnnotation
{
	protected NullBaseColumnAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	@Override
	protected abstract BaseColumnAnnotation buildAnnotation();

	// ***** table
	public String getTable() {
		return null;
	}

	public void setTable(String table) {
		if (table != null) {
			this.buildAnnotation().setTable(table);
		}
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** unique
	public Boolean getUnique() {
		return null;
	}

	public void setUnique(Boolean unique) {
		if (unique != null) {
			this.buildAnnotation().setUnique(unique);
		}
	}

	public TextRange getUniqueTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** updatable
	public Boolean getUpdatable() {
		return null;
	}

	public void setUpdatable(Boolean updatable) {
		if (updatable != null) {
			this.buildAnnotation().setUpdatable(updatable);
		}
	}

	public TextRange getUpdatableTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** insertable
	public Boolean getInsertable() {
		return null;
	}

	public void setInsertable(Boolean insertable) {
		if (insertable != null) {
			this.buildAnnotation().setInsertable(insertable);
		}
	}

	public TextRange getInsertableTextRange(CompilationUnit astRoot) {
		return null;
	}

	// ***** nullable
	public Boolean getNullable() {
		return null;
	}

	public void setNullable(Boolean nullable) {
		if (nullable != null) {
			this.buildAnnotation().setNullable(nullable);
		}
	}

	public TextRange getNullableTextRange(CompilationUnit astRoot) {
		return null;
	}

}
