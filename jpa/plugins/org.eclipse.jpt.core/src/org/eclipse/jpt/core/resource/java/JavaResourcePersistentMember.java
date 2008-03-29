/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaResourcePersistentMember extends JavaResourceNode
{

	/**
	 * Return all <code>JavaResource</code>s that correspond to type
	 * mapping annotations specified in the source code.  In JPA these could be 
	 * Entity, MappedSuperclass, Embeddable.
	 * <p>Does not return duplicate annotations as this error is handled by the java compiler.
	 */
	<T extends JavaResourceNode> Iterator<T> mappingAnnotations();
	
		String MAPPING_ANNOTATIONS_COLLECTION = "mappingAnnotationsCollection";
	
	int mappingAnnotationsSize();
	/**
	 * Return the <code>JavaResource</code> specified on this JavaPersistentResource
	 * In the case of multiples the first one will be returned as defined by the order of
	 * {@link org.eclipse.jpt.core.internal.platform.GenericJpaPlatform#typeMappingAnnotationDefinitions()} or 
	 * {@link org.eclipse.jpt.core.internal.platform.GenericJpaPlatform#attributeMappingAnnotationDefinitions()}
	 * @return
	 */
	JavaResourceNode getMappingAnnotation();

	/**
	 * Returns the <code>JavaResource</code> with this fully qualifed annotation name. 
	 * In JPA the valid annotations are "javax.persistence.Embedddable", "javax.persistence.Entity", 
	 * and "javax.persistence.MappedSuperclass"
	 * Return the first if there are duplicates in the source code
	 * @param annotationName - fully qualified annotation name
	 * @return
	 */
	//TODO not sure we need this API, first 2 seem sufficient
	JavaResourceNode getMappingAnnotation(String annotationName);

	/**
	 * Use this to change the type mapping annotation.  This will only remove
	 * other mapping annotations in case there were multiple before.  It
	 * will not remove any non-mapping annotations
	 * @param annotationName - fully qualified annotation name
	 */
	void setMappingAnnotation(String annotationName);

	/**
	 * Return all <code>JavaResource</code>s that correspond to annotations in the source code.
	 * Does not return duplicate annotations as this error is handled by the java compiler.
	 * No <code>MappingAnnotation</code>s should be included.
	 * @see #mappingAnnotations()
	 */
	<T extends JavaResourceNode> Iterator<T>  annotations();
	
		String ANNOTATIONS_COLLECTION = "annotationsCollection";

	int annotationsSize();

	//TODO tie the singular and plural annotations together somehow in the resource model so we can give
	//a validation error for the case of both being specified
	/**
	 * Given a nestable and container annotation name return the specified <code>JavaResource</code>s.
	 * If both the nestable and container annotations are specified on the Type, then only
	 * return the nestable annotations specified within the container annotation. This is
	 * only going to return JavaResources that match the nestableAnnotationName
	 */
	<T extends JavaResourceNode> ListIterator<T> annotations(String nestableAnnotationName, String containerAnnotationName);
	
	
	/**
	 * Returns the <code>JavaResource</code> with this fully qualifed annotation name. 
	 * Return the first if there are duplicates in the source code.
	 * @param annotationName
	 * @return
	 */
	JavaResourceNode getAnnotation(String annotationName);
	
	/**
	 * Returns the <code>JavaResource</code> with this fully qualifed annotation name. 
	 * Return the first if there are duplicates in the source code.  Will not return null,
	 * but a null Object instead if no annotation with this name exists in the java source.
	 * @param annotationName
	 * @return
	 */
	JavaResourceNode getNonNullAnnotation(String annotationName);

	/**
	 * Return a null implementation of <code>JavaResourceNode</code> with this fully qualifed annotation name.
	 * The corresponding AnnotationDefinition needs to implement buildNullAnnotation()
	 * {@link AnnotationDefinition#buildNullAnnotation(JavaResourcePersistentMember, org.eclipse.jpt.core.internal.jdtutility.Member)}
	 * @param annotationName
	 * @return
	 */
	JavaResourceNode getNullMappingAnnotation(String annotationName);

	/**
	 * Add an annotation for the given fully qualified annotation name
	 */
	JavaResourceNode addAnnotation(String annotationName);
	
	void removeAnnotation(String annotationName);

	/**
	 * Add a new NestableAnnotation named nestableAnnotationName.  Create a new container annotation
	 * if necessary and add the nestable annotation to it.  If both nestable and container already
	 * exist then add to the container annotation leaving the existing nestable annotaion alone.
	 * If only nestable exists, then create the new container annotation and move the nestable to it
	 * also adding the new one.  If neither exists, create a new nestable annotation.
	 * @return the new JavaResource with the name nestableAnnotationName
	 */
	JavaResourceNode addAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);
	
	void removeAnnotation(int index, String nestableAnnotationName, String containerAnnotationName);
	
	/**
	 * Move nestableAnnotation found in the containerAnnotation from the specified source 
	 * index to the specified target index.    
	 */
	void move(int targetIndex, int sourceIndex, String containerAnnotationName);
	
	/**
	 * Return whether the underlying JDT member is persistable according to the JPA spec
	 * @return
	 */
	boolean isPersistable();
		String PERSISTABLE_PROPERTY = "persistableProperty";
		
	/**
	 * Return whether the underlying JDT member is currently annotated as being persistent
	 * (equivalent to "is mapped")
	 */
	boolean isPersisted();

	/**
	 * Return true if this JavaPersistentResource represents the underlying JDT IMeber
	 * @param member
	 * @return
	 */
	boolean isFor(IMember member);
	
	
	/**
	 * return the text range for the name of the persistent resource
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
	
	/**
	 * Resolve type information that could be dependent on other files being added/removed
	 */
	void resolveTypes(CompilationUnit astRoot);


}
