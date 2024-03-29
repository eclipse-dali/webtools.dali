/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.Converter</code>
 */
public final class EclipseLinkBinaryConverterAnnotation
	extends EclipseLinkBinaryNamedConverterAnnotation
	implements ConverterAnnotation
{
	private String converterClass;


	public EclipseLinkBinaryConverterAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.converterClass = this.buildConverterClass();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setConverterClass_(this.buildConverterClass());
	}


	// ********** BinaryNamedConverterAnnotation implementation **********

	@Override
	String getNameElementName() {
		return EclipseLink.CONVERTER__NAME;
	}

	// ********** ConverterAnnotation implementation **********

	// ***** converter class
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		throw new UnsupportedOperationException();
	}

	private void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
	}

	public String getFullyQualifiedConverterClassName() {
		return this.converterClass;
	}

	private String buildConverterClass() {
		return (String) this.getJdtMemberValue(EclipseLink.CONVERTER__CONVERTER_CLASS);
	}

	public TextRange getConverterClassTextRange() {
		throw new UnsupportedOperationException();
	}
}
