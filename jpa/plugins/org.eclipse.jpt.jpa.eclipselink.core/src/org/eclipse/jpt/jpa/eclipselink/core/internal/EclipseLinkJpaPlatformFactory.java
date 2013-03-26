/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.VersionComparator;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory.GenericJpaPlatformVersion;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar1;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class EclipseLinkJpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "org.eclipse.eclipselink.platform"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 1.0
	 */
	public static final String VERSION = "1.0";  //$NON-NLS-1$

	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String GROUP_ID = "eclipselink"; //$NON-NLS-1$


	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory(),
			buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider.instance(),
			buildJpaPlatformVariation(),
			EclipseLinkJPQLGrammar1.instance());
	}

	private JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new EclipseLinkJpaPlatformVersion(VERSION, jpaFacetVersion.getVersionString());
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
			public AccessType[] getSupportedAccessTypes(JptResourceType resourceType) {
				return GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}

	/**
	 * EclipseLink JPA platform version
	 */
	public static class EclipseLinkJpaPlatformVersion
		extends GenericJpaPlatformVersion
	{
		protected final String eclipseLinkVersion;

		public EclipseLinkJpaPlatformVersion(String eclipseLinkVersion, String jpaVersion) {
			super(jpaVersion);
			this.eclipseLinkVersion = eclipseLinkVersion;
		}

		@Override
		public String getVersion() {
			return this.eclipseLinkVersion;
		}

		/**
		 * Return whether the platform is compatible with the specified EclipseLink version.
		 * @see EclipseLinkJpaPlatformFactory#VERSION
		 * @see EclipseLink1_1JpaPlatformFactory#VERSION
		 * @see EclipseLinkJpaPlatformFactory1_2#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_0#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_1#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_2#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_3#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_4#VERSION
		 * @see EclipseLinkJpaPlatformFactory2_5#VERSION
		 */
		public boolean isCompatibleWithEclipseLinkVersion(String version) {
			return VersionComparator.INTEGER_VERSION_COMPARATOR.compare(this.eclipseLinkVersion, version) >= 0;
		}

		@Override
		public String toString() {
			return super.toString() + "/EclipseLink " + this.eclipseLinkVersion; //$NON-NLS-1$
		}
	}
}
