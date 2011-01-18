/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.resource.java.Annotation;

/**
 * null mapping used when an attribute's default mapping cannot be determined
 */
public class GenericJavaNullAttributeMapping
	extends AbstractJavaAttributeMapping<Annotation>
{
	public GenericJavaNullAttributeMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return null;
	}
	

	// ********** metamodel **********  

	@Override
	public MetamodelField getMetamodelField() {
		return null;
	}
}
