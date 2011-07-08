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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaValidationMessages;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkConverterAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * <code>org.eclipse.persistence.annotations.Converter</code>
 */
public class JavaEclipseLinkCustomConverter
	extends JavaEclipseLinkConverterClassConverter<EclipseLinkConverterAnnotation>
	implements EclipseLinkCustomConverter
{
	private String fullyQualifiedConverterClass;
		public static final String FULLY_QUALIFIED_CONVERTER_CLASS_PROPERTY = "fullyQualifiedConverterClass"; //$NON-NLS-1$


	public JavaEclipseLinkCustomConverter(JavaJpaContextNode parent, EclipseLinkConverterAnnotation converterAnnotation) {
		super(parent, converterAnnotation, converterAnnotation.getConverterClass());
	}


	// ********** synchronize/update **********

	@Override
	public void update() {
		super.update();
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

	@Override
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
	protected String getEclipseLinkConverterInterface() {
		return ECLIPSELINK_CONVERTER_CLASS_NAME;
	}

	@Override
	protected String getEclipseLinkConverterInterfaceErrorMessage() {
		return EclipseLinkJpaValidationMessages.CONVERTER_CLASS_IMPLEMENTS_CONVERTER;
	}

	@Override
	protected TextRange getAnnotationConverterClassTextRange(CompilationUnit astRoot) {
		return this.converterAnnotation.getConverterClassTextRange(astRoot);
	}

	@Override
	protected void addConverterClassDoesNotExistMessageTo(List<IMessage> messages, CompilationUnit astRoot) {
		// no need to add message since there will already be a compiler error
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
