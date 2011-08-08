/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * javax.persistence.DiscriminatorColumn
 */
public final class BinaryDiscriminatorColumnAnnotation
	extends BinaryBaseDiscriminatorColumnAnnotation
{


	public BinaryDiscriminatorColumnAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	// ********** BinaryBaseDiscriminatorColumnAnnotation implementation **********

	@Override
	protected String getDiscriminatorTypeElementName() {
		return JPA.DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE;
	}

	@Override
	protected String getLengthElementName() {
		return JPA.DISCRIMINATOR_COLUMN__LENGTH;
	}

	// ********** BinaryNamedColumnAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.DISCRIMINATOR_COLUMN__NAME;
	}

	@Override
	protected String getColumnDefinitionElementName() {
		return JPA.DISCRIMINATOR_COLUMN__COLUMN_DEFINITION;
	}
}
