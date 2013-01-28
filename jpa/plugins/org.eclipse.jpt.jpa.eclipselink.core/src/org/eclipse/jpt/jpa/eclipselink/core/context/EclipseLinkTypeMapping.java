/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.TypeMapping;

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
	Transformer<EclipseLinkTypeMapping, Iterable<EclipseLinkConverter>> CONVERTERS_TRANSFORMER = new ConvertersTransformer();
	class ConvertersTransformer
		extends TransformerAdapter<EclipseLinkTypeMapping, Iterable<EclipseLinkConverter>>
	{
		@Override
		public Iterable<EclipseLinkConverter> transform(EclipseLinkTypeMapping mapping) {
			return mapping.getConverters();
		}
	}
}
