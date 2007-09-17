package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;

public interface JavaPersistentTypeResource extends JavaResource
{

	/**
	 * Return all <code>JavaTypeMappingAnnotation</code>s that correspond to type
	 * mapping annotations specified in the source code.  In JPA these could be 
	 * Entity, MappedSuperclass, Embeddable.
	 * <p>Does not return duplicate annotations as this error is handled by the java compiler.
	 */
	Iterator<JavaTypeMappingAnnotation> javaTypeMappingAnnotations();
	
	/**
	 * Return the <code>JavaTypeMappingAnnotation</code> specified on this JavaPersistentType
	 * In the case of multiples the first one will be returned as defined by the order of 
	 * {@link JpaPlatform#javaTypeMappingAnnotationProviders()}
	 * @return
	 */
	JavaTypeMappingAnnotation javaTypeMappingAnnotation();

	/**
	 * TODO not sure we need this API, first 2 seem sufficient
	 * 
	 * Returns the <code>JavaTypeMappingAnnotation</code> with this fully qualifed annotation name. 
	 * In JPA the valid annotations are "javax.persistence.Embedddable", "javax.persistence.Entity", 
	 * and "javax.persistence.MappedSuperclass"
	 * Return the first if there are duplicates in the source code.
	 * @param annotationName
	 * @return
	 */
	JavaTypeMappingAnnotation javaTypeMappingAnnotation(String annotationName);

	void setJavaTypeMappingAnnotation(String annotationName);

	/**
	 * Return all <code>JavaTypeAnnotation</code>s that correspond to annotations in the source code.
	 * Does not return duplicate annotations as this error is handled by the java compiler.
	 * No <code>JavaTypeMappingAnnotation</code>s should be included.
	 * @see #javaTypeMappingAnnotations()
	 */
	Iterator<JavaTypeAnnotation> javaTypeAnnotations();
	
	//TODO tie the singular and plural annotations together somehow in the resource model so we can give
	//a validation error for the case of both being specified
	/**
	 * Given a singular and plural annotation name return the specified JavaTypeAnnotations.
	 * If both the singular and plural annotations are specified on the Type, then only
	 * return the JavaTypeAnnotations from the plural annotation.
	 * @param singularAnnotation
	 * @param pluralAnnotation
	 * @return
	 */
	ListIterator<JavaTypeAnnotation> javaTypeAnnotations(String singularAnnotation, String pluralAnnotation);
	
	
	/**
	 * Returns the JavaTypeAnnotation with this fully qualifed annotation name. 
	 * Return the first if there are duplicates in the source code.
	 * @param annotationName
	 * @return
	 */
	JavaTypeAnnotation javaTypeAnnotation(String annotationName);
	
	JavaTypeAnnotation addJavaTypeAnnotation(String annotationName);
	
	/**
	 * Add a new Annotation named singularAnnotationName.  Create a new plural annotation
	 * if necessary and add the singular annotation to it.  If both singular and plural already
	 * exist then add to the plural annotation leaving the existing singular annotaion alone.
	 * If only singular exists, then create the new plural annotation and move the singular to it
	 * also adding the new one.  If neither exists, create a new singular annotation.
	 * @return the new Annotation with the name singularAnnotationName
	 */
	JavaTypeAnnotation addJavaTypeAnnotation(int index, String singularAnnotationName, String pluralAnnotationName);
	
	void move(int oldIndex, int newIndex, String pluralAnnotationName);
	
	void move(int newIndex, JavaSingularTypeAnnotation singularAnnotation, String pluralAnnotationName);
	
	void removeJavaTypeAnnotation(String annotationName);
	
	void removeJavaTypeAnnotation(JavaSingularTypeAnnotation singularAnnotation, String pluralAnnotationName);
	
	void removeJavaTypeAnnotation(int index, String pluralAnnotationName);
}
