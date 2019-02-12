/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * <code>org.eclipse.persistence.annotations.Converter</code>
 */
public class EclipseLinkJavaCustomConverter
	extends EclipseLinkJavaConverterClassConverter<ConverterAnnotation>
	implements EclipseLinkCustomConverter
{
	private String fullyQualifiedConverterClass;


	public EclipseLinkJavaCustomConverter(EclipseLinkJavaConverterContainer parent, ConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation, converterAnnotation.getConverterClass());
	}


	// ********** synchronize/update **********

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setFullyQualifiedConverterClass(this.converterAnnotation.getFullyQualifiedConverterClassName());
	}


	// ********** converter class **********

	@Override
	protected String getAnnotationConverterClass() {
		return this.converterAnnotation.getConverterClass();
	}

	@Override
	protected void setAnnotationConverterClass(String converterClass) {
		this.converterAnnotation.setConverterClass(converterClass);
	}


	// ********** fully qualified converter class **********

	public String getFullyQualifiedConverterClass() {
		return this.fullyQualifiedConverterClass;
	}

	protected void setFullyQualifiedConverterClass(String converterClass) {
		String old = this.fullyQualifiedConverterClass;
		this.fullyQualifiedConverterClass = converterClass;
		this.firePropertyChanged(FULLY_QUALIFIED_CONVERTER_CLASS_PROPERTY, old, converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkCustomConverter> getConverterType() {
		return EclipseLinkCustomConverter.class;
	}


	//************ validation ***************

	@Override
	protected String getEclipseLinkConverterInterface() {
		return ECLIPSELINK_CONVERTER_CLASS_NAME;
	}

	@Override
	protected ValidationMessage getEclipseLinkConverterInterfaceErrorMessage() {
		return JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER;
	}

	@Override
	protected TextRange getAnnotationConverterClassTextRange() {
		return this.converterAnnotation.getConverterClassTextRange();
	}

	@Override
	protected void addConverterClassDoesNotExistMessageTo(List<IMessage> messages) {
		// no need to add message since there will already be a compiler error
	}

	// ********** metadata conversion **********

	@Override
	public void convertTo(EclipseLinkOrmConverterContainer ormConverterContainer) {
		ormConverterContainer.addCustomConverter(this.getName()).convertFrom(this);
	}
	
	@Override
	public void delete() {
		this.parent.removeCustomConverter(this);
	}
}
