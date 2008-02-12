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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public abstract class NullNamedColumn extends AbstractResource implements NamedColumn, Annotation
{	
	protected NullNamedColumn(JavaResource parent) {
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
	
	protected abstract NamedColumn createColumnResource();

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

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange columnDefinitionTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
}
