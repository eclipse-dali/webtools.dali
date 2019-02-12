/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEmbeddable;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;

/**
 * JPA 2.0
 * Java embeddable type mapping
 */
public class GenericJavaEmbeddable2_0
	extends AbstractJavaEmbeddable
{
	public GenericJavaEmbeddable2_0(JavaPersistentType parent, EmbeddableAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
	}

	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return ArrayTools.contains(ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}

	public static final String[] ALLOWED_ATTRIBUTE_MAPPING_KEYS =
		new String[] {
			MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY,
			MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
		};
}
