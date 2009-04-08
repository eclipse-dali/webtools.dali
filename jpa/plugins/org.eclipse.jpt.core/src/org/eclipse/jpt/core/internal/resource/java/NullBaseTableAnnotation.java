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

import java.util.ListIterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

/**
 * javax.persistence.Table
 * javax.persistence.JoinTable
 */
public abstract class NullBaseTableAnnotation
	extends NullAnnotation
	implements BaseTableAnnotation
{
	protected NullBaseTableAnnotation(JavaResourcePersistentMember parent) {
		super(parent);
	}

	@Override
	protected BaseTableAnnotation buildSupportingAnnotation() {
		return (BaseTableAnnotation) super.buildSupportingAnnotation();
	}

	// ***** name
	public String getName() {
		return null;
	}

	public void setName(String name) {
		if (name != null) {
			this.buildSupportingAnnotation().setName(name);
		}
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** schema
	public String getSchema() {
		return null;
	}

	public void setSchema(String schema) {
		if (schema != null) {
			this.buildSupportingAnnotation().setSchema(schema);
		}
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** catalog
	public String getCatalog() {
		return null;
	}

	public void setCatalog(String catalog) {
		if (catalog != null) {
			this.buildSupportingAnnotation().setCatalog(catalog);
		}
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	// ***** unique constraints
	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return EmptyListIterator.instance();
	}

	public int uniqueConstraintsSize() {
		return 0;
	}

	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		throw new UnsupportedOperationException();
	}

	public int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint) {
		throw new UnsupportedOperationException();
	}

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		return this.buildSupportingAnnotation().addUniqueConstraint(index);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

}
