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

import java.util.List;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkStructConverter
	extends JavaEclipseLinkConverter<EclipseLinkStructConverterAnnotation>
	implements EclipseLinkStructConverter
{
	private String converterClass;


	public JavaEclipseLinkStructConverter(JavaJpaContextNode parent, EclipseLinkStructConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.converterClass = converterAnnotation.getConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.converterAnnotation.getConverter());
	}


	// ********** converter class **********

	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		this.converterAnnotation.setConverter(converterClass);
		this.setConverterClass_(converterClass);
	}

	protected void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
	}


	// ********** misc **********

	public Class<EclipseLinkStructConverter> getType() {
		return EclipseLinkStructConverter.class;
	}
	
	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.converterClass != null) {
			// the annotation will have a compile error if its converter class is missing
			validateConverterClass(messages, astRoot);
		}
	}
	
	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
		IJavaProject javaProject = getJpaProject().getJavaProject();

		if (StringTools.stringIsEmpty(this.converterClass)) {
			messages.add(
					DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_DEFINED,
					this,
					getConverterClassTextRange(astRoot)
				)
			);			
		}
		
		else if (!converterClassExists(javaProject)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_EXISTS,
					new String[] {this.converterClass},
					this,
					getConverterClassTextRange(astRoot)
				)
			);			
		} 
		else if (!converterClassImplementsInterface(javaProject, ECLIPSELINK_CONVERTER_CLASS_NAME)) {
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
	
	private boolean converterClassExists(IJavaProject javaProject) {
		return (this.converterClass != null) &&
				(JDTTools.findType(javaProject, this.converterClass) != null);
	}

	private boolean converterClassImplementsInterface(IJavaProject javaProject, String interfaceName) {
		return (this.converterClass != null) &&
				JDTTools.typeNamedImplementsInterfaceNamed(javaProject, this.converterClass, interfaceName);
	}
	
	protected TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return this.converterAnnotation.getConverterTextRange(astRoot);
	}

	// ********** adapter **********

	public static class Adapter
		extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<EclipseLinkStructConverter> getConverterType() {
			return EclipseLinkStructConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return EclipseLinkStructConverterAnnotation.ANNOTATION_NAME;
		}

		public JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(EclipseLinkNamedConverterAnnotation converterAnnotation, JavaJpaContextNode parent) {
			return new JavaEclipseLinkStructConverter(parent, (EclipseLinkStructConverterAnnotation) converterAnnotation);
		}
		
	}
}
