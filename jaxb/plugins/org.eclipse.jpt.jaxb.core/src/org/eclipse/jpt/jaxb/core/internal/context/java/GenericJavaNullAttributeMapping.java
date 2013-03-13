/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;


public class GenericJavaNullAttributeMapping
		extends AbstractJavaAttributeMapping<Annotation> {
	
	public GenericJavaNullAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public Annotation getAnnotation() {
		return null;
	}

	@Override
	public String getAnnotationName() {
		return null;
	}
}
