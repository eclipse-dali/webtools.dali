package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import java.util.ListIterator;

public interface JpaPlatform
{
	ListIterator<JavaTypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders();
	
	JavaTypeMappingAnnotationProvider javaTypeMappingAnnotationProvider(String annotationName);

	Iterator<JavaTypeAnnotationProvider> javaTypeAnnotationProviders();
	
	JavaTypeAnnotationProvider javaTypeAnnotationProvider(String annotationName);
	
	Iterator<JavaTypeAnnotationProvider> entityAnnotationProviders();
	
	Iterator<JavaTypeAnnotationProvider> embeddableAnnotationProviders();
	
	Iterator<JavaTypeAnnotationProvider> mappedSuperclassAnnotationProviders();

}
