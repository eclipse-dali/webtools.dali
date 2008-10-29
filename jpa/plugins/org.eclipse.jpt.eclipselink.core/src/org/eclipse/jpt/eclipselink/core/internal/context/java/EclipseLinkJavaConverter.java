/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.NamedConverterAnnotation;

public abstract class EclipseLinkJavaConverter extends AbstractJavaJpaContextNode
	implements EclipseLinkConverter
{
	private JavaResourcePersistentMember resourcePersistentMember;
	
	private String name;
	
	
	protected EclipseLinkJavaConverter(JavaJpaContextNode parent, JavaResourcePersistentMember jrpm) {
		super(parent);
		this.initialize(jrpm);
	}
	
	
	protected NamedConverterAnnotation getAnnotation() {
		return (NamedConverterAnnotation) this.resourcePersistentMember.getSupportingAnnotation(getAnnotationName());
	}
	
	protected abstract String getAnnotationName();
	
	
	// **************** name ***************************************************
	
	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		String oldName = this.name;
		this.name = newName;
		getAnnotation().setName(newName);
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}

	protected void setName_(String newName) {
		String oldName = this.name;
		this.name = newName;
		firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}
	
	
	// **************** resource interaction ***********************************
	
	protected void initialize(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		this.name = this.name(getAnnotation());
	}
	
	protected void update(JavaResourcePersistentMember jrpm) {
		this.resourcePersistentMember = jrpm;
		this.setName_(this.name(getAnnotation()));
	}
	
	protected String name(NamedConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getName();
	}
	
	
	// **************** validation *********************************************
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation().getTextRange(astRoot);
	}
}
