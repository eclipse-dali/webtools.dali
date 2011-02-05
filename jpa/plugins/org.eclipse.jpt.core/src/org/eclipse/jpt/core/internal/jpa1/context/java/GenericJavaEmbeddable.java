/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEmbeddable;
import org.eclipse.jpt.core.resource.java.EmbeddableAnnotation;

/**
 * Java embeddable type mapping
 */
public class GenericJavaEmbeddable
	extends AbstractJavaEmbeddable
{
	public GenericJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation mappingAnnotation) {
		super(parent, mappingAnnotation);
	}
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		//generic only allows basic and transient within an Embeddable
		return ArrayTools.contains(ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
	}

	public static final String[] ALLOWED_ATTRIBUTE_MAPPING_KEYS =
		new String[] {
			MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY,
			MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY
		};
}
