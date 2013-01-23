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
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPlatformFactory;
import org.eclipse.jpt.jpa.core.JpaPlatformVariation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar1;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class EclipseLink1_2JpaPlatformFactory
	implements JpaPlatformFactory
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink1_2"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 1.2
	 */
	public static final String VERSION = "1.2";  //$NON-NLS-1$


	/**
	 * zero-argument constructor
	 */
	public EclipseLink1_2JpaPlatformFactory() {
		super();
	}

	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(),
			new EclipseLinkJpaFactory(),
			buildAnnotationProvider(),
			EclipseLink1_2JpaPlatformProvider.instance(),
			buildJpaPlatformVariation(),
			EclipseLinkJPQLGrammar1.instance());
	}

	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance(),
			EclipseLink1_2JpaAnnotationDefinitionProvider.instance());
	}

	protected JpaPlatformVariation buildJpaPlatformVariation() {
		return new JpaPlatformVariation() {
			public Supported getTablePerConcreteClassInheritanceIsSupported() {
				return Supported.YES;
			}
			public boolean isJoinTableOverridable() {
				return false;
			}
			public AccessType[] getSupportedAccessTypes(JptResourceType resourceType) {
				return GENERIC_SUPPORTED_ACCESS_TYPES;
			}
		};
	}

	private JpaPlatform.Version buildJpaVersion() {
		return new EclipseLinkJpaPlatformVersion(VERSION, JpaProject.FACET_VERSION_STRING);
	}
}
