/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;

/**
 * javax.persistence.Basic
 */
public final class BinaryBasicAnnotation
	extends BinaryAnnotation
	implements BasicAnnotation
{
	private Boolean optional;
	private FetchType fetch;


	public BinaryBasicAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.optional = this.buildOptional();
		this.fetch = this.buildFetch();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setOptional_(this.buildOptional());
		this.setFetch_(this.buildFetch());
	}


	//*************** Basic implementation ****************

	// ***** optional
	public Boolean getOptional() {
		return this.optional;
	}

	public void setOptional(Boolean optional) {
		throw new UnsupportedOperationException();
	}

	private void setOptional_(Boolean optional) {
		Boolean old = this.optional;
		this.optional = optional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, optional);
	}

	private Boolean buildOptional() {
		return (Boolean) this.getJdtMemberValue(JPA.BASIC__OPTIONAL);
	}

	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
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
		return FetchType.fromJavaAnnotationValue(this.getJdtMemberValue(JPA.BASIC__FETCH));
	}

	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

}
