package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaEmbeddableProvider implements JavaTypeMappingAnnotationProvider
{
	// singleton
	private static final JavaEmbeddableProvider INSTANCE = new JavaEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaEmbeddableProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEmbeddableProvider() {
		super();
	}

	public JavaEmbeddableResource buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return new JavaEmbeddableResourceImpl(type, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return JavaEmbeddableResource.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.EMBEDDABLE;
	}
}
