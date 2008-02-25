package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Collection;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.BaseJpaPlatform;

public class TestJpaPlatform extends BaseJpaPlatform
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
