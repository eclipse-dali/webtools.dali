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

import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorColumnAnnotation;
import org.eclipse.jpt.core.resource.java.DiscriminatorType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;



public class NullDiscriminatorColumn extends NullNamedColumn implements DiscriminatorColumnAnnotation, Annotation
{	
	public NullDiscriminatorColumn(JavaResourcePersistentMember parent) {
		super(parent);
	}
	
	@Override
	public JavaResourcePersistentMember getParent() {
		return (JavaResourcePersistentMember) super.getParent();
	}

	public String getAnnotationName() {
		return DiscriminatorColumnAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected DiscriminatorColumnAnnotation createColumnResource() {
		return (DiscriminatorColumnAnnotation) getParent().addAnnotation(getAnnotationName());
	}

	public DiscriminatorType getDiscriminatorType() {
		return null;
	}

	public void setDiscriminatorType(DiscriminatorType discriminatorType) {
		if (discriminatorType != null) {
			createColumnResource().setDiscriminatorType(discriminatorType);
		}
	}

	public Integer getLength() {
		return null;
	}

	public void setLength(Integer length) {
		if (length != null) {
			createColumnResource().setLength(length);
		}
	}
}
