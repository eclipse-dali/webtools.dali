/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;
import org.eclipse.jpt.core.utility.TextRange;


public class NullOneToOne extends AbstractJavaResourceNode implements OneToOneAnnotation, Annotation
{	
	protected NullOneToOne(JavaResourcePersistentMember parent) {
		super(parent);
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
		return OneToOneAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	protected OneToOneAnnotation createOneToOneResource() {
		getParent().setMappingAnnotation(getAnnotationName());
		return (OneToOneAnnotation) getParent().getMappingAnnotation();
	}

	public FetchType getFetch() {
		return null;
	}
	
	public void setFetch(FetchType fetch) {
		if (fetch != null) {
			createOneToOneResource().setFetch(fetch);
		}				
	}
	
	public Boolean getOptional() {
		return null;
	}

	public void setOptional(Boolean optional) {
		if (optional != null) {
			createOneToOneResource().setOptional(optional);
		}				
	}
	
	public String getMappedBy() {
		return null;
	}

	public void setMappedBy(String mappedBy) {
		
	}

	public String getFullyQualifiedTargetEntity() {
		return null;
	}

	public String getTargetEntity() {
		return null;
	}

	public void setTargetEntity(String targetEntity) {
		if (targetEntity != null) {
			createOneToOneResource().setTargetEntity(targetEntity);
		}				
	}

	public boolean isCascadeAll() {
		return false;
	}

	public void setCascadeAll(boolean all) {
		
	}

	public boolean isCascadeMerge() {
		return false;
	}

	public void setCascadeMerge(boolean merge) {
		
	}

	public boolean isCascadePersist() {
		return false;
	}

	public void setCascadePersist(boolean persist) {
		
	}

	public boolean isCascadeRefresh() {
		return false;
	}

	public void setCascadeRefresh(boolean refresh) {
		
	}

	public boolean isCascadeRemove() {
		return false;
	}

	public void setCascadeRemove(boolean remove) {
		
	}

	public void update(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getCascadeTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getTargetEntityTextRange(CompilationUnit astRoot) {
		return null;
	}

	public boolean mappedByTouches(int pos, CompilationUnit astRoot) {
		return false;
	}


}
