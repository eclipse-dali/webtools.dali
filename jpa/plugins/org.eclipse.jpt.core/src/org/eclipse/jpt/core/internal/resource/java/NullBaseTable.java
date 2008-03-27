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

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.TableAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;

public abstract class NullBaseTable extends AbstractJavaResourceNode implements Annotation
{
	protected NullBaseTable(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	public void initialize(CompilationUnit astRoot) {
		//null, nothing to initialize
	}
	
	public org.eclipse.jdt.core.dom.Annotation getJdtAnnotation(CompilationUnit astRoot) {
		return null;
	}
	
	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}
	
	protected TableAnnotation createTableResource() {
		return (TableAnnotation) getParent().addAnnotation(getAnnotationName());
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

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		return createTableResource().addUniqueConstraint(index);		
	}

	public void removeUniqueConstraint(int index) {
		throw new UnsupportedOperationException();
	}

	public int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint) {
		throw new UnsupportedOperationException();
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		throw new UnsupportedOperationException();
	}
	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		throw new UnsupportedOperationException();
	}

	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return EmptyListIterator.instance();
	}

	public int uniqueConstraintsSize() {
		return 0;
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
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
