/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * null javax.persistence.Basic
 */
public final class NullBasicAnnotation
	extends NullAnnotation
	implements BasicAnnotation
{
	protected NullBasicAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}
	
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected BasicAnnotation addAnnotation() {
		return (BasicAnnotation) super.addAnnotation();
	}
	
	
	// ***** fetch
	
	public FetchType getFetch() {
		return null;
	}
	
	public void setFetch(FetchType fetch) {
		if (fetch != null) {
			this.addAnnotation().setFetch(fetch);
		}
	}
	
	public TextRange getFetchTextRange(CompilationUnit astRoot) {
		return null;
	}
	
	
	// ***** optional
	
	public Boolean getOptional() {
		return null;
	}
	
	public void setOptional(Boolean optional) {
		if (optional != null) {
			this.addAnnotation().setOptional(optional);
		}
	}
	
	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		return null;
	}
}
