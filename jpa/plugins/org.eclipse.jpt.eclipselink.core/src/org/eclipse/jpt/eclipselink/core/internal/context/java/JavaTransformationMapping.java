/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.TransformationMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.TransformationAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaTransformationMapping extends AbstractJavaAttributeMapping<TransformationAnnotation> implements TransformationMapping
{
	
	public JavaTransformationMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.TRANSFORMATION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return TransformationAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}
}
