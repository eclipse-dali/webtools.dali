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
import org.eclipse.jpt.core.resource.java.EnumType;
import org.eclipse.jpt.core.resource.java.Enumerated;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;


public class NullEnumerated extends AbstractJavaResourceNode implements Enumerated, Annotation
{	
	protected NullEnumerated(JavaResourcePersistentMember parent) {
		super(parent);
	}

	@Override
	public JavaResourcePersistentMember parent() {
		return (JavaResourcePersistentMember) super.parent();
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
		return Enumerated.ANNOTATION_NAME;
	}
		
	protected Enumerated createEnumeratedResource() {
		return (Enumerated) parent().addAnnotation(getAnnotationName());
	}


	public EnumType getValue() {
		return null;
	}
	
	public void setValue(EnumType value) {
		if (value != null) {
			createEnumeratedResource().setValue(value);
		}		
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public TextRange textRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange valueTextRange(CompilationUnit astRoot) {
		return null;
	}
}
