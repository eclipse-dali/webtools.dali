package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;

public interface JavaTableResource extends JavaTypeAnnotation
{
	String getName();
	
	void setName(String name);
	
	String getCatalog();
	
	void setCatalog(String catalog);

	String getSchema();
	
	void setSchema(String schema);
	
	void updateFromJava(CompilationUnit astRoot);

}
