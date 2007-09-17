package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Type;


public class JavaMappedSuperclassResourceImpl extends AbstractJavaAnnotationResource<Type> implements JavaMappedSuperclassResource
{
	protected JavaMappedSuperclassResourceImpl(Type type, JpaPlatform jpaPlatform) {
		super(type, jpaPlatform, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public String getAnnotationName() {
		return JPA.MAPPED_SUPERCLASS;
	}
		
	public Iterator<JavaTypeAnnotationProvider> javaTypeAnnotationProviders() {
		return jpaPlatform().mappedSuperclassAnnotationProviders();
	}

	public void updateFromJava(CompilationUnit astRoot) {
	}

}
