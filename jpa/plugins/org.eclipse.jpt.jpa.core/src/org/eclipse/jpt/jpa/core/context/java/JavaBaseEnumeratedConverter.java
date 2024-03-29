/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.MapKeyEnumeratedAnnotation2_0;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;

/**
 * Java enumerated/map key enumerated converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaBaseEnumeratedConverter
	extends BaseEnumeratedConverter, JavaConverter
{
	// combine interfaces
	
	// ********** enumerated adapter **********

	public static class BasicAdapter
		extends JavaConverter.AbstractAdapter
	{
		private static final BasicAdapter INSTANCE = new BasicAdapter();
		public static BasicAdapter instance() {
			return INSTANCE;
		}

		private BasicAdapter() {
			super();
		}

		public Class<BaseEnumeratedConverter> getConverterType() {
			return BaseEnumeratedConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return EnumeratedAnnotation.ANNOTATION_NAME;
		}

		public JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory) {
			return factory.buildJavaBaseEnumeratedConverter(this.buildConverterParentAdapter(parent), (EnumeratedAnnotation) converterAnnotation);
		}
	}

	// ********** map key enumerated adapter **********

	public static class MapKeyAdapter
		extends JavaConverter.AbstractAdapter
	{
		private static final MapKeyAdapter INSTANCE = new MapKeyAdapter();
		public static MapKeyAdapter instance() {
			return INSTANCE;
		}

		private MapKeyAdapter() {
			super();
		}

		public Class<BaseEnumeratedConverter> getConverterType() {
			return BaseEnumeratedConverter.class;
		}

		@Override
		protected String getAnnotationName() {
			return MapKeyEnumeratedAnnotation2_0.ANNOTATION_NAME;
		}

		public JavaConverter buildConverter(Annotation converterAnnotation, JavaAttributeMapping parent, JpaFactory factory) {
			return factory.buildJavaBaseEnumeratedConverter(this.buildConverterParentAdapter(parent), (MapKeyEnumeratedAnnotation2_0) converterAnnotation);
		}
	}
}
