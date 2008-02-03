/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public abstract class NullAbstractTable extends AbstractResource implements Table, Annotation
{
	protected NullAbstractTable(JavaResource parent) {
		super(parent);
	}
	
	public void initialize(CompilationUnit astRoot) {
		//null, nothing to initialize
	}
	
	public org.eclipse.jdt.core.dom.Annotation jdtAnnotation(CompilationUnit astRoot) {
		return null;
	}
	
	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public JavaPersistentResource parent() {
		return (JavaPersistentResource) super.parent();
	}
	
	protected Table createTableResource() {
		return (Table) parent().addAnnotation(getAnnotationName());
	}
	
	public String getName() {
		return null;
	}
	
	public void setName(String name) {
		if (name != null) {
			createTableResource().setName(name);
		}
	}

	public String getCatalog() {
		return null;
	}
	
	public void setCatalog(String catalog) {
		if (catalog != null) {
			createTableResource().setCatalog(catalog);
		}
	}

	public String getSchema() {
		return null;
	}
	
	public void setSchema(String schema) {
		if (schema != null) {
			createTableResource().setSchema(schema);
		}
	}

	public UniqueConstraint addUniqueConstraint(int index) {
		return createTableResource().addUniqueConstraint(index);		
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	public int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint) {
		throw new UnsupportedOperationException();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	public UniqueConstraint uniqueConstraintAt(int index) {
		throw new UnsupportedOperationException();
	}

	public ListIterator<UniqueConstraint> uniqueConstraints() {
		return EmptyListIterator.instance();
	}

	public int uniqueConstraintsSize() {
		return 0;
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange catalogTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange schemaTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return false;
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
