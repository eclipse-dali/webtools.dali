/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JpaPlatformVariation;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.core.internal.jpa2.Generic2_0JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class EclipseLink2_0JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public EclipseLink2_0JpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new EclipseLink2_0JpaFactory(), 
			this.buildJpaAnnotationProvider(),
			EclipseLink2_0JpaPlatformProvider.instance(), 
			this.buildJpaVariation());
	}
	
	private JpaPlatform.Version buildJpaVersion() {
		return new JpaPlatform.Version() {
			public String getVersion() {
				return JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_2_0;
			}
			
			public String getJpaVersion() {
				return JptCorePlugin.JPA_FACET_VERSION_2_0;
			}
			
			public boolean is2_0Compatible() {
				return true;
			}
		};
	}
	
	protected JpaAnnotationProvider buildJpaAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			Generic2_0JpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}

	protected JpaPlatformVariation buildJpaVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.YES;
			}
			public boolean isJoinTableOverridable() {
				return true;
			}
		};
	}
}
