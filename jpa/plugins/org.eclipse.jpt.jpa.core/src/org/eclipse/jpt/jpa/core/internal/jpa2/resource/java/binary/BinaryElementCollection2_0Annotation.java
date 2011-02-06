/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;

/**
 * org.eclipse.persistence.annotations.Transformation
 */
public class BinaryElementCollection2_0Annotation
	extends BinaryAnnotation
	implements ElementCollection2_0Annotation
{
	private String targetClass;
	private FetchType fetch;


	public BinaryElementCollection2_0Annotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.targetClass = this.buildTargetClass();
		this.fetch = this.buildFetch();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setTargetClass_(this.buildTargetClass());
		this.setFetch_(this.buildFetch());
	}


	// ********** ElementCollection2_0Annotation implementation **********

	// ***** target entity
	public String getTargetClass() {
		return this.targetClass;
	}

	public void setTargetClass(String targetClass) {
		throw new UnsupportedOperationException();
	}

	private void setTargetClass_(String targetClass) {
		String old = this.targetClass;
		this.targetClass = targetClass;
		this.firePropertyChanged(TARGET_CLASS_PROPERTY, old, targetClass);
	}

	private String buildTargetClass() {
		return (String) this.getJdtMemberValue(JPA2_0.ELEMENT_COLLECTION__TARGET_CLASS);
	}

	public TextRange getTargetClassTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified target entity class name
	public String getFullyQualifiedTargetClassName() {
		return this.targetClass;
	}

	// ***** fetch
	public FetchType getFetch() {
		return this.fetch;
	}

	public void setFetch(FetchType fetch) {
		throw new UnsupportedOperationException();
	}

	private void setFetch_(FetchType fetch) {
		FetchType old = this.fetch;
		this.fetch = fetch;
		this.firePropertyChanged(FETCH_PROPERTY, old, fetch);
	}

	private FetchType buildFetch() {
		return FetchType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA2_0.ELEMENT_COLLECTION__FETCH));
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
