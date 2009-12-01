/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_2;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class EclipseLink1_2JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public EclipseLink1_2JpaPlatformFactory() {
		super();
	}
	
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new EclipseLinkJpaFactory(), 
			buildJpaAnnotationProvider(),
			EclipseLink1_2JpaPlatformProvider.instance(), 
			buildJpaPlatformVariation());
	}
	
	protected JpaAnnotationProvider buildJpaAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}
	
	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.YES;
			}
			public boolean isJoinTableOverridable() {
				return false;
			}
		};
	}
	
	private JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkVersion(
				JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_1_2,
				JptCorePlugin.JPA_FACET_VERSION_1_0
			);
	}
}
