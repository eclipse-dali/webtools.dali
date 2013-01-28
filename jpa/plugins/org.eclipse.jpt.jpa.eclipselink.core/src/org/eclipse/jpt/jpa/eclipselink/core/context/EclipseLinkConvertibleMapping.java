/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;

/**
 * EclipseLink convertible mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface EclipseLinkConvertibleMapping {

	EclipseLinkConverterContainer getConverterContainer();

	Transformer<AttributeMapping, Iterable<EclipseLinkConverter>> ATTRIBUTE_MAPPING_CONVERTERS_TRANSFORMER = new AttributeMappingConvertersTransformer();
	class AttributeMappingConvertersTransformer
		extends TransformerAdapter<AttributeMapping, Iterable<EclipseLinkConverter>>
	{
		@Override
		public Iterable<EclipseLinkConverter> transform(AttributeMapping attributeMapping) {
			return (attributeMapping instanceof EclipseLinkConvertibleMapping) ?
					((EclipseLinkConvertibleMapping) attributeMapping).getConverterContainer().getConverters() :
					IterableTools.<EclipseLinkConverter>emptyIterable();
		}
	}
}
