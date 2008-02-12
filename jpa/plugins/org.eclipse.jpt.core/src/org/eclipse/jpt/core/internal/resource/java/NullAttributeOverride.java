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
	//TODO should I hold on to the IColumnMapping that this attribute override is built from?
	//this would make it more similar to the Virtual mappings concept in xml
	
	
	private final NullAttributeOverrideColumn column;
	
	private String name;
	
	public NullAttributeOverride(JavaResource parent, String name) {
		super(parent);
		this.name = name;
		this.column = new NullAttributeOverrideColumn(this);
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
		return this.name;
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
		return this.column;
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
