package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;

public interface JavaTypeAnnotation extends JavaResource
{	
	String getAnnotationName();
	
	DeclarationAnnotationAdapter getDeclarationAnnotationAdapter();
	
	void removeAnnotation();

	void newAnnotation();
}
