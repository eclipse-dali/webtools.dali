/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.Type;

public interface JpaAnnotationProvider
{
	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param type
	 * @param mappingAnnotationName
	 * @return
	 */
	Annotation buildTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String mappingAnnotationName);

	Annotation buildNullTypeMappingAnnotation(JavaResourcePersistentType parent, Type type, String mappingAnnotationName);

	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param type
	 * @param annotationName
	 * @return
	 */
	Annotation buildTypeAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	
	Annotation buildNullTypeAnnotation(JavaResourcePersistentType parent, Type type, String annotationName);
	
	/**
	 * Ordered iterator of fully qualified annotation names that can apply to a Type
	 */
	ListIterator<String> typeMappingAnnotationNames();
	
	/**
	 * Iterator of fully qualified annotation(non-mapping) names that can apply to a Type
	 */
	Iterator<String> typeAnnotationNames();
	
	/**
	 * Build a Annotation with the given fully qualififed annotation name.
	 * @param attribute
	 * @param mappingAnnotationName
	 * @return
	 */
	Annotation buildAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String mappingAnnotationName);
	
	Annotation buildNullAttributeMappingAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String mappingAnnotationName);

	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param attribute
	 * @param annotationName
	 * @return
	 */
	Annotation buildAttributeAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);
	
	Annotation buildNullAttributeAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, String annotationName);

	
	/**
	 * Ordered iterator of fully qualified annotation names that can apply to an Attribute
	 */
	ListIterator<String> attributeMappingAnnotationNames();
	
	/**
	 * Iterator of fully qualified annotation(non-mapping) names that can apply to an Attribute
	 */
	Iterator<String>  attributeAnnotationNames();

}
