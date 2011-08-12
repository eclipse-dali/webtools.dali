/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.TemporalConverter;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.ConverterTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.JavaElementCollectionTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.JavaTemporalConverterValidator;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;

/**
 * Java temporal converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaTemporalConverter
	extends TemporalConverter, JavaConverter
{
	// ********** adapter **********

	public static class BasicAdapter extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new BasicAdapter();
		public static Adapter instance() {
			return INSTANCE;
		}
	
		private BasicAdapter() {
			super();
		}
	
		@Override
		protected Owner buildOwner() {
			return new Owner() {
				public JptValidator buildValidator(Converter converter, ConverterTextRangeResolver textRangeResolver) {
					return new JavaTemporalConverterValidator((TemporalConverter) converter, textRangeResolver);
				}
			};
		}
	}

	public static class ElementCollectionAdapter extends AbstractAdapter
	{
		private static final Adapter INSTANCE = new ElementCollectionAdapter();
		public static Adapter instance() {
			return INSTANCE;
		}
	
		private ElementCollectionAdapter() {
			super();
		}
	
		@Override
		protected Owner buildOwner() {
			return new Owner() {
				public JptValidator buildValidator(Converter converter, ConverterTextRangeResolver textRangeResolver) {
					return new JavaElementCollectionTemporalConverterValidator((TemporalConverter) converter, textRangeResolver);
				}
			};
		}
	}

	abstract static class AbstractAdapter
		extends JavaConverter.AbstractAdapter
	{
	
		AbstractAdapter() {
			super();
		}

		public Class<? extends Converter> getConverterType() {
			return TemporalConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return TemporalAnnotation.ANNOTATION_NAME;
		}

		public JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory) {
			return factory.buildJavaTemporalConverter(parent, (TemporalAnnotation) converterAnnotation, this.buildOwner());
		}
	}
}
