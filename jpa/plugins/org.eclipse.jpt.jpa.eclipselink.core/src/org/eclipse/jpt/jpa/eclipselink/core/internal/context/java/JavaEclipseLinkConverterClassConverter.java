/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterClassConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
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


	public JavaEclipseLinkConverterClassConverter(JavaJpaContextNode parent, A converterAnnotation, String converterClass) {
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


	//************ validation ***************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateConverterClass(messages, astRoot);
	}

	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.converterClass == null) {
			// the annotation will have a compile error if its converter class is missing
			return;
		}

		if (StringTools.stringIsEmpty(this.converterClass)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_DEFINED,
					this,
					this.getConverterClassTextRange(astRoot)
				)
			);
			return;
		}

		if ( ! this.converterClassExists()) {
			this.addConverterClassDoesNotExistMessageTo(messages, astRoot);
			return;
		}

		if ( ! this.converterClassImplementsInterface(this.getEclipseLinkConverterInterface())) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					this.getEclipseLinkConverterInterfaceErrorMessage(),
					new String[] {this.getFullyQualifiedConverterClass()},
					this,
					this.getConverterClassTextRange(astRoot)
				)
			);
		}
	}

	protected void addConverterClassDoesNotExistMessageTo(List<IMessage> messages, CompilationUnit astRoot) {
		messages.add(
			DefaultEclipseLinkJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				EclipseLinkJpaValidationMessages.CONVERTER_CLASS_EXISTS,
				new String[] {this.getFullyQualifiedConverterClass()},
				this,
				this.getConverterClassTextRange(astRoot)
			)
		);
	}

	/**
	 * Return the name of the EclipseLink interface the converter class must
	 * implement.
	 */
	protected abstract String getEclipseLinkConverterInterface();

	protected abstract String getEclipseLinkConverterInterfaceErrorMessage();

	protected boolean converterClassExists() {
		return this.typeExists(this.getFullyQualifiedConverterClass());
	}

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
				JDTTools.typeNamedImplementsInterfaceNamed(this.getJavaProject(), typeName, interfaceName);
	}

	protected boolean converterClassImplementsInterface(String interfaceName) {
		return this.typeImplementsInterface(this.getFullyQualifiedConverterClass(), interfaceName);
	}

	protected abstract String getFullyQualifiedConverterClass();

	protected TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return this.getValidationTextRange(this.getAnnotationConverterClassTextRange(astRoot), astRoot);
	}

	protected abstract TextRange getAnnotationConverterClassTextRange(CompilationUnit astRoot);

	protected IJavaProject getJavaProject() {
		return this.getJpaProject().getJavaProject();
	}
}
