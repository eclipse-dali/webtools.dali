package org.eclipse.jpt.core.internal.resource.java;

import java.util.ListIterator;

public interface JavaPluralTypeAnnotation<E extends JavaSingularTypeAnnotation> extends JavaTypeAnnotation
{
	String getSingularAnnotationName();
	
	ListIterator<E> javaTypeAnnotations();
	
	int javaTypeAnnotationsSize();

	E singularAnnotationAt(int index);
	
	int indexOf(E singularTypeAnnotation);
	
	E add(int index);
	
	void remove(E javaTypeAnnotation);
	
	void remove(int index);
	
	void move(int oldIndex, int newIndex);
}
