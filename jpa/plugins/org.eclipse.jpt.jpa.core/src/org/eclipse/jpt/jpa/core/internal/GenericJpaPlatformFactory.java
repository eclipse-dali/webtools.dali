/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaFactory;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar1_0;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * zero-argument constructor
	 */
	public GenericJpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(String id) {
		return new GenericJpaPlatform(
			id,
			this.buildJpaVersion(),
			new GenericJpaFactory(),
			new JpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider.instance()),
			GenericJpaPlatformProvider.instance(),
			this.buildJpaPlatformVariation(),
			JPQLGrammar1_0.instance());
	}

	private JpaPlatform.Version buildJpaVersion() {
		return new SimpleVersion(JpaFacet.VERSION_1_0.getVersionString());
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

	public static class SimpleVersion implements JpaPlatform.Version {
		protected final String jpaVersion;

		public SimpleVersion(String jpaVersion) {
			super();
			this.jpaVersion = jpaVersion;
		}

		/**
		 * The generic platform's version is the same as the JPA version.
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
			return VERSION_COMPARATOR.compare(this.jpaVersion, version) >= 0;
		}

		@Override
		public String toString() {
			return "JPA version: " + this.getJpaVersion(); //$NON-NLS-1$
		}
	}
}