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
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaDerivedId2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedId2_0
{
	protected boolean value;
	
	
	public GenericJavaDerivedId2_0(JavaSingleRelationshipMapping2_0 parent) {
		super(parent);
	}
	
	
	@Override
	public JavaSingleRelationshipMapping2_0 getParent() {
		return (JavaSingleRelationshipMapping2_0) super.getParent();
	}
	
	protected JavaResourcePersistentAttribute getResourceAttribute() {
		return getParent().getPersistentAttribute().getResourcePersistentAttribute();
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
			addIdAnnotation();
		}
		else {
			removeIdAnnotation();
		}
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected void setValue_(boolean newValue) {
		boolean oldValue = this.value;
		this.value = newValue;
		firePropertyChanged(VALUE_PROPERTY, oldValue, newValue);
	}
	
	protected boolean getResourceDerivedId() {
		return getIdAnnotation() != null;
	}
	
	protected IdAnnotation getIdAnnotation() {
		return (IdAnnotation) getResourceAttribute().getAnnotation(JPA.ID);
	}
	
	protected void addIdAnnotation() {
		getResourceAttribute().addAnnotation(JPA.ID);
	}
	
	protected void removeIdAnnotation() {
		getResourceAttribute().removeAnnotation(JPA.ID);
	}
	
	public void initialize() {
		this.value = getResourceDerivedId();
	}
	
	public void update() {
		this.setValue_(getResourceDerivedId());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		IdAnnotation annotation = this.getIdAnnotation();
		return annotation == null ? null : annotation.getTextRange(astRoot);
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		// no validation rules
	}
}
