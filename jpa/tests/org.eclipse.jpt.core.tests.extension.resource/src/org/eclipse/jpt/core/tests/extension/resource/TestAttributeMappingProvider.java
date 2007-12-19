package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;

public class TestAttributeMappingProvider
	implements IJavaAttributeMappingProvider
{
	// singleton
	private static final TestAttributeMappingProvider INSTANCE = new TestAttributeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private TestAttributeMappingProvider() {
		super();
	}

	public String key() {
		return "test";
	}
	
	public IJavaAttributeMapping buildMapping(Attribute attribute, IJpaFactory factory) {
		return ((TestJpaFactory) factory).createTestAttributeMapping(attribute);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return null;
	}


}
