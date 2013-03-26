/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaAnnotationDefinitionProvider2_0;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlDefinition2_1;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar2_2;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class EclipseLink2_2JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink2_2"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 2.2
	 */
	public static final String VERSION = "2.2";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLink2_2JpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory2_0(),
			this.buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider2_2.instance(),
			this.buildJpaVariation(),
			EclipseLinkJPQLGrammar2_2.instance());
	}

	protected JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new EclipseLinkJpaPlatformVersion(VERSION, jpaFacetVersion.getVersionString());
	}

	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider2_0.instance(),
			EclipseLink2_2JpaAnnotationDefinitionProvider.instance());
	}

	protected JpaPlatformVariation buildJpaVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.YES;
			}
			public boolean isJoinTableOverridable() {
				return true;
			}
			public AccessType[] getSupportedAccessTypes(JptResourceType resourceType) {
				return resourceType.isKindOf(EclipseLinkOrmXmlDefinition2_1.instance().getResourceType()) ?
						EclipseLink2_1JpaPlatformFactory.SUPPORTED_ACCESS_TYPES :
						GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}
}
