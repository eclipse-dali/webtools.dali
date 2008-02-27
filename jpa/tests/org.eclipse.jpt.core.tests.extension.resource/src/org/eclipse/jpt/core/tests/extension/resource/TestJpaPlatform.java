package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Collection;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatform;

public class TestJpaPlatform extends GenericJpaPlatform
{
	public static final String ID = "core.testJpaPlatform";

	@Override
	public String getId() {
		return ID;
	}

	@Override
	protected JpaFactory buildJpaFactory() {
		return new TestJpaFactory();
	}
	
	@Override
	protected void addJavaTypeMappingProvidersTo(Collection<JavaTypeMappingProvider> providers) {
		super.addJavaTypeMappingProvidersTo(providers);
		providers.add(TestTypeMappingProvider.instance());
	}
	
	@Override
	protected void addJavaAttributeMappingProvidersTo(Collection<JavaAttributeMappingProvider> providers) {
		super.addJavaAttributeMappingProvidersTo(providers);
		providers.add(TestAttributeMappingProvider.instance());
	}

}
