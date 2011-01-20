/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.internal.DefaultEclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class JavaEclipseLinkCustomConverter
	extends JavaEclipseLinkConverter<EclipseLinkConverterAnnotation>
	implements EclipseLinkCustomConverter
{
	private String converterClass;
	private String fullyQualifiedConverterClass;
		public static final String FULLY_QUALIFIED_CONVERTER_CLASS_PROPERTY = "fullyQualifiedConverterClass"; //$NON-NLS-1$


	public JavaEclipseLinkCustomConverter(JavaJpaContextNode parent, EclipseLinkConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation);
		this.converterClass = converterAnnotation.getConverterClass();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setConverterClass_(this.converterAnnotation.getConverterClass());
	}

	@Override
	public void update() {
		super.update();
		this.setFullyQualifiedConverterClass(this.converterAnnotation.getFullyQualifiedConverterClassName());
	}


	// ********** converter class **********

	public String getConverterClass() {
		return this.converterClass;
	}

	public void setConverterClass(String converterClass) {
		this.converterAnnotation.setConverterClass(converterClass);
		this.setConverterClass_(converterClass);
	}

	protected void setConverterClass_(String converterClass) {
		String old = this.converterClass;
		this.converterClass = converterClass;
		this.firePropertyChanged(CONVERTER_CLASS_PROPERTY, old, converterClass);
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

	public Class<EclipseLinkCustomConverter> getType() {
		return EclipseLinkCustomConverter.class;
	}


	//************ validation ***************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		if (this.converterClass != null) {
			// the annotation will have a compile error if its converter class is missing
			this.validateConverterClass(messages, astRoot);
		}
	}

	protected void validateConverterClass(List<IMessage> messages, CompilationUnit astRoot) {
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
		else if ( ! this.converterAnnotation.converterClassImplementsInterface(ECLIPSELINK_CONVERTER_CLASS_NAME, astRoot)) {
			messages.add(
				DefaultEclipseLinkJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER,
					new String[] {this.converterClass},
					this,
					this.getConverterClassTextRange(astRoot)
				)
			);
		}
	}

	protected TextRange getConverterClassTextRange(CompilationUnit astRoot) {
		return this.converterAnnotation.getConverterClassTextRange(astRoot);
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

		public Class<EclipseLinkCustomConverter> getConverterType() {
			return EclipseLinkCustomConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return EclipseLinkConverterAnnotation.ANNOTATION_NAME;
		}

		public JavaEclipseLinkConverter<? extends EclipseLinkNamedConverterAnnotation> buildConverter(EclipseLinkNamedConverterAnnotation converterAnnotation, JavaJpaContextNode parent) {
			return new JavaEclipseLinkCustomConverter(parent, (EclipseLinkConverterAnnotation) converterAnnotation);
		}
	}
}
