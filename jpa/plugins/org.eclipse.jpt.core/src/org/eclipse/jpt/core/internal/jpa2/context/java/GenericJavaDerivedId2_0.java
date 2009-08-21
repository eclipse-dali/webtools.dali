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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericJavaDerivedId2_0
	extends AbstractJavaJpaContextNode
	implements JavaDerivedId2_0
{
	protected boolean derivedId;
	
	
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
	
	public boolean isDerivedId() {
		return this.derivedId;
	}
	
	public void setDerivedId(boolean newDerivedId) {
		if (this.derivedId == newDerivedId) {
			return;
		}
		boolean oldDerivedId = this.derivedId;
		this.derivedId = newDerivedId;
		
		if (newDerivedId) {
			addIdAnnotation();
		}
		else {
			removeIdAnnotation();
		}
		firePropertyChanged(DERIVED_ID_PROPERTY, oldDerivedId, newDerivedId);
	}
	
	protected void setDerivedId_(boolean newDerivedId) {
		boolean oldDerivedId = this.derivedId;
		this.derivedId = newDerivedId;
		firePropertyChanged(DERIVED_ID_PROPERTY, oldDerivedId, newDerivedId);
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
		this.derivedId = getResourceDerivedId();
	}
	
	public void update() {
		this.setDerivedId_(getResourceDerivedId());
	}
	
	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		IdAnnotation annotation = this.getIdAnnotation();
		return annotation == null ? null : annotation.getTextRange(astRoot);
	}
}
