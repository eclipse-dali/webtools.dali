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

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaEmbeddable;


public class GenericJavaEmbeddable extends AbstractJavaEmbeddable
{
	public GenericJavaEmbeddable(JavaPersistentType parent) {
		super(parent);
	}
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		//generic only allows basic and transient within an Embeddable
		return attributeMappingKey == MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
}
