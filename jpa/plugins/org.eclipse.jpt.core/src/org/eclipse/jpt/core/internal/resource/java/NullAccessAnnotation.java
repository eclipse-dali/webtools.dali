/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.AbstractJavaResourceNode;
import org.eclipse.jpt.core.resource.java.AccessAnnotation;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.TextRange;

public class NullAccessAnnotation extends AbstractJavaResourceNode implements AccessAnnotation, Annotation
{
	protected NullAccessAnnotation(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	@Override
	public JavaResourcePersistentType getParent() {
		return (JavaResourcePersistentType) super.getParent();
	}
	
	public String getAnnotationName() {
		return AccessAnnotation.ANNOTATION_NAME;
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
	
	protected AccessAnnotation createAccessAnnotation() {
		return (AccessAnnotation) getParent().addSupportingAnnotation(getAnnotationName());
	}
	
	public AccessType getValue() {
		return null;
	}
	
	public void setValue(AccessType value) {
		if (value != null) {
			createAccessAnnotation().setValue(value);
		}
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	public void update(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
