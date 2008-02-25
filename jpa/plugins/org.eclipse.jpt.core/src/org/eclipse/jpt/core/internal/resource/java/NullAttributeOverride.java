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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;


public class NullAttributeOverride extends AbstractResource implements AttributeOverrideAnnotation, Annotation
{	
	//TODO should I hold on to the IColumnMapping that this attribute override is built from?
	//this would make it more similar to the Virtual mappings concept in xml
	
	
	private final NullAttributeOverrideColumn column;
	
	private String name;
	
	public NullAttributeOverride(JavaResourceNode parent, String name) {
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
		return AttributeOverrideAnnotation.ANNOTATION_NAME;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (name != null) {
			createAttributeOverrideResource().setName(name);
		}		
	}

	public ColumnAnnotation getNonNullColumn() {
		return getColumn();
	}
	
	public ColumnAnnotation getColumn() {
		return this.column;
	}
	
	public ColumnAnnotation addColumn() {
		throw new UnsupportedOperationException();
	}
	
	public void removeColumn() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public JavaResourcePersistentMember parent() {
		return (JavaResourcePersistentMember) super.parent();
	}
	
	protected AttributeOverrideAnnotation createAttributeOverrideResource() {
		return (AttributeOverrideAnnotation) parent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	public TextRange nameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return null;
	}

}
