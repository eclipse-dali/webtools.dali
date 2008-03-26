/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.java.Basic;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;


public class NullBasic extends AbstractJavaResourceNode implements Basic, Annotation
{	
	protected NullBasic(JavaResourcePersistentMember parent) {
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
		return Basic.ANNOTATION_NAME;
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	protected Basic createBasicResource() {
		getParent().setMappingAnnotation(getAnnotationName());
		return (Basic) getParent().mappingAnnotation();
	}

	public FetchType getFetch() {
		return null;
	}
	
	public void setFetch(FetchType fetch) {
		if (fetch != null) {
			createBasicResource().setFetch(fetch);
		}				
	}
	
	public Boolean getOptional() {
		return null;
	}

	public void setOptional(Boolean optional) {
		if (optional != null) {
			createBasicResource().setOptional(optional);
		}				
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange fetchTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public TextRange optionalTextRange(CompilationUnit astRoot) {
		return null;
	}
}
