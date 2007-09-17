package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;


public interface JavaTypeMappingAnnotation extends JavaTypeAnnotation
{

	Iterator<JavaTypeAnnotationProvider> javaTypeAnnotationProviders();
}
