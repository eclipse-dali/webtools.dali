package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Collection;
import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;

public class TestJpaPlatform extends BaseJpaPlatform
{
	public static final String PLATFORM_ID = "core.testJpaPlatform";

	public String getId() {
		return PLATFORM_ID;
	}

	protected IJpaFactory createJpaFactory() {
		return new TestJpaFactory();
	}
	
	@Override
	protected void addJavaTypeMappingProvidersTo(Collection<IJavaTypeMappingProvider> providers) {
		super.addJavaTypeMappingProvidersTo(providers);
		providers.add(TestTypeMappingProvider.instance());
	}
	
	@Override
	protected void addJavaAttributeMappingProvidersTo(Collection<IJavaAttributeMappingProvider> providers) {
		super.addJavaAttributeMappingProvidersTo(providers);
		providers.add(TestAttributeMappingProvider.instance());
	}

}
