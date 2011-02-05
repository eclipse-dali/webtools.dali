/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.core.context.AttributeMapping;

/**
 * Gather some of the behavior common to the Java and XML models. :-(
 */
public final class AttributeMappingTools
{

	// ********** all mapping names transformer **********

	public static final Transformer<AttributeMapping, Iterator<String>> ALL_MAPPING_NAMES_TRANSFORMER = new AllMappingNamesTransformer();
	static class AllMappingNamesTransformer
		implements Transformer<AttributeMapping, Iterator<String>>
	{
		public Iterator<String> transform(AttributeMapping mapping) {
			return mapping.allMappingNames();
		}
	}


	// ********** all overridable attribute mapping names transformer **********

	public static final Transformer<AttributeMapping, Iterator<String>> ALL_OVERRIDABLE_ATTRIBUTE_MAPPING_NAMES_TRANSFORMER = new AllOverridableAttributeMappingNamesTransformer();
	static class AllOverridableAttributeMappingNamesTransformer
		implements Transformer<AttributeMapping, Iterator<String>>
	{
		public Iterator<String> transform(AttributeMapping mapping) {
			return mapping.allOverridableAttributeMappingNames();
		}
	}


	// ********** all overridable association mapping names transformer **********

	public static final Transformer<AttributeMapping, Iterator<String>> ALL_OVERRIDABLE_ASSOCIATION_MAPPING_NAMES_TRANSFORMER = new AllOverridableAssociationMappingNamesTransformer();
	static class AllOverridableAssociationMappingNamesTransformer
		implements Transformer<AttributeMapping, Iterator<String>>
	{
		public Iterator<String> transform(AttributeMapping mapping) {
			return mapping.allOverridableAssociationMappingNames();
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private AttributeMappingTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
