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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.StructConverter</code>
 */
public final class BinaryEclipseLinkStructConverterAnnotation
	extends BinaryEclipseLinkNamedConverterAnnotation
	implements EclipseLinkStructConverterAnnotation
{
	private String converter;


	public BinaryEclipseLinkStructConverterAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.converter = this.buildConverter();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setConverter_(this.buildConverter());
	}


	// ********** BinaryNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.STRUCT_CONVERTER__NAME;
	}


	// ********** StructConverterAnnotation implementation **********

	// ***** converter
	public String getConverter() {
		return this.converter;
	}

	public void setConverter(String converter) {
		throw new UnsupportedOperationException();
	}

	private void setConverter_(String converter) {
		String old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	private String buildConverter() {
		return (String) this.getJdtMemberValue(EclipseLink.STRUCT_CONVERTER__CONVERTER);
	}

	public TextRange getConverterTextRange() {
		throw new UnsupportedOperationException();
	}
}
