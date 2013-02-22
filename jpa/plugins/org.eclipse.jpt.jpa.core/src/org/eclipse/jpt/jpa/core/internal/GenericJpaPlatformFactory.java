/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.VersionComparator;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaFactory;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar1_0;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "generic"; //$NON-NLS-1$

	/**
	 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String GROUP_ID = "generic"; //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public GenericJpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			this.buildJpaVersion(config.getJpaFacetVersion()),
			new GenericJpaFactory(),
			new JpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider.instance()),
			GenericJpaPlatformProvider.instance(),
			this.buildJpaPlatformVariation(),
			JPQLGrammar1_0.instance());
	}

	private JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new GenericJpaPlatformVersion(jpaFacetVersion.getVersionString());
	}

	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
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
	 * Generic JPA platform version
	 */
	public static class GenericJpaPlatformVersion
		implements JpaPlatform.Version
	{
		protected final String jpaVersion;

		public GenericJpaPlatformVersion(String jpaVersion) {
			super();
			this.jpaVersion = jpaVersion;
		}

		/**
		 * The generic JPA platform's version is the same as the JPA version.
		 */
		public String getVersion() {
			return this.getJpaVersion();
		}

		public String getJpaVersion() {
			return this.jpaVersion;
		}

		/**
		 * For now, generic platforms are backward-compatible.
		 */
		public boolean isCompatibleWithJpaVersion(String version) {
			return VersionComparator.INTEGER_VERSION_COMPARATOR.compare(this.jpaVersion, version) >= 0;
		}

		@Override
		public String toString() {
			return "JPA " + this.jpaVersion; //$NON-NLS-1$
		}
	}
}