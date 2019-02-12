/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatformFactory.GenericJpaPlatformVersion;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.persistence.jpa.jpql.parser.JPQLGrammar2_1;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformFactory2_1
	implements JpaPlatformFactory
{
	public static final String ID = "generic2_1"; //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public GenericJpaPlatformFactory2_1() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			this.buildJpaVersion(config.getJpaFacetVersion()),
			new GenericJpaFactory2_1(),
			new JpaAnnotationProvider(GenericJpaAnnotationDefinitionProvider2_1.instance()),
			GenericJpaPlatformProvider2_1.instance(),
			this.buildJpaPlatformVariation(),
			JPQLGrammar2_1.instance());
	}

	private JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new GenericJpaPlatformVersion(jpaFacetVersion.getVersionString());
	}

	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			//table_per_class inheritance support is optional in the 2.1 spec
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.MAYBE;
			}
			public boolean isJoinTableOverridable() {
				return true;
			}
			public AccessType[] getSupportedAccessTypes(JptResourceType resourceType) {
				return GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}
}
