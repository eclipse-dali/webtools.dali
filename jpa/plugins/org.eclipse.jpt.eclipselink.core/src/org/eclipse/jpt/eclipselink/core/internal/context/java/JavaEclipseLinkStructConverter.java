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

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkNamedConverterAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkStructConverterAnnotation;

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
