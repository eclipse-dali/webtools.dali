/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
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
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.utility.internal.StringTools;

public abstract class JavaEclipseLinkConverter extends AbstractJavaJpaContextNode
	implements EclipseLinkConverter
{
	private JavaResourcePersistentMember resourcePersistentMember;
	
	private String name;
	
	
	protected JavaEclipseLinkConverter(JavaJpaContextNode parent) {
		super(parent);
	}
	
	
	protected EclipseLinkNamedConverterAnnotation getAnnotation() {
		return (EclipseLinkNamedConverterAnnotation) this.resourcePersistentMember.getSupportingAnnotation(getAnnotationName());
	}
	
	protected abstract String getAnnotationName();
	
	@Override
	public EclipseLinkPersistenceUnit getPersistenceUnit() {
		return (EclipseLinkPersistenceUnit) super.getPersistenceUnit();
	}
	
	public char getEnclosingTypeSeparator() {
		return '.';
	}
	
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
		getPersistenceUnit().addConverter(this);
	}
	
	protected String name(EclipseLinkNamedConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getName();
	}
	
	
	// **************** validation *********************************************
	
	public boolean overrides(EclipseLinkConverter converter) {
		// java is at the base of the tree
		return false;
	}
	
	public boolean duplicates(EclipseLinkConverter converter) {
		return (this != converter)
				&& ! StringTools.stringIsEmpty(this.name)
				&& this.name.equals(converter.getName())
				&& ! this.overrides(converter)
				&& ! converter.overrides(this);
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return getAnnotation().getTextRange(astRoot);
	}
}
