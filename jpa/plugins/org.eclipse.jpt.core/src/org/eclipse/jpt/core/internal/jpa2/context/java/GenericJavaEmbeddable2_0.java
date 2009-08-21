/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEmbeddable;
import org.eclipse.jpt.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.utility.internal.CollectionTools;


public class GenericJavaEmbeddable2_0
	extends AbstractJavaEmbeddable
{
	public GenericJavaEmbeddable2_0(JavaPersistentType parent) {
		super(parent);
	}
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return CollectionTools.contains(ALLOWED_ATTRIBUTE_MAPPING_KEYS, attributeMappingKey);
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
