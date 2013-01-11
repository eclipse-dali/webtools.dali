/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.internal.jpa2.Generic2_0JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformConfig;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXml2_1Definition;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar2_4;

public class EclipseLink2_4JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink2_4"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 2.4
	 */
	public static final String VERSION = "2.4";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLink2_4JpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatformConfig config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(),
			new EclipseLink2_4JpaFactory(),
			buildAnnotationProvider(),
			EclipseLink2_4JpaPlatformProvider.instance(),
			buildJpaVariation(),
			EclipseLinkJPQLGrammar2_4.instance());
	}

	protected JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkJpaPlatformVersion(VERSION, JpaProject2_0.FACET_VERSION_STRING);
	}

	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
				Generic2_0JpaAnnotationDefinitionProvider.instance(),
				EclipseLink2_4JpaAnnotationDefinitionProvider.instance());
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
				return resourceType.isKindOf(EclipseLinkOrmXml2_1Definition.instance().getResourceType()) ?
						EclipseLink2_1JpaPlatformFactory.SUPPORTED_ACCESS_TYPES :
						GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}
}
