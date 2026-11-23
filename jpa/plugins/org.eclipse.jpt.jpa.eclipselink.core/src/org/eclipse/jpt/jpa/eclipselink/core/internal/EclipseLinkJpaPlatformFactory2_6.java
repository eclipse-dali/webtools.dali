/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.internal.GenericJpaPlatform;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;
import org.eclipse.persistence.jpa.jpql.parser.EclipseLinkJPQLGrammar2_6;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * EclipseLink 2.6 platform.
 * <p>
 * EclipseLink 2.6 implements JPA 2.1 and introduces no new
 * <code>eclipselink-orm.xml</code> schema; it continues to use
 * <code>eclipselink_orm_2_5.xsd</code>. The 2.5 resource models, resource
 * definitions and context model factory therefore apply unchanged, and only
 * the platform version and the JPQL grammar differ from 2.5.
 */
public class EclipseLinkJpaPlatformFactory2_6
	extends EclipseLinkJpaPlatformFactory2_5
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink2_6"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 2.6
	 */
	public static final String VERSION = "2.6";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory2_6() {
		super();
	}

	@Override
	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory2_5(),
			buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider2_6.instance(),
			buildJpaVariation(),
			EclipseLinkJPQLGrammar2_6.instance());
	}

	@Override
	protected JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new EclipseLinkJpaPlatformVersion(VERSION, jpaFacetVersion.getVersionString());
	}
}
