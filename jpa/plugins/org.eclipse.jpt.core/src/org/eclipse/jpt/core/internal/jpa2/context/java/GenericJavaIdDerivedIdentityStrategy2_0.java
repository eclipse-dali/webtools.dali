/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaIdDerivedIdentityStrategy2_0
	extends AbstractJavaJpaContextNode
	implements JavaIdDerivedIdentityStrategy2_0
{
	protected boolean value;
	
	
	public GenericJavaIdDerivedIdentityStrategy2_0(JavaDerivedIdentity2_0 parent) {
		super(parent);
	}
	
	
	public JavaDerivedIdentity2_0 getDerivedIdentity() {
		return (JavaDerivedIdentity2_0) super.getParent();
	}
	
	public JavaSingleRelationshipMapping2_0 getMapping() {
		return getDerivedIdentity().getMapping();
	}
	
	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return getMapping().getPersistentAttribute().getResourcePersistentAttribute();
	}
	
	protected IdAnnotation getAnnotation() {
		return (IdAnnotation) getResourceAttribute().getAnnotation(JPA.ID);
	}
	
	protected boolean getResourceValue() {
		return getAnnotation() != null;
	}
	
	protected void addAnnotation() {
		getResourceAttribute().addAnnotation(JPA.ID);
	}
	
	protected void removeAnnotation() {
		getResourceAttribute().removeAnnotation(JPA.ID);
	}
	
	public boolean getValue() {
		return this.value;
	}
	
	public void setValue(boolean newValue) {
		if (this.value == newValue) {
			return;
		}
		boolean oldValue = this.value;
		this.value = newValue;
		
		if (newValue) {
			addAnnotation();
		}
		else {
			removeAnnotation();
		}
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setValue_(boolean newValue) {
		boolean oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	public boolean isSpecified() {
		return getAnnotation() != null;
	}
	
	public void addStrategy() {
		if (getAnnotation() == null) {
			addAnnotation();
		}
	}
	
	public void removeStrategy() {
		if (getAnnotation() != null) {
			removeAnnotation();
		}
	}
	
	public void initialize() {
		this.value = getResourceValue();
	}
	
	public void update() {
		this.setValue_(getResourceValue());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		IdAnnotation annotation = this.getAnnotation();
		return annotation == null ? null : annotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// no validation rules
	}
}
