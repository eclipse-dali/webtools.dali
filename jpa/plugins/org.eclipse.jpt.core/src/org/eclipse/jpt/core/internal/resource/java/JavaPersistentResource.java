/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IMember;

public interface JavaPersistentResource extends JavaResource
{

	/**
	 * Return all <code>MappingAnnotation</code>s that correspond to type
	 * mapping annotations specified in the source code.  In JPA these could be 
	 * Entity, MappedSuperclass, Embeddable.
	 * <p>Does not return duplicate annotations as this error is handled by the java compiler.
	 */
	Iterator<MappingAnnotation> mappingAnnotations();
	
	/**
	 * Return the <code>MappingAnnotation</code> specified on this JavaPersistentTypeResource
	 * In the case of multiples the first one will be returned as defined by the order of 
	 * {@link JpaPlatform#javaTypeMappingAnnotationProviders()}
	 * @return
	 */
	MappingAnnotation mappingAnnotation();

	/**
	 * Returns the <code>MappingAnnotation</code> with this fully qualifed annotation name. 
	 * In JPA the valid annotations are "javax.persistence.Embedddable", "javax.persistence.Entity", 
	 * and "javax.persistence.MappedSuperclass"
	 * Return the first if there are duplicates in the source code
	 * @param annotationName - fully qualified annotation name
	 * @return
	 */
	//TODO not sure we need this API, first 2 seem sufficient
	MappingAnnotation mappingAnnotation(String annotationName);

	/**
	 * Use this to change the type mapping annotation.  This will
	 * remove all annotations that applied to the previous type mapping annotation
	 * but do not apply to the new mapping annotation.  Will also remove
	 * all other type mapping annotations in case there were multiple before.
	 * @see MappingAnnotationProvider#correspondingAnnotationNames()
	 * @param annotationName - fully qualified annotation name
	 */
	void setMappingAnnotation(String annotationName);

	/**
	 * Return all <code>Annotation</code>s that correspond to annotations in the source code.
	 * Does not return duplicate annotations as this error is handled by the java compiler.
	 * No <code>MappingAnnotation</code>s should be included.
	 * @see #mappingAnnotations()
	 */
	Iterator<Annotation> annotations();
	
	//TODO tie the singular and plural annotations together somehow in the resource model so we can give
	//a validation error for the case of both being specified
	/**
	 * Given a singular and plural annotation name return the specified <code>Annotation</code>s.
	 * If both the singular and plural annotations are specified on the Type, then only
	 * return the Annotations specified within the plural annotation.
	 * @param singularAnnotation
	 * @param pluralAnnotation
	 * @return
	 */
	ListIterator<Annotation> annotations(String singularAnnotation, String pluralAnnotation);
	
	
	/**
	 * Returns the <code>Annotation</code> with this fully qualifed annotation name. 
	 * Return the first if there are duplicates in the source code.
	 * @param annotationName
	 * @return
	 */
	Annotation annotation(String annotationName);
	
	/**
	 * Add an annotation for the given fully qualified annotation name
	 */
	Annotation addAnnotation(String annotationName);
	
	/**
	 * Add a new Annotation named singularAnnotationName.  Create a new plural annotation
	 * if necessary and add the singular annotation to it.  If both singular and plural already
	 * exist then add to the plural annotation leaving the existing singular annotaion alone.
	 * If only singular exists, then create the new plural annotation and move the singular to it
	 * also adding the new one.  If neither exists, create a new singular annotation.
	 * @return the new Annotation with the name singularAnnotationName
	 */
	Annotation addAnnotation(int index, String singularAnnotationName, String pluralAnnotationName);
	
	void move(int oldIndex, int newIndex, String pluralAnnotationName);
	
	void move(int newIndex, SingularAnnotation singularAnnotation, String pluralAnnotationName);
	
	void removeAnnotation(String annotationName);
	
	void removeAnnotation(SingularAnnotation singularAnnotation, String pluralAnnotationName);
	
	void removeAnnotation(int index, String pluralAnnotationName);
		
	/**
	 * Return whether the underlying JDT member is persistable according to the JPA spec
	 * @return
	 */
	boolean isPersistable();
	
	/**
	 * Return true if this JavaPersistentResource represents the underlying JDT IMeber
	 * @param member
	 * @return
	 */
	boolean isFor(IMember member);

}
