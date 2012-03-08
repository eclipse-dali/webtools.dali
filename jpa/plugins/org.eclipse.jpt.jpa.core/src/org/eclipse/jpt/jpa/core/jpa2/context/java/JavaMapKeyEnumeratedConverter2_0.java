/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaConverter;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumerated2_0Annotation;

/**
 * Java map key enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaMapKeyEnumeratedConverter2_0
	extends JavaBaseEnumeratedConverter
{
	// ********** adapter **********

	public static class Adapter
		extends JavaConverter.AbstractAdapter
	{
		private static final Adapter INSTANCE = new Adapter();
		public static Adapter instance() {
			return INSTANCE;
		}

		private Adapter() {
			super();
		}

		public Class<? extends Converter> getConverterType() {
			return BaseEnumeratedConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return MapKeyEnumerated2_0Annotation.ANNOTATION_NAME;
		}

		public JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory) {
			return factory.buildJavaBaseEnumeratedConverter(parent, (MapKeyEnumerated2_0Annotation) converterAnnotation, this.buildOwner());
		}
	}
}
