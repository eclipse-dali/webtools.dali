/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;


public class VirtualAttributeOverride extends AbstractJavaResourceNode implements AttributeOverrideAnnotation, Annotation
{	
	private final VirtualAttributeOverrideColumn column;
	
	private String name;
		
	public VirtualAttributeOverride(JavaResourceNode parent, String name, Column column) {
		super(parent);
		this.name = name;
		this.column = new VirtualAttributeOverrideColumn(this, column);
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
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	protected AttributeOverrideAnnotation createAttributeOverrideResource() {
		return (AttributeOverrideAnnotation) getParent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return false;
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

}
