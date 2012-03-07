/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.ConvertibleKeyMapping2_0;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface EclipseLinkTypeMapping
	extends TypeMapping, EclipseLinkConvertibleMapping
{
	EclipseLinkCustomizer getCustomizer();
	
	EclipseLinkChangeTracking getChangeTracking();

	/**
	 * Return whether this type mapping specifies primary key columns rather than using
	 * JPA-style attributes
	 * (Uses the @PrimaryKey annotation for java, or the primary-key element for xml)
	 * 
	 * Note: there is no context-level or UI support for this feature as of yet.
	 * Note: this is a 1.1 feature, but this check has been implemented for all versions
	 */
	boolean usesPrimaryKeyColumns();

	/**
	 * Return true if the type mapping is multitenant and any of the tenant discriminator
	 * columns (specified or default) have the primaryKey option set to true
	 */
	boolean usesPrimaryKeyTenantDiscriminatorColumns();

	Iterable<EclipseLinkConverter> getConverters();

	Transformer<AttributeMapping, Iterable<EclipseLinkConverter>> ATTRIBUTE_MAPPING_CONVERTER_TRANSFORMER =
		new Transformer<AttributeMapping, Iterable<EclipseLinkConverter>>() {
			public Iterable<EclipseLinkConverter> transform(AttributeMapping attributeMapping) {
			List<EclipseLinkConverter> converters = new ArrayList<EclipseLinkConverter>(); 
				if (attributeMapping instanceof EclipseLinkConvertibleMapping) {
				Iterable<EclipseLinkConverter> mappingConverters = 
						((EclipseLinkConvertibleMapping) attributeMapping).getConverterContainer().getConverters();
				CollectionTools.addAll(converters, mappingConverters);
				}
			return converters;
			}
		};
}
