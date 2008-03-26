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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.Temporal;
import org.eclipse.jpt.core.resource.java.TemporalType;
import org.eclipse.jpt.core.utility.TextRange;


public class NullTemporal extends AbstractJavaResourceNode implements Temporal, Annotation
{	
	protected NullTemporal(JavaResourcePersistentMember parent) {
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
		return Temporal.ANNOTATION_NAME;
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}
	
	protected Temporal createTemporalResource() {
		return (Temporal) getParent().addAnnotation(getAnnotationName());
	}


	public TemporalType getValue() {
		return null;
	}
	
	public void setValue(TemporalType value) {
		if (value != null) {
			createTemporalResource().setValue(value);
		}		
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
}
