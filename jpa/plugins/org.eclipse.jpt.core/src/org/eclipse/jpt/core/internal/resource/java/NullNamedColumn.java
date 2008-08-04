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
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NamedColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;


public abstract class NullNamedColumn extends AbstractJavaResourceNode implements NamedColumnAnnotation, Annotation
{	
	protected NullNamedColumn(JavaResourceNode parent) {
		super(parent);
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
	
	protected abstract NamedColumnAnnotation createColumnResource();

	public String getName() {
		return null;
	}
	
	public void setName(String name) {
		if (name != null) {
			createColumnResource().setName(name);
		}		
	}

	public String getColumnDefinition() {
		return null;
	}
	
	public void setColumnDefinition(String columnDefinition) {
		if (columnDefinition != null) {
			createColumnResource().setColumnDefinition(columnDefinition);
		}		
	}

	public void update(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getColumnDefinitionTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
}
