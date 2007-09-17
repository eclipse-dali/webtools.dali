package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class JavaSecondaryTableProvider implements JavaTypeAnnotationProvider
{
	// singleton
	private static final JavaSecondaryTableProvider INSTANCE = new JavaSecondaryTableProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaSecondaryTableProvider() {
		super();
	}


	public JavaSecondaryTableResource buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return null;
		//TODO //return JavaSecondaryTableResourceImpl.createJavaSecondaryTable(jpaPlatform, type);
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return JavaSecondaryTableResource.DECLARATION_ANNOTATION_ADAPTER;
	}
}
