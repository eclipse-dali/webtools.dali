/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.DiscriminatorType;

/**
 * <code>javax.persistence.DiscriminatorColumn</code>
 */
public abstract class NullBaseDiscriminatorColumnAnnotation<A extends DiscriminatorColumnAnnotation>
	extends NullNamedColumnAnnotation<A>
	implements DiscriminatorColumnAnnotation
{

	public NullBaseDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
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
