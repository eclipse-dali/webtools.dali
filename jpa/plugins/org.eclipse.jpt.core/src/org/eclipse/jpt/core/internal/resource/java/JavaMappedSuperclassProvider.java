package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaMappedSuperclassProvider implements JavaTypeMappingAnnotationProvider
{
	// singleton
	private static final JavaMappedSuperclassProvider INSTANCE = new JavaMappedSuperclassProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaMappedSuperclassProvider() {
		super();
	}


	public JavaMappedSuperclassResource buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return new JavaMappedSuperclassResourceImpl(type, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ID_CLASS,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD);
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return JavaMappedSuperclassResource.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.MAPPED_SUPERCLASS;
	}
}
