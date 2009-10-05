/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.JpaAnnotationProvider;
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
public class EclipseLinkJpaPlatformFactory
	implements JpaPlatformFactory
{

	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory() {
		super();
	}
	
	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			buildJpaVersion(),
			new EclipseLinkJpaFactory(), 
			buildJpaAnnotationProvider(), 
			EclipseLinkJpaPlatformProvider.instance(), 
			buildJpaPlatformVariation());
	}
	
	private JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkVersion(
				JptEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_1_0,
				JptCorePlugin.JPA_FACET_VERSION_1_0
			);
	}
	
	protected JpaAnnotationProvider buildJpaAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}
	
	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.NO;
			}
			public boolean isJoinTableOverridable() {
				return false;
			}
		};
	}


	public static class EclipseLinkVersion extends SimpleVersion {
		protected final String eclipseLinkVersion;

		public EclipseLinkVersion(String eclipseLinkVersion, String jpaVersion) {
			super(jpaVersion);
			this.eclipseLinkVersion = eclipseLinkVersion;
		}

		@Override
		public String getVersion() {
			return this.eclipseLinkVersion;
		}

		@Override
		public String toString() {
			return super.toString() + " EclipseLink version: " + this.getVersion(); //$NON-NLS-1$
		}
	}

}
