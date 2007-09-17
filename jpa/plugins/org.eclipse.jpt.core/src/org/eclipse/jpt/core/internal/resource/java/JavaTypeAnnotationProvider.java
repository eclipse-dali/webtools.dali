package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public interface JavaTypeAnnotationProvider
{
	/**
	 * Return the fully qualified annotation name
	 */
	String getAnnotationName();
		
	DeclarationAnnotationAdapter getDeclarationAnnotationAdapter();
	
	JavaTypeAnnotation buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform);
}
