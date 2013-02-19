/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterClassConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>
 * <ul>
 * <li>org.eclipse.persistence.annotations.Converter
 * <li>org.eclipse.persistence.annotations.StructConverter
 * </ul>
 * </code>
 */
public abstract class JavaEclipseLinkConverterClassConverter<A extends EclipseLinkNamedConverterAnnotation>
	extends JavaEclipseLinkConverter<A>
	implements EclipseLinkConverterClassConverter
{
	private String converterClass;


	public JavaEclipseLinkConverterClassConverter(JpaContextNode parent, A converterAnnotation, String converterClass) {
		super(parent, converterAnnotation);
		this.converterClass = converterClass;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.getAnnotationConverterClass());
	}


	// ********** converter class **********

	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		this.setAnnotationConverterClass(converterClass);
		this.setConverterClass_(converterClass);
	}

	protected void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
	}

	protected abstract String getAnnotationConverterClass();

	protected abstract void setAnnotationConverterClass(String converterClass);


	// ********** JDT IType **********

	/**
	 * Add <code>null</code> check.
	 */
	protected boolean typeExists(String typeName) {
		return (typeName != null) && 
				(JDTTools.findType(this.getJavaProject(), typeName) != null);
	}

	/**
	 * Add <code>null</code> check.
	 */
	protected boolean typeImplementsInterface(String typeName, String interfaceName) {
		return (typeName != null) && 
				JDTTools.typeIsSubType(this.getJavaProject(), typeName, interfaceName);
	}


	//************ validation ***************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateConverterClass(messages);
	}

	protected void validateConverterClass(List<IMessage> messages) {
		if (this.converterClass == null) {
			// the annotation will have a compile error if its converter class is missing
			return;
		}

		if (StringTools.isBlank(this.converterClass)) {
			messages.add(
					this.buildErrorValidationMessage(
					JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_DEFINED,
					this.getConverterClassTextRange()
				)
			);
			return;
		}

		if ( ! this.converterClassExists()) {
			this.addConverterClassDoesNotExistMessageTo(messages);
			return;
		}

		if ( ! this.converterClassImplementsInterface(this.getEclipseLinkConverterInterface())) {
			messages.add(
				this.buildErrorValidationMessage(
					this.getEclipseLinkConverterInterfaceErrorMessage(),
					this.getConverterClassTextRange(),
					this.getFullyQualifiedConverterClass()
				)
			);
		}
	}

	protected void addConverterClassDoesNotExistMessageTo(List<IMessage> messages) {
		messages.add(
			this.buildErrorValidationMessage(
				JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_EXISTS,
				this.getConverterClassTextRange(),
				this.getFullyQualifiedConverterClass()
			)
		);
	}

	/**
	 * Return the name of the EclipseLink interface the converter class must
	 * implement.
	 */
	protected abstract String getEclipseLinkConverterInterface();

	protected abstract ValidationMessage getEclipseLinkConverterInterfaceErrorMessage();

	protected boolean converterClassExists() {
		return this.typeExists(this.getFullyQualifiedConverterClass());
	}

	protected boolean converterClassImplementsInterface(String interfaceName) {
		return this.typeImplementsInterface(this.getFullyQualifiedConverterClass(), interfaceName);
	}

	protected TextRange getConverterClassTextRange() {
		return this.getValidationTextRange(this.getAnnotationConverterClassTextRange());
	}

	protected abstract TextRange getAnnotationConverterClassTextRange();

	@Override
	public boolean isEquivalentTo(JpaNamedContextNode node) {
		return super.isEquivalentTo(node)
				&& this.isEquivalentTo((EclipseLinkConverterClassConverter) node);
	}

	protected boolean isEquivalentTo(EclipseLinkConverterClassConverter converter) {
		return ObjectTools.equals(this.getFullyQualifiedConverterClass(), converter.getFullyQualifiedConverterClass());
	}
}
