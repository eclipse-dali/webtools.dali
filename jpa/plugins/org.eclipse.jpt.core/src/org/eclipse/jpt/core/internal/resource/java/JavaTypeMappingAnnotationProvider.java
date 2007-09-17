package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public interface JavaTypeMappingAnnotationProvider extends JavaTypeAnnotationProvider
{
	/**
	 * Return the fully qualified names of the annotations that can exist
	 * with the mapping annotation.  These will be used to create JavaTypeAnnotations
	 */
	Iterator<String> correspondingAnnotationNames();
	
	JavaTypeMappingAnnotation buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform);
}
