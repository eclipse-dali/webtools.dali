/*******************************************************************************
 * Copyright (c) 2011, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.core.internal.utility.TypeTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterClassConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.java.EclipseLinkJavaConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.NamedConverterAnnotation;
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
public abstract class EclipseLinkJavaConverterClassConverter<A extends NamedConverterAnnotation>
	extends EclipseLinkJavaConverter<A>
	implements EclipseLinkConverterClassConverter
{
	private String converterClass;


	public EclipseLinkJavaConverterClassConverter(EclipseLinkJavaConverterContainer parent, A converterAnnotation, String converterClass) {
		super(parent, converterAnnotation);
		this.converterClass = converterClass;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
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
				(JavaProjectTools.findType(this.getJavaProject(), typeName) != null);
	}

	/**
	 * Add <code>null</code> check.
	 */
	protected boolean typeImplementsInterface(String typeName, String interfaceName) {
		return (typeName != null) && 
				TypeTools.isSubTypeOf(typeName, interfaceName, this.getJavaProject());
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
					this.buildValidationMessage(
						this.getConverterClassTextRange(),
						JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_DEFINED
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
				this.buildValidationMessage(
					this.getConverterClassTextRange(),
					this.getEclipseLinkConverterInterfaceErrorMessage(),
					this.getFullyQualifiedConverterClass()
				)
			);
		}
	}

	protected void addConverterClassDoesNotExistMessageTo(List<IMessage> messages) {
		messages.add(
			this.buildValidationMessage(
				this.getConverterClassTextRange(),
				JptJpaEclipseLinkCoreValidationMessages.CONVERTER_CLASS_EXISTS,
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
	protected boolean isEquivalentTo_(EclipseLinkConverter other) {
		return super.isEquivalentTo_(other) &&
				this.isEquivalentTo_((EclipseLinkConverterClassConverter) other);
	}

	protected boolean isEquivalentTo_(EclipseLinkConverterClassConverter converter) {
		return ObjectTools.equals(this.getFullyQualifiedConverterClass(), converter.getFullyQualifiedConverterClass());
	}
}
