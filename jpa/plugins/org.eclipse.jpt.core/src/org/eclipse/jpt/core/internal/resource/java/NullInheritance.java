/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public class NullInheritance extends AbstractResource implements Inheritance, Annotation
{	
	protected NullInheritance(JavaResource parent) {
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
		return Inheritance.ANNOTATION_NAME;
	}

	public InheritanceType getStrategy() {
		return null;
	}

	public void setStrategy(InheritanceType strategy) {
		if (strategy != null) {
			createInheritanceResource().setStrategy(strategy);
		}
		
	}
	
	@Override
	public JavaPersistentResource parent() {
		return (JavaPersistentResource) super.parent();
	}
	
	protected Inheritance createInheritanceResource() {
		return (Inheritance) parent().addAnnotation(getAnnotationName());
	}

	public void updateFromJava(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	
	}

	public ITextRange strategyTextRange(CompilationUnit astRoot) {
		return null;
	}

	public ITextRange textRange(CompilationUnit astRoot) {
		return null;
	}

}
