/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.core.internal.resource.java.Annotation;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;

public interface IJpaAnnotationProvider
{
	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param type
	 * @param mappingAnnotationName
	 * @return
	 */
	Annotation buildTypeMappingAnnotation(JavaPersistentTypeResource parent, Type type, String mappingAnnotationName);

	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param type
	 * @param annotationName
	 * @return
	 */
	Annotation buildTypeAnnotation(JavaPersistentTypeResource parent, Type type, String annotationName);
	
	Annotation buildNullTypeAnnotation(JavaPersistentTypeResource parent, Type type, String annotationName);

	/**
	 * Return the fully qualified names of the annotations that can exist
	 * with the given mapping annotation on a Type.  This will be all the JPA 
	 * annotations that can apply in the same context as the given mapping annotation. 
	 * @param mappingAnnotationName
	 * @return
	 */
	Iterator<String> correspondingTypeAnnotationNames(String mappingAnnotationName);
	
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
	Annotation buildAttributeMappingAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, String mappingAnnotationName);
	
	/**
	 * Build an Annotation with the given fully qualififed annotation name.
	 * @param attribute
	 * @param annotationName
	 * @return
	 */
	Annotation buildAttributeAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, String annotationName);
	
	Annotation buildNullAttributeAnnotation(JavaPersistentAttributeResource parent, Attribute attribute, String annotationName);

	/**
	 * Return the fully qualified names of the annotations that can exist
	 * with the given mapping annotation on an attribute.  This will be all the JPA 
	 * annotations that can apply in the same context as the given mapping annotation. 
	 * @param mappingAnnotationName
	 * @return
	 */
	Iterator<String> correspondingAttributeAnnotationNames(String mappingAnnotationName);
	
	/**
	 * Ordered iterator of fully qualified annotation names that can apply to an Attribute
	 */
	ListIterator<String> attributeMappingAnnotationNames();
	
	/**
	 * Iterator of fully qualified annotation(non-mapping) names that can apply to an Attribute
	 */
	Iterator<String>  attributeAnnotationNames();

}
