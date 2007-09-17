package org.eclipse.jpt.core.internal.resource.java;


public interface JavaSingularTypeAnnotation extends JavaTypeAnnotation
{
	void initializeFrom(JavaSingularTypeAnnotation oldAnnotation);
	
	void moveAnnotation(int newIndex);
	
	void removeAnnotation();
}
