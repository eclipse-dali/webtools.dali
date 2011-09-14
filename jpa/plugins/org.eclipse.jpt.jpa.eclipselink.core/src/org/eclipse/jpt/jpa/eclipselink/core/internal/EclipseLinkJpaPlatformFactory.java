/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.Comparator;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory.SimpleVersion;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.wst.common.project.facet.core.DefaultVersionComparator;

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
			buildAnnotationProvider(), 
			EclipseLinkJpaPlatformProvider.instance(), 
			buildJpaPlatformVariation());
	}
	
	private JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkVersion(
				JptJpaEclipseLinkCorePlugin.ECLIPSELINK_PLATFORM_VERSION_1_0,
				JpaFacet.VERSION_1_0.getVersionString());
	}
	
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
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
		public static final Comparator<String> VERSION_COMPARATOR = new DefaultVersionComparator();

		protected final String eclipseLinkVersion;

		public EclipseLinkVersion(String eclipseLinkVersion, String jpaVersion) {
			super(jpaVersion);
			this.eclipseLinkVersion = eclipseLinkVersion;
		}

		@Override
		public String getVersion() {
			return this.eclipseLinkVersion;
		}
		
		/**
		 * Return whether the platform is compatible with the specified EclipseLink version.
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_1_0
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_1_1
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_1_2
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_2_0
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_2_1
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_2_2
		 * @see JptJpaEclipseLinkCorePlugin#ECLIPSELINK_PLATFORM_VERSION_2_3
		 */
		public boolean isCompatibleWithVersion(String version) {
			return VERSION_COMPARATOR.compare(this.eclipseLinkVersion, version) >= 0;
		}

		@Override
		public String toString() {
			return super.toString() + " EclipseLink version: " + this.getVersion(); //$NON-NLS-1$
		}
	}

}
