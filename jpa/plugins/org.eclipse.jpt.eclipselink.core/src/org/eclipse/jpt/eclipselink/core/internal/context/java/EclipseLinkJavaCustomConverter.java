/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class EclipseLinkJavaCustomConverter extends EclipseLinkJavaConverter 
	implements CustomConverter
{
	private String converterClass;
	
	public EclipseLinkJavaCustomConverter(JavaJpaContextNode parent) {
		super(parent);
	}
	
	
	public String getType() {
		return EclipseLinkConverter.CUSTOM_CONVERTER;
	}
	
	@Override
	public String getAnnotationName() {
		return EclipseLinkConverterAnnotation.ANNOTATION_NAME;
	}
	
	@Override
	protected EclipseLinkConverterAnnotation getAnnotation() {
		return (EclipseLinkConverterAnnotation) super.getAnnotation();
	}
	
	
	// **************** converter class ****************************************
	
	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		getAnnotation().setConverterClass(newConverterClass);
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}
	
	protected void setConverterClass_(String newConverterClass) {
		String oldConverterClass = this.converterClass;
		this.converterClass = newConverterClass;
		firePropertyChanged(CONVERTER_CLASS_PROPERTY, oldConverterClass, newConverterClass);
	}

	
	// **************** resource interaction ***********************************
	
	@Override
	protected void initialize(JavaResourcePersistentMember jrpm) {
		super.initialize(jrpm);
		this.converterClass = this.converterClass(getAnnotation());
	}
	
	@Override
	public void update(JavaResourcePersistentMember jrpm) {
		super.update(jrpm);
		this.setConverterClass_(this.converterClass(getAnnotation()));
	}
	
	protected String converterClass(EclipseLinkConverterAnnotation resourceConverter) {
		return resourceConverter == null ? null : resourceConverter.getConverterClass();
	}

	public TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return getAnnotation().getConverterClassTextRange(astRoot);
	}
	
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		validateConverterClass(messages, astRoot);
	}
	
	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.converterClass == null) {
			//Annotation itself will have compile error if converter class not defined
			return;
		}
		if (! getAnnotation().converterClassImplementsInterface(ECLIPSELINK_CONVERTER_CLASS_NAME, astRoot)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER,
					new String[] {this.converterClass},
					this, 
					getConverterClassTextRange(astRoot)
				)
			);
		}
	}
}
