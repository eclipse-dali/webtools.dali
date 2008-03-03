/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import java.util.Collection;
import org.eclipse.jpt.core.JpaAnnotationProvider;
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
	public JpaAnnotationProvider annotationProvider() {
		return super.annotationProvider();
	}
	
	@Override
	protected void addJavaTypeMappingProvidersTo(Collection<JavaTypeMappingProvider> providers) {
		super.addJavaTypeMappingProvidersTo(providers);
		providers.add(JavaTestTypeMappingProvider.instance());
	}
	
	@Override
	protected void addJavaAttributeMappingProvidersTo(Collection<JavaAttributeMappingProvider> providers) {
		super.addJavaAttributeMappingProvidersTo(providers);
		providers.add(JavaTestAttributeMappingProvider.instance());
	}

}
