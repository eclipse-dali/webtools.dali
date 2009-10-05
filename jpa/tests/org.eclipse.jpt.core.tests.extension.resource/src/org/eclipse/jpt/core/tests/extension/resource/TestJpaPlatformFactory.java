/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.GenericJpaPlatformFactory.SimpleVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
@SuppressWarnings("nls")
public class TestJpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public TestJpaPlatformFactory() {
		super();
	}
	
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new TestJpaFactory(), 
			new GenericJpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider.instance()), 
			TestJpaPlatformProvider.instance(),
			buildJpaPlatformVariation());
	}
	
	private JpaPlatform.Version buildJpaVersion() {
		return new SimpleVersion(JptCorePlugin.JPA_FACET_VERSION_1_0) {
			@Override
			public String getVersion() {
				return "BOOOYAH!";
			}
		};
	}
	
	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
			}
			public boolean isJoinTableOverridable() {
				return false;
			}
		};
	}
}
