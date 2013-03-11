/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.java.FetchType;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.TransformationAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Transformation</code>
 */
public final class NullEclipseLinkTransformationAnnotation
	extends NullAnnotation<TransformationAnnotation>
	implements TransformationAnnotation
{	
	protected NullEclipseLinkTransformationAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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
	
	public TextRange getFetchTextRange() {
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

	public TextRange getOptionalTextRange() {
		return null;
	}
}
