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



public class NullDiscriminatorColumn extends NullNamedColumn implements DiscriminatorColumn, Annotation
{	
	public NullDiscriminatorColumn(JavaResource parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return DiscriminatorColumn.ANNOTATION_NAME;
	}

	@Override
	protected DiscriminatorColumn createColumnResource() {
		return (DiscriminatorColumn) super.createColumnResource();
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
