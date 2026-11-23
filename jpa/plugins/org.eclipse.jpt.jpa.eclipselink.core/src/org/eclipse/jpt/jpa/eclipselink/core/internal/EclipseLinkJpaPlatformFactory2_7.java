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
 * EclipseLink 2.7 platform.
 * <p>
 * EclipseLink 2.7 implements JPA 2.2 and, like 2.6, introduces no new
 * <code>eclipselink-orm.xml</code> schema - it continues to use
 * <code>eclipselink_orm_2_5.xsd</code>. EclipseLink ships no JPQL grammar
 * between 2.6 and 4.0, so 2.6 is the most recent grammar that applies here.
 */
public class EclipseLinkJpaPlatformFactory2_7
	extends EclipseLinkJpaPlatformFactory2_5
{
	/**
	 * See <code>org.eclipse.jpt.jpa.eclipselink.core/plugin.xml:org.eclipse.jpt.jpa.core.jpaPlatforms</code>.
	 */
	public static final String ID = "eclipselink2_7"; //$NON-NLS-1$

	/**
	 * Version string for EclipseLink platform version 2.7
	 */
	public static final String VERSION = "2.7";  //$NON-NLS-1$

	/**
	 * zero-argument constructor
	 */
	public EclipseLinkJpaPlatformFactory2_7() {
		super();
	}

	@Override
	public JpaPlatform buildJpaPlatform(JpaPlatform.Config config) {
		return new GenericJpaPlatform(
			config,
			buildJpaVersion(config.getJpaFacetVersion()),
			new EclipseLinkJpaFactory2_7(),
			buildAnnotationProvider(),
			EclipseLinkJpaPlatformProvider2_7.instance(),
			buildJpaVariation(),
			// EclipseLink 2.6 grammar: newest one in the bundled JPQL parser (2.7.3); 4.0/5.0 grammars need a newer parser (#9)
			EclipseLinkJPQLGrammar2_6.instance());
	}

	@Override
	protected JpaPlatform.Version buildJpaVersion(IProjectFacetVersion jpaFacetVersion) {
		return new EclipseLinkJpaPlatformVersion(VERSION, jpaFacetVersion.getVersionString());
	}
}
