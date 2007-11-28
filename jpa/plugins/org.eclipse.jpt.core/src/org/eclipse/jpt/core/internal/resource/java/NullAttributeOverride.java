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


public class NullAttributeOverride extends AbstractResource implements AttributeOverride, Annotation
{	
	public NullAttributeOverride(JavaResource parent) {
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

	public String getAnnotationName() {
		return AttributeOverride.ANNOTATION_NAME;
	}

	public String getName() {
		return null;
	}
	
	public void setName(String name) {
		if (name != null) {
			createAttributeOverrideResource().setName(name);
		}		
	}

	public Column getNonNullColumn() {
		return getColumn();
	}
	
	public Column getColumn() {
		return new NullColumn(this);
	}
	
	public Column addColumn() {
		throw new UnsupportedOperationException();
	}
	
	public void removeColumn() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public JavaPersistentResource parent() {
		return (JavaPersistentResource) super.parent();
	}
	
	protected AttributeOverride createAttributeOverrideResource() {
		return (AttributeOverride) parent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	public ITextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return null;
	}

}
