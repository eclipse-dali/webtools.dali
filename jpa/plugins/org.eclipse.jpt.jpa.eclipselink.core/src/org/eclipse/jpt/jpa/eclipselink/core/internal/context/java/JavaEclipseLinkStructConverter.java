/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.StructConverter</code>
 */
public class JavaEclipseLinkStructConverter
	extends JavaEclipseLinkConverterClassConverter<EclipseLinkStructConverterAnnotation>
	implements EclipseLinkStructConverter
{
	public JavaEclipseLinkStructConverter(JavaJpaContextNode parent, EclipseLinkStructConverterAnnotation converterAnnotation) {
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

	public Class<EclipseLinkStructConverter> getType() {
		return EclipseLinkStructConverter.class;
	}


	// ********** validation **********

	@Override
	protected String getEclipseLinkConverterInterface() {
		return ECLIPSELINK_STRUCT_CONVERTER_CLASS_NAME;
	}

	@Override
	protected String getEclipseLinkConverterInterfaceErrorMessage() {
		return EclipseLinkJpaValidationMessages.STRUCT_CONVERTER_CLASS_IMPLEMENTS_STRUCT_CONVERTER;
	}

	@Override
	protected TextRange getAnnotationConverterClassTextRange(CompilationUnit astRoot) {
		return this.converterAnnotation.getConverterTextRange(astRoot);
	}

	@Override
	public boolean isIdentical(EclipseLinkConverter eclipseLinkConverter) {
		return super.isIdentical(eclipseLinkConverter) && 
				StringTools.stringsAreEqual(this.getFullyQualifiedConverterClass(), (((EclipseLinkStructConverter)eclipseLinkConverter).getConverterClass()));
	}

	/**
	 * Since the converter class is a string, it must be fully-qualified.
	 */
	@Override
	protected String getFullyQualifiedConverterClass() {
		return this.getConverterClass();
	}

}
