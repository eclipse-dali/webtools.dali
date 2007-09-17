package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface JavaResource
{
	JpaPlatform jpaPlatform();
	
	void updateFromJava(CompilationUnit astRoot);

}
