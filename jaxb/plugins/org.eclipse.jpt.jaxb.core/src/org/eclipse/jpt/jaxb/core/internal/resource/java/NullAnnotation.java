/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.resource.java.source.AbstractJavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;

/**
 * Simplify null annotation classes
 */
public abstract class NullAnnotation
	extends AbstractJavaResourceNode
	implements Annotation
{

	protected NullAnnotation(JavaResourceNode parent) {
		super(parent);
	}
	
	public void initialize(CompilationUnit astRoot) {
		// do nothing
	}

	public org.eclipse.jdt.core.dom.Annotation getAstAnnotation(CompilationUnit astRoot) {
		return null;
	}

	public JavaResourceCompilationUnit getJavaResourceCompilationUnit() {
		throw new UnsupportedOperationException();
	}

	public void newAnnotation() {
		throw new UnsupportedOperationException();
	}

	public void removeAnnotation() {
		throw new UnsupportedOperationException();
	}

	public TextRange getTextRange(CompilationUnit astRoot) {
		return null;
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Convenience method: Cast the annotation's parent to a
	 * persistent member.
	 */
	protected JavaResourceMember getMember() {
		return (JavaResourceMember) this.parent;
	}
	
	/**
	 * Convenience method: Add the type or attribute's annotation
	 * and return it.
	 * Pre-condition: The annotation's parent must be a persistent member
	 * (type or attribute).
	 */
	protected Annotation addAnnotation() {
		return this.getMember().addAnnotation(this.getAnnotationName());
	}
	
}
