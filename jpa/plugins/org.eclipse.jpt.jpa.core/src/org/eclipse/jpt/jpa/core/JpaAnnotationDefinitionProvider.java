/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Provides annotation definitions and nestable annotation definitions
 * {@link org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider}
 * then uses a collection of these to build annotations.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.2
 */
public interface JpaAnnotationDefinitionProvider
{
	/**
	 * Return all annotation definitions
	 */
	Iterable<AnnotationDefinition> getAnnotationDefinitions();
	Transformer<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>> ANNOTATION_DEFINITIONS_TRANSFORMER = new AnnotationDefinitionsTransformer();
	class AnnotationDefinitionsTransformer
		extends TransformerAdapter<JpaAnnotationDefinitionProvider, Iterable<AnnotationDefinition>>
	{
		@Override
		public Iterable<AnnotationDefinition> transform(JpaAnnotationDefinitionProvider provider) {
			return provider.getAnnotationDefinitions();
		}
	}

	/**
	 * Return all nestable annotation definitions
	 */
	Iterable<NestableAnnotationDefinition> getNestableAnnotationDefinitions();
	Transformer<JpaAnnotationDefinitionProvider, Iterable<NestableAnnotationDefinition>> NESTABLE_ANNOTATION_DEFINITIONS_TRANSFORMER = new NestableAnnotationDefinitionsTransformer();
	class NestableAnnotationDefinitionsTransformer
		extends TransformerAdapter<JpaAnnotationDefinitionProvider, Iterable<NestableAnnotationDefinition>>
	{
		@Override
		public Iterable<NestableAnnotationDefinition> transform(JpaAnnotationDefinitionProvider provider) {
			return provider.getNestableAnnotationDefinitions();
		}
	}
}
