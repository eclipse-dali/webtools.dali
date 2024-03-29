/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;

/**
 * <code>org.eclipse.persistence.annotations.StructConverter</code>
 */
public class EclipseLinkJavaStructConverter
	extends EclipseLinkJavaConverterClassConverter<StructConverterAnnotation>
	implements EclipseLinkStructConverter
{
	public EclipseLinkJavaStructConverter(EclipseLinkJavaConverterContainer parent, StructConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation, converterAnnotation.getConverter());
	}


	// ********** converter class **********

	@Override
	protected String getAnnotationConverterClass() {
		return this.converterAnnotation.getConverter();
	}

	@Override
	protected void setAnnotationConverterClass(String converterClass) {
		this.converterAnnotation.setConverter(converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkStructConverter> getConverterType() {
		return EclipseLinkStructConverter.class;
	}


	// ********** validation **********

	@Override
	protected String getEclipseLinkConverterInterface() {
		return ECLIPSELINK_STRUCT_CONVERTER_CLASS_NAME;
	}

	@Override
	protected ValidationMessage getEclipseLinkConverterInterfaceErrorMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER;
	}

	@Override
	protected TextRange getAnnotationConverterClassTextRange() {
		return this.converterAnnotation.getConverterTextRange();
	}

	/**
	 * Since the converter class is a string, it must be fully-qualified.
	 */
	public String getFullyQualifiedConverterClass() {
		return this.getConverterClass();
	}

	// ********** metadata conversion **********

	@Override
	public void convertTo(EclipseLinkOrmConverterContainer ormConverterContainer) {
		ormConverterContainer.addStructConverter(this.getName()).convertFrom(this);
	}
	
	@Override
	public void delete() {
		this.parent.removeStructConverter(this);
	}
}
