package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class JavaTableProvider implements JavaTypeAnnotationProvider
{
	// singleton
	private static final JavaTableProvider INSTANCE = new JavaTableProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaTableProvider() {
		super();
	}


	public JavaTableResource buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return new JavaTableResourceImpl(jpaPlatform, type);
	}

	public String getAnnotationName() {
		return JPA.TABLE;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return JavaTableResourceImpl.DECLARATION_ANNOTATION_ADAPTER;
	}
}
