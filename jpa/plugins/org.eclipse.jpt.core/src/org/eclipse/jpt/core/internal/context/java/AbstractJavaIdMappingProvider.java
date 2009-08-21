/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.IdAnnotation;

public abstract class AbstractJavaIdMappingProvider
	extends AbstractJavaAttributeMappingProvider
{
	protected AbstractJavaIdMappingProvider() {
		super();
	}
	
	
	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return IdAnnotation.ANNOTATION_NAME;
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return factory.buildJavaIdMapping(parent);
	}
}
