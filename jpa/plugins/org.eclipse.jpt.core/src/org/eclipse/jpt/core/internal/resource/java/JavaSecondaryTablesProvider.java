package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class JavaSecondaryTablesProvider implements JavaTypeAnnotationProvider
{
	// singleton
	private static final JavaSecondaryTablesProvider INSTANCE = new JavaSecondaryTablesProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaSecondaryTablesProvider() {
		super();
	}


	public JavaSecondaryTablesResource buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return new JavaSecondaryTablesResourceImpl(type, jpaPlatform);
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLES;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return JavaSecondaryTablesResource.DECLARATION_ANNOTATION_ADAPTER;
	}
}
