/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTypeConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.TypeConverter</code>
 */
public final class BinaryEclipseLinkTypeConverterAnnotation
	extends BinaryEclipseLinkBaseTypeConverterAnnotation
	implements EclipseLinkTypeConverterAnnotation
{
	public BinaryEclipseLinkTypeConverterAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.TYPE_CONVERTER__NAME;
	}


	// ********** BinaryBaseTypeConverterAnnotation implementation **********

	@Override
	String getDataTypeElementName() {
		return EclipseLink.TYPE_CONVERTER__DATA_TYPE;
	}

	@Override
	String getObjectTypeElementName() {
		return EclipseLink.TYPE_CONVERTER__OBJECT_TYPE;
	}
}
