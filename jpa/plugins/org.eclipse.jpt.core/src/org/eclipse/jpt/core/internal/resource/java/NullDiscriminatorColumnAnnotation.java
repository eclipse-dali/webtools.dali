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

import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * javax.persistence.DiscriminatorColumn
 */
public final class NullDiscriminatorColumnAnnotation
	extends NullNamedColumnAnnotation
	implements DiscriminatorColumnAnnotation
{	
	public NullDiscriminatorColumnAnnotation(JavaResourcePersistentType parent) {
		super(parent);
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected DiscriminatorColumnAnnotation addAnnotation() {
		return (DiscriminatorColumnAnnotation) super.addAnnotation();
	}

	// ***** discriminator type
	public DiscriminatorType getDiscriminatorType() {
		return null;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		if (discriminatorType != null) {
			this.addAnnotation().setDiscriminatorType(discriminatorType);
		}
	}

	// ***** length
	public Integer getLength() {
		return null;
	}

	public void setLength(Integer length) {
		if (length != null) {
			this.addAnnotation().setLength(length);
		}
	}

}
